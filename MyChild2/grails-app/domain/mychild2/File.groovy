package mychild2

import java.sql.Timestamp;

class File {

	String fileId
	String fileName
	String filePath
	String description
	String accessStart
	String Version
	Date archiveDatetime
	String accessEnd
	String statusCode
	String createdByUserId
	Boolean isActive
	Timestamp createdDatetime
	Timestamp lastModifiedDatetime
	String lastModifiedBy
	
    static constraints = {
    }
}
