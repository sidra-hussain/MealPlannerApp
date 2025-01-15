package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MealPlanActivity : AppCompatActivity() {
    private lateinit var  myMealPlanText : TextView
    private lateinit var myRecyclerView : RecyclerView
    private lateinit var myHomeButton : Button
    private lateinit var recyclerView : RecyclerView
    private lateinit var myRecipebutton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_plan)

        myMealPlanText = findViewById(R.id.mealPlanTextView)
        myRecyclerView = findViewById(R.id.mealPlanRecyler)
        myHomeButton = findViewById(R.id.planHomeButton)
        recyclerView = findViewById(R.id.mealPlanRecyler)
        myRecipebutton = findViewById(R.id.mealRecipesButton)

        val intent = getIntent()
        val day = intent.getStringExtra("Day").toString()
        myMealPlanText.text = "$day Meal Plan"
        val uid = FirebaseAuth.getInstance().currentUser!!.uid!!

        val usersRef = FirebaseDatabase.getInstance().getReference("Users/$uid/$day")
        // Create a mutable list to store recipes
        val meals = mutableListOf<Meal>()

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (mealSnapshot in dataSnapshot.children) {
                    val mealName = mealSnapshot.key ?: ""
                    val recipe = mealSnapshot.getValue(Recipe::class.java)
                    if (recipe != null){
                        val meal = Meal(mealName, recipe)
                        Log.d("Recipe", meal.toString())
                        meals.add(meal)
                        //Make sure the order of the meals is correct (Breakfast, Lunch and Dinner)
                        val sortedMeals = meals.sortedBy {
                            when (it.meal.toLowerCase()) {
                                "breakfast" -> 1
                                "lunch" -> 2
                                "dinner" -> 3
                                else -> 4
                            }
                        }
                        val adapter=MealAdapter(sortedMeals)
                        recyclerView.adapter=adapter
                        recyclerView.layoutManager= LinearLayoutManager(this@MealPlanActivity)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                println("Error: ${databaseError.message}")
            }
        })

        myHomeButton.setOnClickListener(){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        myRecipebutton.setOnClickListener(){
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }
    }
}