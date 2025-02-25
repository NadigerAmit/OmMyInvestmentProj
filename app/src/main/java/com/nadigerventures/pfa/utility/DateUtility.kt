package com.nadigerventures.pfa.utility

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.nadigerventures.pfa.ui.screens.dateFormat
import java.text.ParseException

import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
private val TAG = "DateUtility"

class DateUtility {
    companion object{

        fun showDatePickerDialog(context: Context,
                                 inputDate:String,
                                 dateFormat:String,
                                 onChange: (String) -> Unit = {}) {
            val calendar = getCalendar(inputDate, dateFormat) ?: return
            DatePickerDialog(
                context, { _, year, month, day ->
                    onChange(getPickedDateAsString(year, month, day, dateFormat))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fun getCalendar(inputDate:String,dateFormat:String): Calendar? {
            //return Calendar.getInstance()
            return if (inputDate.isEmpty())
                Calendar.getInstance()
            else
                getLastPickedDateCalendar(inputDate,dateFormat)

        }

        private fun getLastPickedDateCalendar(inputDate:String, dateFormat:String): Calendar? {
            val dateFormat = SimpleDateFormat(dateFormat)
            val calendar = Calendar.getInstance()
            try {
                calendar.time = dateFormat.parse(inputDate)
            } catch (e: ParseException) {
                Log.e(TAG,""+e.message)
                return null
            }

            return calendar
        }

        fun getPickedDateAsString(year: Int, month: Int, day: Int,
                                          dateFormat:String): String {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val dateFormat = SimpleDateFormat(dateFormat)
            return dateFormat.format(calendar.time)
        }

        fun getNumberOfDaysBetweenTwoDays(fromDate: Calendar, toDate: Calendar): Long {
            val fromDateInString = getPickedDateAsString(
                fromDate.get(Calendar.YEAR),
                fromDate.get(Calendar.MONTH),
                fromDate.get(Calendar.DAY_OF_MONTH)
                , dateFormat
            )
            val toDateInString = getPickedDateAsString(
                toDate.get(Calendar.YEAR),
                toDate.get(Calendar.MONTH),
                toDate.get(Calendar.DAY_OF_MONTH)
                , dateFormat
            )
            val millionSeconds = fromDate.time.time-toDate.time.time
            var numberOfDays = TimeUnit.MILLISECONDS.toDays(millionSeconds)
            val days: Double = millionSeconds / TimeUnit.DAYS.toMillis(1).toDouble()

            if(fromDateInString != toDateInString){
                if(days >numberOfDays){
                    numberOfDays += 1
                }
            }

            /*
            Log.i("DateUtility","FromDate = $fromDateInString  ToDate = $toDateInString ," +
                    "diffMillionSec = $millionSeconds " + "isBothDatesSame = " +
                    "${fromDateInString == toDateInString}"+
                    " days = ${TimeUnit.MILLISECONDS.toDays(millionSeconds)}  " +
                    "FinalNumOfDays = $numberOfDays " +
                    "Days = $days" )

             */
            return numberOfDays
        }

        fun getDateInCalendar(milliSeconds: Long, dateFormat: String?): Calendar? {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return calendar
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getPeriodBetween2Dates(from:Calendar, to:Calendar):String {
            // parse the date with a suitable formatter
            val calendar = Calendar.getInstance()
            val tz = calendar.timeZone
            // Getting zone id
            // Getting zone id
            val zoneId: ZoneId = tz.toZoneId()

            val localFromDateTime: LocalDateTime = LocalDateTime.ofInstant(from.toInstant(), zoneId)
            val localToTime: LocalDateTime = LocalDateTime.ofInstant(to.toInstant(), zoneId)

            val fromDate = LocalDate.parse("04112005", DateTimeFormatter.ofPattern("ddMMyyyy"))
            // get today's date
            val today = LocalDate.now()
            // calculate the period between those two
            var period = Period.between(localFromDateTime.toLocalDate(), localToTime.toLocalDate())
            // and print it in a human-readable way
            var duration:String = ""
            /*
            duration= ""+period.getYears() + " years, " + period.getMonths() + " months and " +
                    ""+ period.getDays() + " days"
             */

            if(period.days <0) {
                duration = ""+ period.days + " days"
            } else {
                if(period.days >0) {
                    duration = ""+ period.days + " days"
                }
                if(period.months >0) {
                    duration = ""+period.months + " months "+duration
                }
                if(period.years >0) {
                    duration = ""+period.years +" years, " +duration
                }
            }
            /*
            println("The difference between " + fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + " and " + today.format(DateTimeFormatter.ISO_LOCAL_DATE) + duration)

             */
            return duration
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun localDateToCalendar(dateTime:LocalDateTime):Calendar {
           // val localDateTime = LocalDateTime.now()
            val instant: Instant = dateTime.atZone(ZoneId.systemDefault()).toInstant()
            val date = Date.from(instant)
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }
    }
}

