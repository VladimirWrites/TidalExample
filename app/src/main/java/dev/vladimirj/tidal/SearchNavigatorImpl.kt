package dev.vladimirj.tidal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dev.vladimirj.tidal.search.ui.SearchNavigator

class SearchNavigatorImpl: SearchNavigator {

    override fun goToSearch(activity: FragmentActivity) {
        // TODO: Implement navigation
    }

    private fun navigateToFragment(activity: FragmentActivity, fragment: Fragment, tag: String, addToBackStack: Boolean = false) {
        val ft = activity.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment, tag)
        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.commit()
    }
}