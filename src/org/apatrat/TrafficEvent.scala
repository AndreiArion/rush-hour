package org.apatrat

class TrafficEvent (startDateIn:java.util.Date, descIn: String) {
	var startDate = startDateIn
	var desc = descIn
	
	override def toString()={
		var df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
													//May 10 1961 00:00:00 GMT-0600 
													//2009-02-12 15:10:00
			df.format(startDate)+","+ desc+"\n"
	}
	
}