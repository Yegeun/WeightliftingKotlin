package com.example.weightliflitngv2.ui


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow


class ProfileActivity : AppCompatActivity() {

    var timeFormat = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
    var hourFormat = SimpleDateFormat("hh", Locale.ENGLISH)
    var minuteFormat = SimpleDateFormat("mm", Locale.ENGLISH)
    var apFormat = SimpleDateFormat("aa", Locale.ENGLISH)

    //notifications
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.weightliflitngv2.notificationexample"
    private val description = "Reminder To Login Weights"

    //alarm manager https://www.youtube.com/watch?v=rzxgQOo2ZF4
    lateinit var context: Context
    lateinit var alarmManager: AlarmManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //alarm manager
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        button_profile_coach.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val newFragment = ProfileCoachDialogue()
            newFragment.show(ft, "")
        }
        button_profile_athlete.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val newFragment = ProfileAthleteDialogue()
            newFragment.show(ft, "")
            // Create an ArrayAdapter using the string array and a default spinner layout
        }

        button_set.setOnClickListener {
            //https://www.youtube.com/watch?v=gollUUFBKQA
            val now = Calendar.getInstance()

            try {
                if (textVeiw_time.text != "00:00am") {
                    val date = timeFormat.parse(textVeiw_time.text.toString())
                    now.time = date
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    //override the set time
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    val intent = Intent(context, Receiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    var h=hourFormat.format(selectedTime.time).toInt()
                    val m=minuteFormat.format(selectedTime.time).toInt()
                    var ap = 0
                    if(apFormat.format(selectedTime.time).toString() =="AM" && h==12 ){
                        h = 0
                    }else if(apFormat.format(selectedTime.time).toString() =="PM" && h==12 ){
                        ap = 0
                    } else if(apFormat.format(selectedTime.time).toString() =="PM"){
                        ap = 43200000
                    }

                    val millisTime = (h*3600000)+(m*60000)+ap

                    Toast.makeText(this, millisTime.toLong().toString(), Toast.LENGTH_SHORT).show()

                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        millisTime.toLong(),
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )


//                    val intent2 = Intent(this, LauncherActivity::class.java)
//                    val pendingIntent2 =
//                        PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
//                    notificationManager =
//                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    notificationChannel =
//                        NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
//                    notificationChannel.enableLights(true)
//                    notificationChannel.lightColor = Color.GREEN
//                    notificationChannel.enableVibration(false)
//                    notificationManager.createNotificationChannel(notificationChannel)
//                    builder = Notification.Builder(this, channelId)
//                        .setContentTitle("Reminder")
//                        .setContentText("LOG IN WEIGHTS")
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
//                        .setContentIntent(pendingIntent2)
//                    notificationManager.notify(1234, builder.build())


                    textVeiw_time.text = timeFormat.format(selectedTime.time)
                    Log.d("ProfileActivity", "Create: " + Date().toString())
                    //TODO save at shared prefrences
                },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
            )
            timePicker.show()


        }

        button_cancel.setOnClickListener {
            val intent = Intent(context, Receiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("ProfileActivity", "Cancel:" + Date().toString())
            alarmManager.cancel(pendingIntent)
        }
    }
    class Receiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("ProfileActivity", "Reciever: " + Date().toString())
        }
    }
}








