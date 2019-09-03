package com.example.jvmori.discovermovies.ui.view.discover

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager.widget.ViewPager
import com.example.jvmori.discovermovies.MainActivity
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.BaseAdapter
import com.example.jvmori.discovermovies.ui.adapters.SliderPagerAdapter
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresViewInterface
import com.example.jvmori.discovermovies.ui.presenter.nowPlaying.NowPlayingContract
import com.example.jvmori.discovermovies.ui.presenter.trending.TrendingContract
import com.example.jvmori.discovermovies.util.navigateToDetails
import com.example.jvmori.discovermovies.util.navigateToMovieList
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_discover.*
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DiscoverFragment : DaggerFragment(),
    GenresViewInterface,
    TrendingContract.TrendingView,
    NowPlayingContract.NowPlayingView,
    BaseAdapter.IOnItemClickListener<MovieResult>
{

    @Inject
    lateinit var genresPresenter: GenresPresenterInterface
    @Inject
    lateinit var trendingPresenter: TrendingContract.TrendingPresenter
    @Inject
    lateinit var nowPlayingPresenter : NowPlayingContract.NowPlayingPresenter

    //TODO: dagger inject
    private var genresMap = mutableMapOf<Int, String>()
    private lateinit var contextActivity: Context
    private var timer: Timer? = null
    private var movies : List<MovieResult>? = null

    override fun onAttach(context: Context) {
        if (context is MainActivity) contextActivity = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trendingPresenter.setView(this)
        nowPlayingPresenter.setView(this)
        trendingPresenter.getTrending("week",3)
        nowPlayingPresenter.fetchNowPlaying()
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        genresPresenter.setView(this)
        genresPresenter.getGenres()
        //TODO: databinding
        slider_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
               restartTimer()
            }
            override fun onPageSelected(position: Int) {

            }
        })
    }
    private fun restartTimer(){
        timer?.cancel()
        movies?.let {
            timer = Timer()
            timer?.schedule(SliderTimer(contextActivity, slider_pager, it), 4000, 6000)
        }
    }

    override fun displayGenres(genreResponse: List<Genre>) {
        genreResponse.forEach {
            genresMap[it.idGenre] = it.name
        }
    }

    override fun showRandomTrending(movies: List<MovieResult>) {
        this.movies = mutableListOf()
        this.movies = movies
        setupSliderAdapter(movies)
        setupSliderTimer(movies)
    }

    override fun showAllTrending(movies: List<MovieResult>) {
        popularMoviesSection.setRecyclerView(this.requireContext(), movies)
        popularMoviesSection.setIOnItemClickedListener(this)
        popularMoviesSection.clickOnMoreBtn {
            //navigateToMovieList(Genre(1000, "Trending"), this, R.id.action_discoverFragment_to_trendingMoviesFragment)
        }
        showRandomTrending(trendingPresenter.chooseRandomMovies(3, movies))
}

    override fun showNowPlaying(movies: List<MovieResult>) {
        nowPlayingMoviesSection.setRecyclerView(this.requireContext(), movies)
        nowPlayingMoviesSection.setIOnItemClickedListener(this)
    }

    override fun onItemClicked(movieResult: MovieResult) {
        navigateToDetails(movieResult, this, R.id.action_discoverFragment_to_detailsFragment)
    }

    private fun setupSliderAdapter(movies: List<MovieResult>) {
        val adapter = SliderPagerAdapter(this.requireContext(), movies, genresMap)
        slider_pager.adapter = adapter
        worm_dots_indicator.setViewPager(slider_pager)
    }

    private fun setupSliderTimer(movies: List<MovieResult>) {
        timer = Timer()
        timer?.schedule(SliderTimer(contextActivity, slider_pager, movies), 4000, 6000)
    }

    override fun showProgressBar() {
        loadingIncluded?.visibility = View.VISIBLE
        content?.visibility = View.GONE
    }

    override fun hideProgressBar() {
        loadingIncluded?.visibility = View.GONE
        content?.visibility = View.VISIBLE
    }

    override fun displayError(s: String) {
        Log.i(MainActivity.TAG, "error")
    }

    class SliderTimer(
        private var context: Context,
        private var sliderPager: ViewPager?,
        private val movies: List<MovieResult>
    ) : TimerTask() {
        override fun run() {
            sliderPager?.let{
                (context as MainActivity).runOnUiThread {
                    if (it.currentItem < movies.size - 1) {
                        it.setCurrentItem(it.currentItem + 1, true)
                    } else {
                        it.setCurrentItem(0, true)
                    }
                }
            }
        }
    }
}
