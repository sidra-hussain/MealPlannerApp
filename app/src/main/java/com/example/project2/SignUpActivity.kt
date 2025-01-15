package com.example.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {
    private lateinit var myEmailText : EditText
    private lateinit var myPasswordText : EditText
    private lateinit var myVerifyPasswordText : EditText
    private lateinit var mySignUpButton : Button
    private lateinit var myAuthenticator : FirebaseAuth
    private lateinit var mySignUpText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        myEmailText = findViewById(R.id.signupEmailText)
        myPasswordText = findViewById(R.id.signupPasswordText)
        myVerifyPasswordText = findViewById(R.id.signupPasswordVerificationText)
        mySignUpButton = findViewById(R.id.signupsubmitButton)
        mySignUpText = findViewById(R.id.signupTextView)
        myAuthenticator = FirebaseAuth.getInstance()
        val sharedPrefs=getSharedPreferences("savedStuff", MODE_PRIVATE)
        sharedPrefs.edit().putString("username", "").apply()
        sharedPrefs.edit().putString("password", "").apply()

        mySignUpButton.setOnClickListener(){

            if (myVerifyPasswordText.length() < 6){
                mySignUpText.setText("Password is too short, it must be at least 6 charaters")
            }

            else if(myVerifyPasswordText.text.toString() != myPasswordText.text.toString()){
                mySignUpText.setText("Passwords Do Not Match, Please Re-enter them!")
            }

            else {
                val inputtedUsername: String = myEmailText.text.toString().trim()
                val inputtedPassword: String = myPasswordText.text.toString().trim()

                myAuthenticator.createUserWithEmailAndPassword(
                    inputtedUsername,inputtedPassword).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success
                        Log.d("SignUp", "createUserWithEmail:success")
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()

                        // You can automatically sign in the user after sign-up if needed
                        // auth.signInWithEmailAndPassword(email, password)

                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Sign up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }

        }
    }
}