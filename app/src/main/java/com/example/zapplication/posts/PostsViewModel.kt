package com.example.zapplication.posts

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
constructor(private val postHandler: PostHandler):ViewModel() {
    private val _postLiveData by lazy { MutableLiveData<Resource<List<Posts>>>().also { getPosts() } }
    val data= _postLiveData as LiveData<Resource<List<Posts>>>

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _postLiveData.postValue(Resource.loading())
            postHandler.getPost().first().also {
                _postLiveData.postValue(it as Resource<List<Posts>>)

            }
        }
    }
}

