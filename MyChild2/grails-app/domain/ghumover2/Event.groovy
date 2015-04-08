package ghumover2

import org.grails.databinding.BindingFormat


class Event {

    Long eventId
    static belongsTo = [calendar_date:CalendarDate , grade:Grade ]
    String title
    String description
    String startTime
    String endTime

    String flag

   static mapping = {
       id generator: 'increment' , name:'eventId'
       date sqlType: 'DATE'

   }






    static constraints = {

      
         flag inList: ["SCHOOL", "GRADE"]
         title(nullable: true)
         description(nullable: true)
         startTime(nullable: true)
         endTime(nullable: true)


    }


}
