package com.nyan.android.animeme.di

import com.nyan.android.animeme.network.NetworkMapper
import com.nyan.android.animeme.network.NetworkService
import com.nyan.android.animeme.repository.MainRepository
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