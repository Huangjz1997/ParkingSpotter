package edu.rosehulman.parkingspotter
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {

    lateinit var authListener: FirebaseAuth.AuthStateListener
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        lateinit var uid : String
        authListener = FirebaseAuth.AuthStateListener { auth: FirebaseAuth ->
            val user = auth.currentUser
            if (user == null) {

            }
            else {
                uid = user.uid
                val myIntent = Intent(this, ContentHolder::class.java)
                myIntent.putExtra("uid", user.uid);
                startActivity(myIntent)
            }
        }

        var button: Button =  findViewById(R.id.register)
        button.setOnClickListener(){
            val myIntent = Intent(this, RegisterPage::class.java)
            startActivity(myIntent)
        }

        var button1: Button =  findViewById(R.id.login)

        button1.setOnClickListener(){
            val username = username.text;
            val password = password.text;

            login(username.toString(),password.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)

    }

    fun login(username:String, password: String){
        auth.signInWithEmailAndPassword(username, password).addOnSuccessListener {
            val myIntent = Intent(this, ContentHolder::class.java)
            startActivity(myIntent)
        }.addOnFailureListener{
            Toast.makeText(this,"Wrong email/password combination!", Toast.LENGTH_SHORT).show()
        }
    }
}
