package ghumover2

import grails.rest.Resource
import org.grails.databinding.BindingFormat;


@Resource(formats=['json', 'xml'])
class ExamSchedule {

	static belongsTo = [exam:Exam]
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
