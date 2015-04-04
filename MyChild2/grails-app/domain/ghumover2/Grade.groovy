package ghumover2
import grails.rest.Resource

import java.text.SimpleDateFormat

@Resource
class Grade {

    static hasMany = [students:Student,exams:Exam , events:Event , attendance:Attendance , teachers:Teacher , timetable:TimeTable ]

    Long gradeId
    int name
	String section
    Long classTeacherId

 

static mapping ={
	id generator: 'increment',name: 'gradeId'
    }

    static constraints = {

        classTeacherId(nullable: true)
      

    }


    def addSubjectAndTeacher(Subject subject , Teacher teacher)
      {

          new GradeTeacherSubject(grade: this , teacher:teacher , subject:subject).save()

      }


     def getAttendance(String date)
     {

         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
         Date att_date = formatter.parse(date);
         return  Attendance.findAllByDateAndGrade(att_date,this)
     }



}
