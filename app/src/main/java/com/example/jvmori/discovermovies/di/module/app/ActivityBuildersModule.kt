package com.example.jvmori.discovermovies.di.module.app

import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.di.module.main.*
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import com.example.jvmori.discovermovies.di.scope.MainActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            TrendingModule::class,
            PresenterModule::class,
            NowPlayingModule::class,
            CollectionModule::class,
            GenresModule::class,
            DetailsModule::class,
            MoviesModule::class
        ]
    )
    abstract fun contributeMainActivity() : MainActivity
}
