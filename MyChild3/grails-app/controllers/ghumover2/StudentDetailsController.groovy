package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import groovy.json.JsonSlurper



class StudentDetailsController extends RestfulController  {
    static responseFormats = ['json', 'xml']
	def springSecurityService
    StudentDetailsController() {
        super(Student)
    }

	static allowedMethods = [forgetPassword: "POST"]
	private static final String ROLE_TEACHER = 'ROLE_TEACHER'
	private static final String ROLE_PARENT = 'ROLE_PARENT'
	private static final String ROLE_ADMIN = 'ROLE_ADMIN'
	
	def forgetPassowrd(){
		String emailId = params.emailId
	//	String newPassword = params.password_new
		def message
		User user=User.findByUsername(emailId)
		if(user){
			user.password="1234"
			user.save()
			//sendMail
			message="password sent to registered mail id"
		}else{
		
		message="email id is not valid"
		}
		
		render message
	}
	
	
	def updatePassword() {
		def result = [:]
		
		def message ="error"
		
		println "test this {{params.password}}"
		
		String password = params.password
		String newPassword = params.password_new
		String newPassword2 = params.password_new2
		
		if (!password || !newPassword || !newPassword2 || newPassword != newPassword2) {
			message = 'Please enter your current password and a valid new password'
		
			result['status'] = 'error'
			result['message']=message
		   render result
		   
		}
		
	
	User user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
	//	if (!springSecurityService.passwordEncoder.isPasswordValid(user.password, password, null /*salt*/)) {
		//   message = 'Current password is incorrect'
		   
		  // render message
		//}
	 
		//if (passwordEncoder.isPasswordValid(user.password, newPassword, null /*salt*/)) {
		  // message = 'Please choose a different password from your current one'
		  // render message
	//	}
	 
		user.password = newPassword
		user.passwordExpired = false
		user.save() // if you have password constraints check them here
	     message="password changed successfully"
		result['status'] = 'error'
			result['message']=message
		   render result
	 }

    def getStudentsOfClass()
    {

        def output = [:]
        try {

            Grade grade = Grade.findByNameAndSection(Integer.parseInt(params.grade),params.section)
            JSON.use('studentDetail')
                    {
                        output['total_no_of_students'] = grade.students.size()
                        output['students'] = grade.students
                        render output as JSON
                    }

        }
        catch (Exception e)
        {

            render e

        }

    }


    def studentSave()
    {
        try{



               if(params.father)
               {
                    params.father['username'] = params.father['emailId'];
                    params.father['password'] = '123';
               }
              if(params.mother)
              {
                   params.mother['username'] = params.mother['emailId'];
                  params.mother['password'] = '123';

              }
             if(params.local_guardian)
             {
                params.local_guardian['username'] = params.local_guardian['emailId']
                params.local_guardian['password'] = '123';
             }

                Grade grd = Grade.findByGradeId(Integer.parseInt(params.student['gradeId']));

               Student student = new Student(params.student)
               Address address = new Address(params.address).save()
               student.present_address = address
               student.grade = grd;
               student.save()

                Guardian father = new Guardian(params.father).save()
                student.setAsFather(father)
				def rol=Role.findByAuthority(ROLE_PARENT)
				new UserRole(user: father, role: rol).save()
                  

                Guardian mother = new Guardian(params.mother).save()
                student.setAsMother(mother)
			
				new UserRole(user: mother, role: rol).save()
				
				String father_tags  = father.tags;
				String mother_tags  = mother.tags;
				
				if(father_tags == null){
					father_tags = grd.gradetags +",\"G\",\"S-"+student.studentId+"\""
				}else{
					father_tags = father.tags+",\"s-"+student.studentId+"\""
				}
				if(mother_tags == null){
					mother_tags = grd.gradetags +",\"G\",\"S-"+student.studentId+"\""
				}else{
					mother_tags = mother.tags+",\"s-"+student.studentId+"\""
				}
				println "------------------------------------ "+1
				Guardian.executeUpdate("Update Guardian set tags = '"+mother_tags+"' where username ='"+mother.username+"'")
	
				Guardian.executeUpdate("Update Guardian set tags = '"+father_tags+"' where username ='"+father.username+"'")
				println "--"

                def output = [:]
                output['status'] = 'success'
                output['data'] =  s
                render output as JSON



        }
        catch (Exception e)
        {
              render e as JSON

        }

    }






}