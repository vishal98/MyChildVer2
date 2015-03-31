package mychild2

import java.sql.Timestamp

class FileAccessMap {
	
	String fileId
	String accessibleToId
	String accessType
	String accessLevel
	String accesStart
	String accessEnd
	String isActive
	String createdByUserId
	Timestamp createdDatetime
	Timestamp lastModifiedDatetime
	String lastModifiedBy

    static constraints = {
    }
}
