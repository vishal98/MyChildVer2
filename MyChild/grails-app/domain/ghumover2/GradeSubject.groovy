package ghumover2
import grails.rest.Resource

@Resource
class GradeSubject {

    Integer grade
	Subject subject
    static constraints = {
    }
}
