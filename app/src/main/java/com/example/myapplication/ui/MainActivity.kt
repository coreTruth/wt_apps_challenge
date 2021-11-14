package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        initView()
        initViewModel()
        setContentView(viewBinding.root)
    }

    private fun initView() = with(viewBinding) {
        bookAdapter = BookAdapter()
        rvBookList.adapter = bookAdapter
    }

    private fun initViewModel() = with(viewModel) {
        loading.observe(this@MainActivity, ::showProgressBar)
        books.observe(this@MainActivity) {
            bookAdapter.submitList(it)
        }
        state.observe(this@MainActivity) {
            when(it) {
                is MainActivityViewModel.MainActivityViewState.Success -> Unit
                is MainActivityViewModel.MainActivityViewState.Error -> {
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        viewBinding.progressBar.isVisible = show
    }
}