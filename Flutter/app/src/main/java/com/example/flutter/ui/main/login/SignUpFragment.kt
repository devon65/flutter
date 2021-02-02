package com.example.flutter.ui.main.login

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable

import com.example.flutter.R
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.io.FileNotFoundException

class SignUpFragment : Fragment() {
    private var param1: String? = null
    private lateinit var profilePic: ImageView
    private var listener: OnSignUpFragmentInteractionListener? = null

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
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val switchToLogin = view.findViewById<TextView>(R.id.login_here)
        switchToLogin.setOnClickListener{listener?.launchLoginScreen()}

        val nameOfUser = view.findViewById<EditText>(R.id.signup_name)
        val userAlias = view.findViewById<EditText>(R.id.signup_alias)
        val password = view.findViewById<EditText>(R.id.signup_password)

        val signUpButton = view.findViewById<Button>(R.id.signup_button)
        signUpButton.setOnClickListener{listener?.onSignUpPressed(nameOfUser.text.toString(),
            userAlias.text.toString(), password.text.toString())}

        profilePic = view.findViewById(R.id.signup_profile_pic)
        profilePic.setOnClickListener{getProfilePic()}

        val chooseProfilePic = view.findViewById<TextView>(R.id.signup_choose_photo)
        chooseProfilePic.setOnClickListener{getProfilePic()}

        return view
    }

    private fun getProfilePic() {
        val intent = Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            try {
                val targetUri = data?.data
                val bitmap = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(targetUri))
                if (bitmap != null){
                    profilePic.background = bitmap.toDrawable(resources)
                }
            }catch (e: FileNotFoundException){
                Log.e(TAG, "Could not find profile picture on system.")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSignUpFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnSignUpFragmentInteractionListener {
        fun onSignUpPressed(nameOfUser: String, userAlias: String, password: String)
        fun launchLoginScreen()
        fun selectProfilePicture(callback: (bitmap: Bitmap) -> Unit)
    }

    companion object {
        private val TAG = "SignUpFragment"
        @JvmStatic
        fun newInstance() =
            SignUpFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
