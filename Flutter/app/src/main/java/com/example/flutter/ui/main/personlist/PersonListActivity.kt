package com.example.flutter.ui.main.personlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.flutter.R
import com.example.flutter.models.User
import com.example.flutter.ui.main.story.StoryContract
import com.example.flutter.ui.main.story.UserStoryActivity

class PersonListActivity : AppCompatActivity(), PersonListFragment.PersonListFragmentListener,
    PersonListContract.IPersonListActivity{

    internal lateinit var presenter: PersonListContract.IPersonListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_list)

        val title = intent.getStringExtra(PERSON_LIST_TITLE) ?: ""
        val titleView = findViewById<TextView>(R.id.person_list_title)
        titleView.text = title

        val personList = intent.getStringArrayListExtra(PERSON_ID_LIST)
        launchPersonListFragment(personList)
        setPresenter(PersonListPresenter(this))
    }

    override fun setPresenter(presenter: PersonListContract.IPersonListPresenter) {
        this.presenter = presenter
    }

    fun launchPersonListFragment(personIdList: ArrayList<String>){
        val fragment = PersonListFragment.newInstance(personIdList)
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

    override fun getUserListByIdList(idList: List<String>): List<User> {
        return presenter.getUserListByIdList(idList)
    }

    companion object{
        const val PERSON_ID_LIST = "personIdList"
        const val PERSON_LIST_TITLE = "personListTitle"
    }
}
