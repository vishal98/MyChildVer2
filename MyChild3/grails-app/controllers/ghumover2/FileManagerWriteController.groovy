package ghumover2

import grails.plugin.aws.s3.S3FileUpload;

class FileManagerWriteController {

	S3FileUpload st = new S3FileUpload()
	def fg = st.acl
	
	public def upload(){
	def s3file = new MyChildFile("testupload.txt").s3upload {
    path "Test1/"
}
	}
 
	def saveClassFiles(){
		
	}
}
