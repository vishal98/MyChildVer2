package ghumover2

import org.grails.databinding.BindingFormat

import java.text.SimpleDateFormat
import java.util.Date
import java.sql.Timestamp

class CalendarDate
{
	@BindingFormat('dd-MM-yyyy')
		Date calendar_date
		Integer year
		Integer quarter
		Integer month
		Integer day_of_month
		Integer day_of_week
		 String monthName
		 String dayName
		 Integer week_of_year
		 Boolean isWeekday
		 Boolean isHoliday
		 Boolean isPayday
		 String holiday_description
		 static hasMany = [events:Event]

	 static constraints = {

		 year(nullable:true)
		 quarter(nullable :true)
		 month(nullable :true)
		 day_of_month(nullable:true)
		 day_of_week(nullable :true)
		 monthName(nullable :true)
		 dayName(nullable :true)
		 week_of_year(nullable :true)
		 isWeekday(nullable :true)
		 isHoliday(nullable :true)
		 isPayday(nullable :true)
		 holiday_description(nullable :true)
	 }

	static mapping = {
		version false

	}



}
