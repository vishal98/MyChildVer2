package test

class Book {
	String title
	 static belongsTo = [author: Author]

    static constraints = {
    }
}
