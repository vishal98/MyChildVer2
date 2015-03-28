package ghumover2



class ExamSchedule {

    Exam exam
	ExamSyllabus subjectSyllabus
    Subject subject
    Teacher teacher
    Date startTime
    Date endTime
  

    static constraints = {
        startTime(nullable: true)
        endTime(nullable: true)
    }
}
