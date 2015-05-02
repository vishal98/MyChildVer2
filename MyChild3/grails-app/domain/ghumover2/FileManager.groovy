package ghumover2

import ghumover2.MyChildFile;
import ghumover2.User;

class FileManager {

	String fileGroupType//school album,personal files
	String fileGroupName
	User user
	static hasMany = [files:MyChildFile]
	
    static constraints = {
    }
}
