package ghumover2

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
@Resource


@Secured(['ROLE_TEACHER','ROLE_PARENT'])
class TimeTableController {

   // static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]



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

}
