package ghumover2
import grails.rest.Resource

@Resource
class Grade {

    static hasMany = [teachers:Teacher , students:Student,subject:Subject,exams:Exam]
    Long gradeId
    int name
	String section
    Integer classTeacherId
	String classTeacherName
 

static mapping ={
	id generator: 'increment',name: 'gradeId'
    }

    static constraints = {

    	classTeacherId(nullable:true)
		classTeacherName(nullable:true)
        gradeId(nullable: true)
        classTeacherId(nullable: true)
        classTeacherName(nullable: true )
    }
}
