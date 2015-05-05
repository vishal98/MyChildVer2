package ghumover2

import grails.rest.Resource

@Resource(formats=['json', 'xml'])
class ExamSyllabus {

	
	static belongsTo = [exam:Exam]
   // Exam exam
    Subject subject
    String syllabus

    static constraints = {
    }
}
