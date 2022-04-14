package com.haberturm.rickandmorty.di

import com.haberturm.rickandmorty.data.repositories.Repository
import com.haberturm.rickandmorty.data.repositories.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRepository() : Repository{
        return RepositoryImpl()
    }
}