package com.example.zapplication.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostHandler
@Inject
constructor(private val postRepository: PostRepository) {
     fun getPost()=postRepository.getPost()
}