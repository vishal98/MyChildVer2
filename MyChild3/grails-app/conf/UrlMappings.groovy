class UrlMappings {
	
		static mappings = {
			"/$controller/$action?/$id?(.$format)?"{
				constraints {
					// apply constraints here
				}
			}

			"/"(view:"/index")
			"500"(view:'/error')

"/Parent/username/$username"{
					controller = "parent"
					action = "getParentDetails"
			}


			// TIME TABLE
			"/app/timetable/$gradeId/$section"
					{
						controller = "TimeTable"
						action = "getWeekTimetable"

					}

			"/app/timetable/$gradeId/$section/$day"
					{
						controller = "TimeTable"
						action =  "getDayTimeTable"
					}







			// PARENT ACCOUNT DETAILS
			"/app/parent/accountInfo/$id"
					{
						controller = "Guardian"
						action = "getAccountInfo"
					}
			"/app/parent/$id/getChildren"
					{
						controller = "Guardian"
						action = "getAllChildren"

					}


			"/Parent/exam/$classid"{
				controller = "parent"
				action = "getExamDetails"
			}

			"/Parent/child/schedule/$examId"{
				controller = "parent"
				action = "getExamSchedule"
			}





			//HOMEWORK

			"/app/getHomework/student/$studentId"
					{

						controller = "Homework"
						action = "getStudentHomework"
					}




	
		  
					"/app/getHomework/student/$studentId/$dateAssigned"

					{

						controller = "Homework"
						action = "getStudentHomeworkByDate"
					}

			"/app/teacher/homework/save"(controller: "Homework", action: "saveHomework" ,parseRequest: true)







			//TEACHER


			"/Teacher/studentList/$gradeId"{
					controller = "teacher"
					action = "getStudentList"
			}

			"/Teacher/$studentList/$grade/$section"
					  {
						  controller = "teacher"
						  action = "getStudentListByGradeSection"
					  }
			"/Teacher/id/$userId"{
				controller = "teacher"
				action = "getTeacherDetails"
			}
			"/app/subject/$grade/$section"{
				controller = "teacher"
				action = "getSubject"
			}


			"/app/teacher/getAllSubjectsInAllGrades"
					{
						controller = "teacher"
						action = "getAllSubjectsInAllGrades"
					}

			"/app/teacher/sendMail/$grade/$section"(controller: "teacher", action: "sendMailToParents" ,parseRequest: true)







			//apis for conversations


			"/app/conversations/get"
					{
						controller = "conversation"
						action = "getCurUserConversations"

					}


				 "/app/conversations/get/$userId"
						 {
							 controller = "conversation"
							 action = "getUserConversations"
						 }



				"/app/conversations/getFrom/$userId"
						{
								controller = "conversation"
								action = "getConversationFromUser"

						}

			"/app/conversations/getFromTo/$fromId/$toId"
					{
						controller = "conversation"
						action = "getConversationFromAndTo"

					}

			 "/app/conversations/new"(controller: "conversation", action: "newMail" ,parseRequest: true)


			"/app/conversations/reply"(controller: "conversation" , action: "replyMsg" , parseRequest: true)



			"/app/conversations/sent"
			{
				controller = "conversation"
				action = "getSentConversations"

			}





		   //Get user list

			"/app/parent/getUsers/$studentId"
					{
						controller = "guardian"
						action = "getTeacherList"
					}


					
					
					//Attendance
					
							"/app/attendance/grade/$grade/$section/$date"
									{
					
										controller = "attendance"
										action = "getGradeAttendance"
					
									}
							"/app/teacher/attendance/save"(controller: "attendance" , action: "saveAttendance" , parseRequest: true)
					
					
						   "/app/attendance/student/$studentId/from/$fromDate/to/$toDate"
								   {
									   controller = "attendance"
									   action = "getStudentAttendanceBetween"
					
								   }
					
							"/app/attendance/student/$studentId/month/$month/$year"
									{
										 controller = "attendance"
										action = "getAttendanceOfMonth "
									}
						

			
			
			"/app/teacher/timetable/"
			{
				controller = "TimeTable"
				action = "getTeacherWeekTimetable"
			}
	       "/app/teacher/timetable/$day"
			 {
				 controller = "TimeTable"
				 action = "getTeacherDayTimetable"

			 }



			//Student list Api

	/*  "/app/get/students/$grade/$section"
			  {
				  controller = "student"
				  action = "getStudentsOfClass"

			  }
*/


			//mail pendings

			"/app/conversations/sent"
					{
						controller = "conversation"
						action = "getSentConversations"

					}




			//  Teacher events

			 "/app/events/teacher/$month/$year"
					 {
						 controller = "Teacher"
						 action = "getTeacherMonthEvents"

					 }
			"/app/events/teacher/$date"
					{
						controller = "teacher"
						action = "getTeacherEvents"

					}

            // student events
             "/app/events/student/$studentId/$month/$year"
					 {

						 controller = "guardian"
						 action = "getStudentMonthEvents"

					 }

			"/app/events/student/$studentId/$date"
					{
						controller = "guardian"
						action = "getStudentClassEvents"

					}
					
					"/app/teacher/timetable/week"
					{
						controller = "teacher"
						action = "getTeacherWeekTimetable"
					}

					


			/*
			Admin Apis
			 */

			"/app/admin/grades"(resources:"grade" ,  includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])

			"/app/admin/teachers"(resources:"teacher" , includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])

			"/app/admin/students"(resources:"student" , includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])

			"/app/admin/events"(resources:"event" , includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])

			"/app/admin/guardian"(resources:"guardian"  , includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])



			"/app/admin/schoolclasses"(resources: "SchoolClass" , includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])

			"/app/exam/results"(resources: "ExamResult" , includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])

			"/app/exams"(resources:"Exam" ,includes:['index', 'show' , 'save' , 'update' , 'delete','create','patch'])


			//mail pendings
			
						"/app/conversations/sent"
								{
									controller = "conversation"
									action = "getSentConversations"
			
								}
			
			
			
			
						//  Teacher events
			
						 "/app/events/teacher/$month/$year"
								 {
									 controller = "Teacher"
									 action = "getTeacherMonthEvents"
			
								 }
						"/app/events/teacher/$date"
								{
									controller = "teacher"
									action = "getTeacherEvents"
			
								}
			
						// student events
						 "/app/events/student/$studentId/$month/$year"
								 {
			
									 controller = "guardian"
									 action = "getStudentMonthEvents"
			
								 }
			
						"/app/events/student/$studentId/$date"
								{
									controller = "guardian"
									action = "getStudentClassEvents"
			
								}
								
								"/app/teacher/timetable/week"
								{
									controller = "teacher"
									action = "getTeacherWeekTimetable"
								}
			
								
			

								// TIME TABLE
								"/app/getuserAlbums"
										{
											controller = "FileManagerRead"
											action = "readImage"
					
										}
                           
										
										"/app/exams/result/$studentId"
										{
											controller = "ExamDetails"
											action = "studentResult"
						
										}

	
		}
		}

