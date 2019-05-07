package com.example.jvmori.discovermovies.ui.view.discover

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.ui.adapters.GenreAdapter
import com.example.jvmori.discovermovies.ui.adapters.SliderPagerAdapter
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresPresenterInterface
import com.example.jvmori.discovermovies.ui.presenter.genres.GenresViewInterface
import kotlinx.android.synthetic.main.fragment_discover.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DiscoverFragment : Fragment(), GenresViewInterface{

    @Inject
    lateinit var genresPresenter : GenresPresenterInterface
    private var genresMap = mutableMapOf<Int,String>()

    val movies = mutableListOf<MovieResult>()
    override fun onAttach(context: Context) {
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movies.add(0, MovieResult(0,false,"","/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
            mutableListOf(28,12,878),"pl","pl","",1.4, "","","Avengers: Endgame",
            false,23.4,123))
        movies.add(1, MovieResult(0,false,"","/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
            mutableListOf(28,12,878),"pl","pl","",1.4, "","","Avengers: Endgame",
            false,23.4,123))
        movies.add(2, MovieResult(0,false,"","/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
            mutableListOf(28,12,878),"pl","pl","",1.4, "","","Avengers: Endgame",
            false,23.4,123))
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genresPresenter.setView(this)
        genresPresenter.getGenres()
    }

    override fun displayGenres(genreResponse: List<Genre>) {
        genreResponse.forEach {
            genresMap[it.idGenre] = it.name
        }
        val adapter = SliderPagerAdapter(this.requireContext(), movies, genresMap)
        slider_pager.adapter = adapter
    }
    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

    override fun displayError(s: String) {

    }
}
