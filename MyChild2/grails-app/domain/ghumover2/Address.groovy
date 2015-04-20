package ghumover2

class Address {
    String address
    String place
    String landmark
    static constraints = {

        address(nullable: true)
        landmark(nullable: true)
        place(nullable: true)
    }
}
