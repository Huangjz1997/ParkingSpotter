package edu.rosehulman.parkingspotter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [getFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [getFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class getFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var tempSelection: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_get,container,false)
        var selectButton = view.findViewById<Button>(R.id.selectLotButton)

        selectButton.setOnClickListener{
//            val listItems = arrayOf("Speed Side","Speed Main", "Precopo Main", "SRC Main","SRC Back")
            val dialogBuilder = AlertDialog.Builder(this.context)
            dialogBuilder.setTitle("Hi")
            dialogBuilder.setMessage("Select the parking lot")
            dialogBuilder.setCancelable(false)
            val dialogView = LayoutInflater.from(this.context).inflate(R.layout.dialog_choose,null,false)
            dialogBuilder.setView(dialogView)

            dialogBuilder.setNeutralButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            var speedSide = dialogView.findViewById<Button>(R.id.speedSide)
            speedSide.setOnClickListener{
                tempSelection = 11
                speedSide.setBackgroundColor(Color.RED)
            }
            dialogBuilder.setPositiveButton("Select"){_,_ ->
                if(tempSelection == 11) {
                    listener!!.onFragmentInteraction(11)
                }
            }
            dialogBuilder.create().show()


        }



//        var speedMain = view.findViewById<Button>(R.id.speedMain)
//        var percopoMain = view.findViewById<Button>(R.id.percopoMain)
//        var srcMain = view.findViewById<Button>(R.id.srcMain)
//        var srcBack = view.findViewById<Button>(R.id.srcBack)

         return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Int) {
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
        fun onFragmentInteraction(num: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment getFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            getFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
