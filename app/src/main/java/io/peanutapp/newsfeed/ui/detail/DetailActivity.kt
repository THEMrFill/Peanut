package io.peanutapp.newsfeed.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.peanutapp.newsfeed.R
import io.peanutapp.newsfeed.ui.main.MainFragment

class DetailActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    if (savedInstanceState == null) {
      val id = intent.getStringExtra("id")
      id?.let { id ->
        supportFragmentManager.beginTransaction()
          .replace(R.id.container, DetailFragment.newInstance(id))
          .commitNow()
      } ?: run {
        onBackPressed()
      }
    }
  }
}
