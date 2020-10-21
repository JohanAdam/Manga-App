package com.example.android.marsrealestate.di

import com.example.android.marsrealestate.network.NetworkMapper
import com.example.android.marsrealestate.network.NetworkService
import com.example.android.marsrealestate.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        networkService: NetworkService,
        networkMapper: NetworkMapper): MainRepository {
        return MainRepository(networkService, networkMapper)
    }

}