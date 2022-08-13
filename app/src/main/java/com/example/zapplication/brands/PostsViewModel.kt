package com.example.zapplication.brands

import androidx.lifecycle.*
import com.example.zapplication.entities.Posts
import com.example.zapplication.repository.PostHandler
import com.example.zapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel
@Inject
constructor(private val brandHandler: PostHandler):ViewModel() {
    private val _brandLiveData by lazy { MutableLiveData<Resource<List<Posts>>>().also { getPosts() } }
    val data= _brandLiveData as LiveData<Resource<List<Posts>>>

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _brandLiveData.postValue(Resource.loading())
            brandHandler.getPost().first().also {
                _brandLiveData.postValue(it as Resource <List<Posts>>)

            }

        }
    }
}

