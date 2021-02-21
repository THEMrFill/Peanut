package io.peanutapp.newsfeed.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.peanutapp.newsfeed.R
import io.peanutapp.newsfeed.databinding.DetailFragmentBinding
import io.peanutapp.newsfeed.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment: Fragment() {
  companion object {
    const val ID = "ID"

    fun newInstance(id: String): DetailFragment {
      val bundle = Bundle()
      bundle.putString(ID, id)
      val fragment = DetailFragment()
      fragment.arguments = bundle
      return fragment
    }
  }

  private var id: String? = null

  private val viewModel: DetailViewModel by viewModel()
  private lateinit var binding: DetailFragmentBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.detail_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = DetailFragmentBinding.bind(view)

    id = arguments?.getString(ID)

    setupObservers()
  }

  private fun setupObservers() {
    with (viewModel) {
      id?.let { id ->
        getPost(id)
      }
      post.nonNullObserve(viewLifecycleOwner) { post ->
        binding.newsPost = post
      }
      error.nonNullObserve(viewLifecycleOwner) { error ->

      }
    }
  }


}
