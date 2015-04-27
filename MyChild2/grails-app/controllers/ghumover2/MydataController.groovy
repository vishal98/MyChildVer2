package ghumover2

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

class MydataController {

    def index() { 
		def rest = new RestBuilder()
		def resp = rest.post("https://api.pushbots.com/push/one"){
			header 'x-pushbots-appid', '550e9e371d0ab1de488b4569'
			header 'x-pushbots-secret', 'e68461d7755b0d3733b4b36717aea77d'
	json {
		token ="APA91bFS1Qg5lkFPOn6D6Liqc4pnUWcvTiyyn6XIW8RL5y-10rBt5PVg2XaVY5z7EaCGxAZN959OGpl3CYhK1pQVEm4AY7RD5DHSEz691VwOI8CElsgjnClHB7zfk5ioZ7SWAOlIVRA3"
			 platform ="1"
			  msg="Push one Notification from API  SArath 3/30call"
			   sound="ding"
			   badge="badge"
				payload="JSON"
	  
	}
		
			
		}
		
		System.out.print("resp val : "+resp.json)
		render resp.json as JSON
	
	
		
		//render (view:'/')
	}
}
