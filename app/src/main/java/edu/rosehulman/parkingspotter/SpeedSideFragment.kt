package edu.rosehulman.parkingspotter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlinx.android.synthetic.main.fragment_speed_side.*
import kotlinx.android.synthetic.main.fragment_speed_side.view.*
import kotlinx.android.synthetic.main.report.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [speedSideFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [speedSideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class speedSideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var uid: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var spotList = ArrayList<Spot>()

    private val spotRef = FirebaseFirestore.getInstance().collection("SpeedSide")
    private val tokenRef = FirebaseFirestore.getInstance().collection("Tokens")
    private val numRef = FirebaseFirestore.getInstance().collection("Tokens").whereEqualTo("uid",uid)

    private var tokenList = ArrayList<Token>()
    init {
        spotRef.addSnapshotListener{ snapshot: QuerySnapshot? , exception: FirebaseFirestoreException? ->
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

        tokenRef.addSnapshotListener{ snapshot: QuerySnapshot? , exception: FirebaseFirestoreException? ->
            if(exception != null){
            }
            for (docChange in snapshot!!.documentChanges){
                val token = Token.fromSnapshot(docChange.document)
                when(docChange.type){
                    DocumentChange.Type.ADDED -> {
                        tokenList.add(0, token)
                    }
                }
            }
        }
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_speed_side, container, false)
        var reportButton = view.findViewById<Button>(R.id.report)

        reportButton.setOnClickListener{

            val dialogBuilder = AlertDialog.Builder(this.context)
            dialogBuilder.setTitle("Hi")
            dialogBuilder.setMessage("Report this spot!")
            dialogBuilder.setCancelable(false)
            val dialogView = LayoutInflater.from(this.context).inflate(R.layout.report,null,false)
            dialogBuilder.setView(dialogView)

            dialogBuilder.setNeutralButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            dialogBuilder.setPositiveButton("Report"){_,_ ->
                val row =  dialogView.report_row.text.toString().toInt()
                val col = dialogView.report_column.text.toString().toInt()
                updateReport(row,col);
                tokenRef.get().addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        view.speedside_token.setText("My current tokens: ".plus(task.result!!.size().toString()))
                    }
                }
            }
            dialogBuilder.create().show()
        }
        tokenRef.get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                view.speedside_token.setText("My current tokens: ".plus(task.result!!.size().toString()))
            }
        }

        return view
    }

    fun updateReport(row : Number,col: Number){
        rownum.text = rownum.text.toString().plus(row.toString())
        colnum.text = colnum.text.toString().plus(col.toString())
        spotList.add(Spot(row.toString(),col.toString()))
        spotRef.add(Spot(row.toString(),col.toString()))
        tokenRef.add(Token(uid!!))
        tokenList.add(Token(uid!!))



    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(flag: Int) {
        listener?.onFragmentInteraction(flag)
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
        fun onFragmentInteraction(flag: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment speedSideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(uid: String) =
            speedSideFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, uid)
                }
            }
    }
}
