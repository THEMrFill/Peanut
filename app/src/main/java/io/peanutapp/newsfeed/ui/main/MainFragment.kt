package io.peanutapp.newsfeed.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.peanutapp.newsfeed.R
import io.peanutapp.newsfeed.databinding.MainFragmentBinding
import io.peanutapp.newsfeed.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var binding: MainFragmentBinding
  private val viewModel: MainViewModel by viewModel()
  private val mainAdapter = MainAdapter(::rowClick)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = MainFragmentBinding.bind(view)
    with (binding) {
      with(recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = mainAdapter
      }
    }
    setupObservers()
  }

  private fun setupObservers() {
    with (viewModel) {
      news.nonNullObserve(viewLifecycleOwner) { news ->
        mainAdapter.setProducts(news)
      }
      error.nonNullObserve(viewLifecycleOwner) { error ->

      }
    }
  }

  private fun rowClick(id: String) {
    (activity as MainActivity).pushToPost(id)
  }
}
