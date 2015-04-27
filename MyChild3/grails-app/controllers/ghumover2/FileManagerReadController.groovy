package ghumover2

import sun.misc.BASE64Decoder

class FileManagerReadController {

    def index() { }
	
	def readImage(){
		def file = params.file.toString().substring((params.file.toString().indexOf(",") + 1), params.file.toString().size())
		byte[] decodedBytes = new BASE64Decoder().decodeBuffer(file)
		def image = new File("testimage.jpg")
		image.setBytes(decodedBytes)
	}

	
}