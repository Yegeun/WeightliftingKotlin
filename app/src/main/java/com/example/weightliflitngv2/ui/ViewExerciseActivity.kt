package com.example.weightliflitngv2.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.preference.PreferenceManager
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.NODE_ATHLETES
import com.example.weightliflitngv2.data.NODE_ATHLETE_EMAIL
import com.example.weightliflitngv2.data.NODE_DATE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_view_exercise.*
import java.io.File


class ViewExerciseActivity : AppCompatActivity() {
    lateinit var stringoflists: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_exercise)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        textView_name.text= mSharedPreference.getString("athlete_email", null).toString()
        textView_date.text= NODE_DATE
        stringoflists = ""
        fetchAthleteExercises()

        //get's a screenshot
        val rootView = window.decorView.findViewById<View>(android.R.id.content)

        button_share.setOnClickListener(){
            val shot = getScreenShot(rootView)
//            imageView3.setImageBitmap(shot)
            if (shot != null) {
//                saveBitmap(shot, NODE_DATE)
//                val path = Environment.getExternalStorageDirectory().absolutePath + "/Screenshots"
//                val dir = File(path)
//                val file = File(dir, NODE_DATE)
            }
            val a=textView_exercise1.text
            val b=textView_weight1.text
            val c=textView_set1.text
            val d=textView_rep1.text
            val e=textView_exercise2.text
            val f=textView_weight2.text
            val g=textView_set2.text
            val h=textView_rep2.text
            val i=textView_exercise3.text
            val j=textView_weight3.text
            val k=textView_set3.text
            val l=textView_rep3.text
            val m=textView_exercise4.text
            val n=textView_weight4.text
            val o=textView_set4.text
            val p=textView_rep4.text
            stringoflists = "Exercise,Weight,Sets,Reps\n$a $b $c $d\n$e $f $g $h\n$i $j $k $l\n$m $n $o $p"
            shareText(stringoflists)


        }
    }

    //https://stackoverflow.com/questions/30196965/how-to-take-a-screenshot-of-current-activity-and-then-share-it
    private fun getScreenShot(view: View): Bitmap? {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun shareImage(file: File) {
        val uri: Uri = Uri.fromFile(file)
//        intent.putExtra(Intent.EXTRA_SUBJECT, "")
//        intent.putExtra(Intent.EXTRA_TEXT, "")
        val intent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_STREAM, file)
            this.type = "image/jpeg"
        }
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "No App Available", Toast.LENGTH_SHORT).show()
        }
    }
    private fun shareText(text: String){
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent,"Share Workout"))
    }
    private fun fetchAthleteExercises(){
        FirebaseDatabase.getInstance().getReference(NODE_ATHLETES)
            .child(NODE_ATHLETE_EMAIL)
            .child(NODE_DATE)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()){
                        val post = dataSnapshot.value as Map<*, *>
                        textView_exercise1.text = post["exercise1"].toString()
                        textView_weight1.text = post["weight1"].toString()
                        textView_set1.text = post["set1"].toString()
                        textView_rep1.text = post["rep1"].toString()
                        textView_exercise2.text = post["exercise2"].toString()
                        textView_weight2.text = post["weight2"].toString()
                        textView_set2.text = post["set2"].toString()
                        textView_rep2.text = post["rep2"].toString()
                        textView_exercise3.text = post["exercise3"].toString()
                        textView_weight3.text = post["weight3"].toString()
                        textView_set3.text = post["set3"].toString()
                        textView_rep3.text = post["rep3"].toString()
                        textView_exercise4.text = post["exercise4"].toString()
                        textView_weight4.text = post["weight4"].toString()
                        textView_set4.text = post["set4"].toString()
                        textView_rep4.text = post["rep4"].toString()
                    }
                }
            })
    }


}

