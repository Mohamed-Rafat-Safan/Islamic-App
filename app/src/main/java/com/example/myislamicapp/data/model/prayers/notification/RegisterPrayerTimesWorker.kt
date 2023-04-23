package com.example.myislamicapp.data.model.prayers.notification

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.myislamicapp.data.model.prayers.PrayersTiming
import com.example.myislamicapp.data.model.prayers.Timings
import com.example.myislamicapp.utils.Constant.Companion.NOTIFICATION_CONTENT_KEY
import com.example.myislamicapp.utils.Constant.Companion.NOTIFICATION_TITLE_KEY
import com.mohamedrafat.newsapp.api.RetrofitInstance
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class RegisterPrayerTimesWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {


    override fun doWork(): Result {
        return try {
            val calendar: Calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH] + 1

            val preferences = PrayersPreferences(applicationContext)
            val city: String = preferences.getCityNameEn()

            val timesResponse = RetrofitInstance.api.getPrayers(city, "Egypt", 1, month, year).execute()
            if (timesResponse.isSuccessful) {
                val data = timesResponse.body()?.data    // return all day in one month
                for (day in 1..data?.size!!) {
                    val timings = data[day].timings   // هيجيب كل list من الصلاوات الي في اليوم الواحد
                    val prayers = convertFromTimings(timings)  // هنا هيجيب الاوقات الي محتاجها فقط
                    prayers.forEach { prayerTiming ->
                        val prayerTag = "$year/$month/$day ${prayerTiming.prayersName}"
                        val delay: Long = calculatePrayerDelay(year, month, day, prayerTiming) // الوقت المنتظر لهذه الصلاه

                        if (delay > 0) {
                            val input = Data.Builder()
                                .putString(NOTIFICATION_TITLE_KEY, "صلاة "+prayerTiming.prayersName)
                                .putString(NOTIFICATION_CONTENT_KEY, "حان الآن موعد الصلاة")
                                .build()

                            val registerPrayerRequest: OneTimeWorkRequest = OneTimeWorkRequest
                                .Builder(AzanNotificationWorker::class.java)
                                .addTag(prayerTag)  // this to prevent the repeat if more prayer have the same time
                                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                                .setInputData(input)
                                .build()

                            WorkManager.getInstance(applicationContext)
                                .enqueueUniqueWork(
                                    prayerTag,
                                    ExistingWorkPolicy.REPLACE,  // هنا لو حصل تكرار هيشيل القديم ويحط الجديد
                                    registerPrayerRequest
                                )
                        }
                    }
                }

                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }


    // هذه function علشان تحسب الوقت المنظر لكل صلاه في اليوم
    private fun calculatePrayerDelay(
        year: Int,
        month: Int,
        day: Int,
        prayerTiming: PrayersTiming
    ): Long {
        val pattern = "yyyy/MM/dd HH:mm"
        val decimalFormat = DecimalFormat("00")
        val time: String = prayerTiming.prayersTime.split(" ").get(0)
        val prayerDate = "$year/${decimalFormat.format(month)}/${decimalFormat.format(day)} $time"
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            val date: Date = format.parse(prayerDate)!!    // convert string to date
            val currentTime = System.currentTimeMillis()
            Log.d("TAG", "calculatePrayerDelay: $date")
            Log.d("TAG", "calculatePrayerDelay: diff = " + (date.time - currentTime) + " " + date.time)

            date.time - currentTime

        } catch (e: ParseException) {
            e.printStackTrace()
            -1
        }
    }


    fun convertFromTimings(timings: Timings?): ArrayList<PrayersTiming> {
        val res: ArrayList<PrayersTiming> = ArrayList()
        if (timings != null) {
            res.add(PrayersTiming("Fajr", timings.Fajr, "AM"))
            res.add(PrayersTiming("Sunrise", timings.Sunrise, "AM"))
            res.add(PrayersTiming("Dhuhr", timings.Dhuhr, "AM"))
            res.add(PrayersTiming("Asr", timings.Asr, "PM"))
            res.add(PrayersTiming("Maghrib", timings.Maghrib, "PM"))
            res.add(PrayersTiming("Isha", timings.Isha, "PM"))
        }
        return res
    }

}