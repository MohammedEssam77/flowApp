package com.example.zapplication.brands


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.zapplication.databinding.FragmentPostBinding
import com.example.zapplication.entities.Posts
import com.example.zapplication.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {
    private lateinit var binding : FragmentPostBinding
    private val viewModel by viewModels<PostsViewModel>()
    private var results = mutableListOf<Posts>()
    private val adapter by lazy {
       PostAdapter(PostItemClick { it ->


        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPostBinding.inflate(inflater, container,false)
            .apply {
                binding = this
                lifecycleOwner = viewLifecycleOwner
                vm = viewModel
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemsRecyclerview.adapter = adapter

        setupObservers()
        binding.swipeRefresh.setOnRefreshListener{viewModel.getPosts()}
    }

    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner, Observer { result ->
            when (result.status.get()) {
                Status.LOADING -> binding.swipeRefresh.isRefreshing = true
                Status.SUCCESS -> result.data?.let { adapter.submitList(it) }
                Status.ERROR -> Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT)
                    .show()
                else -> {
              //     adapter.submitList(results)
                    binding.swipeRefresh.isRefreshing = false
                 }
            }
        })
    }
}
