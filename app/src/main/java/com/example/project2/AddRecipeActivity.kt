package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var image : ImageView
    private lateinit var myRecipeText : TextView
    private lateinit var myServingsText : TextView
    private lateinit var myCookTimeText : TextView
    private lateinit var myBreakfastRadioButton: RadioButton
    private lateinit var myLunchRadioButton : RadioButton
    private lateinit var myDinnerRadioButton: RadioButton
    private lateinit var mySpinner : Spinner
    private lateinit var addToPlanButton : Button
    private lateinit var myHomeButton : Button
    private lateinit var myRecipesButton : Button
    private lateinit var firebaseDatabase : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        image = findViewById(R.id.recipeAddImageView)
        myRecipeText = findViewById(R.id.addRecipeTitleTextView)
        myServingsText = findViewById(R.id.servingsTextView)
        myCookTimeText = findViewById(R.id.cookTimeTextView)
        myBreakfastRadioButton = findViewById(R.id.breakfastradioButton)
        myLunchRadioButton = findViewById(R.id.lunchRadioButton)
        myDinnerRadioButton = findViewById(R.id.dinnerRadioButton)
        mySpinner = findViewById(R.id.daysSpinner)
        addToPlanButton = findViewById(R.id.addtoPlanButton)
        myHomeButton = findViewById(R.id.addRecipeHomeButton2)
        myRecipesButton = findViewById(R.id.addRecipesRecipesButton)
        firebaseDatabase = FirebaseDatabase.getInstance()

        val data = getDaysOfWeek()
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        mySpinner.adapter = adapter

        val intent = getIntent()
        val title = intent.getStringExtra("Recipe Title").toString()
        myRecipeText.text = title
        val servings = intent.getStringExtra("Recipe servings").toString()
        val url = intent.getStringExtra("Recipe id")
        val id = intent.getStringExtra("Recipe url")
        val cooktime = intent.getStringExtra("Recipe readyInMinutes").toString()
        myServingsText.text = "Servings: $servings"
        myCookTimeText.text = "Cook Time: " + cooktime
        val imageUrl = intent.getStringExtra("Recipe image").toString()
        Picasso.get()
            .load(imageUrl)
            .into(image)
        var day = "Sunday"
        var meal = "Breakfast"
        mySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ){
                day = data[position]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        myHomeButton.setOnClickListener(){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        myRecipesButton.setOnClickListener(){
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        addToPlanButton.setOnClickListener(){

            if(myBreakfastRadioButton.isChecked){
                meal = "Breakfast" }
            else if(myLunchRadioButton.isChecked){
                meal = "Lunch" }
            else if(myDinnerRadioButton.isChecked){
                meal = "Dinner" }
            addToPlan(url.toString(), servings, cooktime, title,
                id.toString(), imageUrl, day, meal)

        }

    }

    private fun getDaysOfWeek(): List<String> {
        val daysOfWeek = mutableListOf<String>()
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val calendar = Calendar.getInstance()

        for (i in Calendar.SUNDAY..Calendar.SATURDAY) {
            calendar.set(Calendar.DAY_OF_WEEK, i)
            daysOfWeek.add(sdf.format(calendar.time))
        }

        return daysOfWeek
    }

    private fun addToPlan(id : String, servings: String, cooktime: String, title: String,
                          url : String, image: String, day: String, meal : String){
        val uid= FirebaseAuth.getInstance().currentUser!!.uid!!
        val recipe=Recipe(
            id = id,
            title = title,
            url = url,
            image = image,
            servings = servings,
            readyInMinutes =  cooktime.toInt()
        )
        val reference=firebaseDatabase.getReference("Users/$uid/$day/$meal")
        reference.setValue(recipe)
    }
}