package ghumover2
import grails.rest.Resource

@Resource(formats=['json', 'xml'])
class Guardian extends User
{

    String name

    String educational_qualification
    String profession
    String designation
    String mobileNumber
    String emailId
    String officeNumber
  static mappedBy = [children:'father']

    static hasMany = [children:Student]


    static constraints = {
        educational_qualification(nullable: true)
        profession(nullable: true)
        designation(nullable: true)
        mobileNumber(nullable: true)
        emailId(nullable: true)
        officeNumber(nullable: true)
    }
    static mapping = {



    }
}