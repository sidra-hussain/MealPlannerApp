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


class RecipeAdapter (val recipes: List<Recipe>):
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View, val recipes: List<Recipe>):
        RecyclerView.ViewHolder(rootLayout), View.OnClickListener {
        val recipeTitle : TextView =rootLayout.findViewById(R.id.recipeRecipeTitle)
        val image : ImageView = rootLayout.findViewById(R.id.RecipeIcon)
        val servings : TextView = rootLayout.findViewById(R.id.RecipeServings)
        val cooktime : TextView = rootLayout.findViewById(R.id.RecipeCookTime)
        val addButton : Button = rootLayout.findViewById(R.id.selectButton)

        init {
            // Set the click listener for the item view
            // If the item is clicked then, the recipie is displayed in the webbrowser
            rootLayout.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val recipe = recipes[position]

            //Displays the recipe in the browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.url))
            view.context.startActivity(intent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create a new viewHolder
        //need to read the xml row
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val rootLayout: View =layoutInflater.inflate(R.layout.cardviewlayout, parent, false)
        val viewHolder=ViewHolder(rootLayout, recipes)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe=recipes[position]
        holder.recipeTitle.text= currentRecipe.title
        holder.servings.text="Servings: " + currentRecipe.servings
        holder.cooktime.text="Cook Time: " + currentRecipe.readyInMinutes.toString() + " Minutes"
        var image = currentRecipe.id
        var imageUrl = "https://spoonacular.com/recipeImages/$image-90x90.jpg"
        Picasso.get()
            .load(imageUrl)
            .into(holder.image)

        holder.addButton.setOnClickListener { // Handle button click
            val context = holder.itemView.context
            val intent = Intent(context, AddRecipeActivity::class.java)
            intent.putExtra("Recipe Title", currentRecipe.title)
            intent.putExtra("Recipe id", currentRecipe.id)
            intent.putExtra("Recipe readyInMinutes", currentRecipe.readyInMinutes.toString())
            intent.putExtra("Recipe url", currentRecipe.url)
            intent.putExtra("Recipe image", imageUrl)
            intent.putExtra("Recipe servings", currentRecipe.servings.toString())
            context.startActivity(intent)

        }


    }
}