package com.example.flutter.ui.main.story

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flutter.R
import com.example.flutter.Utils.SessionInfo
import com.example.flutter.models.Status
import com.example.flutter.models.User
import com.example.flutter.ui.main.login.SignUpFragment
import com.example.flutter.ui.main.status.OnStatusInteractionListener
import com.example.flutter.ui.main.status.StatusRecyclerViewAdapter
import java.io.FileNotFoundException
import java.lang.Exception
import android.view.View.OnFocusChangeListener



/**
 * A placeholder fragment containing a simple view.
 */
class StoryBoardFragment : Fragment() {

    private var displayedUser: User? = null
    private var listener: OnStoryBoardInteractionListener? = null
    private var statusAttachment: ImageView? = null
    private var profilePicture: ImageView? = null
    private var updateStatusLayout: LinearLayout? = null
    private var statusText: EditText? = null
    private var postStatusButton: Button? = null
    private var cancelStatusButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val userId = it.getString(USER_ID)
            this.displayedUser = listener?.getUser(userId)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_story_board, container, false)

        val userName = view.findViewById<TextView>(R.id.user_name)
        userName.text = displayedUser?.name
        val userAlias = view.findViewById<TextView>(R.id.user_alias)
        userAlias.text = displayedUser?.alias

        val followersButton = view.findViewById<Button>(R.id.followers_button)
        followersButton.setOnClickListener{
            val personIdList = ArrayList(displayedUser?.followers ?: listOf())
            val title = if(personIdList.isNullOrEmpty()) {
                String.format(getString(R.string.person_list_has_no_friends), displayedUser?.name)
            }else{
                String.format(getString(R.string.person_list_title_followers), displayedUser?.name)
            }
            listener?.launchPersonList(title, personIdList)
        }
        val usersFollowedButton = view.findViewById<Button>(R.id.users_followed_button)
        usersFollowedButton.setOnClickListener{
            val personIdList = ArrayList(displayedUser?.usersFollowed ?: listOf())
            val title = if(personIdList.isNullOrEmpty()) {
                String.format(getString(R.string.person_list_has_no_friends), displayedUser?.name)
            }else{
                String.format(getString(R.string.person_list_title_users_followed), displayedUser?.name)
            }
            listener?.launchPersonList(title, personIdList)}

        val statusList = view.findViewById<RecyclerView>(R.id.story_status_list)
        val storyFeed = listener?.getUserStory(displayedUser) ?: listOf()
        // Set the adapter
        with(statusList) {
            layoutManager = LinearLayoutManager(context)
            adapter = StatusRecyclerViewAdapter(storyFeed, listener)
        }

        if (displayedUser?.userId == SessionInfo.currentUser.userId){
            initializeCurrentUserEditing(view)
        }

        return view
    }

    private fun initializeCurrentUserEditing(view: View){
        profilePicture = view.findViewById<ImageView>(R.id.story_profile_pic)
        profilePicture?.setOnClickListener{launchSelectPhoto(EDIT_PROFILE_PIC_REQUEST_CODE)}
        val editProfileButton = view.findViewById<LinearLayout>(R.id.story_edit_profile_pic)
        editProfileButton.visibility = View.VISIBLE
        editProfileButton.setOnClickListener{launchSelectPhoto(EDIT_PROFILE_PIC_REQUEST_CODE)}

        updateStatusLayout = view.findViewById<LinearLayout>(R.id.story_update_status_layout)
        updateStatusLayout?.visibility = View.VISIBLE

        val statusUpdateAttachmentButton = view.findViewById<ImageView>(R.id.story_update_status_attachment_icon)
        statusUpdateAttachmentButton.setOnClickListener{
            enableStatusEditing()
            launchSelectPhoto(ADD_ATTACHMENT_REQUEST_CODE)
        }

        statusText = view.findViewById<EditText>(R.id.story_update_status_text)
        statusText?.setOnFocusChangeListener{ v, hasFocus ->
            if (hasFocus){
                enableStatusEditing()
            }
        }
        statusText?.setOnClickListener{enableStatusEditing()}
        statusAttachment = view.findViewById<ImageView>(R.id.story_status_attachment)

        postStatusButton = view.findViewById<Button>(R.id.story_post_status_button)
        postStatusButton?.setOnClickListener{onPostStatusClicked()}
        cancelStatusButton = view.findViewById<Button>(R.id.story_cancel_status_button)
        cancelStatusButton?.setOnClickListener{clearOutStatusEdit()}
    }

    private fun launchSelectPhoto(requestCode: Int){
        val intent = Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    private fun enableStatusEditing(){
        statusText?.isCursorVisible = true
        postStatusButton?.visibility = View.VISIBLE
        cancelStatusButton?.visibility = View.VISIBLE
    }

    private fun onPostStatusClicked(){
        clearOutStatusEdit()
    }

    private fun clearOutStatusEdit(){
        statusText?.text?.clear()
        statusText?.isCursorVisible = false
        statusAttachment?.setImageResource(android.R.color.transparent)
        postStatusButton?.visibility = View.GONE
        cancelStatusButton?.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            try {
                val targetUri = data?.data
                val bitmap = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(targetUri))
                if (bitmap != null){
                    when (requestCode) {
                        EDIT_PROFILE_PIC_REQUEST_CODE -> this.profilePicture?.background = bitmap.toDrawable(resources)
                        ADD_ATTACHMENT_REQUEST_CODE -> this.statusAttachment?.setImageBitmap(bitmap)
                    }
                }
            }catch (e: FileNotFoundException){
                Log.e(TAG, "Could not find selected picture on system.")
            }catch (e: Exception){
                Log.e(TAG, "Could not convert file")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnStoryBoardInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnStoryBoardInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnStoryBoardInteractionListener: OnStatusInteractionListener {
        fun getUser(userId: String?): User?
        fun getUserStory(user: User?): List<Status>
        fun launchPersonList(title: String, userIdList: ArrayList<String>)
    }

    companion object {
        private const val TAG = "StoryBoardFragment"
        private const val USER_ID = "userId"
        private const val EDIT_PROFILE_PIC_REQUEST_CODE = 0
        private const val ADD_ATTACHMENT_REQUEST_CODE = 1

        @JvmStatic
        fun newInstance(userId: String? = null): StoryBoardFragment {
            return StoryBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                }
            }
        }
    }
}