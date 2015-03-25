package ghumover2
import grails.rest.Resource

@Resource
class Subject {
     
    Integer subjectId
	String subjectName
	//int grade
	//String section



    static constraints = {
        id generator: 'increment',name: 'subjectId'
    }
}
