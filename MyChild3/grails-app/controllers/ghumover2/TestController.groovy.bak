package ghumover2

import grails.converters.JSON

import java.text.SimpleDateFormat

class TestController {


    def index() {

          JSON.use('TeachersSubjects'){
         //  render Teacher.findByUsername(params.id).getAllGradesAndSubjects() as JSON


       render Teacher.findByUsername(params.id).getSubjectsInGrade(Grade.findByNameAndSection(Integer.parseInt(params.g),params.s)) as JSON
          }
    }

   def allEvents()
      {

          SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

          Date date = formatter.parse("12-04-2015");
          CalendarDate dt =  CalendarDate.findByCalendar_date(date)



          render Event.findAllByDate(dt.calendar_date) as JSON
      }

    def allGradeEvents()
      {
          SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

          Date date = formatter.parse("12-04-2015");
          CalendarDate dt =  CalendarDate.findByCalendar_date(date)
          render Grade.findByNameAndSection(5,"A").events as JSON
      }

}
