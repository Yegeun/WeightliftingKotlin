package com.example.weightliflitngv2.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_ATHLETE_EMAIL
import com.example.weightliflitngv2.data.NODE_COACH_EMAIL
import com.example.weightliflitngv2.data.NODE_DATE
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialogue_fragment_edit_athlete.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences:SharedPreferences
    lateinit var email: String
    lateinit var athleteEmail:String
    private lateinit var coach:String

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mSharedPreference: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(baseContext)
    email = mSharedPreference.getString("email", null).toString()
    athleteEmail = mSharedPreference.getString("athlete_email", null).toString()
    coach =  mSharedPreference.getString("coach", null).toString()

    athleteEmail = athleteEmail.replace(Regex("""[@,.]"""), "_") // replaces with underscore so it can save it
    NODE_COACH_EMAIL=email
    NODE_ATHLETE_EMAIL=athleteEmail

    //Offline Database maximum of 10mb
    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    val athletes = FirebaseDatabase.getInstance().getReference("athletes")
    athletes.keepSynced(true)

    //updates current time and date
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatted = current.format(formatter)
    NODE_DATE = formatted // this puts the current date to a variable

//        toast(athleteEmail)
    if(!hasNetworkAvailable(this)){
        showDialog()
    }

    //checking if user is a Coach or an Athlete

    if(coach=="no") run {
        buttonAddAthlete.visibility = View.GONE
    }else{
        buttonAddAthlete.visibility = View.VISIBLE
    }
    if(NODE_ATHLETE_EMAIL=="athleteemail"|| NODE_ATHLETE_EMAIL=="none"){
        button_addExercise.visibility = View.GONE
        button_view.visibility = View.GONE
    }else{
        button_addExercise.visibility = View.VISIBLE
        button_view.visibility = View.VISIBLE
    }
    buttonAddAthlete.setOnClickListener(){
        startActivity(Intent( applicationContext, AddAthlete::class.java ))
    }
    button_addExercise.setOnClickListener(){
        startActivity(Intent( applicationContext, SubmitExerciseActivity::class.java))
    }
    button_view.setOnClickListener(){
        startActivity(Intent( applicationContext,ViewExerciseActivity ::class.java))
    }

}
    // Kotlin code sample

    // The next line should be the first statement in the file
    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        Log.d("hello", "hasNetworkAvailable: ${(network != null)}")
        return (network != null)
    }

    // Method to show an alert dialog with yes, no and cancel button
    private fun showDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog:AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Not Connected to the internet")

        // Set a message for alert dialog
        builder.setMessage("You are not connected to the internet would you like to go into your settings")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                DialogInterface.BUTTON_NEGATIVE -> toast("Negative/No button clicked.")
            }
        }

        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

