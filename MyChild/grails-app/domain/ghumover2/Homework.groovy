package ghumover2

import org.grails.databinding.BindingFormat

class Homework 
{
	Integer homeworkId
	Grade grade
	String subject
	@BindingFormat('dd-MM-yyyy')
	Date dueDate
	String homework
	String section
	@BindingFormat('dd-MM-yyyy')
	Date dateCreated
	Student student
	String message
	String gradeFlag
	static namedQueries = {
		todaysPosts {
		   def now = new Date().clearTime()
		   between('dateCreated', now, now+1)
		 
		}
	}
	static constraints = {
		student(nullable:true)
		grade(nullable:true)
		homeworkId(nullable: true)
		grade(nullable: true)
		subject(nullable: true)
		dueDate(nullable: true)
		homework(nullable: true)
		section(nullable: true)
		student(nullable: true)
		message(nullable: true)
		gradeFlag(nullable: true)



		}
	
	
}
