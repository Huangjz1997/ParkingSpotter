package edu.rosehulman.parkingspotter

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_get.view.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [postFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [postFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class postFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var spotList = ArrayList<Spot>()
    private val spotRef = FirebaseFirestore.getInstance().collection("SpeedSide")
    private val tokenRef = FirebaseFirestore.getInstance().collection("Tokens")
    init {
        spotRef.addSnapshotListener{ snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            if(exception != null){

            }
            for (docChange in snapshot!!.documentChanges){
                val spot = Spot.fromSnapshot(docChange.document)
                when(docChange.type){

                    DocumentChange.Type.ADDED -> {
                        spotList.add(0, spot)

                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        var getButton = view.findViewById<Button>(R.id.getbut)

        getButton.setOnClickListener {
//
//            val dialogBuilder = AlertDialog.Builder(this.context)
//            dialogBuilder.setTitle("Hi")
//            dialogBuilder.setMessage("Report this spot!")
//            dialogBuilder.setCancelable(false)
//            val dialogView = LayoutInflater.from(this.context).inflate(R.layout.report, null, false)
//            dialogBuilder.setView(dialogView)
//
//            dialogBuilder.setNeutralButton("Cancel") { dialog, which ->
//                dialog.cancel()
//            }
//
            //!!!!!!!!!!!!!!!!!!!!!!!!!

            val tempSpot = spotList.get(nextInt(0,5));
            val tempRow = "Row: "
            val tempCol = "Column: "
            val tempString = "\n ParkingLot \n SpeedSide"


            view.post_row.setText(tempRow.plus(tempSpot.row))
            view.post_column.setText(tempRow.plus(tempSpot.column).plus(tempString))



        }
        tokenRef.get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                view.get_token.setText("My current tokens: ".plus(task.result!!.size().toString()))
            }
        }

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment postFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            postFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
