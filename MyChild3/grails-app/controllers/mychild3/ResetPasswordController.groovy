package mychild3

import ghumover2.User

class ResetPasswordController {

	
    def index() { }
	def springSecurityService
	
	def resetPassword(){
		
		def usert=new User()
		def user=usert.findByUsername("gg")
		def pasword =user.getPassword()
	
		def password2 =springSecurityService.encodePassword("newpassword")
		 password2.equalsIgnoreCase("password")
		 springSecurityService.reauthenticate("user", "new pwd")
		 
		 
	
		}

	
	}
