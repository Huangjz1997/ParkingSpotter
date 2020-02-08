package edu.rosehulman.parkingspotter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import edu.rosehulman.parkingspotter.getFragment.OnFragmentInteractionListener

class ContentHolder : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener, getFragment.OnFragmentInteractionListener, postFragment.OnFragmentInteractionListener{
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_holder)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_content, HomeFragment())
        ft.commit()
    }

    override fun onFragmentInteraction(flag: Int) {
        Log.d("sfwe","$flag")
        if(flag == 1) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.main_content, getFragment())
            ft.commit()
        }else if(flag == 2){

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.main_content, postFragment())
            ft.commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_content, postFragment())
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            R.id.log_out -> {
                auth.signOut()
                val myIntent = Intent(this, LoginPage::class.java)
               startActivity(myIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
