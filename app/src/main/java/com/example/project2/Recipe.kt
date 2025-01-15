package com.example.project2

data class Recipe (
    val id : String = "",
    val title : String = "",
    val url : String = "",
    val image : String = "",
    val servings : String = "",
    val readyInMinutes: Int = 0,
){
    // Default no-argument constructor required by Firebase
    constructor() : this("", "", "", "", "", 0)
}