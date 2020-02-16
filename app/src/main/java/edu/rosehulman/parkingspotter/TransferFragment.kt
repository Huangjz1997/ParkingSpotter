package edu.rosehulman.parkingspotter

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_transfer.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_UID = "uid"
private const val ARG_EMAIL = "email"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [transferFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [transferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferFragment : Fragment() {
    val auth = FirebaseAuth.getInstance()
    private var listener: OnFragmentInteractionListener? = null
    private var uid: String? = null
    private var email: String? = null
    private var tokenRef: ListenerRegistration? = null
    private var tokenList = ArrayList<Token>()

//    private var userRef: ListenerRegistration? = null
//    private var userList = ArrayList<User>()

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
//        userRef = FirebaseFirestore.getInstance().collection("Users").addSnapshotListener{ snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
//            if(exception != null){
//            }
//            for (docChange in snapshot!!.documentChanges){
//                val user = User.fromSnapshot(docChange.document)
//                when(docChange.type){
//                    DocumentChange.Type.ADDED -> {
//                        userList.add(0,user)
//                    }
////                    DocumentChange.Type.REMOVED -> {
////                        tokenList.removeAt(0)
////                    }
//                }
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_transfer, container, false)

        var receiverName = view.receive_name
        var numToken = view.transfer_token_num

        transfer(receiverName.toString(),numToken.toString())

        return view
    }

    fun transfer(receiverName:String, numToken:String){

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

        fun onFragmentInteraction(flag: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment transferFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(uid: String, email: String) =
           TransferFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UID, uid)
                    putString(ARG_EMAIL, email)
                }
            }
    }
}
