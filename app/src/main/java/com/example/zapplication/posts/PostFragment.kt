package com.example.zapplication.posts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zapplication.databinding.FragmentPostBinding
import com.example.zapplication.entities.Posts
import com.example.zapplication.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {
    private lateinit var binding: FragmentPostBinding
    private val viewModel by viewModels<PostsViewModel>()
    private val adapter by lazy {
        PostAdapter(PostItemClick { it ->
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPostBinding.inflate(inflater, container, false)
            .apply {
                binding = this
                lifecycleOwner = viewLifecycleOwner
                vm = viewModel
            }.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        binding.swipeRefresh.setOnRefreshListener { viewModel.getPosts() }
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(requireContext(),1)
        binding.itemsRecyclerview.layoutManager =layoutManager
        binding.itemsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount =layoutManager.childCount
                val pastVisibleItem =layoutManager.findFirstVisibleItemPosition()
                val total =adapter.itemCount
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        viewModel.getPosts()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        binding.itemsRecyclerview.adapter = adapter

    }
    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner, Observer { result ->
            binding.swipeRefresh.isRefreshing =result.status.get() ==Status.LOADING
            when (result.status.get()) {
                Status.SUCCESS -> result.data?.let { adapter.submitList(it) }
                Status.ERROR -> Toast.makeText(requireContext(), result.msg, Toast.LENGTH_SHORT)
                    .show()
                else -> {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        })
    }
}
