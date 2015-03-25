package ghumover2

class Exam {

    Long examId
    String examName
    String examType
	Grade grade
	
	static hasMany = [ examSchedule :ExamSchedule ,examSyllabus:ExamSyllabus]
	
	static mapping ={
		id generator: 'increment',name: 'examId'
		}
	
	
    static constraints = {
		examType(nullable:true)
		grade(nullable:true)
		
		
		
		
    }
}
