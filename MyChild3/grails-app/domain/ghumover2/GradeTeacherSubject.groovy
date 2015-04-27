package ghumover2

class GradeTeacherSubject {

    Grade grade
    Teacher teacher
    Subject subject

    static constraints = {

        grade unique: ['teacher' , 'subject']
    }
}
