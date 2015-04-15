import java.text.SimpleDateFormat
import java.util.Date
import ghumover2.*
import grails.converters.JSON
import groovy.sql.Sql


class BootStrap {

	private static final String ROLE_TEACHER = 'ROLE_TEACHER'
	private static final String ROLE_PARENT = 'ROLE_PARENT'
	private static final String ROLE_ADMIN = 'ROLE_ADMIN'
	
	


	def dataSource
	def init = { servletContext ->

		// BOOTSTRAPING DATES
		def createQuery = "CREATE TABLE IF NOT EXISTS ints ( i tinyint unique );"
		def insertQuery = "INSERT INTO ints (i) VALUES (0),(1),(2),(3),(4),(5),(6),(7),(8),(9)   ON DUPLICATE KEY UPDATE i = VALUES(i);"
		def insertCalenderDates = """\

										INSERT INTO calendar_date (calendar_date)
										SELECT DATE('2010-01-01') + INTERVAL a.i*10000 + b.i*1000 + c.i*100 + d.i*10 + e.i DAY
										FROM ints a JOIN ints b JOIN ints c JOIN ints d JOIN ints e
										WHERE (a.i*10000 + b.i*1000 + c.i*100 + d.i*10 + e.i) <= 11322
										ORDER BY 1;
                                    """
		def updateCalenderDates = """\
															UPDATE calendar_date
															SET is_weekday = CASE WHEN dayofweek(calendar_date) IN (1,7) THEN 0 ELSE 1 END,
															is_holiday = 0,
															is_payday = 0,
															year = YEAR(calendar_date),
															quarter = quarter(calendar_date),
															month = MONTH(calendar_date),
															day_of_month = dayofmonth(calendar_date),
															day_of_week = dayofweek(calendar_date),
															month_name = monthname(calendar_date),
															day_name = dayname(calendar_date),
															week_of_year = week(calendar_date),
															holiday_description = '';
"""

		def sql = new Sql(dataSource)

		sql.executeUpdate(createQuery)
		sql.executeUpdate(insertQuery)

		sql.executeUpdate(insertCalenderDates)
		sql.executeUpdate(updateCalenderDates)

		//ADDED AUGUST 15 AS HOLIDAY
				CalendarDate.executeUpdate("update CalendarDate c set c.holiday_description='Independance Day' , is_holiday = true " +
				"where c.month=8 and c.day_of_month = 15")



CalendarDate.executeUpdate("update CalendarDate c set c.isHoliday = true , c.holiday_description='Sunday' where c.dayName like ? " , ['Sunday']);


		// END OF BOOTSTRAPING DATES



		Role roleTeacher;
		Role roleParent;
		Teacher teacher;
		Guardian parent;


		roleTeacher = new Role(authority: ROLE_TEACHER)
		roleTeacher.save()

		roleParent = new Role(authority: ROLE_PARENT)
		roleParent.save()
		
		def roleAdmin = new Role(authority: ROLE_ADMIN)
		roleAdmin.save()
 
 def admin = new User(username: "admin" , password: '123' ).save()
 new UserRole(user: admin, role: roleAdmin).save()



		Subject english , hindi , chemistry , physics , computerScience , history , socialScience , biology , maths

		english = new Subject(subjectName: "English").save()
		hindi = new Subject(subjectName: "Hindi").save()
		chemistry = new Subject(subjectName: "Chemistry").save()
		computerScience = new Subject(subjectName: "Computer Science").save()
		history = new Subject(subjectName: "History").save()
		socialScience = new Subject(subjectName: "Social Studies").save()
		biology = new Subject(subjectName: "Biology").save()
		maths = new Subject(subjectName: "Maths").save()
		physics = new Subject(subjectName: "Physics").save()

		Subject ILanguage = new Subject(subjectName: "I Language").save()
		Subject IILanguage = new Subject(subjectName: "II Language").save()
        Subject science = new Subject(subjectName: "Science").save()
		Subject computers = new Subject(subjectName: "Computers").save()
		Subject  gk = new Subject(subjectName: "G.K").save()
		Subject clubAct = new Subject(subjectName: "Club Activities").save()
		Subject pt = new Subject(subjectName: "PT").save()
		Subject activities = new Subject(subjectName: "Activities").save()



		Grade cl5A , cl5B , cl6A , cl6B , cl7A ,cl7B , cl8A , cl8B , cl9A , cl9B , cl10A , cl10B

		cl5A = new Grade(name: 5 , section: "A").save()
		cl5B = new Grade(name: 5 , section: "B").save()
		cl6A = new Grade(name: 6 , section: "A").save()
		cl6B = new Grade(name: 6 , section: "B").save()
		cl7A = new Grade(name: 7 , section: "A").save()
		cl7B = new Grade(name: 7 , section: "B").save()
		cl8A = new Grade(name: 8 , section: "A").save()
		cl8B = new Grade(name: 8 , section: "B").save()
		cl9A = new Grade(name: 9 , section: "A").save()
		cl9B = new Grade(name: 9 , section: "B").save()
		cl10A = new Grade(name: 10 , section: "A").save()
		cl10B = new Grade(name: 10 , section: "B").save()


		Teacher mathew , sibi , satheesh , raji , robin , binu , hari , shyam , anil , maya , vinod

        mathew = new Teacher(username: "mathew" , password: "123" ,teacherName: "Mathew" , teacherEmailId: "mathew@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
		sibi = new Teacher(username: "sibi" , password: "123" ,teacherName: "Sibi" , teacherEmailId: "sibi@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
		satheesh = new Teacher(username: "satheesh" , password: "123" ,teacherName: "Satheesh Kumar" , teacherEmailId: "satheesh@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
		raji =  new Teacher(username: "raji" , password: "123" ,teacherName: "Raji" , teacherEmailId: "raji@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
		robin = new Teacher(username: "robin" , password: "123" ,teacherName: "Robin" , teacherEmailId: "robin@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        binu = new Teacher(username: "binu" , password: "123" ,teacherName: "Binu" , teacherEmailId: "binu@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        hari = new Teacher(username: "hari" , password: "123" ,teacherName: "Hari" , teacherEmailId: "hari@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        shyam = new Teacher(username: "shyam" , password: "123" ,teacherName: "Shyam" , teacherEmailId: "shyam@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        anil = new Teacher(username: "anil" , password: "123" ,teacherName: "Anil" , teacherEmailId: "anil@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        maya = new Teacher(username: "maya" , password: "123" ,teacherName: "Maya" , teacherEmailId: "maya@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        vinod = new Teacher(username: "vinod" , password: "123" ,teacherName: "Vinod" , teacherEmailId: "vinod@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()

		new UserRole(user: mathew , role: roleTeacher).save()
		new UserRole(user: sibi , role: roleTeacher).save()
		new UserRole(user: satheesh , role: roleTeacher).save()
		new UserRole(user: raji, role: roleTeacher).save()
		new UserRole(user: robin, role: roleTeacher).save()
		new UserRole(user: binu, role: roleTeacher).save()
		new UserRole(user: hari, role: roleTeacher).save()
		new UserRole(user: shyam, role: roleTeacher).save()
		new UserRole(user: anil, role: roleTeacher).save()
		new UserRole(user: maya, role: roleTeacher).save()
		new UserRole(user: vinod, role: roleTeacher).save()



		def archana = new Teacher(username: "archana" , password: "123" , teacherName: "Archana" , teacherEmailId: "archana@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        def vanajakshi = new Teacher(username: "vanajakshi" , password: "123" , teacherName: "Vanajakshi" , teacherEmailId: "vanajakshi@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
		def sheetal = new Teacher(username: "sheetal" , password: "123" , teacherName: "Sheetal" , teacherEmailId: "sheetal@test.com", teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        def kiran = new Teacher(username: "kiran" , password: "123" , teacherEmailId:"kiran@test.com" , teacherName: "Kiran", teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        def naresh =  new Teacher(username: "naresh" , password: "123" , teacherEmailId: "naresh@test.com" , teacherName: "Naresh", teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()
        def ptTeacher = new Teacher(username: "pt" , password: "123" , teacherName: "PT Teacher" , teacherEmailId: "pt@test.com" , teacherPhoto: "teacher.jpg" ,phoneNo: "984700000" ).save()

		[archana,vanajakshi,sheetal,kiran,naresh,ptTeacher].each {
			new UserRole(user: it, role: roleTeacher).save()
		}

		archana.addToGradeSubject(cl5A,maths)
		vanajakshi.addToGradeSubject(cl5A,ILanguage)
		vanajakshi.addToGradeSubject(cl5A,clubAct)
		sheetal.addToGradeSubject(cl5A,science)
		kiran.addToGradeSubject(cl5A,IILanguage)
		naresh.addToGradeSubject(cl5A,socialScience)
		ptTeacher.addToGradeSubject(cl5A,pt)
		mathew.addToGradeSubject(cl5A,activities)






		    mathew.addToGradeSubject(cl5A,english)
			mathew.addToGradeSubject(cl5A,hindi)
			mathew.addToGradeSubject(cl5A,computerScience)
			mathew.addToGradeSubject(cl5A,hindi)
            mathew.addToGradeSubject(cl5B,history)
            mathew.addToGradeSubject(cl9A,english)
            mathew.addToGradeSubject(cl9B,english)

            sibi.addToGradeSubject(cl5A,hindi)
            sibi.addToGradeSubject(cl5B,hindi)
            sibi.addToGradeSubject(cl7A,english)
            sibi.addToGradeSubject(cl7B,english)
            sibi.addToGradeSubject(cl9A,maths)
            sibi.addToGradeSubject(cl9B,maths)

            satheesh.addToGradeSubject(cl5A,chemistry)
            satheesh.addToGradeSubject(cl5B,chemistry)
            satheesh.addToGradeSubject(cl7A,hindi)
            satheesh.addToGradeSubject(cl7B,hindi)

            raji.addToGradeSubject(cl5A,physics)
            raji.addToGradeSubject(cl5B,physics)
            raji.addToGradeSubject(cl6A,english)
            raji.addToGradeSubject(cl6B,english)


                robin.addToGradeSubject(cl7A,biology)
                robin.addToGradeSubject(cl7B,biology)
                robin.addToGradeSubject(cl9A,biology)
                robin.addToGradeSubject(cl9B,biology)

                binu.addToGradeSubject(cl8A,hindi)
                binu.addToGradeSubject(cl8B,hindi)

                hari.addToGradeSubject(cl8A,computerScience)
                hari.addToGradeSubject(cl8B,computerScience)

                shyam.addToGradeSubject(cl6A,computerScience)
                shyam.addToGradeSubject(cl6B,computerScience)
                shyam.addToGradeSubject(cl8A,maths)
                shyam.addToGradeSubject(cl8B,maths)
                shyam.addToGradeSubject(cl9A,computerScience)
                shyam.addToGradeSubject(cl9B,computerScience)

                anil.addToGradeSubject(cl8A,english)
                anil.addToGradeSubject(cl8B,english)

                maya.addToGradeSubject(cl6A,hindi)
                maya.addToGradeSubject(cl6B,hindi)
                maya.addToGradeSubject(cl9A,hindi)
                maya.addToGradeSubject(cl9B,hindi)

                vinod.addToGradeSubject(cl6A,history)
                vinod.addToGradeSubject(cl6B,history)
                vinod.addToGradeSubject(cl7A,socialScience)
                vinod.addToGradeSubject(cl7B,socialScience)

				cl5A.setClassTeacherId(mathew.id)
				cl5B.setClassTeacherId(sibi.id)
				cl6A.setClassTeacherId(raji.id)
				cl6B.setClassTeacherId(vinod.id)
				cl7A.setClassTeacherId(satheesh.id)
				cl7B.setClassTeacherId(robin.id)
				cl8A.setClassTeacherId(anil.id)
				cl8B.setClassTeacherId(binu.id)
				cl9A.setClassTeacherId(maya.id)
				cl9B.setClassTeacherId(shyam.id)






 
            def father , mother , local_guardian , s1 , s2 , s3
           // FIRST STUDENT DETAILS
            s1 =  new Student(grade:cl5A  , registerNumber: "ST100" ,studentName: "Rohith" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Father" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()  ).save()
            s1.setAsFather( new Guardian(name: "Ravi" , username: "ravi@test.com" , password: "123" , educational_qualification: "MBA" , designation: "Manager" , profession: "Private Employee" , emailId: "father@user.com" , officeNumber: "04868699000" , mobileNumber: "98470000" ).save() )
            s1.setAsMother( new Guardian(name:"Raani" , username: "raani@test.com" , password: "123" , educational_qualification: "Bcom" , designation: "College Professor" , profession: "Lecturer" , emailId: "mother@user.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979"  ).save() )

            father = Guardian.findByUsername("ravi@test.com")
            mother = Guardian.findByUsername("raani@test.com")

            s2 =  new Student(grade: cl5A , registerNumber: "ST101" ,studentName: "Renjith" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Father" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save() ).save()
            s2.setAsFather( father )
            s2.setAsMother( mother )

            s3 =  new Student(grade: cl5A ,  registerNumber: "ST102" ,studentName: "Rohan" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Father"  , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
            s3.setAsFather( father )
            s3.setAsMother( mother )

            new UserRole(user:father , role:roleParent).save(flush: true)
            new UserRole(user:mother , role:roleParent).save(flush: true)


            s1 =  new Student( grade:cl5A  , registerNumber: "ST106" ,studentName: "Neha" , gender: "Female" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()  ).save()
            s1.setAsFather( new Guardian(name: "Nagesh" , username: "nagesh@test.com" , password: "123" , educational_qualification: "MBA" , designation: "Manager" , profession: "Private Employee" , emailId: "father@user.com" , officeNumber: "04868699000" , mobileNumber: "98470000" ).save() )
            s1.setAsMother( new Guardian(name:"Nanditha" , username: "nanditha@test.com" , password: "123" , educational_qualification: "Bcom" , designation: "College Professor" , profession: "Lecturer" , emailId: "mother@user.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979"  ).save() )

            father = Guardian.findByUsername("nagesh@test.com")
            mother = Guardian.findByUsername("nanditha@test.com")

            s2 =  new Student(grade: cl5A , registerNumber: "ST107" ,studentName: "Nivas" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
            s2.setAsFather( father )
            s2.setAsMother( mother )

            s3 =  new Student( grade: cl5A ,  registerNumber: "ST108" ,studentName: "Nikhitha" , gender: "Female" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
            s3.setAsFather( father )
            s3.setAsMother( mother )

            new UserRole(user:father , role:roleParent).save(flush: true)
            new UserRole(user:mother , role:roleParent).save(flush: true)




            // SECOND STUDENT DETAILS

            s1 =  new Student( grade:cl5B  , registerNumber: "ST103" ,studentName: "Midhun" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Local Guardian" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()  ).save()
            s1.setAsFather( new Guardian(name: "Mahadev" , username: "mahadev@test.com" , password: "123" , educational_qualification: "MBA" , designation: "Manager" , profession: "Private Employee" , emailId: "father@user.com" , officeNumber: "04868699000" , mobileNumber: "98470000" ).save() )
            s1.setAsMother( new Guardian(name:"Malini" , username: "malini@test.com" , password: "123" , educational_qualification: "Bcom" , designation: "College Professor" , profession: "Lecturer" , emailId: "mother@user.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979"  ).save() )
            s1.setAsLocalGuardian((new Guardian(name:"Manish" , username: "manish@test.com" , password: "123" , educational_qualification: "MCA" , designation: "Software Engineer" , profession: "IT Professional" , emailId: "local_guard@test.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979" )).save())
            father = Guardian.findByUsername("mahadev@test.com")
            mother = Guardian.findByUsername("malini@test.com")
            local_guardian = Guardian.findByUsername("manish@test.com")

		// SECOND STUDENT DETAILS

            s2 =  new Student(grade: cl5B , registerNumber: "ST104" ,studentName: "Manoj" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Local Guardian" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
            s2.setAsFather( father )
            s2.setAsMother( mother )
            s2.setAsLocalGuardian( local_guardian )

            s3 =  new Student(grade: cl5A ,  registerNumber: "ST105" ,studentName: "Mohith" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Local Guardian", present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save() ).save()
            s3.setAsFather( father )
            s3.setAsMother( mother )
            s3.setAsLocalGuardian( local_guardian )

            new UserRole(user:father , role:roleParent).save(flush: true)
            new UserRole(user:mother , role:roleParent).save(flush: true)



            // third group STUDENT DETAILS




        s1 =  new Student( grade:cl5A  , registerNumber: "ST109" ,studentName: "Akhil" , gender: "male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()  ).save()
        s1.setAsFather( new Guardian(name: "Jacob" , username: "jacob@test.com" , password: "123" , educational_qualification: "MBA" , designation: "Manager" , profession: "Private Employee" , emailId: "father@user.com" , officeNumber: "04868699000" , mobileNumber: "98470000" ).save() )
        s1.setAsMother( new Guardian(name:"Reena" , username: "reena@test.com" , password: "123" , educational_qualification: "Bcom" , designation: "College Professor" , profession: "Lecturer" , emailId: "mother@user.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979"  ).save() )

        father = Guardian.findByUsername("jacob@test.com")
        mother = Guardian.findByUsername("reena@test.com")

        s2 =  new Student(grade: cl5A , registerNumber: "ST110" ,studentName: "Abhijith" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
        s2.setAsFather( father )
        s2.setAsMother( mother )

        s3 =  new Student( grade: cl5B ,  registerNumber: "ST111" ,studentName: "Ashiq" , gender: "male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
        s3.setAsFather( father )
        s3.setAsMother( mother )

        new UserRole(user:father , role:roleParent).save(flush: true)
        new UserRole(user:mother , role:roleParent).save(flush: true)




            s1 =  new Student( grade:cl5A  , registerNumber: "ST112" ,studentName: "Bony" , gender: "male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()  ).save()
            s1.setAsFather( new Guardian(name: "Joy" , username: "joy@test.com" , password: "123" , educational_qualification: "MBA" , designation: "Manager" , profession: "Private Employee" , emailId: "father@user.com" , officeNumber: "04868699000" , mobileNumber: "98470000" ).save() )
            s1.setAsMother( new Guardian(name:"Molly" , username: "molly@test.com" , password: "123" , educational_qualification: "Bcom" , designation: "College Professor" , profession: "Lecturer" , emailId: "mother@user.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979"  ).save() )

            father = Guardian.findByUsername("joy@test.com")
            mother = Guardian.findByUsername("molly@test.com")

            s2 =  new Student(grade: cl5A , registerNumber: "ST113" ,studentName: "Binil" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
            s2.setAsFather( father )
            s2.setAsMother( mother )



            new UserRole(user:father , role:roleParent).save(flush: true)
            new UserRole(user:mother , role:roleParent).save(flush: true)


            s1 =  new Student( grade:cl5B  , registerNumber: "ST114" ,studentName: "Nijo" , gender: "male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()  ).save()
            s1.setAsFather( new Guardian(name: "Raju" , username: "raju@test.com" , password: "123" , educational_qualification: "MBA" , designation: "Manager" , profession: "Private Employee" , emailId: "father@user.com" , officeNumber: "04868699000" , mobileNumber: "98470000" ).save() )
            s1.setAsMother( new Guardian(name:"Geetha" , username: "geetha@test.com" , password: "123" , educational_qualification: "Bcom" , designation: "College Professor" , profession: "Lecturer" , emailId: "mother@user.com" ,officeNumber: "0489898989" , mobileNumber: "94466797979"  ).save() )

            father = Guardian.findByUsername("raju@test.com")
            mother = Guardian.findByUsername("geetha@test.com")

            s2 =  new Student(grade: cl5A , registerNumber: "ST115" ,studentName: "Nithin" , gender: "Male" , dob:"12-12-2000" , studentPhoto: "photo.jpg", no_of_siblings: 2 , present_guardian: "Mother" , present_address: new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala" ).save()).save()
            s2.setAsFather( father )
            s2.setAsMother( mother )



            new UserRole(user:father , role:roleParent).save(flush: true)
            new UserRole(user:mother , role:roleParent).save(flush: true)









            //Homeworks for students

               new Homework(grade: cl5A , subject: "english" , homework: "English homework", dueDate: "31-12-2016"  ,student: Student.findByStudentId(1) , message: "English Homework for Student , 5 A ", gradeFlag: '0').save(flush: true)





		// Add exam entries Date startTime


		new Exam(examName: "English" , examType: "Class test").save(flush: true)
		new Exam(examName: "Chemistry" , examType: "Class test").save(flush: true)
		new Exam(examName: "Physics" , examType: "Model Exam").save(flush: true)
		new Exam(examName: "Mathematics" , examType: "Model Exam").save(flush: true)
		new Exam(examName: "Hindi" , examType: "ModelExam").save(flush: true)
		new Exam(examName: "History", examType: "Mid Term Exam").save(flush: true)
		new Exam(examName: "Computer Science", examType: "Mid Term Exam").save(flush: true)


		def exam1 , exam2 ,exam3 ,exam4 ,exam5,exam6,exam7
		exam1 = Exam.get(1)
		exam2 = Exam.get(2)
		exam3 = Exam.get(3)
		exam4 = Exam.get(4)
		exam5 = Exam.get(5)
		exam6 = Exam.get(6)
		exam7 = Exam.get(7)



		new ExamSyllabus(exam: exam1 , subject: english ,syllabus: "Chapter 1-7 " ).save(flush: true)
		new ExamSyllabus(exam: exam1 , subject: chemistry,syllabus: "Oraganic Chem Chapter 1-3").save(flush: true)
		new ExamSyllabus(exam: exam1 , subject: physics , syllabus: "Chapter 1-3 newton laws").save(flush: true)
		new ExamSyllabus(exam: exam1 , subject: maths,syllabus: "CompleteAlgebra").save(flush: true)
		new ExamSyllabus(exam: exam1 , subject: hindi , syllabus: "Poems and  Chapter 1-3").save(flush: true)
		new ExamSyllabus(exam: exam1 , subject: history , syllabus: "Complete Indus valley civilzation  ").save(flush: true)
		new ExamSyllabus(exam: exam2 , subject: english ,syllabus: "Non Vowel " ).save(flush: true)
		new ExamSyllabus(exam: exam2 , subject: chemistry,syllabus: "Liquid and its properties").save(flush: true)
		new ExamSyllabus(exam: exam2 , subject: physics , syllabus: "Chapter 1-3 newton laws").save(flush: true)
		new ExamSyllabus(exam: exam2 , subject: maths,syllabus: "Chapter 2-4").save(flush: true)
		new ExamSyllabus(exam: exam2 , subject: hindi , syllabus: "Chapter 1-5").save(flush: true)
		new ExamSyllabus(exam: exam2 , subject: history , syllabus: "Complete India Indpedence story chapters").save(flush: true)


		def examSyllabus1 , examSyllabus2 ,examSyllabus3, examSyllabus4 , examSyllabus5 ,examSyllabus6,examSyllabus7 , examSyllabus8 ,examSyllabus9, examSyllabus10 , examSyllabus11 ,examSyllabus12
		examSyllabus1 = ExamSyllabus.get(1)
		examSyllabus2 = ExamSyllabus.get(2)
		examSyllabus3 = ExamSyllabus.get(3)
		examSyllabus4 = ExamSyllabus.get(4)
		examSyllabus5 = ExamSyllabus.get(5)
		examSyllabus6 = ExamSyllabus.get(6)
		examSyllabus7 = ExamSyllabus.get(7)
		examSyllabus8 = ExamSyllabus.get(8)
		examSyllabus9 = ExamSyllabus.get(9)
		examSyllabus10 = ExamSyllabus.get(10)
		examSyllabus11 = ExamSyllabus.get(11)
		examSyllabus12 = ExamSyllabus.get(12)

		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm"); new Date(2014, 02, 11, 04, 30)

		new ExamSchedule(exam: exam1  ,subjectSyllabus: examSyllabus1,  subject: english ,teacher :sibi,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:00" ).save(flush: true)
		new ExamSchedule(exam: exam1  ,subjectSyllabus: examSyllabus2 , subject: chemistry ,teacher :mathew,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam1  ,subjectSyllabus: examSyllabus3, subject: physics ,teacher :cl10A,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam1 ,subjectSyllabus: examSyllabus4, subject: maths ,teacher : sibi,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam1 ,subjectSyllabus: examSyllabus5 , subject: hindi ,teacher :mathew,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam1 ,subjectSyllabus: examSyllabus6, subject: history ,teacher :satheesh,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam3  ,subjectSyllabus: examSyllabus1 , subject: computerScience ,teacher :sibi,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam2 ,subjectSyllabus: examSyllabus11 , subject: hindi ,teacher :mathew,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam2 ,subjectSyllabus: examSyllabus12, subject: history ,teacher :satheesh,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam3  ,subjectSyllabus: examSyllabus1 , subject: computerScience ,teacher :sibi,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam2  ,subjectSyllabus: examSyllabus7,  subject: english ,teacher :sibi,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:00" ).save(flush: true)
		new ExamSchedule(exam: exam2  ,subjectSyllabus: examSyllabus8, subject: chemistry ,teacher :mathew,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam2  ,subjectSyllabus: examSyllabus9, subject: physics ,teacher :cl10A,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
		new ExamSchedule(exam: exam2 ,subjectSyllabus: examSyllabus10, subject: maths ,teacher : sibi,startTime: "11-02-2014 09:30",endTime: "11-02-2014 10:30").save(flush: true)
	
		
		def examSchedule1, examSchedule2 ,examSchedule3,examSchedule4,examSchedule5,examSchedule6,examSchedule7

		examSchedule1 = ExamSchedule.get(1)
		examSchedule2 = ExamSchedule.get(2)
		examSchedule3 = ExamSchedule.get(3)
		examSchedule4 = ExamSchedule.get(4)
		examSchedule5 = ExamSchedule.get(5)
		examSchedule6 = ExamSchedule.get(6)
		examSchedule7 = ExamSchedule.get(7)

		cl5A.addToExams(exam1).save(flush: true)
		cl5A.addToExams(exam2).save(flush: true)
		cl5A.addToExams(exam3).save(flush: true)




		exam1.addToExamSubjectSchedule(examSchedule1)
				.addToExamSubjectSchedule(examSchedule2)
				.addToExamSubjectSchedule(examSchedule3).save(flush: true)

		   // Add exam entries Date startTime
   
//NEW VALID TIME TABLE ENTRIES

               ["Monday" , "Tuesday" , "Wednesday" , "Thursday" , "Friday"].each {

                  /* cl5A.addToTimetable(new TimeTable(grade: cl5A , day: it , teacher:mathew , subject: english , startTime: "10:00 AM" , endTime: "11:00 AM")).save()
                   cl5A.addToTimetable(new TimeTable(grade: cl5A , day: it , teacher:sibi , subject: hindi , startTime: "11:10 AM" , endTime: "12:00 PM")).save()
                   cl5A.addToTimetable(new TimeTable(grade: cl5A , day: it , teacher:satheesh , subject: chemistry , startTime: "12:05 PM" , endTime: "1:00 PM")).save()
                   cl5A.addToTimetable(new TimeTable(grade: cl5A , day: it , teacher:raji , subject: physics , startTime: "2:00 PM" , endTime: "3:00 PM")).save()

                   cl5B.addToTimetable(new TimeTable(grade: cl5B , day: it , teacher:raji , subject: physics , startTime: "10:00 AM" , endTime: "11:00 AM")).save()
                   cl5B.addToTimetable(new TimeTable(grade: cl5B , day: it , teacher:satheesh , subject: chemistry , startTime: "11:10 AM" , endTime: "12:00 PM")).save()
                   cl5B.addToTimetable(new TimeTable(grade: cl5B , day: it , teacher:sibi , subject: hindi , startTime: "12:05 PM" , endTime: "1:00 PM")).save()
                   cl5B.addToTimetable(new TimeTable(grade: cl5B , day: it , teacher:mathew , subject: english , startTime: "2:00 PM" , endTime: "3:00 PM")).save()



				   cl5A.addToTimetable(new TimeTable(grade: cl5A , day: it)).save()

 */





               }
			


		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:archana , subject: maths , startTime: "07:30 AM" , endTime: "08:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:vanajakshi , subject: ILanguage , startTime: "08:00 AM" , endTime: "09:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:sheetal , subject: science , startTime: "09:00 AM" , endTime: "10:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:kiran , subject: IILanguage , startTime: "10:30 AM" , endTime: "11:30 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:naresh , subject: socialScience , startTime: "11:30 AM" , endTime: "12:30 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:archana , subject: maths , startTime: "01:00 PM" , endTime: "02:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:ptTeacher , subject: pt , startTime: "02:00 PM" , endTime: "03:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Monday" , teacher:mathew , subject: activities , startTime: "03:00 PM" , endTime: "03:30 PM")).save()


		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:kiran , subject: IILanguage , startTime: "07:30 AM" , endTime: "08:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:archana , subject: maths , startTime: "08:00 AM" , endTime: "09:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:naresh , subject: socialScience , startTime: "09:00 AM" , endTime: "10:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:vanajakshi , subject: ILanguage , startTime: "10:30 AM" , endTime: "11:30 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:archana , subject: maths , startTime: "11:30 AM" , endTime: "12:30 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:archana , subject: maths , startTime: "01:00 PM" , endTime: "02:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:ptTeacher , subject: pt , startTime: "02:00 PM" , endTime: "03:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Tuesday" , teacher:mathew , subject: activities , startTime: "03:00 PM" , endTime: "03:30 PM")).save()


		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:archana , subject: maths , startTime: "07:30 AM" , endTime: "08:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:vanajakshi , subject: ILanguage , startTime: "08:00 AM" , endTime: "09:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:sheetal , subject: science , startTime: "09:00 AM" , endTime: "10:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:kiran , subject: IILanguage , startTime: "10:30 AM" , endTime: "11:30 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:naresh , subject: socialScience , startTime: "11:30 AM" , endTime: "12:30 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:archana , subject: maths , startTime: "01:00 PM" , endTime: "02:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:sheetal , subject: science , startTime: "02:00 PM" , endTime: "03:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Wednesday" , teacher:mathew , subject: activities , startTime: "03:00 PM" , endTime: "03:30 PM")).save()



		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:archana , subject: IILanguage , startTime: "07:30 AM" , endTime: "08:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:vanajakshi , subject: maths , startTime: "08:00 AM" , endTime: "09:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:sheetal , subject: pt , startTime: "09:00 AM" , endTime: "10:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:kiran , subject: ILanguage , startTime: "10:30 AM" , endTime: "11:30 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:naresh , subject: maths , startTime: "11:30 AM" , endTime: "12:30 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:archana , subject: maths , startTime: "01:00 PM" , endTime: "02:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:sheetal , subject: pt , startTime: "02:00 PM" , endTime: "03:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Thursday" , teacher:mathew , subject: activities , startTime: "03:00 PM" , endTime: "03:30 PM")).save()


		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:sheetal , subject: science , startTime: "07:30 AM" , endTime: "08:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:vanajakshi , subject: ILanguage , startTime: "08:00 AM" , endTime: "09:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:vanajakshi , subject: clubAct , startTime: "09:00 AM" , endTime: "10:00 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:kiran , subject: IILanguage , startTime: "10:30 AM" , endTime: "11:30 AM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:naresh , subject: socialScience , startTime: "11:30 AM" , endTime: "12:30 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:archana , subject: maths , startTime: "01:00 PM" , endTime: "02:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:vanajakshi , subject: clubAct , startTime: "02:00 PM" , endTime: "03:00 PM")).save()
		cl5A.addToTimetable(new TimeTable(grade: cl5A , day: "Friday" , teacher:mathew , subject: activities , startTime: "03:00 PM" , endTime: "03:30 PM")).save()





		User ravi , rani , manish , malini , mahadev , nagesh
		ravi = User.findByUsername("ravi@test.com")
		rani = User.findByUsername("raani@test.com")
		malini = User.findByUsername("malini@test.com")
		mahadev = User.findByUsername("mahadev@test.com")
		nagesh = User.findByUsername("nagesh@test.com")
		manish = User.findByUsername("manish@test.com")



		Conversation testconv = new Conversation(fromId:ravi.username , toId: rani.username , title: "Test Conversation between ravi and raani" , inTrash: false,isRead: false,toDate: new Date(),fromDate: new Date())
		testconv.addToMessages(new Message(fromId: "ravi" , toId: "rani" , messageText: "Haai raani" , messageTime:new Date() ))
				.addToMessages(new Message(fromId: "raani" , toId: "ravi" , messageText: "Haai raviii" , messageTime: new Date()))
				.save()
		ravi.addToConversations(testconv)
		rani.addToConversations(testconv)
		ravi.save()
		rani.save()
		testconv = new Conversation(fromId:ravi.username , toId: rani.username , title: "Test Conversation between ravi and raani" , inTrash: false,isRead: false,toDate: new Date(),fromDate: new Date())
		testconv.addToMessages(new Message(fromId: "ravi" , toId: "rani" , messageText: "Again Haai raani" , messageTime:new Date() ))
				.addToMessages(new Message(fromId: "raani" , toId: "ravi" , messageText: "Again Haai raviii" , messageTime: new Date()))
				.save()
		ravi.addToConversations(testconv)
		rani.addToConversations(testconv)
		ravi.save()
		rani.save()


		testconv = new Conversation(fromId: mahadev.username , toId: malini.username , title: "Message from Mahadev to Malini" , inTrash: false , isRead: false , toDate: new Date() , fromDate: new Date() )
				.addToMessages(new Message(fromId: "Mahadev" , toId: "Malini" ,messageTime: new Date() , messageText: "Haai malini"))
				.addToMessages(new Message(fromId: "Malini" , toId: "Mahadev" , messageText: "Hello mahadev" , messageTime: new Date()))
				.save()
		malini.addToConversations(testconv).save()
		mahadev.addToConversations(testconv).save()





		testconv = new Conversation(fromId: manish.username , toId: mathew.username , title: "Message from Manish" , inTrash: false , isRead: false , toDate: new Date() , fromDate: new Date())
		        .addToMessages(new Message(fromId: "Manish" , toId: "Mathew" , messageText: "Hai this is Manush" , messageTime: new Date()))
		        .addToMessages(new Message(fromId: "Mathew" , toId: "Manish" , messageText: "Yes manish , mathew here" , messageTime:  new Date()))
		        .addToMessages(new Message(fromId: "Manish" , toId: "Mathew" , messageText: "How are you maathew" , messageTime: new Date()))
		        .addToMessages(new Message(fromId: "Mathew" , toId: "Manish" , messageText: "I am fine manish How are you" , messageTime: new Date()))
		        .addToMessages(new Message(fromId: "Manish" , toId: "Mathew" , messageText: "I am also fine mathew , Good night" , messageTime: new Date()))
		        .addToMessages(new Message(fromId: "Mathew" , toId: "Manish" , messageText: "Good night manish...", messageTime: new Date()))
		        .save()
		mathew.addToConversations(testconv).save()
		manish.addToConversations(testconv).save()

		testconv = new Conversation(fromId: nagesh.username , toId: mathew.username , title: "Message from Nagesh" , inTrash: false , isRead: false , toDate: new Date() , fromDate: new Date())
				.addToMessages(new Message(fromId: "Nagesh" , toId: "Mathew" , messageText: "Hai this is Nagesh" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Mathew" , toId: "Nagesh" , messageText: "Yes nagesh , mathew here" , messageTime:  new Date()))
				.addToMessages(new Message(fromId: "Nagesh" , toId: "Mathew" , messageText: "How are you maathew" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Mathew" , toId: "Nagesh" , messageText: "I am fine nagesh How are you" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Nagesh" , toId: "Mathew" , messageText: "I am also fine mathew , Good night" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Mathew" , toId: "Nagesh" , messageText: "Good night nagesh...", messageTime: new Date()))
				.save()
		mathew.addToConversations(testconv).save()
		nagesh.addToConversations(testconv).save()

		testconv = new Conversation(fromId: malini.username , toId: mathew.username , title: "Message from Malini" , inTrash: false , isRead: false , toDate: new Date() , fromDate: new Date())
				.addToMessages(new Message(fromId: "Malini" , toId: "Mathew" , messageText: "Hai this is Manush" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Mathew" , toId: "Malini" , messageText: "Yes malini , mathew here" , messageTime:  new Date()))
				.addToMessages(new Message(fromId: "Malini" , toId: "Mathew" , messageText: "How are you maathew" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Mathew" , toId: "Malini" , messageText: "I am fine malini How are you" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Malini" , toId: "Mathew" , messageText: "I am also fine mathew , Good night" , messageTime: new Date()))
				.addToMessages(new Message(fromId: "Mathew" , toId: "Malini" , messageText: "Good night malini...", messageTime: new Date()))
				.save()
		mathew.addToConversations(testconv).save()
		malini.addToConversations(testconv).save()


























          new Event(calendar_date: CalendarDate.today() ,grade: Grade.findByNameAndSection(5,"A") , title: "Class PTA Meeting" , description: "Parents meeting of all 5 A students" , startTime: "Evening 3.30 " , endTime: "Evening 5.30"  , flag: "GRADE" ).save(flush: true)
          new Event(calendar_date: CalendarDate.today() , title: "Sports Day" , description: "Annual Sports day" , startTime: "Morning 9.30 " , endTime: "Evening 3.00"  , flag: "SCHOOL" ).save()
          new Event(calendar_date: CalendarDate.today() ,grade: Grade.findByNameAndSection(5,"B") , title: "Class PTA Meeting" , description: "Parents meeting of all 5 B students" , startTime: "Evening 3.30 " , endTime: "Evening 5.30"  , flag: "GRADE" ).save(flush: true)
          new Event(calendar_date: CalendarDate.today() , title: "Arts Day" , description: "Annual Arts day" , startTime: "Morning 11.30 " , endTime: "Evening 4.00"  , flag: "SCHOOL" ).save()

          new Event(calendar_date: CalendarDate.getDate("08-04-2015") ,grade: Grade.findByNameAndSection(6,"A") , title: "Class PTA Meeting" , description: "Parents meeting of all 6 A students" , startTime: "Evening 3.30 " , endTime: "Evening 5.30"  , flag: "GRADE" ).save(flush: true)



		JSON.createNamedConfig('thin') {
			it.registerObjectMarshaller( Grade ) { Grade grade ->

				def output = [:]
				output['grade'] = grade.name.toString()
				output['section'] = grade.section

				return output
			}
		}

		JSON.registerObjectMarshaller( Grade ) { Grade grade ->
			
							[
							gradeName : grade.name.toString(),
							section : grade.section,
							 student : grade.students.collect{ Student std ->
			[studentId: std.studentId.toString(), studentName: std.studentName
				]
							}]
						}

		JSON.createNamedConfig('homework') {
			it.registerObjectMarshaller( Homework ) { Homework home ->



				def output = [:]
				output['subject'] = home.subject
				output['dueDate'] = home.dueDate.format("dd-MM-yyyy")
				output['homeGivenDate'] = home.dateCreated.format("dd-MM-yyyy")
				output['details'] = home.message

				return output
			}
		}


		JSON.createNamedConfig('student') {
			it.registerObjectMarshaller( Student ) { Student student ->

				def output = [:]
				output['studentId'] = student.studentId.toString()
				output['studentName'] = student.studentName

				return output
			}
		}




		JSON.registerObjectMarshaller(Student) {
			 Student student ->

				def output = [:]
				output['studentId'] = student.studentId.toString()
				output['studentName'] = student.studentName
				output['grade']=student.grade.name.toString()
				output['section']=student.grade.section
				output['classTeacherName']=student.grade.classTeacherName
				
				

				return output
			
		}
		JSON.registerObjectMarshaller(Subject) {
			Subject subject ->

			   def output = [:]
			   output['subjectId'] = subject.subjectId.toString()
			   output['subjectName'] = subject.subjectName

			   

			   return output
		   
	   }
		
		JSON.createNamedConfig('msg') {
			it.registerObjectMarshaller( Message ) { Message msg ->
			
					 return ['code': msg.code,
				'value': msg.value]
			}
		}


		JSON.registerObjectMarshaller(ExamSyllabus) {
			ExamSyllabus examSyllabus ->


				return  ['syllabus':  examSyllabus.syllabus,
						 'subjectName': examSyllabus.subject.subjectName,


				]


		}

		JSON.registerObjectMarshaller(Exam) {
			Exam subject ->



				return  ['examId':  subject.examId?subject.examId.toString():'',
						 'examType': subject.examType,
						 'schedule':subject.examSubjectSchedule,


				]

		}


		JSON.registerObjectMarshaller(Message) {

			Message m ->
				return ['messageId' : m.messageId.toString() ,
						'messageText' : m.messageText ,
						'messageTime' : m.messageTime.format('EEEE, dd MMMM yyyy, hh:mm:ss a') ,
						'from' : m.fromId ,
						'to' : m.toId


				]
		}



		JSON.registerObjectMarshaller(ExamSchedule) {
			ExamSchedule exSchedule ->

				return  ['subjectName':  exSchedule.subject.subjectName,
										 'subjectSyllabus':exSchedule.subjectSyllabus.syllabus,
										 'teacherName':exSchedule.teacher.teacherName,
										 'examStartTime':exSchedule.startTime? exSchedule.startTime.format('EEEE, dd MMMM yyyy, hh:mm:ss a'):'date not',

										 'examEndTime':exSchedule.endTime? exSchedule.startTime.format('EEEE, dd MMMM yyyy, hh:mm:ss a'):'date not' ]



		}

		JSON.createNamedConfig('exam') {
			it.registerObjectMarshaller( Grade ) { Grade msg ->

				return ['exams': msg.exams
				]
			}
		}
			
		JSON.createNamedConfig('teacherC') {
			it.registerObjectMarshaller( Teacher ) { Teacher teach ->

			

				 return  ['teacher':['teacherId': teach.id.toString(),
				'teacherName': teach.teacherName,
				'emailId':teach.teacherEmailId,
				'grades' : teach.grades
				]]
			}
		}
		
		JSON.createNamedConfig('teacherSub') {
			it.registerObjectMarshaller( Teacher ) { Teacher teach ->

			

				 return  [
				'subjects' : teach.subject
				]
			}
		}
		JSON.createNamedConfig('Success') {
			it.registerObjectMarshaller( Success ) { Success success ->

				def output = [:]
				output['success'] = 0
				output['failure'] = 1


				return output
			}
		}
		// Marshellers for classes

		JSON.registerObjectMarshaller( Guardian ) { Guardian g ->
			return [

					name : g.name,
					educational_qualification : g.educational_qualification,
					profession : g.profession,
					username : g.designation,
					mobileNumber : g.mobileNumber,
					emailId : g.emailId,
					officeNumber : g.officeNumber,
					children : g.getChildren()


				   ]
		}

		JSON.createNamedConfig('ParentAccInfo') {
			it.registerObjectMarshaller( Guardian ) { Guardian g ->



				return  ['accountInfo':['username': g.username,
										'name': g.name,
										'educational_qualification' : g.educational_qualification ,
										'profession' : g.profession,
										'designation' : g.designation ,
										'mobileNumber' : g.mobileNumber ,
										'emailId' : g.emailId,
										'officeNumber' : g.officeNumber,
										'numberOfChildren' : g.getChildren()?.size().toString(),

											]

						]


			}
		}


		JSON.createNamedConfig('getChildren') {
			it.registerObjectMarshaller( Student ) { Student s ->



				return                  [
										  'studentId': s.studentId?.toString(),
										  'registerNumber': s.registerNumber,
										  'studentName' : s.studentName ,
										  'grade' : s.grade?.name.toString(),
										  'section' : s.grade?.section,
										  'gender' : s.gender,
										  'present_address' : s.present_address ,
										  'no_of_siblings' : s.no_of_siblings.toString() ,
										  'dob' : s.dob.format("dd-MM-yyyy"),
										  'age' : s.getAge() ,
										  'present_guardian' : s.present_guardian


										]
			}
		}


		JSON.createNamedConfig('studentDetail') {
			it.registerObjectMarshaller( Student ) { Student s ->



				return                  [
										  'studentId': s.studentId?.toString(),
										  'registerNumber': s.registerNumber,
										  'studentName' : s.studentName ,
										  'grade' : s.grade?.name.toString(),
										  'section' : s.grade?.section,
										  'gender' : s.gender,
										  'present_address' : s.present_address ,
										  'no_of_siblings' : s.no_of_siblings.toString() ,
										  'dob' : s.dob,
										  'age' : s.getAge() ,
										  'present_guardian' : s.present_guardian,
										   'father' :   [
														   'id' : s.getFather()?.id.toString(),
														   'name' : s.getFather()?.name ,
														],
										  'mother' :   [
												  'id' : s.getMother()?.id.toString(),
												  'name' : s.getMother()?.name ,
										  ],
										  'local_guardian' :   [
												  'id' : (s.getLocalGuardian())? s.getLocalGuardian()?.id.toString() : "No local guardian" ,
												  'name' : (s.getLocalGuardian())? s.getLocalGuardian()?.name : "No local guardian" ,
										  ]

									  ]
			}
		}





		JSON.registerObjectMarshaller( Student ) { Student s ->
			return [

					studentId : s.studentId.toString() ,
					registerNumber : s.registerNumber,
					studentName : s.studentName ,
					gender : s.gender ,
					present_address : s.present_address ,
					no_of_siblings : s.no_of_siblings.toString() ,
					dob : s.dob ,
					studentPhoto : s.studentPhoto ,
					present_guardian : s.present_guardian ,
					grade : s.grade?.name.toString() ,
					section : s.grade?.section ,
				//	father: s?.getFather() ,
				//	mother: s?.getMother() ,
					local_guardian: (s.getLocalGuardian()) ? s.getLocalGuardian() : "No local guardian"

			]
		}

		JSON.registerObjectMarshaller( TimeTable ) { TimeTable t ->
			return [
					  subject : t.subject?.subjectName ,
					  day : t.day ,
					  teacher: t.teacher?.teacherName ,
					  grade: t.grade?.name.toString() ,
					  section: t.grade?.section



					]}

	JSON.registerObjectMarshaller( Address ) { Address a ->
			return [
					address : a.address ,
					place : a.place ,
					landmark: a.landmark

			]}


		JSON.registerObjectMarshaller( Homework ) { Homework h ->
			return [

					'homeworkList' : [

					homeworkId: h.homeworkId.toString(),
					grade : h.grade?.name.toString(),
					section : h.grade?.section ,
					subject: h.subject ,
					dueDate : h.dueDate.format("dd-MM-yyyy") ,
					homework: h.homework ,
					dateCreated : h.dateCreated.format("dd-MM-yyyy") ,
					student : h.student?.studentName ,
					studentId : h.student?.studentId.toString() ,
					message : h.message ,
					gradeFlag : h.gradeFlag


			]]
		}



		JSON.createNamedConfig('getTimeTable') {
			it.registerObjectMarshaller( TimeTable ) { TimeTable t ->



				return  [
							 subject: t.subject?.subjectName,
							 teacher: t.teacher?.teacherName ,
							 teacherId: t.teacher?.teacherId.toString(),
							 teacherPhoto: t.teacher?.teacherPhoto,
							 startTime : t.startTime ,
							 endTime : t.endTime
						 ]
			}
		}


			JSON.createNamedConfig('studentHomework') {
				it.registerObjectMarshaller( Homework ) { Homework h ->



					return  [
							 'homeworkId' : h.homeworkId.toString() ,
							 'subject'    : h.subject ,
							 'dueDate'    : h.dueDate.format("dd-MM-yyyy") ,
							 'dateCreated': h.dateCreated.format("dd-MM-yyyy") ,
							 'message'    : h.message,
							 'homework'   : h.homework
							]
				}
			}





		JSON.registerObjectMarshaller( Conversation  )
				{   cnv ->



					 return [
								 'threadId': cnv.threadId.toString(),
								 'numberOfMessages' : cnv.messages.size(),
								 //'fromDate': cnv.fromDate?.format('EEEE, dd MMMM yyyy, hh:mm:ss a'),
								 'fromId': cnv.fromId ,
								 'toId': cnv.toId,
								 'inTrash': cnv.inTrash,
								 'isRead': cnv.isRead,
								 'title': cnv.title,
								 'toDate': cnv.toDate?.format('EEEE, dd MMMM yyyy, hh:mm:ss a'),
								 'messages' : cnv.messages



							]

				}
		JSON.createNamedConfig('msgList')
				{
					it.registerObjectMarshaller( Message ) { Message m ->
						return [
								  'messageId' : m.messageId.toString(),
								  'fromId' : m.fromId,
								  'toId' : m.toId ,
								  'messageText' : m.messageText,
								  'messageTime' : m.messageTime?.format('EEEE, dd MMMM yyyy, hh:mm:ss a')

							   ]}
				}



            JSON.registerObjectMarshaller( Event )
					{
						e ->


                                    [
									     'eventId' : e.eventId.toString(),
                                    'title' : e.title ,
                                    'description' : e.description ,
                                    'eventDate' : e.calendar_date?.calendar_date.format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") ,
                                    'startTime' : e.startTime ,
                                    'endTime' : e.endTime,
                                    'eventType': (e.flag == 'SCHOOL') ? "School Event" : "Class Event of class  "+e.grade.name+" "+e.grade.section

							        ]





					}



		JSON.createNamedConfig('TeacherListForParent')
				{
					it.registerObjectMarshaller(Teacher) { Teacher t ->

						return [ 'teacherId' : t.id.toString() ,
								 'username' : t.username,
								 'teacherName' : t.teacherName ,
								 'teacherPhoto': t.teacherPhoto,
                                 'teacherEmailId' : t.teacherEmailId ,
								 'phoneNo' : t.phoneNo ,
								  'subjects':  "English , Hindi , Maths"
						       ]


					}
				}


		JSON.createNamedConfig('TeachersSubjects')
				{
					it.registerObjectMarshaller(GradeTeacherSubject) { GradeTeacherSubject g ->

                           return [ 'gradeId' : g.grade?.gradeId.toString() ,
								    'gradename': g.grade?.name.toString(),
								    'section':g.grade?.section,
								    'subjectName':g.subject?.subjectName ,
								    'subjectId':g.subject?.subjectId.toString()


                           ]

					}
				}





		JSON.registerObjectMarshaller( Attendance ) { Attendance a ->
			return [



							'attendanceId' : a.attendanceId.toString(),
					        'date' : a.date.format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
							'grade' : a.grade?.name.toString() ,
							'section' : a.grade?.section,
					        'total_students' : a.grade?.students.size().toString(),
					         'total_absent' : a.absentees.size().toString() ,
							'absentees':a.absentees


					]
		}



		JSON.createNamedConfig('absentees')
				{
					it.registerObjectMarshaller(Student) { Student s ->

					   return ['stdentId' : s.studentId.toString() ,
							   'studentName':s.studentName
					    ]

					}
				}


        JSON.registerObjectMarshaller( TimeTable ) { TimeTable t ->
            return [



                    'grade' : t.grade.name?.toString(),
                    'section' : t.grade?.section ,
                    'subject' : t.subject?.subjectName,

                   'day' : t.day ,
                   'startTime': t.startTime ,
                   'endTime' : t.endTime


            ]
        }



        JSON.createNamedConfig('teacherWeekTT')
                {
                    it.registerObjectMarshaller(Teacher) {
                        Teacher t ->
                            return [
                                    'teacherId' : t.id.toString() ,
                                    'teacherName'  : t.teacherName ,
                                    'timetable' : t.timetables
                            ]

                    }


                }



			JSON.createNamedConfig('TeachergetTimeTable') {
				it.registerObjectMarshaller( TimeTable ) { TimeTable t ->



					return  [
							grade: t.grade?.name.toString() ,
							section: t.grade?.section ,
							subjectName: t.subject?.subjectName.toString(),
							subjectId: t.subject?.subjectId.toString() ,
							startTime : t.startTime ,
							endTime : t.endTime
					]
				}
			}







	}

	def destroy = {
	}
}