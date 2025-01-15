package com.example.project2

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MealAdapter (val meals: List<Meal>): RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View, val meals: List<Meal>): RecyclerView.ViewHolder(rootLayout), View.OnClickListener {
        val mealTitle : TextView =rootLayout.findViewById(R.id.mealTitle)
        val recipeTitle : TextView =rootLayout.findViewById(R.id.mealRecipeTitle)
        val image : ImageView = rootLayout.findViewById(R.id.mealIcon)
        val servings : TextView = rootLayout.findViewById(R.id.mealServings)
        val cooktime : TextView = rootLayout.findViewById(R.id.mealCookTime)

        init {
            // Set the click listener for the item view
            // If the item is clicked then, the recipie is displayed in the webbrowser
            rootLayout.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val meal = meals[position]

            //Displays the recipe in the browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.recipe.url))
            view.context.startActivity(intent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create a new viewHolder
        //need to read the xml row
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val rootLayout: View =layoutInflater.inflate(R.layout.mealplanviewlayout, parent, false)
        val viewHolder=ViewHolder(rootLayout, meals)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMeal=meals[position]
        holder.mealTitle.text= currentMeal.meal
        holder.recipeTitle.text = currentMeal.recipe.title
        holder.servings.text="Servings: " + currentMeal.recipe.servings
        holder.cooktime.text="Cook Time: " + currentMeal.recipe.readyInMinutes.toString() + " Minutes"
        var image = currentMeal.recipe.id
        var imageUrl = "https://spoonacular.com/recipeImages/$image-90x90.jpg"
        Picasso.get()
            .load(imageUrl)
            .into(holder.image)
    }
}