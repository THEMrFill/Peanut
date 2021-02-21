package io.peanutapp.newsfeed

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.peanutapp.newsfeed.ui.main.MainFragment
import org.junit.Test

/*
 * NOTE: At the moment this isn't working, I don't know why, but I think it's part of Room,
 * as I've tended to land on Realm recently, I've not had enough recent experience with
 * Room and Espresso, so I don't know if this is an issue
 *
 * Running the app by hand while this is running actually passes the tests
 *
 * Given more time, I could find out why it's not working, but wanted to get the code to you
 */

class ListTest {

  // as we know (at this point) that the API isn't working, so the list will ALWAYS
  // have 10 entries - I'd normally swap the API call with a mocked one
  // see the tests here for an example:
  // https://github.com/THEMrFill/Retail-inMotion
  @Test
  fun checkListIsPopulated() {
    launchFragmentInContainer<MainFragment>()
    Thread.sleep(100)
    onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(10))
  }

}
