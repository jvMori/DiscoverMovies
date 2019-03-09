package com.example.jvmori.discovermovies.ui.view.details


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.network.response.MovieDetails
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsFragment : Fragment(), DetailsView{

    @Inject
    lateinit var detailsPresenter: DetailsPresenter

    override fun onAttach(context: Context) {
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsPresenter.setView(this)

        val movieId : Int? = getMovieId()
        movieId?.let {
            detailsPresenter.fetchDetails(movieId)
        }
    }

    private fun getMovieId(): Int? {
        return DetailsFragmentArgs.fromBundle(arguments).movieId
    }

    override fun showResults(movieDetails: MovieDetails) {
        setDetailViewUI(movieDetails)
    }

    override fun showProgressBar() {
        //progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun displayError(s: String) {
        errorLayout.visibility = View.VISIBLE
        errorText.text = s
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsPresenter.onClear()
    }

    private fun setDetailViewUI(movieDetails: MovieDetails) {
        LoadImage.loadImage(this.requireContext(), backdropImg, Const.base_backdrop_url + movieDetails.backdropPath)
        LoadImage.loadImage(this.requireContext(), posterImg, Const.base_poster_url + movieDetails.posterPath)
        titleTextView.text = movieDetails.title
        runtimeDate.text = "${movieDetails.runtime} min, ${movieDetails.releaseDate}"

        val categoryTxt = StringBuilder()
        for (genre in movieDetails.genres) {
            val txtGenre = genre.name
            val txtGenres = movieDetails.genres
            categoryTxt.append(txtGenre)
            if (txtGenre != txtGenres[txtGenres.size - 1].name)
                categoryTxt.append(" | ")
        }
        category.text = categoryTxt
        overview.text = movieDetails.overview
        rating.text = movieDetails.voteAverage.toString()
        val rating = (movieDetails.voteAverage) * 10
        //MovieItemAdapter.setStars(rating, starsLayout)
    }
}
