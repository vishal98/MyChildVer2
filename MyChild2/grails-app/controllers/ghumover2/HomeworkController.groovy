package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.rest.client.RestBuilder

import java.sql.Array
import java.text.SimpleDateFormat;


@Secured(['ROLE_TEACHER','ROLE_PARENT'])
class HomeworkController extends RestfulController
{
	static allowedMethods = [saveHomework: "POST"]
	static responseFormats = ['json', 'xml']
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	HomeworkController() {
		super(Homework)
	}

	def getStudentHomeworkByDate()
	{
 
		try{
 
 
 
			def student =  Student.findByStudentId(params.studentId)
			Date date = formatter.parse(params.dateAssigned)
			def grade   =    student.grade
			def output  = [:]
			output['StudentId'] = student.studentId
			output['studentName'] = student.studentName
 
			JSON.use('studentHomework')
					{
						def homeworks = Homework.findAll("from  Homework as h where ((h.grade = ? and h.gradeFlag = 'g') or h.student = ?) and h.dateCreated = ?  order by h.dateCreated desc ", [grade, student,date])
						output['number_of_homeworks'] = homeworks.size()
						output['homeworks'] = homeworks
						render output as JSON
					}
 
		}
		catch (Exception e)
		{
			render e as JSON
		}
 
	}

   def getClassHomework()
   {
			def gradeName = params.gradeId
			def section = params.section
			def grade = Grade.findByNameAndSection(gradeName,section)
			def response = Homework.findAllByGradeAndDateCreated(grade,params.dateAssigned)
			render response as JSON

   }

   def getClassHomeworkBySubject()
   {
	   def gradeName = params.gradeId
	   def section = params.section

	   def subject = params.subject
	   def grade = Grade.findByNameAndSection(gradeName,section)
	   def response = Homework.findAllByGradeAndSubjectAndDateCreated(grade,subject,params.dateAssigned)
	   render response as JSON
   }

	def saveHomework() {
	
		try {

			def gradeFlag = params.gradeFlag

			def grade = Grade.findByNameAndSection(params.grade, params.section)
			def subject = params.subject
			Date date = formatter.parse(params.dueDate);
			Student tempStudent

			def output = [:]
			def data = []

			if (gradeFlag == 's') {
				params.studentList.each { studentId ->
					tempStudent = Student.get(studentId)
					data << new Homework(grade: grade, subject: subject, homework: params.homework, student: tempStudent, message: params.message, dueDate: date, gradeFlag: "s").save(flush: true)

				}
				output['status'] = 'success'
				output['message'] = 'Homework details for ' + data.size() + ' students successfully stored'
				output['data'] = data
				render output as JSON
			} else if (gradeFlag == 'g') {

				data << new Homework(grade: grade, subject: subject, homework: params.homework, message: params.message, dueDate: date, gradeFlag: "g").save(flush: true)
				output['status'] = 'success'
				output['message'] = 'Homework details for class  successfully stored'
				output['data'] = data
				render output as JSON

			} else {
				output = [status: "error", message: "invalid gradeflag '" + gradeFlag + "'", data: "NULL"]
				// render output as JSON
			}
		}
		catch (Exception e) {
			render e
		}


	}







	  def getStudentHomework()
	  
			 {
				  try{



					  def student =  Student.findByStudentId(params.studentId)
					  def grade   =    student.grade
					  def output  = [:]
					  output['StudentId'] = student.studentId
					  output['studentName'] = student.studentName

					 JSON.use('studentHomework')
							 {
								 def homeworks = Homework.findAll("from  Homework as h where (h.grade = ? and h.gradeFlag = 'g') or h.student = ? order by h.dateCreated desc ", [grade, student])
								 output['number_of_homeworks'] = homeworks.size()
								 output['homeworks'] = homeworks
								 render output as JSON
							 }

				  }
				  catch (Exception e)
				  {
					  render e as JSON
				  }

			 }





}