package ghumover2
import grails.rest.Resource
import org.grails.databinding.BindingFormat

@Resource(formats=['json', 'xml'])
class Student  {

	Integer studentId
	String registerNumber
	String studentName
	String gender
	Address present_address
	int no_of_siblings
	@BindingFormat('dd-MM-yyyy')
	Date dob
	String studentPhoto

	String present_guardian
	static belongsTo = [grade:Grade , father:Guardian , mother:Guardian , local_guardian:Guardian]
	static hasMany = [siblings:Sibling]




	static constraints = {
		id generator: 'increment',name: 'studentId'
		gender(nullable: true)
		registerNumber(nullable:true)
		grade(nullable:true)
		present_address(nullable: true)
		no_of_siblings(nullable :true)
		dob(nullable: true)
		studentPhoto(nullable: true)
	 	father(nullable: true)
	 	mother(nullable: true)
		local_guardian(nullable: true)
		present_guardian(nullable: true)
	}

	static mapping = {



	}
}
