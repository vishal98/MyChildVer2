package ghumover2

import grails.converters.JSON

import java.text.SimpleDateFormat
import java.util.List
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_TEACHER'])
class TeacherController {
	def springSecurityService
	User user
	static allowedMethods = [sendMailToParents	: "POST"]


	def getGrade (){
		def article=new Grade()
		def articleList=article.list()
		println articleList


		JSON.use('thin') { render articleList as JSON }
	}

	def getHomeWork (){
		def article=new Homework()
		def articleList=article.list()
		println articleList


		JSON.use('homework') { render articleList as JSON }
	}

	def getTeacherDetails (){
		def article=new Teacher()
		String grade= params.userId
 
		def trek=article.findAllWhere(username:grade).first()

		JSON.use('teacherC') { render trek as JSON }
	}

	def getMsg (){
		def msgType=new Message()

		def trek=msgType.findAllWhere(type:"msg")

		JSON.use('msg') { render trek as JSON }
	}


	def getSubject (){



		def output = [:]
		def subjects = [:]
		user  =   springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
		Teacher t = Teacher.findByUsername(user.username)
		Grade grade = Grade.findByNameAndSection(Integer.parseInt(params.grade),params.section)


		JSON.use('TeachersSubjects'){
		output['teacherId'] = t.id.toString()
		output['username'] = user.username
		output['grade'] = grade.name
		output['section'] = grade.section
		output['subjects'] = t.getSubjectsInGrade(grade)
		render output as JSON
		}

	}

	def getStudentList (){
		def article=new Student()
		Long grade= Long.parseLong(params.gradeId)

		def trek=article.findAllWhere('grade.gradeId':grade)
		//render trek as JSON


		JSON.use('student') { render trek as JSON }



	}

	def getStudentListByGradeSection()
	{
		def article=new Student()
		int grade=  Integer.parseInt(params.grade)
		String section = params.section
		def trek=Student.findAll("from Student as s where s.grade.name = ? and s.grade.section = ? ",[grade,section])
		//render trek as JSON


		JSON.use('student') { render trek as JSON }

	}


	def sendMessage(){
		/*	//JSON Object is not bound to params it is bound to rehquest in POST/PUT
		 def jsonObj = request.JSON
		 def catalogParams = [] as List
		 jsonObj.student.each{
		 catalogParams << new Student(it)
		 }
		 //Set the domain back to the jsonObj
		 jsonObj.parametros = catalogParams
		 //Bind to catalog
		 def stud = new Student(jsonObj)
		 //Synonymous to new Catalog(params) but here you cannot use params.
		 //Save
		 if (!stud.save(flush: true)){
		 stud.errors.each {
		 println it
		 }
		 }
		 render stud
		 }*/

		def jsonObj = request.JSON
		def stud
		for (int i = 0; i < jsonObj.size(); i++) {
			stud = new Homework(jsonObj[i])


			if (!stud.save(flush: true)){
				stud.errors.each {
					println it
					render failure as JSON
				}
			}
		}

		render stud as JSON
	}

	def getTeacherEvents()
	    {

			try {
                def output = []
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date date = formatter.parse(params.date);
				user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
			    Teacher t = Teacher.findByUsername(user.username)

				render t.grades.events.date.find { it == date } as JSON


			}
			catch (NullPointerException ne)
			{
                 render(ne)
			}
			catch (Exception e)
			{
                render e
			}



		}



	def getAllSubjectsInAllGrades()
	        {
				def output = [:]
				def subjects = [:]
				user  =   springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
				Teacher t = Teacher.findByUsername(user.username)
				JSON.use('TeachersSubjects')
						{
				output['teacherId'] = user.id.toString()
				output['username'] = user.username
				output['gradesAndSubjects'] = t.getAllGradesAndSubjects()
				render output as JSON
						}
			}


	    def sendMailToParents()
		  {


			  try {

				  String message  = params.message
				  int gradeId = Integer.parseInt(params.grade)
				  String section = params.section
                  String title = params.title
				  Conversation tempConv
				  user  =   springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
				  Teacher t = Teacher.findByUsername(user.username)
                  Grade grade = Grade.findByNameAndSection(gradeId,section)
                  def output = [:]
				  def data = [:]
				  grade.students.each {

					  if(it.getFather()!= null){
						  tempConv = new Conversation(fromId: it.getFather().username, toId:t.username , title: title , inTrash: false , isRead: false , toDate: new Date())
								  .addToMessages(new Message(messageText:message , messageTime: new Date() , fromId: t.teacherName , toId: it.getFather()?.name))
								  .save()
						                          it.getFather().addToConversations(tempConv).save()
                                                  t.addToConversations(tempConv).save()

					  }

					  if(it.getMother()!= null)
					   {
						   tempConv = new Conversation(fromId: t.username , toId: it.getMother()?.username , title: title , inTrash: false , isRead: false , toDate: new Date())
								   .addToMessages(new Message(messageText:message , messageTime: new Date() , fromId: t.teacherName , toId: it.getMother()?.name))
								   .save()
						   it.getMother().addToConversations(tempConv).save()
						   t.addToConversations(tempConv).save()

					   }
					  
					  if(it.getLocalGuardian()!= null)
					   {
						  tempConv =  new Conversation(fromId: t.username , toId: it.getLocalGuardian()?.username , title: title , inTrash: false , isRead: false , toDate: new Date())
								   .addToMessages(new Message(messageText:message , messageTime: new Date() , fromId: t.teacherName , toId: it.getLocalGuardian()?.name))
								   .save()
						   it.getLocalGuardian().addToConversations(tempConv).save()
						   t.addToConversations(tempConv).save()
					   }
				  }


				 data['title'] = title
				 data['message'] = message

                 output['status'] = "success"
				 output['message'] = "Message successfully sent to parents of "+grade.name +" "+section+" students"
                 output['data'] = data

				 render output as JSON



			  }
			  catch (Exception e)
			  {
				  render e
			  }




		  }






}


