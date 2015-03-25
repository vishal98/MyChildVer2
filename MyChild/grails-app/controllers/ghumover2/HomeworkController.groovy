package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured;


@Secured(['ROLE_TEACHER','ROLE_PARENT'])
class HomeworkController extends RestfulController
{
    static allowedMethods = [saveHomework: "POST"]
    static responseFormats = ['json', 'xml']

    HomeworkController() {
        super(Homework)
    }


   def getClassHomework()
   {
            def gradeName = params.gradeId
            Date date =   Date.parse("dd-MM-yyyy", params.dateAssigned).clearTime()
            def section = params.section
            def grade = Grade.findByNameAndSection(gradeName,section)
            def response = Homework.findAllByGradeAndDateCreated(grade,date)
            render response as JSON

   }

   def getClassHomeworkBySubject()
   {
       def gradeName = params.gradeId
       def section = params.section
       Date date =   Date.parse("dd-MM-yyyy", params.dateAssigned).clearTime()
       def subject = params.subject
       def grade = Grade.findByNameAndSection(gradeName,section)
       def response = Homework.findAllByGradeAndSubjectAndDateCreated(grade,subject,date)
       render response as JSON
   }

    def saveHomework()
         {

             //render params

             try{

                  def grade = Grade.findByName(params.gradeId)
                 def subject = Subject.findBySubjectId(params.subjectId)
                 def response = [:]
                 if(new Homework(grade: grade , subject: subject , dueDate:params.dueDate, homework: params.homework , section:params.section , message: params.message , gradeFlag:params.gradeFlag ).save(flush:true))
                  {
                      response['status'] = "Success"
                      response['message'] = "Successfully saved"
                      render response as JSON
                  }
                 else
                 {
                     response['status'] = "Success"
                     response['message'] = "Some error has been occured"
                     render response as JSON
                 }

             }
             catch (Exception e)
             {
                 render e as JSON
             }


         }







}