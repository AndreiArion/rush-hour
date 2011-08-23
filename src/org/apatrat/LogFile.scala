package org.apatrat

class LogFile (file:java.io.File ) {
	var fileName = file.getName();
	var modDate = file.lastModified();
	var date = new java.util.Date(modDate)
	var fileRef = file;
	var content = getContent(file)	
	var lineNoN = new Array[Boolean](5)
	var lineCont = new Array[String](5)

	
	override def toString():String = lineNoN.mkString("-")+" "+lineCont.mkString("-")+" "+date+" "// +fileRef+" " + content
	
	def getContent(f: java.io.File ) : String = {
      val source = new org.xml.sax.InputSource(new java.io.FileInputStream(f))
      val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
      val xmlRoot = adapter.loadXML(source, EventCollector.parser)
      val divList = xmlRoot \\ "div"
      val trafficDiv = divList filter{node => (node \ "@class").text == "trafic"}
      //Console println ("\n\n================>"+trafficDiv)
      var strCont = trafficDiv.toString();
      if(strCont.contains("img")){    	      	  
    	  strCont.replaceAll("http://www.ratp.fr/informer/picts/v_pivi/pi_picto","img")    	
    	  strCont.replaceAll("http://www.ratp.fr/informer/picts/moteur/ligne","img") 
      }else{
    	  ""
      }
	}
	
	def computeLines()={
		      	if (content.contains("rer_a.gif")) {
		      		lineNoN(0)=true
		      		lineCont(0)=getEventDesc("A")
		      	}
		      	if (content.contains("rer_b.gif")) {
		      		lineNoN(1) = true
		      		lineCont(1)=getEventDesc("B")
		      	}
		      	if (content.contains("rer_c.gif")) {
		      		lineNoN(2) = true
		      		lineCont(2)=getEventDesc("C")		      			
		      	}
		      	if (content.contains("rer_d.gif")) {
		      		lineNoN(3) = true
		      		lineCont(3)=getEventDesc("D")
		      	}
		      	if (content.contains("rer_e.gif")) {
		      		lineNoN(4) = true 
		      		lineCont(4)=getEventDesc("E")
		      	}
	}
	
	def getEventDesc(a:String)={
		var idxStart = content.indexOf("p_rer_"+a.toLowerCase()+".gif'>")
		var start=content.substring(idxStart+15,content.length)
		idxStart=start.indexOf("</img> ")
		start=start.substring(idxStart+7,start.length)
		var idxEnd = start.indexOf("<img style='width: 21px; height: 21px'; alt='RER'")
		if(idxEnd<0){
			idxEnd = start.indexOf("Trafic normal")			
		}
		if(idxEnd>0)
			start.substring(0,idxEnd)
		else start
	}
	
}