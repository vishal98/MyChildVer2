package ghumover2
import grails.rest.Resource

@Resource
class Subject {
	
	
	
	static hasMany = [grade:Grade,teachers:Teacher]
	Long subjectId
	String subjectName
	static belongsTo = [Grade,Teacher]
	static mapping = {
		id generator: 'increment',name: 'subjectId'
	}
	static constraints = {

	}

}
