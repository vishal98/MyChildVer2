package ghumover2

import org.grails.databinding.BindingFormat

class Event {

    Long eventId
    @BindingFormat('dd-MM-yyyy')
    Date date
    static belongsTo = [date:CalendarDate , gradeId:Grade ]
    static  mappedBy = [date:'calendar_date']

    String title
    String description
    String startTime
    String endTime

    String flag

   static mapping = {
       id generator: 'increment' , name:'eventId'
       date  sqlType: "DATE"

   }






    static constraints = {

         gradeId(nullable: true)
         flag inList: ["SCHOOL", "GRADE"]
         title(nullable: true)
         description(nullable: true)
         startTime(nullable: true)
         endTime(nullable: true)


    }


}
