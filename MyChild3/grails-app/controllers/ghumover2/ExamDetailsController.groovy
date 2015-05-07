package ghumover2

import grails.converters.JSON

import java.text.SimpleDateFormat

class ExamDetailsController {

    def newExam() {



        def output = [:]
               try{

                   String examName = params.examName
                   String examType = params.examType
                   int grade = Integer.parseInt(params.grade)
                   SchoolClass sc = SchoolClass.findByClassName(grade)
                   def saveExam
                   if(params.grade && params.section)
                   {
                       Grade g =  Grade.findByNameAndSection(grade,params.section)
                       saveExam = new Exam(examName: examName , examType: examType ,grade:grade , schoolclass : grade ).save(flush: true)
                   }
                   else {
                       saveExam = new Exam(examName: examName , examType: examType, schoolclass : grade ).save(flush: true)
                   }

                   if(saveExam)
                   {
                       output['status'] = 'success'
                       output['message'] = 'Exam details successfully saved'
                       output['data'] = saveExam.collect{ ["examId" : it.examId , "examName":it.examName , "examType":it.examType] }
                       render output as JSON
                   }

               }
                   catch(Exception e)
                   {
                       render e as JSON
                   }



    }


    def newExamSyllabus()
    {
        def output = [:]
        try{

            int examId = Integer.parseInt(params.examId)
            int subjectId = Integer.parseInt((params.subjectId))
            String syllabus = params.syllabus
            Exam e = Exam.findByExamId(examId)
            Subject s = Subject.findBySubjectId(subjectId)
            def saveSyllabus = new ExamSyllabus(exam: e , subject:s ,syllabus: syllabus).save(flush: true)
            if(saveSyllabus)
             {
                 output['satatus'] = 'success'
                 output['message'] = 'Exam Syllabus saved successfully'
                 output['data'] = saveSyllabus.collect{ ['examId':it.exam?.examId , examName: it.exam?.examName , "Subject" : it.subject.subjectName ,"syllabus" : params.syllabus ]  }
                 render output as JSON
             }
            render params



        }
        catch(Exception e)
        {
            render e as JSON

        }

    }

    def newExamSchedule()
    {

        def output = [:]
        try{

            int examId = Integer.parseInt(params.examId)
            int syllabusId = Integer.parseInt(params.syllabusId)
            int subjectId = Integer.parseInt(params.subjectId)
            int teacherId = Integer.parseInt(params.teacherId)
            SimpleDateFormat ft =  new SimpleDateFormat ("dd-MM-yyyy hh:mm");

            Date startTime = ft.parse(params.startTime);

            Date endTime = ft.parse(params.endTime);



            Exam e = Exam.findByExamId(examId)
            ExamSyllabus es = ExamSyllabus.findById(syllabusId)
            Subject sub = Subject.findBySubjectId(subjectId)
            Teacher t = Teacher.findById(teacherId)

            def saveSchedule = new ExamSchedule(exam: e , subjectSyllabus: es , subject: sub , teacher: t , startTime: startTime, endTime : endTime ).save(flush: true)
            if(saveSchedule)
            {
                output['satatus'] = 'success'
                output['message'] = 'Exam Schedule saved successfully'
                output['data'] = saveSchedule.collect{ ['examId':it.exam?.examId , examName: it.exam?.examName , "Subject" : it.subject.subjectName ,"teacher" : it.teacher?.teacherName ,"examDate": it.startTime.format('dd-MM-yyyy')  ,  "startTime" :it.startTime.format("KK:mm a")  , "endTime" :  it.endTime.format("KK:mm a") ]  }
                render output as JSON
            }





        }
        catch(Exception e)
        {
               render e as JSON
        }
    }

    def newExamResult()

    {
        def output = [:]
        try{
            int examId = Integer.parseInt(params.examId)
            int studentId =  Integer.parseInt(params.studentId)
            int marks = Integer.parseInt(params.marks)
            int maxMarks = Integer.parseInt(params.maxMarks)
            int subjectId = Integer.parseInt(params.subjectId)

            Exam exam = Exam.findByExamId(examId)
            Student student = Student.findByStudentId(studentId)
            Subject sub = Subject.findBySubjectId(subjectId)


            def saveResult = new ExamResult(exam: exam , student: student , subject: sub , marks: marks , maxMarks: maxMarks )

            if(saveResult)
            {
                output['status'] = 'success'
                output['message'] = 'Result stored successfully'
                output['data'] = saveResult.collect(){ [ student: it.student?.studentName , exam : it.exam?.examName , maxMarks: it.maxMarks , marks: it.marks]}
            }

            render output as JSON





        }
        catch (Exception e)
        {
             render e as JSON
        }
    }


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
                         result : (it.results.findAll { it.student.studentId == studentId  }) ? (it.results.findAll { it.student.studentId == studentId  }).collect() { [ subjectId : it.subject?.subjectId.toString() , subjectName: it.subject?.subjectName , marks: it.marks.toString() , maxMarks: it.maxMarks.toString()] }  : "Not Available"
                         
                 ]
                 
                 
             }

            def output = [:]
            output['exam'] = res
            render output as JSON


           }
        catch(Exception e)
        {
            render e as JSON

        }

    }




}
