package com.example.jvmori.discovermovies.di.module.app

import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.di.module.*
import com.example.jvmori.discovermovies.di.module.main.MainFragmentBuildersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            PresenterModule::class,
            TrendingModule::class,
            NowPlayingModule::class,
            CollectionModule::class,
            GenresModule::class,
            DetailsModule::class,
            MoviesModule::class
        ]
    )
    abstract fun contributeMainActivity() : MainActivity
}
