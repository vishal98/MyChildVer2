package ghumover2
import grails.rest.Resource

@Resource
class Grade {

	    static hasMany = [teachers:Teacher , students:Student,subject:Subject,exams:Exam]
	
	    Long gradeId
	    int name
		String section
	    Teacher classTeacher 
		
	 

static mapping ={
	id generator: 'increment',name: 'gradeId'
    }

    static constraints = {

    	classTeacher(nullable:true)
       // gradeId(nullable: true)
  
    }
}
