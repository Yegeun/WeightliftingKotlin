package com.example.weightliflitngv2.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.weightliflitngv2.R
import com.example.weightliflitngv2.data.Exercises
import com.example.weightliflitngv2.data.NODE_DATE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.linearLayout
import kotlinx.android.synthetic.main.activity_submit_exercise.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SubmitExerciseActivity : AppCompatActivity() {
    private lateinit var viewModel: SubmitExerciseViewModel
    lateinit var  textViewName: TextView
    var number = 0
    var asd = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_exercise)

        viewModel = ViewModelProviders.of(this).get(SubmitExerciseViewModel::class.java)
        //names of the different layouts
        val four = findViewById<LinearLayout>(R.id.layout1)

        Toast.makeText(applicationContext,asd, Toast.LENGTH_SHORT).show()

        textViewName = findViewById(R.id.textViewDate)
        number = linearLayout.childCount
        Toast.makeText(applicationContext,number.toString(), Toast.LENGTH_SHORT).show()
//        hey = viewModel.add()
//        Toast.makeText(applicationContext,hey, Toast.LENGTH_SHORT ).show()

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        NODE_DATE = formatted // this puts the current date to a variable

        textViewName.text = NODE_DATE

        button_add.setOnClickListener{
            four.visibility = View.VISIBLE
        }
        button_delete.setOnClickListener(){
            four.visibility = View.GONE
        }
        button_submit.setOnClickListener(){
            val exercise = Exercises()
            if(edit_text_exercise1.text.toString()!=""){
                exercise.exercise1 = edit_text_exercise1.text.toString()
                exercise.weight1 = edit_text_weight1.text.toString()
                exercise.set1 = edit_text_set1.text.toString()
                exercise.rep1 = edit_text_rep1.text.toString()
            }
            else{
                exercise.exercise1 = null
            }
            if(edit_text_exercise2.text.toString()!=""){
                exercise.exercise2 = edit_text_exercise2.text.toString()
                exercise.weight2 = edit_text_weight2.text.toString()
                exercise.set2 = edit_text_set2.text.toString()
                exercise.rep2 = edit_text_rep2.text.toString()
            }
            else{
                exercise.exercise2 = null
            }
            if(edit_text_exercise3.text.toString()!=""){
                exercise.exercise3 = edit_text_exercise3.text.toString()
                exercise.weight3 = edit_text_weight3.text.toString()
                exercise.set3 = edit_text_set3.text.toString()
                exercise.rep3 = edit_text_rep3.text.toString()
            }
            else{
                exercise.exercise3 = null
            }
            if(edit_text_exercise4.text.toString()!=""){
                exercise.exercise4 = edit_text_exercise4.text.toString()
                exercise.weight4 = edit_text_weight4.text.toString()
                exercise.set4 = edit_text_set4.text.toString()
                exercise.rep4 = edit_text_rep4.text.toString()
            }
            else{
                exercise.exercise4 = null
            }
            viewModel.addExercise(exercise)

            //TODO Create alert diaglogue so that it notifies user
        }
    }
}

