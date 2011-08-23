package org.apatrat

class Event (startDateIn:java.util.Date,endDateIn:java.util.Date, descIn: String, lineIdIn: String) {
	var startDate = startDateIn
	var endDate = endDateIn
	var desc = "<br/><br/><br/>" + descIn.replaceAll("'","&#145;").replaceAll("\n","").
		replaceAll("http://www.ratp.fr/informer/picts/v_pivi/pi_picto","img").replaceAll("/informer/picts/v_pivi/pi_picto","img").
		replaceAll("/informer/picts/moteur/ligne","img")
	var lineId = dec(lineIdIn)
	var color= decColor(lineIdIn);
	
	override def toString()={
		var df = new java.text.SimpleDateFormat("MMM d yyyy HH:mm:ss 'GMT'Z");
													//May 10 1961 00:00:00 GMT-0600 
		"{\n"+
			"\t'start':'"+ df.format(startDate)+"',\n"+
			"\t'end':'"+df.format(endDate) + "',\n"+
			"\t'title':' ',\n"+ 
			"\t'description':'"+desc+"',\n"+	
			"\t'image':'"+"img/p_rer_"+lineId+".gif',\n"+	
			"\t'color':'"+color+"',\n"+				
			"\t'icon':'"+"img/p_rer_"+lineId+".gif',\n"+	
		"\t'isDuration':true\n}\n"
	}
	
	def dec(id:String):String={
		if(id.equals("0")){			
			return "a"
		}
		if(id.equals("1")){
			return "b"
		}
		if(id.equals("2")){
			return "c"
		}
		if(id.equals("3")){
			return "d"
		}
		if(id.equals("4")){
			return "e"
		}
		""
	}
	
	def decColor(id:String):String={
		if(id.equals("0")){			
			return "red"
		}
		if(id.equals("1")){
			return "blue"
		}
		if(id.equals("2")){
			return "yellow"
		}
		if(id.equals("3")){
			return "green"
		}
		if(id.equals("4")){
			return "magenta"
		}
		""
	}
}