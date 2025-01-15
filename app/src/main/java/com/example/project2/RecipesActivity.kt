package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var mySearchText : EditText
    private lateinit var myAddTextView : TextView
    private lateinit var myEnterButton : TextView
    private lateinit var myHomeButton : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recyclerView = findViewById(R.id.recipeRecyclerView)
        mySearchText = findViewById(R.id.searchEditText)
        myAddTextView = findViewById(R.id.addRecipeTextView)
        myEnterButton = findViewById(R.id.searchButton)
        myHomeButton = findViewById(R.id.RecipeHomeButton)

        val apiKey = getString(R.string.key)
        var recipeManager = RecipeManager()
        //Coroutines
        CoroutineScope(Dispatchers.IO).launch{
            var recipes = recipeManager.retrieveRecipes(apiKey)

            withContext(Dispatchers.Main){
                val adapter=RecipeAdapter(recipes)
                recyclerView.adapter=adapter
                recyclerView.layoutManager= LinearLayoutManager(this@RecipesActivity)
            }
        }

        myEnterButton.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch{
                var recipes = recipeManager.retrieveRecipes(apiKey, mySearchText.text.toString())

                withContext(Dispatchers.Main){
                    val adapter=RecipeAdapter(recipes)
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager= LinearLayoutManager(this@RecipesActivity)
                }
            }

        }
        myHomeButton.setOnClickListener(){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}