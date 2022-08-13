package com.example.zapplication.di

import com.example.zapplication.repository.PostRepository
import com.example.zapplication.repository.RepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImp: RepositoryImp):PostRepository
}