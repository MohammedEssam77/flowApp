package com.example.zapplication.repository

import com.example.zapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
     fun getPost(): Flow<Resource<*>>
}