package edu.rosehulman.parkingspotter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)

        var button: Button =  findViewById(R.id.start)
        button.setOnClickListener(){
            val myIntent = Intent(this, LoginPage::class.java)
            startActivity(myIntent)
        }
    }
}
