package ghumover2

class SchoolClass {
	
	Long classId
	String className
	String classTags
	static belongsTo =
	[school: School]
	School school
	static hasMany = [grades:Grade]
	
	static mapping = {
		id generator: 'increment',name: 'classId'
		
	}
    static constraints = {
    }
	
	def afterInsert = {
		classId = this.classId;
		
	}
}
