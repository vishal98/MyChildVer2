package ghumover2

import java.sql.Timestamp

class TimeTable {

    Subject subject
    String day
    Teacher teacher
    String startTime
    String endTime
    static belongsTo = [grade:Grade]
    static constraints = {
      startTime(nullable: true)
      endTime(nullable: true)
    }
}
