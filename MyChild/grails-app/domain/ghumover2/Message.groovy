package ghumover2

import java.sql.Timestamp

class Message {

    
	String code;
	String value;
	String type;
	Timestamp creationDate
	Timestamp updatedDate
	
	static constraints = {
		
		creationDate(nullable:true)
		updatedDate(nullable:true)
	}
}
