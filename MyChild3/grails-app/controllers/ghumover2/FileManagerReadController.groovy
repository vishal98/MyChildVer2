package ghumover2

import grails.converters.JSON
import sun.misc.BASE64Decoder

class FileManagerReadController {

    def index() { }
	
	def readImage(){
	
		FileManager [] fileManagerList=new FileManager()
		FileManager fileManager=new FileManager()
		fileManager.setFileGroupType("Album")
		fileManager.setFileGroupName("Children Day Competition")
		fileManager.setAlbumCoverUrl("http://www.comments99.com/c/childrens_day/childrens_day_050.jpg")
		fileManager.setPostedDate(Calendar.getInstance().getTime())
		fileManager.setFileCount("2")
		
		
	
		MyChildFile file =new MyChildFile()
		file.setFileId("123")
		file.setFileName("test")
		file.setFilePath("http://cceffect.org/wp-content/uploads/2014/09/school1.jpg")
	    file.setDescription("schoolImage")
		
		
		
		MyChildFile file2 =new MyChildFile()
		file2.setFileId("1243")
		file2.setFileName("test2")
		file2.setFilePath("http://www1.pgcps.org/uploadedImages/Region_4_Schools/Elementary/Kenilworth/01school.jpg")
		file2.setDescription("schoolImage2")
		
		fileManager.addToFiles(file)
		fileManager.addToFiles(file2)
		
		fileManagerList[0]=fileManager
		
		render  fileManagerList as JSON
	}

	
}