package ghumover2

import java.sql.Timestamp

class TimeTable {

    Subject subject
    String day

    String startTime
    String endTime
    static belongsTo = [grade:Grade , teacher:Teacher ]
    static constraints = {
      startTime(nullable: true)
      endTime(nullable: true)
    }
}
