package ghumover2

class SchoolClass {
	
	Long class_id
	String name_class
	String class_tags
	static belongsTo =
	[school: School]
	School school
	static hasMany = [grades:Grade]
	
	static mapping = {
		id generator: 'increment',name: 'class_id'
		
	}
    static constraints = {
    }
	
	def afterInsert = {
		class_id = this.class_id;
		
	}
}
