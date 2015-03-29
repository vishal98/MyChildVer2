package ghumover2

import grails.converters.JSON
import java.util.List
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_TEACHER'])
class TeacherController {
	def springSecurityService
	User user
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



		output['teacherId'] = user.id.toString()
		output['username'] = user.username
		output['grade'] = grade.name
		output['section'] = grade.section
		output['subjects'] = grade.subject.toList()
		render output as JSON

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
}


