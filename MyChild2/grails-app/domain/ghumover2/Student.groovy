package ghumover2
import grails.rest.Resource
import org.grails.databinding.BindingFormat
import javax.persistence.Transient;

@Resource(formats=['json', 'xml'])
class Student  {

	Long studentId
	String registerNumber
	String studentName
	String gender
	Address present_address
	int no_of_siblings
	@BindingFormat('dd-MM-yyyy')
	Date dob
	String studentPhoto
	String present_guardian
	static belongsTo = [grade : Grade]

	static hasMany = [siblings:Sibling]

	static mapping = { id generator: 'increment',name: 'studentId' }



	static constraints = {

		gender(nullable: true)
		registerNumber(nullable:true)
		grade(nullable:true)
		present_address(nullable: true)
		no_of_siblings(nullable :true)
		dob(nullable: true)
		studentPhoto(nullable: true)
		 present_guardian(nullable: true)
	}

	void setAsFather(Guardian father)
	{
		new GuardianChildren(guardian:father, student:this , guardianType:"father").save()
	}
	void setAsMother(Guardian mother)
	{
		new GuardianChildren(guardian: mother , student:this , guardianType:"mother").save()
	}
	void setAsLocalGuardian(Guardian local_guardian)
	{

		new GuardianChildren(guardian: local_guardian , student:this , guardianType:"local_guardian").save()

	}


	@Transient
	Guardian getMother() {

		return GuardianChildren.findByStudentAndGuardianType(this,"mother")?.guardian

	}

	@Transient
	Guardian getFather() {
		return GuardianChildren.findByStudentAndGuardianType(this,"father")?.guardian
	}

	@Transient
	Guardian getLocalGuardian() {

		return GuardianChildren.findByStudentAndGuardianType(this,"local_guardian")?.guardian

	}
	@Transient
	List<Student> getParents() {
		return  GuardianChildren.findAllByStudent(this).guardian
	}



	def getAge()
	{

		return "age"
	}



}
