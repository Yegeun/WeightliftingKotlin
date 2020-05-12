package com.example.weightliflitngv2.ui

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import java.io.FileOutputStream

class ViewExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_exercise)
        val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(baseContext)
        textView_name.text= mSharedPreference.getString("athlete_email", null).toString()
        textView_date.text= NODE_DATE
        fetchAthleteExercises()

        //get's a screenshot
        val rootView = window.decorView.findViewById<View>(android.R.id.content)

        button_share.setOnClickListener(){
            val shot = getScreenShot(rootView)
            toast("this works")
            imageView3.setImageBitmap(shot)
            if (shot != null) {
                saveBitmap(shot,"hello")
            }
        }
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

    //https://blog.mindorks.com/how-to-programmatically-take-a-screenshot-on-android
//    private fun getScreenShot(view: View): Bitmap {
//        val returnedBitmap = Bitmap.createBitmap(200, 500, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(returnedBitmap)
//        val bgDrawable = view.background
//        if (bgDrawable != null) bgDrawable.draw(canvas)
//        else canvas.drawColor(Color.WHITE)
//        view.draw(canvas)
//        return returnedBitmap
//    }
    //https://stackoverflow.com/questions/30196965/how-to-take-a-screenshot-of-current-activity-and-then-share-it
    private fun getScreenShot(view: View): Bitmap? {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }
    private fun saveBitmap(bm: Bitmap, fileName: String): File? {
        val path =
            Environment.getExternalStorageDirectory().absolutePath + "/Screenshots"
        val dir = File(path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, fileName)
        try {
            val fOut = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file // save the screenshot into the folder that is created as "Screenshots" if it's not existed.
    }
}

