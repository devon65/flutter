package com.example.flutter.ui.main.personlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.utils.Constants
import com.example.flutter.models.User
import com.example.flutter.ui.main.story.UserStoryActivity

class PersonListActivity : AppCompatActivity(), PersonListFragment.PersonListFragmentListener,
    PersonListContract.IPersonListActivity{

    internal lateinit var presenter: PersonListContract.IPersonListPresenter
    internal lateinit var titleView: TextView
    internal lateinit var displayedUserId: String
    internal lateinit var displayedUsersName: String
    internal lateinit var personListType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_list)

        title = getString(R.string.person_list_my_peeps)
        titleView = findViewById(R.id.person_list_title)

        displayedUserId = intent.getStringExtra(USER_ID)
        personListType = intent.getStringExtra(PERSON_LIST_TYPE)
        displayedUsersName = intent.getStringExtra(NAME)
        launchPersonListFragment()
        setPresenter(PersonListPresenter(this))
    }

    override fun setPresenter(presenter: PersonListContract.IPersonListPresenter) {
        this.presenter = presenter
    }

    fun launchPersonListFragment(){
        val fragment = PersonListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.person_list_fragment_holder, fragment)
            .commit()
    }

    override fun onPersonClicked(person: User?) {
        val userId = person?.userId
        if (userId != null) {
            val intent = Intent(this, UserStoryActivity::class.java).apply {
                putExtra(UserStoryActivity.USER_ID, userId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
    }

    override fun getPersonList(
        onSuccess: (List<User>) -> Unit,
        onFailure: () -> Unit,
        lastUser: User?
    ) {
        presenter.getPersonList(personListType, displayedUserId,
        {
            titleView.text = when {
                it.isNullOrEmpty() -> String.format(getString(R.string.person_list_has_no_friends), displayedUsersName)
                personListType == Constants.FOLLOWERS -> String.format(getString(R.string.person_list_title_followers), displayedUsersName)
                personListType == Constants.USERS_FOLLOWED -> String.format(getString(R.string.person_list_title_users_followed), displayedUsersName)
                else -> "My Peeps!"
            }
            onSuccess(it)
        }, onFailure, lastUser)
    }

    companion object{
        const val PERSON_LIST_TYPE = "personListType"
        const val USER_ID = "userId"
        const val NAME = "name"
    }
}
