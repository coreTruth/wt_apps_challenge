package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.entity.Book
import com.example.myapplication.data.network.NetworkResource
import com.example.myapplication.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor (
   private val bookRepository: BookRepository
): ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _books = MutableLiveData<List<Book>>(mutableListOf())
    val books: LiveData<List<Book>> get() = _books

    private val _state = MutableLiveData<MainActivityViewState>()
    val state: LiveData<MainActivityViewState> get() = _state

    init {
        fetchData()
    }

    private fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        _loading.postValue(true)

        when(val response = bookRepository.fetchBookList()) {
            is NetworkResource.Success -> handleSuccess(response.value)
            is NetworkResource.Failure -> handleError(R.string.fail_fetch_book_list)
        }
        _loading.postValue(false)
    }

    private fun handleSuccess(bookList: List<Book>) {
        _books.postValue(bookList)
        _state.postValue(MainActivityViewState.Success)
    }

    private fun handleError(resId: Int) {
        _state.postValue(MainActivityViewState.Error(resId))
    }

    sealed class MainActivityViewState {
        object Success : MainActivityViewState()

        data class Error(
            val error: Int
        ) : MainActivityViewState()
    }
}