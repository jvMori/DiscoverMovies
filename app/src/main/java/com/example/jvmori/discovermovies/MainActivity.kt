package com.example.jvmori.discovermovies
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import androidx.navigation.Navigation
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {
    companion object {
        val TAG = "DiscoverMovies"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val controller = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, controller)
    }

}
