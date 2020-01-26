package edu.rosehulman.parkingspotter

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.rosehulman.parkingspotter.getFragment.OnFragmentInteractionListener

class ContentHolder : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener, OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_holder)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_content, HomeFragment())
        ft.commit()
    }

    override fun onFragmentInteraction(flag: Int) {
        if(flag == 1){
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.main_content, getFragment())
            ft.commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
