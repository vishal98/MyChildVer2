package ghumover2
import grails.rest.Resource

import javax.persistence.Transient

@Resource
class Teacher  extends User  {

	 static belongsTo = Grade
	 static hasMany = [grades:Grade,subject:Subject , timetables:TimeTable]

	 Long teacherId
	 String teacherName
	 String teacherPhoto
	 String teacherEmailId
	 String phoneNo

	 static mapping = {
		  id generator: 'increment',name: 'teacherId'
		  
	}


	void addToGradeSubject(Grade grade , Subject subject)
    {


		new GradeTeacherSubject(grade: grade, teacher: this , subject:subject).save()
		this.addToGrades(grade).save()
		grade.addToTeachers(this).save()
		this.addToSubject(subject).save()

    }
	void removeFromGradeSubject(Grade grade , Subject subject)
	{
		GradeTeacherSubject.findByGradeAndTeacherAndSubject(grade,this,subject).delete()
	}

	def getSubjectsInGrade(grade)
	{
		return GradeTeacherSubject.findAllByGradeAndTeacher(grade,this)
	}


	def getAllGradesAndSubjects()
	{
		return GradeTeacherSubject.findAll("from GradeTeacherSubject as g where g.teacher = ? ",[this])
	}



}
