package ghumover2
import grails.rest.Resource

@Resource
class Subject {
    
	
	
	static hasMany = [grade:Grade,teachers:Teacher]
    Integer subjectId
	String subjectName
	//int grade
	//String section
	static belongsTo = [Grade,Teacher]
    static constraints = {
		id generator: 'increment',name: 'subjectId'
    }
}
