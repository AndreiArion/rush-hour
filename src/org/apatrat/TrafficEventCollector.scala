package org.apatrat;

object TrafficEventCollector {
	val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
    val parser = parserFactory.newSAXParser()
	var curEventArray = new Array[Event](5)
	var eventsList = List[TrafficEvent]();
	val inputDir = "/home/andrei/Ratp/timeline/traffic/."
	val outputFile = "/home/andrei/Ratp/timeline/data.txt"
	val fileContentPrefix=""
	val fileContentPostfix=""
			
	
  def main(args: Array[String]): Unit = {
    val files = (new java.io.File(inputDir)).listFiles
    //val fileNames = files.foreach(println);

    var fileList = List[TrafficLogFile]();

    for (file <- files) {
      fileList = new TrafficLogFile(file) :: fileList;
    }

    //Console println fileList
    fileList = fileList.sortWith((s, t) =>
      s.modDate < t.modDate)
    //fileList.foreach(s=>s.computeLines)
    //Console println fileList.mkString("\n=====>")
    computeEvents(fileList)
    eventsList = eventsList.sortWith((s, t) =>
      s.startDate.compareTo(t.startDate)<0)
    val output=fileContentPrefix+eventsList.mkString("")+fileContentPostfix
    Console println output
    var out= new java.io.FileOutputStream(outputFile); // declare a file output object
    var p= new java.io.PrintStream( out ); // declare a print stream object
    p.println(output)
    p.close()   
  }
	
	
	def computeEvents(fileList:List[TrafficLogFile])={	  	
		for(logFile<-fileList){
				val tr=new TrafficEvent(logFile.date,logFile.content)
				eventsList = tr :: eventsList						
			
		}		
	}
}
