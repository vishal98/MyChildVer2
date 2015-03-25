package ghumover2
import grails.rest.Resource

@Resource
class Teacher  extends User  {

     static belongsTo = Grade
     static hasMany = [grades:Grade,subject:Subject ]

     Long teacherId
	 String teacherName
	 String teacherPhoto
     String teacherEmailId
     String phoneNo

     static constraints = {
          id generator: 'increment',name: 'teacherId'
          
    }
}
