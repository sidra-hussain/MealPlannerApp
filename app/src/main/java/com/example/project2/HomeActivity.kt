package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var myAddButton : Button
    private lateinit var myLogOutButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recyclerView = findViewById(R.id.calendarRecyclerView)
        myAddButton = findViewById(R.id.addRecipeHomeButton)
        myLogOutButton = findViewById(R.id.logOutButton)
        val firebaseAuth = FirebaseAuth.getInstance()

        val daysOfWeek = getDaysOfWeek()
        recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
        val weekAdapter = WeekAdapter(daysOfWeek)
        Log.d("Days of the week", daysOfWeek.toString())
        recyclerView.adapter = weekAdapter

        myAddButton.setOnClickListener(){
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        myLogOutButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            firebaseAuth.signOut();
            startActivity(intent)

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
        daysOfWeek.add(0, "Days of the Week")

        return daysOfWeek
    }
}