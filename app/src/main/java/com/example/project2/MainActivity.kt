package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var myEmailText : EditText
    private lateinit var myPasswordText : EditText
    private lateinit var myLoginButton : Button
    private lateinit var mySignUpButton : Button
    private lateinit var myAuthenticator : FirebaseAuth
    private lateinit var myLoginTextView : TextView
    private lateinit var myCheckBox: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myEmailText = findViewById(R.id.loginEmailText)
        myPasswordText = findViewById(R.id.loginPasswordText)
        myLoginButton = findViewById(R.id.loginButton)
        mySignUpButton = findViewById(R.id.signupButton)
        myLoginTextView = findViewById(R.id.loginTextView)
        myCheckBox = findViewById(R.id.checkBox)
        myAuthenticator = FirebaseAuth.getInstance()


        val sharedPrefs=getSharedPreferences("savedStuff", MODE_PRIVATE)
        val lastEmail = sharedPrefs.getString("username", "")
        val lastPassword = sharedPrefs.getString("password", "")

        if (lastEmail != "" && lastPassword != ""){
            myEmailText.setText(lastEmail)
            myPasswordText.setText(lastPassword)
            myCheckBox.isChecked = true
        }

        myLoginButton.setOnClickListener(){
            val inputtedUsername = myEmailText.text.toString().trim()
            val inputtedPassword = myPasswordText.text.toString().trim()
            if (inputtedUsername != null && inputtedPassword != null){
                myAuthenticator.signInWithEmailAndPassword(inputtedUsername, inputtedPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = myAuthenticator.currentUser

                            if(myCheckBox.isChecked){
                                sharedPrefs.edit().putString("username", myEmailText.text.toString()).apply()
                                sharedPrefs.edit().putString("password", myPasswordText.text.toString()).apply()
                            }
                            else {
                                sharedPrefs.edit().putString("username", "").apply()
                                sharedPrefs.edit().putString("password", "").apply()
                            }

                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)

                        } else {
                            val exception = task.exception
                            Log.d("exception", exception.toString())
                            myLoginTextView.text = "Login Failed: Try Again"
                        }
                    }

            }

        }

        mySignUpButton.setOnClickListener(){

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}