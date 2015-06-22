package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import groovy.json.JsonSlurper



class StudentDetailsController extends RestfulController  {
    static responseFormats = ['json', 'xml']
    StudentDetailsController() {
        super(Student)
    }

	
	private static final String ROLE_TEACHER = 'ROLE_TEACHER'
	private static final String ROLE_PARENT = 'ROLE_PARENT'
	private static final String ROLE_ADMIN = 'ROLE_ADMIN'

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