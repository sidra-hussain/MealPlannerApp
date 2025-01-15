package com.example.project2

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class RecipeManager {
    val okHttpClient : OkHttpClient
    init{
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build();
    }

    fun retrieveRecipes(apiKey : String) : MutableList<Recipe>{

        val request = Request.Builder()
            .url("https://api.spoonacular.com/recipes/search?apiKey=$apiKey")
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        val responseBody :String?=response.body?.string()
        var recipeList = mutableListOf<Recipe>()

        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            val json= JSONObject(responseBody)
            val recipes = json.getJSONArray("results")
            for (i in 0 until recipes.length()){
                val currentRecipe = recipes.getJSONObject(i)
                val readyInMinutes = currentRecipe.getString("readyInMinutes").toInt()
                val url = currentRecipe.getString("sourceUrl")
                val image = currentRecipe.getString("image")
                val id = currentRecipe.getString("id")
                val title = currentRecipe.getString("title")
                val servings = currentRecipe.getString("servings")


                val source=Recipe(
                    id = id,
                    readyInMinutes = readyInMinutes,
                    url = url,
                    image = image,
                    title = title,
                    servings = servings
                )

                recipeList.add(source)
            }
        }
        else {
            Log.d("SpoonacularAPI", "SpoonacularAPI search call failed")
            return mutableListOf<Recipe>()
        }

        return recipeList
    }

    fun retrieveRecipes(apiKey : String, searchText : String) : MutableList<Recipe>{

        val request = Request.Builder()
            .url("https://api.spoonacular.com/recipes/search?query=$searchText&apiKey=$apiKey")
            .get()
            .build()
        val response = okHttpClient.newCall(request).execute()
        val responseBody :String?=response.body?.string()
        var recipeList = mutableListOf<Recipe>()

        if (response.isSuccessful && !responseBody.isNullOrBlank()) {

            val json= JSONObject(responseBody)
            val recipes = json.getJSONArray("results")
            for (i in 0 until recipes.length()){
                val currentRecipe = recipes.getJSONObject(i)
                val readyInMinutes = currentRecipe.getString("readyInMinutes").toInt()
                val url = currentRecipe.getString("sourceUrl")
                val image = currentRecipe.getString("image")
                val id = currentRecipe.getString("id")
                val title = currentRecipe.getString("title")
                val servings = currentRecipe.getString("servings")


                val source=Recipe(
                    id = id,
                    readyInMinutes = readyInMinutes,
                    url = url,
                    image = image,
                    title = title,
                    servings = servings
                )

                recipeList.add(source)
            }
        }
        else {
            Log.d("SpoonacularAPI", "SpoonacularAPI search call failed")
            return mutableListOf<Recipe>()
        }

        return recipeList
    }

}