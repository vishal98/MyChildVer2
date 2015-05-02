package ghumover2

import grails.converters.JSON

class ExamDetailsController {

    def index() { }
	
	def studentResult()
	{

		try{

			 Long studentId = Long.parseLong(params.studentId)
			 Student student = Student.get(studentId)
			 SchoolClass sc = SchoolClass.findByClassName(student.grade.name)

			 def exams = Exam.findAllByGradeOrSchoolclass(student.grade,sc)
			 def res = exams.collect(){
				 [
						 studentId: studentId ,
						 studentName: student.studentName ,
						 examId : it.examId.toString() ,
						 examName : it.examName ,
						 examType : it.examType ,
						 class : it.schoolclass?.className.toString() ,
						 grade : [gradeId : it.grade?.gradeId.toString() , gradeName: it.grade?.name , section : it.grade?.section] ,
						 examSchedule : it.examSubjectSchedule.collect()    { ExamSchedule es -> [ subject:[ subjectId:  es.subject?.subjectId.toString() ,
																											subjectName: es.subject?.subjectName ] ,
																								  syllabus : [ id:es.subjectSyllabus?.id.toString() , syllabus: es.subjectSyllabus.syllabus] ,
																								  examDate : es.startTime.format('dd-MM-yyyy') ,
																								  startTime : es.startTime.format("KK:mm a") ,
																								  endTime: es.endTime.format("KK:mm a")]
						 } ,
						 result : (it.results.find { it.student.studentId == studentId  }) ? (it.results.find { it.student.studentId == studentId  }).collect() { [ subjectId : it.subject?.subjectId.toString() , subjectName: it.subject?.subjectName , marks: it.marks.toString() , maxMarks: it.maxMarks.toString()] }  : "Not Available"
						 
				 ]
				 
				 
			 }

			render res as JSON


		   }
		catch(Exception e)
		{
			render e as JSON

		}

	}
}
