package edu.rosehulman.parkingspotter

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import edu.rosehulman.parkingspotter.getFragment.OnFragmentInteractionListener

class ContentHolder : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener, getFragment.OnFragmentInteractionListener, postFragment.OnFragmentInteractionListener{

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
}
