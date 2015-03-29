package ghumover2

import org.grails.databinding.BindingFormat;



class ExamSchedule {

	Exam exam
	ExamSyllabus subjectSyllabus
   Subject subject
   Teacher teacher
   @BindingFormat("dd-MM-yyyy HH:mm")
   Date startTime
   @BindingFormat("dd-MM-yyyy HH:mm")
   Date endTime
 

   static constraints = {
	   startTime(nullable: true)
	   endTime(nullable: true)
   }
}
