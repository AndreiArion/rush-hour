package org.apatrat;

object EventCollector {
	val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
    val parser = parserFactory.newSAXParser()
	var curEventArray = new Array[Event](5)
	var eventsList = List[Event]();
	val inputDir = "/home/andrei/Ratp/timeline/ex/."
	val outputFile = "/home/andrei/Ratp/timeline/local_data.js"
	val fileContentPrefix="var timeline_data = {  // save as a global variable\n\t/*'dateTimeFormat': 'iso8601',*/\n\t"+
	"/*'wikiURL': \"http://simile.mit.edu/shelf/\",\n\t'wikiSection': \"Simile Cubism Timeline\",*/\n"+
	"'events' : [\n\t"
	val fileContentPostfix="\n]\n}"
			
	
  def main(args: Array[String]): Unit = {
    val files = (new java.io.File(inputDir)).listFiles
    //val fileNames = files.foreach(println);

    var fileList = List[LogFile]();

    for (file <- files) {
      fileList = new LogFile(file) :: fileList;
      //Console.println(file.getName)
    }

    //Console println fileList
    fileList = fileList.sortWith((s, t) =>
      s.modDate < t.modDate)
    fileList.foreach(s=>s.computeLines)
    //Console println fileList.mkString("\n=====>")
    computeEvents(fileList)
    eventsList = eventsList.sortWith((s, t) =>
      s.startDate.compareTo(t.endDate)<0)
    val output=fileContentPrefix+eventsList.mkString(" ,\n")+fileContentPostfix
    Console println output
    var out= new java.io.FileOutputStream(outputFile); // declare a file output object
    var p= new java.io.PrintStream( out ); // declare a print stream object
    p.println(output)
    p.close()   
  }
	
	
	def computeEvents(fileList:List[LogFile])={
	  	var events=new Array[Event](5)
	  	
		for(logFile<-fileList){
			Console println "2-->"+logFile.fileName
			var lineId = logFile.lineNoN
			for(i <- 0 to 4){
				if(lineId(i)){
					//event
					var ev = events(i)
					if(ev!=null){
						if(ev.desc.equals(logFile.content)){
							ev.endDate = logFile.date
						}else{
							//Console println "/////New message"
							events(i).endDate=logFile.date
							//eventsList = events(i)::eventsList
							//events(i)=new Event(logFile.date,logFile.date,logFile.content,i.toString)							
						}
					}else{
						events(i)=new Event(logFile.date,logFile.date,logFile.content,i.toString)
					}					
				}else{
					//traffic normal
					if(events(i)!=null){
						events(i).endDate=logFile.date
						eventsList = events(i)::eventsList
						events(i)=null
					}
				}
				
				
			}
			
			
		}
	  	
	  	events.foreach(s=>if(s!=null) eventsList = s::eventsList)
		
	}
}
