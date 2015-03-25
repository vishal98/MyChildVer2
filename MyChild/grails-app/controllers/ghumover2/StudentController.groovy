package ghumover2

import grails.rest.RestfulController


class StudentController extends RestfulController  {
    static responseFormats = ['json', 'xml']
    StudentController() {
        super(Student)
    }


}