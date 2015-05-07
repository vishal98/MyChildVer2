package ghumover2

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import javax.management.InstanceOfQueryExp;

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;


class MydataController {

    def index() { 
		def rest = new RestBuilder()
		def resp = rest.post("https://api.pushbots.com/push/one"){
			header 'x-pushbots-appid', '550e9e371d0ab1de488b4569'
			header 'x-pushbots-secret', 'e68461d7755b0d3733b4b36717aea77d'
	json {
		token ="APA91bFS1Qg5lkFPOn6D6Liqc4pnUWcvTiyyn6XIW8RL5y-10rBt5PVg2XaVY5z7EaCGxAZN959OGpl3CYhK1pQVEm4AY7RD5DHSEz691VwOI8CElsgjnClHB7zfk5ioZ7SWAOlIVRA3"
			 platform ="1"
			  msg="Push one Notification from API  SArath 3/30call"
			   sound="ding"
			   badge="badge"
				payload="JSON"
	  
	}
		
			
		}
		
		System.out.print("resp val : "+resp.json)
		render resp.json as JSON
	
	
		
		//render (view:'/')
	}
	
	
	
		def test() {
	
			Long school_id = Long.parseLong(params.school_id)
			FileInputStream fis = null;
			try {
				//store file the get data from that file
				def f = request.getFile('excelFile')
				//validate file or do something crazy hehehe
				//now transfer file
				def webrootDir = servletContext.getRealPath("/")+"schoolData-"+school_id //app directory
	
				File file = new File(webrootDir)
				if(!file.exists()){
					file.mkdirs();
				}
				File fileDest = new File(webrootDir,f.getOriginalFilename())
	
				f.transferTo(fileDest)
				School school =  School.get(school_id);
				fis = new FileInputStream(fileDest);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet1 = workbook.getSheetAt(0);//
				Map<String ,Grade> classMap = new HashMap<String ,Grade>();
				Map gradeMap = new HashMap();
				Map teacherMap = new HashMap();
				Map<String,Department> demptIDMap = new HashMap<String,Department>();
				Map<String,ArrayList<Teacher>> deptMap = new HashMap<String,ArrayList<Teacher>>();
				def classobject= null;
				Grade grade = null;
				User user =null;
				//sheet1.getLastRowNum()sheet1.getLastRowNum()
				for(int i=1;i<sheet1.getLastRowNum();i++){
					Guardian guardain =null;
					XSSFRow hssfRow2 = sheet1.getRow(i);
					if(hssfRow2 != null){
						String gaurdainEmail = hssfRow2.getCell(3).getStringCellValue();
						if(gaurdainEmail != null){
							user = User.createCriteria().get  {
								or{
									eq("teacherEmailId",gaurdainEmail)
									eq("emailId",gaurdainEmail)
								}
	
							}
	
							if(null != user){
								if(user  instanceof  Guardian){
									guardain = user;
								}else{
									ErrorLogInExcellUpload ob = new ErrorLogInExcellUpload();
									ob.lineNo = i
									ob.sheetName ="sheet1"
									ob.errorcause = "email id already used as a teacher "
									ob.userEmail = gaurdainEmail
									ob.save(flush:true)
									continue ;
	
								}
							}
							String class_grade = hssfRow2.getCell(0).getStringCellValue();
							int lenght = class_grade.length();
							String class_name = class_grade.substring(0,lenght-1 )
							String grade_name = class_grade.substring(lenght-1 )
							if(classMap.get(class_grade) == null){
								def sc = SchoolClass.createCriteria()
	
								classobject =	sc.get {
									and {
										eq("school.school_id",school_id)
										eq("className",class_name)
									}
								}
								if(classobject == null){
									SchoolClass schoolClass = new SchoolClass()
									schoolClass.className = class_name
									schoolClass.school = school
									schoolClass.classTags = school.tags +",\""+schoolClass.className+"\""
									schoolClass.save(flush:true);
									classobject = schoolClass
									grade = new Grade();
									grade.section = grade_name
									grade.name = (int)classobject.classId
									grade.gradetags = schoolClass.classTags +",\""+schoolClass.className+"-"+grade.section+"\""
									grade.schoolClass =schoolClass
									grade.save(flush:true);
								}
								else{
									def gradeCriteria = Grade.createCriteria()
	
									grade =	gradeCriteria.get {
										and {
											eq("name",(int)classobject.classId)
											eq("section",grade_name)
										}
									}
	
									//SchoolClass schoolClass =  SchoolClass.get(class_id)
	
									if(null == grade ){
										grade = new Grade();
										grade.section = grade_name
										grade.gradetags = classobject.classTags +",\""+classobject.className+"-"+grade.section+"\""
										grade.schoolClass =classobject
										grade.name = (int)classobject.classId
										grade.save(flush:true);
									}
								}
								classMap.put(class_grade, grade)
								// for guardian
								if(null == guardain) {
									guardain =	new Guardian()
									guardain.name = hssfRow2.getCell(2).getStringCellValue();
									guardain.username=hssfRow2.getCell(3).getStringCellValue();
									guardain.password= "123"
									guardain.educational_qualification= ""
									guardain.designation= ""
									guardain.profession= ""
									guardain.emailId= hssfRow2.getCell(3).getStringCellValue();
									guardain.officeNumber= ""
									guardain.mobileNumber= hssfRow2.getCell(4).getNumericCellValue();
									guardain.save(flush:true);
	
								}
								// add student
	
								Student student =  new Student();
								student.grade = grade
								student.registerNumber = ""
								student.studentName  = hssfRow2.getCell(1).getStringCellValue();
								student.gender= ""
								student.dob = new Date();
								student.studentPhoto="photo.jpg"
								student.no_of_siblings=2
								student.present_guardian="Father"
								student.present_address =new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala").save()
								student.save(flush:true);
	
								def	father = Guardian.findByUsername(hssfRow2.getCell(3).getStringCellValue())
								//def mother = Guardian.findByUsername("guardian2@gmail.com")
								String father_tags  = father.tags;
								//String mother_tags  = mother.tags;
	
	
								student.setAsFather( father )
								//student.setAsMother( mother )
								if(father_tags == null){
									father_tags = grade.gradetags +",\"G\",\"S-"+student.studentId+"\""
								}else{
									father_tags = father.tags+",\"s-"+student.studentId+"\""
								}
	
								Guardian.executeUpdate("Update Guardian set tags = '"+father_tags+"' where username ='"+father.username+"'")
	
							}else{
								if(null == guardain) {
									guardain =	new Guardian()
									guardain.name = hssfRow2.getCell(2).getStringCellValue();
									guardain.username=hssfRow2.getCell(3).getStringCellValue();
	
									guardain.password= "123"
									guardain.educational_qualification= ""
									guardain.designation= ""
									guardain.profession= ""
									guardain.emailId= hssfRow2.getCell(3).getStringCellValue();
									guardain.officeNumber= ""
									guardain.mobileNumber= hssfRow2.getCell(4).getNumericCellValue();
	
									guardain.save(flush:true);
	
								}
	
								Student student =  new Student();
								student.grade =classMap.get(class_grade)
								student.registerNumber = ""
								student.studentName  = hssfRow2.getCell(1).getStringCellValue();
								student.gender= ""
								student.dob = new Date();
								student.studentPhoto="photo.jpg"
								student.no_of_siblings=2
								student.present_guardian="Father"
								student.present_address =new Address(address: "Sample Address" , landmark: "Cochin" , place: "Kerala").save()
								student.save(flush:true);
	
								def	father = Guardian.findByUsername(hssfRow2.getCell(3).getStringCellValue())
								//def mother = Guardian.findByUsername("guardian2@gmail.com")
								String father_tags  = father.tags;
								//String mother_tags  = mother.tags;
	
	
								student.setAsFather( father )
								//student.setAsMother( mother )
								if(father_tags == null){
									father_tags = grade.gradetags +",\"G\",\"S-"+student.studentId+"\""
								}else{
									father_tags = father.tags+",\"s-"+student.studentId+"\""
								}
	
								Guardian.executeUpdate("Update Guardian set tags = '"+father_tags+"' where username ='"+father.username+"'")
	
							}
	
						}
	
					}
	
				}
	
	
				XSSFSheet sheet2 = workbook.getSheetAt(1);
				Long userId= User.createCriteria().get { projections { max "id" } } as Long
				//sheet2.getLastRowNum() sheet2.getLastRowNum()
				Department dept =null;
				for(int i=1;i<sheet2.getLastRowNum();i++){
					XSSFRow hssfRow2 = sheet2.getRow(i);
					if(hssfRow2 != null){
						if(null != hssfRow2.getCell(0)){
							String dept_name = hssfRow2.getCell(0).getStringCellValue()
	
							if(demptIDMap.get(dept_name) == null) {
								def deptCriteria = Department.createCriteria()
								dept  =	deptCriteria.get {
									and {
										eq("dept_name",dept_name)
										eq("school_id",school_id)
									}
								}
								try{
									if(null == dept){
										dept = new Department()
										dept.dept_name = hssfRow2.getCell(0).getStringCellValue()
	
										dept.school_id = school_id
										dept.save();
	
									}}
								catch(Exception e){
									println e.getMessage() +"-------------- error arise";
	
								}
	
								demptIDMap.put(dept_name, dept)
							}else{
								dept = demptIDMap.get(dept_name)
							}
	
							//addd teacher
	
							String teachermail = hssfRow2.getCell(2).getStringCellValue()
							user = User.createCriteria().get  {
								or{
									eq("teacherEmailId",teachermail)
									eq("emailId",teachermail)
								}
	
							}
							if(null == user){
								Teacher t = new Teacher()
								if(null == userId)
									userId =1
								else
									userId = userId+1
	
								t.school_id = school_id
								t.teacherName = hssfRow2.getCell(1).getStringCellValue()
								t.teacherPhoto =""
								t.teacherEmailId =teachermail
								t.phoneNo =""
								t.username= teachermail
								t.password= "123"
								//t.teacherId = userId
								t.deviceToken = null;
								t.save(flush:true);
								// add teacher to section
	
								XSSFCell cell3value = hssfRow2.getCell(3)
								if(null != cell3value){
									String class_grade = cell3value.getStringCellValue();
									int lenght = class_grade.length();
									String class_name = class_grade.substring(0,lenght-1 )
									String grade_name = class_grade.substring(lenght-1 )
									Grade grade1 = classMap.get(cell3value.getStringCellValue())
									if(null != grade1){
										grade1.addToTeachers(t)
										grade1.save(flush:true)
	
										String mathew_tags  = t.tags;
										Grade.executeUpdate("Update Grade set classTeacherId = "+t.id+" where gradeId ="+grade1.gradeId)
										if(mathew_tags == null ){
											mathew_tags = grade1.gradetags +",\"T-T\",\"T-"+t.id+"\"," +dept.dept_tags
										}else{
											if(-1< mathew_tags.indexOf("T_T")){
												mathew_tags = t.tags+",\"T-"+t.id+"\"," +dept.dept_tags
											}else{
												mathew_tags = grade1.gradetags +",\"T-T\",\"T-"+t.id+"\"," +t.tags+","+dept.dept_tags
											}
	
										}
										t.tags  = mathew_tags;
										t.save(flush:true);
										dept.addToTeachers(t)
										dept.save(flush:true);
	
									}else{
										// if already calls is created that time checking
	
										def sc = SchoolClass.createCriteria()
										classobject =	sc.get {
											and {
												eq("school.school_id",school_id)
												eq("className",class_name)
											}
										}
										if(classobject == null){
											SchoolClass schoolClass = new SchoolClass()
											schoolClass.className = class_name
											schoolClass.school = school
											schoolClass.classTags = school.tags +",\""+schoolClass.className+"\""
											schoolClass.save(flush:true);
											classobject = schoolClass
											grade = new Grade();
											grade.section = grade_name
											grade.name = (int)classobject.classId
											grade.gradetags = schoolClass.classTags +",\""+schoolClass.className+"-"+grade.section+"\""
											grade.schoolClass =schoolClass
											grade.save(flush:true);
										}
										else{
											def gradeCriteria = Grade.createCriteria()
	
											grade =	gradeCriteria.get {
												and {
													eq("name",(int)classobject.classId)
													eq("section",grade_name)
												}
											}
	
											//SchoolClass schoolClass =  SchoolClass.get(class_id)
	
											if(null == grade ){
												grade = new Grade();
												grade.section = grade_name
	
												grade.gradetags = classobject.classTags +",\""+classobject.className+"-"+grade.section+"\""
												grade.schoolClass =classobject
												grade.name = (int)classobject.classId
												grade.save(flush:true);
											}
										}
	
										grade.addToTeachers(t)
										grade.save(flush:true)
										Grade.executeUpdate("Update Grade set classTeacherId = "+t.id+" where gradeId ="+grade.gradeId)
										String mathew_tags  = t.tags;
										if(mathew_tags == null ){
											mathew_tags = grade1.gradetags +",\"T-T\",\"T-"+t.id+"\"," +dept.dept_tags
										}else{
											if(-1< mathew_tags.indexOf("T_T")){
												mathew_tags = t.tags+",\"T-"+t.id+"\"," +dept.dept_tags
											}else{
												mathew_tags = grade1.gradetags +",\"T-T\",\"T-"+t.id+"\"," +t.tags+","+dept.dept_tags
											}
	
										}
	
										t.tags  = mathew_tags;
										t.save(flush:true);
										dept.addToTeachers(t)
										dept.save(flush:true);
										classMap.put(class_grade, grade)
	
	
									}
								}
	
							}else{
								ErrorLogInExcellUpload ob = new ErrorLogInExcellUpload();
	
								ob.lineNo = i
								ob.sheetName ="sheet2"
								ob.errorcause = "email id already used "
								ob.userEmail = teachermail
								ob.save(flush:true)
	
								// email id already used by other
							}
						}
					}else{
	
					}
	
				}
	
	
			}catch(Exception e){
				println e.getMessage();
	
	
			}
			Map ob = new HashMap();
			ob.putAt("datauploaded", "success");
	
			render ob as JSON
	
		}
	
	}
	

