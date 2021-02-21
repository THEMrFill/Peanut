package io.peanutapp.newsfeed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`

class RecyclerViewItemCountAssertion(val count: Int): ViewAssertion {
  private var expectedCount = 0
  init { expectedCount = count }

  override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
    if (noViewFoundException != null) {
      throw noViewFoundException
    }
    val recyclerView = view as RecyclerView
    val adapter = recyclerView.adapter
    assertThat(adapter!!.itemCount, `is`(expectedCount))
  }
}
