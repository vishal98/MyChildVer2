package ghumover2

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured;

import java.text.SimpleDateFormat

import org.hibernate.criterion.CriteriaSpecification;

@Secured(['ROLE_PARENT'])
class ParentController {
	
	
	
	def getParentDetails(){
		def article=new Guardian()
	//	def articleList=article.list()
		//int id= Integer.parseInt(params.id)
		def trek=article.findAllWhere(username:params.username)
		//println articleList 
		
		

			render trek as JSON
		
	
	}
	
	def accountInfo(){
		def article=new Guardian()
		def articleList=article.list()
		int id= Integer.parseInt(params.id)
		def trek=article.findAllWhere(parentId:id)
		println articleList
		
		
		JSON.use('father') {
			render trek as JSON
		}
	
	}
	def getHomeWork(){
		
	

 Long sid= Integer.parseInt(params.stdid)
		Long clasid= Long.parseLong(params.classid)
		
		
		
		def c = Homework.createCriteria()

		def homeworkList = c.list {
//			createAlias('student', 'std', CriteriaSpecification.INNER_JOIN)
//			createAlias('grade', 'grd', CriteriaSpecification.INNER_JOIN)
//			and {
//				eq('std.studentId',sid)
//			eq('grd.gradeId',clasid)
//		}
			
			or{	grade {
					eq('gradeId',sid)
				
				}
			student {
				eq('studentId',clasid)
			
			}
			}
			
		}
		
		//JSON.use('homework') {
			render homeworkList as JSON
	//	}
	}
	def getTodayHomeWork(){
		def article=new Homework()
	
		def articleList=article.list()
		int tid= Integer.parseInt(params.id)
		
		Date date = new Date()
		def from = date.clearTime()
		def to = from + 1
		//def query = Homework.where{
			//studentId == tid
			//dateCreated in (from .. to)
			
		//}
		def results = query.list()
		render results as JSON
		/*JSON.use('father') {
			render trek as JSON
		}*/
	}
	def getExamDetails(){
		
		//def exam=new Exam()
		def exam = Grade.createCriteria()
		Long clasid= Integer.parseInt(params.classid)
		def homeworkList = exam.list {
		eq('gradeId',clasid)
		
		
		}.first()
		JSON.use('exam') {
			
		
		render homeworkList as JSON
		}
		
	}

	def getExamSchedule(){
		def exam=new ExamSchedule()
		int tid= Integer.parseInt(params.examid)
		def homeworkList = exam.list {
		exam {
			eq('id',tid)
		
		}
		JSON.use('exam') {
			
		render exam as JSON
		}
		}
		
		
		
	}
	def getSyllabus(){
		def article=new Homework()
		def articleList=article.list()
		int tid= Integer.parseInt(params.id)
		
		Date date = new Date()
		def from = date.clearTime()
		def to = from + 1
		//def query = Homework.where{
			//studentId == tid
			//dateCreated in (from .. to)
			
		//}
		def results = query.list()
		render results as JSON
		/*JSON.use('father') {
			render trek as JSON
		}*/
	}
	
}
