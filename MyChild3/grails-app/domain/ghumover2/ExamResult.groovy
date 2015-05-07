package ghumover2

import grails.rest.Resource

@Resource(formats=['json', 'xml'])
class ExamResult {
    Long resultId
    static belongsTo = [exam:Exam]
    Student student
    Subject subject
    int marks

    int maxMarks

    static constraints = {
        marks(nullable:true)
        exam(nullable: true)
        maxMarks(nullable:true)
        student(nullable: true)
        subject(nullable: true)

    }
    static mapping = {
        id generator: 'increment',name: 'resultId'
    }

}