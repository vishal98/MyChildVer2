package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured

import java.text.SimpleDateFormat;

@Secured(['ROLE_PARENT'])
class GuardianController extends RestfulController
{

	static responseFormats = ['json', 'xml']
	def springSecurityService
	User user
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	GuardianController() {
		super(Guardian)
	}


	def getAccountInfo()
	{
		 def id = params.id.toString()



					 Guardian g = (id.isNumber()) ? Guardian.findById(id) : Guardian.findByUsername(id);
					 def guardian = [:]
					 def output = [:]


					 guardian['username'] = g.username
					 guardian['name'] = g.name
					 guardian['educational_qualification'] = g.educational_qualification
					 guardian['profession'] = g.profession
					 guardian['designation'] = g.designation
					 guardian['mobileNumber'] = g.designation
					 guardian['emailId'] = g.emailId
					 guardian['officeNumber'] = g.officeNumber
					 guardian['numberOfChildren'] = g.getChildren().size()
					 output['accountInfo'] = guardian

								JSON.use('getChildren')
										{    def children = g.getChildren()
											output['children'] = children
											render output as JSON

										}






	}

	 def getAllChildren()
	 {
		 def response = [:]
			 try {
						  def id = params.id
						  def output = [:]
						  Guardian  g =   (id.isNumber()) ? Guardian.findById(id) : Guardian.findByUsername(id);
						  def children = g.getChildren()
						  output['numberOfChildren'] = children.size()
						 JSON.use('getChildren')
						  {
							   output['children'] = children
							  render output as JSON
						  }



				 }
			catch (Exception e)
			{
				response['status'] = "error"
				response['message'] = e
				render response as JSON

			}
	 }


	def getStudentClassEvents()
	       {
              def result = [:]
			   try {



				   Date date = formatter.parse(params.date);
                   Long sid = Long.parseLong(params.studentId)
				   Student student = Student.findByStudentId(sid)


				   def eventList = Event.findAll("from Event as e where (e.calendar_date.calendar_date = :date and e.grade.gradeId = :gradeId ) or (e.calendar_date.calendar_date = :date and e.flag = :flag) order by e.calendar_date.calendar_date ",[date:date ,gradeId:student.grade.gradeId,flag:"SCHOOL"  ]  )


				   result['studentId'] = student.studentId.toString()
				   result['teacherName'] = student.studentName
				   result['eventDate'] = date.format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				   result['no_of_events'] = eventList.size().toString()
				   result['events'] = eventList
			       render result as JSON
			   }
              catch (Exception e)
			  {
				  render e
			  }


	        }



	def getStudentMonthEvents()
	  {
	     def result = [:]
		 try {


			 int month = Integer.parseInt(params.month)
			 int year =  Integer.parseInt(params.year)
			 Long sid = Long.parseLong(params.studentId)
			 Student student = Student.findByStudentId(sid)

			 Date start_date = formatter.parse("01-"+month+"-"+year)
			 Date end_date = formatter.parse(CalendarDate.getTotalDaysInMonth(month,year)+"-"+month+"-"+year)


			 def eventList = Event.findAll("from Event as e where (e.calendar_date.calendar_date between :f_date and :t_date  and e.grade.gradeId = :gradeId)  or  ( e.calendar_date.calendar_date between :f_date and :t_date  and e.flag = :flag) order by e.calendar_date.calendar_date " , [f_date:start_date , t_date:end_date , gradeId:student.grade.gradeId , flag:"SCHOOL" ] )

			 result['studentId'] = student.studentId.toString()
			 result['studentName'] = student.studentName
			 result['month'] = month.toString()
			 result['year'] = year.toString()
			 result['from_date'] = start_date.format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			 result['to_date'] = end_date.format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			 result['no_of_events'] = eventList.size().toString()
			 result['events'] = eventList
			 render result as JSON


		 }
		catch (Exception e)
		{
             render e

		}

	  }





	def getTeacherList()
	   {
		     try {

				 user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
				 Student student = Student.findByStudentId(Long.parseLong(params.studentId))
				 JSON.use('TeacherListForParent'){
				 render student.grade.teachers as JSON
				 }

			 }
			 catch (Exception e)
			 {
				 render e
			 }

	   }


}