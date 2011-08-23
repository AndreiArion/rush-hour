package org.apatrat

class TrafficLogFile (file:java.io.File ) {
	var fileName = file.getName();
	var modDate = file.lastModified();
	var date = new java.util.Date(modDate)
	var fileRef = file;
	var content = getContent(file)	


	
	override def toString():String = date+", " + content// +fileRef+" " + content
	
	def getContent(f: java.io.File ) : String = {
      val source = new org.xml.sax.InputSource(new java.io.FileInputStream(f))
      val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
      val xmlRoot = adapter.loadXML(source, TrafficEventCollector.parser)
      val divList = xmlRoot \\ "div"
      val trafficDiv = divList filter{node => (node \ "@id").text == "bouchons"}
      var kmDiv = divList filter{node => (node \ "@class").text == "info" }
      kmDiv = kmDiv filter{node=> (node\"b").text.contains ("km")}
      var km:String = (kmDiv \"b").text.replaceAll("km","")
      Console println ("\n\n================>"+trafficDiv)      
      km.trim
	}
	
	
}