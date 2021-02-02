package com.example.flutter.ui.main.login

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.example.flutter.R
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.w3c.dom.Text


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnLoginFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val switchToSignUpButton = view.findViewById<TextView>(R.id.signup_here)
        switchToSignUpButton.setOnClickListener{listener?.launchSignUpScreen()}

        val userAlias = view.findViewById<EditText>(R.id.login_alias)
        val password = view.findViewById<EditText>(R.id.login_password)

        val loginButton = view.findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener{listener?.onLoginClicked(userAlias.text.toString(), password.text.toString())}
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLoginFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnLoginFragmentInteractionListener {
        fun onLoginClicked(username: String, password: String)
        fun launchSignUpScreen()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
