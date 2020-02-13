package edu.rosehulman.parkingspotter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_get.view.*
import kotlin.random.Random.Default.nextInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_UID = "uid"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GetFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var uid: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var parkLotName: String? = "None"

    private var tokenList = ArrayList<Token>()

    private var spotList = ArrayList<Spot>()
    private var spotRef: ListenerRegistration? = null
    private var tokenRef: ListenerRegistration? = null
    private var tokenSize: Int? = 0

    init {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
        }
        tokenRef = FirebaseFirestore.getInstance().collection("Tokens").whereEqualTo("uid",uid).addSnapshotListener{ snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            if(exception != null){
            }
            for (docChange in snapshot!!.documentChanges){
                val token = Token.fromSnapshot(docChange.document)
                when(docChange.type){
                    DocumentChange.Type.ADDED -> {
                        tokenList.add(0,token)
                    }
                    DocumentChange.Type.REMOVED -> {
                        tokenList.removeAt(0)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_get, container, false)
        var getButton = view.findViewById<Button>(R.id.getbut)
        var selectButton = view.findViewById<Button>(R.id.selectGetButton)

        selectButton.setOnClickListener {
            val builder = AlertDialog.Builder(this.context!!)
            builder.setItems(
                resources.getStringArray(R.array.parklot_array)
            )
            { _, which ->
                when (which) {
                    0 -> {
                        "Speed Side"
                        update(view, "SpeedSide")
                    }
                    1 -> {
                        "Speed Main"
                        update(view, "SpeedMain")
                    }
                    2 -> {
                        "Precopo Main"
                        update(view, "PrecopoMain")
                    }
                    3 -> {
                        "SRC Main"
                        update(view, "SRCMain")
                    }
                    4 -> {
                        "SRC Back"
                        update(view, "SRCBack")
                    }
                    else -> "None"
                }
            }
            builder.create().show()
        }

        getButton.setOnClickListener {
            if (parkLotName === "None") {
                val builder = AlertDialog.Builder(this.context!!)
                builder.setMessage("Please select a parking lot first!")
                builder.create().show()

            } else {
                if(spotList.size == 0){
                    val builder = AlertDialog.Builder(this.context!!)
                    builder.setMessage("No Spot Available in this parking lot!")
                    builder.create().show()
                }else if(tokenList.size == 0){
                    val builder = AlertDialog.Builder(this.context!!)
                    builder.setMessage("You don't have enough token to get a spot!")
                    builder.create().show()
                }
                else{
                    val spot = spotList.get(nextInt(0, spotList.size!!))
                    view.get_row.text = "Row: ".plus(spot.row)
                    view.get_column.text = "Column: ".plus(spot.column)
                    FirebaseFirestore.getInstance().collection("Tokens").document(tokenList[0].id).delete().addOnSuccessListener {
                        FirebaseFirestore.getInstance().collection("Tokens").whereEqualTo("uid",uid).get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                tokenSize = task.result!!.size()
                                view.get_token.setText("My current tokens: ".plus(task.result!!.size().toString()))
                            }
                        }
                    }
                }

            }
        }

        FirebaseFirestore.getInstance().collection("Tokens").whereEqualTo("uid",uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                tokenSize = task.result!!.size()
                view.get_token.setText("My current tokens: ".plus(task.result!!.size().toString()))
            }
        }
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(flag: Int) {
        listener?.onFragmentInteraction(flag)
    }

    private fun update(view: View, lot: String) {
        parkLotName = lot
        spotList = ArrayList<Spot>()
        view.selectGetButton.text = "Current Parking Lot: ${lot}"
        view.get_row.text = "Row: "
        view.get_column.text = "Column: "
        spotRef = FirebaseFirestore.getInstance().collection(lot)
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                }
                for (docChange in snapshot!!.documentChanges) {
                    val spot = Spot.fromSnapshot(docChange.document)
                    when (docChange.type) {
                        DocumentChange.Type.ADDED -> {
                            spotList.add(0, spot)
                        }
                    }
                }
            }

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
         * @return A new instance of fragment GetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(uid: String) =
            GetFragment().apply {
                arguments = Bundle().apply {
                    putString("uid", uid)
                }
            }
    }


}
