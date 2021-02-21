package io.peanutapp.newsfeed.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.peanutapp.newsfeed.R
import io.peanutapp.newsfeed.ui.detail.DetailActivity
import io.peanutapp.newsfeed.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, MainFragment.newInstance())
        .commitNow()
    }
  }

  fun pushToPost(id: String) {
    val intent = Intent(this, DetailActivity::class.java).apply {
      putExtra("id", id)
    }
    startActivity(intent)
  }
}
