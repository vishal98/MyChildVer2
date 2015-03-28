package ghumover2

class Exam {

    String examId
    String examName
    String examType
	Grade grade
	ExamSchedule examSchedule
	
	
     
    static constraints = {
		examSchedule(nullable:true)
		examType(nullable:true)
    }
}
