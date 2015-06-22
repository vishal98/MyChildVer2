package ghumover2

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_TEACHER','ROLE_PARENT'])
class TimeTableDetailsController {

    // static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    User user


    def getWeekTimetable()
    {

        def gradeName = params.gradeId
        def response = [:]
        def section = params.section
        def grade = Grade.findByNameAndSection(gradeName,section)
        def timetable = TimeTable.findAllByGrade(grade)
        def days = TimeTable.executeQuery("select distinct a.day from TimeTable a where a.grade = ? " , [grade])
        JSON.use('getTimeTable')
                {
                    days.each {
                        response[it] = TimeTable.findAllByGradeAndDay(grade,it)
                    }

                    render response as JSON
                }



    }


    def getDayTimeTable()
    {

        def day = params.day
        def section = params.section
        JSON.use('getTimeTable')
                {
                    def grade = Grade.findByNameAndSection(params.gradeId, params.section)
                    def result = TimeTable.findAllByGradeAndDay(grade, day)
                    render result as JSON
                }


    }







    def getTeacherWeekTimetable()
    {
        def output = [:]
        try {

            user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
            Teacher teacher = Teacher.findByUsername(user.username)
            JSON.use('teacherWeekTT')
                    {
                        output['teacherId'] = teacher.id.toString()
                        output['teacherName'] = teacher.teacherName
                        output['timeTable'] = teacher.timetables
                        render output as JSON
                    }
        }
        catch (Exception e)
        {
            render e
        }
    }





    def getTeacherDayTimetable()
    {
        def output = [:]
        try {
            user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() : null
            Teacher teacher = Teacher.findByUsername(user.username)
            String day = params.day
            output['teacherId'] = teacher.id.toString()
            output['teacherName'] = teacher.teacherName
            output['timeTable'] =  TimeTable.findAllByTeacherAndDay(teacher,day)
            render output as JSON

        }
        catch (Exception e)
        {
            render e
        }

    }



  def getclassTimetableList()
  {

      def classTT = [:]
      def output = new ArrayList()
      def timetables = new ArrayList()
      def temp=[:]
      Grade grade
      try{
          def days = TimeTable.executeQuery("select distinct a.day from TimeTable a ")

          Grade.findAll().each {

              grade = it
              classTT['gradeId'] = it.gradeId.toString()
              classTT['gradeName'] = it.name.toString()
              classTT['section'] = it.section


              days.each {
                  temp['day'] = it
                  temp['hours'] = TimeTable.findAllByGradeAndDay(grade,it)
                  timetables.push(temp)
                  temp = [:]

              }
              classTT['timetables'] = timetables
              output.push(classTT)
              classTT = [:]
              timetables = new ArrayList()
              temp = [:]






          }
          render output as JSON



      }
      catch(Exception e)
      {
          render e as JSON
      }
  }



      def saveTimeTable()
      {
          try{

              Teacher teacher;
              Grade grade = Grade.get(Integer.parseInt(params.gradeId))
              String day = params.day
              Subject subject;
              int subjectId;
              int teacherId;

              def timetables = params.timetables


              Subject interval = Subject.findOrSaveWhere(subjectName: "Interval");
             // render interval as JSON
              timetables.each{

                  subjectId = Integer.parseInt(it.subject);
                  teacherId = Integer.parseInt(it.teacherId)

                  subject = (Subject.get(subjectId)) ? Subject.get(subjectId) : interval;

                  teacher = (teacherId == 0)?  null : Teacher.get(teacherId);
                  if(teacher)
                  {
                      new TimeTable(day: day , grade: grade ,teacher: teacher , subject: subject , startTime:it.start , endTime: it.end).save()
                  }
                  else {
                      new TimeTable(day: day , grade: grade  , subject: subject , startTime:it.start , endTime: it.end).save()
                  }

              }
               render "OK"



          }
          catch (Exception e)
          {
              render e

          }

      }







}
