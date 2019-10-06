package com.example.flutter.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.flutter.R
import com.example.flutter.ui.main.feed.NewsFeedFragment
import com.example.flutter.ui.main.story.StoryBoardFragment

private val TAB_TITLES = arrayOf(
        R.string.main_my_story,
        R.string.main_news_feed
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class HomePagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a StoryBoardFragment (defined as a static inner class below).
        return when(position){
            0 -> StoryBoardFragment.newInstance()
            else -> NewsFeedFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}