package edu.rosehulman.parkingspotter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_register_page.*

class RegisterPage : AppCompatActivity() {
    lateinit var authListener: FirebaseAuth.AuthStateListener
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        supportActionBar!!.hide()

        var button: Button =  findViewById(R.id.registeruser)
        button.setOnClickListener(){
            val username = registerusername.text;
            val password = registerpassword.text;
            register(username.toString(),password.toString())
        }
    }

    fun register(username:String, password:String){
         auth.createUserWithEmailAndPassword(username,password).addOnSuccessListener {
             val myIntent = Intent(this, ContentHolder::class.java)
             startActivity(myIntent)
        }.addOnFailureListener{
            Toast.makeText(this,"Register error!", Toast.LENGTH_SHORT).show()
        }
    }
}
