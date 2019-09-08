package com.example.notificationsapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.notificationsapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText


class NewTaskFragment : Fragment() {

    private var listener: OnSaveTaskClickListener? = null
    private lateinit var addButton: MaterialButton
    private lateinit var nameView: TextInputEditText
    private lateinit var flowView: SwitchMaterial


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_task, container, false)

        addButton = view.findViewById(R.id.material_button_add)
        nameView = view.findViewById(R.id.textInputName)
        flowView = view.findViewById(R.id.flowSwitchMaterial)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSaveTaskClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addButton.setOnClickListener {
            val name = nameView.text.toString()

            if (name.isEmpty()) {
                nameView.setError("Campo obrigatório")
            } else if (name.length > 30) {
                nameView.setError("Apenas são permitidos 30 carateres")
            } else {
                val flow = flowView.isChecked
                listener?.saveTaskClick(name, flow)

                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        fun newInstance() = NewTaskFragment()
    }

    interface OnSaveTaskClickListener {
        fun saveTaskClick(taskName: String, flow: Boolean)
    }
}
