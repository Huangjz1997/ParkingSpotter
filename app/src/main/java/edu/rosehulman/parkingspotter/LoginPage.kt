package edu.rosehulman.parkingspotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        var button: Button =  findViewById(R.id.register)
        button.setOnClickListener(){
            val myIntent = Intent(this, RegisterPage::class.java)
            startActivity(myIntent)
        }

        var button1: Button =  findViewById(R.id.login)
        button1.setOnClickListener(){

            val myIntent = Intent(this, ContentHolder::class.java)
            startActivity(myIntent)
        }
    }
}
