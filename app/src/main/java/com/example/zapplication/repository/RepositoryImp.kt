package com.example.zapplication.repository

import com.example.zapplication.entities.Posts
import com.example.zapplication.remote.PostApi
import com.example.zapplication.utils.Resource
import com.example.zapplication.utils.Status
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImp
@Inject
constructor(private val postApi: PostApi):BaseRepo(),PostRepository {

    override fun getPost()
    = loadFromApi ((postApi::getPostItems)).map{
            if (it.status.get() == Status.SUCCESS)
                Resource.success((it.data as List<Posts>))
            else it
        }
    }
