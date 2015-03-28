package ghumover2

class Exam {

    String examId
    String examName
    String examType
	Grade grade
	
	
	static hasMany = [examSubjectSchedule:ExamSchedule ]
	
	
     
    static constraints = {
		
		grade(nullable:true)
		examType(nullable:true)
    }
}
