package com.example.weightliflitngv2.ui


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.SimpleDateFormat
import java.util.*


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

        //prefedit
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        //alarm manager
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        if(textView_time.text=="00:00am"){
            //call
            val mSharedPreference: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(baseContext)
            val time = mSharedPreference.getString("reminder", "00:00am").toString()
            textView_time.text= time
        }

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
                if (textView_time.text != "00:00am") {
                    val date = timeFormat.parse(textView_time.text.toString())
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

                    //intent2s
                    val intent2 = Intent(this, LauncherActivity::class.java)
                    val pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT)

                    //intent for the reciever
                    val intent = Intent(context, Receiver()::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    //conversion to millis
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

//                    Toast.makeText(this, millisTime.toLong().toString(), Toast.LENGTH_SHORT).show()

                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        millisTime.toLong(),
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )

                    textView_time.text = timeFormat.format(selectedTime.time)
                    Log.d("ProfileActivity", "Create: " + Date().toString())

                    //notification
//                    notificationManager =
//                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    notificationChannel =
//                        NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
//                    notificationChannel.enableLights(true)
//                    notificationChannel.lightColor = Color.GREEN
//                    notificationChannel.enableVibration(false)
//                    notificationManager.createNotificationChannel(notificationChannel)
//                    builder = Notification.Builder(this, channelId)
//                        .setContentTitle("Reminder Set")
//                        .setContentText("A Reminder has been set")
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentIntent(pendingIntent2)
//                    notificationManager.notify(1001, builder.build())


                    //save
                    val editor = prefs.edit()
                    editor.putString("reminder",timeFormat.format(selectedTime.time) )
                    editor.apply()

                },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
            )
            timePicker.show()

        }

        button_cancel.setOnClickListener {
            val intent = Intent(context, Receiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(pendingIntent)
            toast("The Alarm is Cancelled")
        }

    }
    class Receiver(): BroadcastReceiver(){
        private var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder
        private val channelId = "com.example.weightliflitngv2.notificationexample"
        private val description = "Reminder To Login Weights"
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("ProfileActivity", "Reciever: " + Date().toString())
            Toast.makeText(context,"This is a reminder to login apps",Toast.LENGTH_LONG).show()
            val intent= Intent(context,ProfileActivity::class.java)
            val pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationManager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(context, channelId)
                .setContentTitle("Reminder Set")
                .setContentText("A Reminder has been set")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
            notificationManager.notify(1002, builder.build())
        }
    }
}








