package com.example.jvmori.discovermovies.ui.view.details


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.application.MoviesApplication
import com.example.jvmori.discovermovies.data.network.response.credits.Cast
import com.example.jvmori.discovermovies.data.network.response.credits.Crew
import com.example.jvmori.discovermovies.data.network.response.movie.MovieDetails
import com.example.jvmori.discovermovies.data.local.entity.MovieResult
import com.example.jvmori.discovermovies.data.network.response.video.VideoResponse
import com.example.jvmori.discovermovies.ui.adapters.CastAdapter
import com.example.jvmori.discovermovies.ui.adapters.CrewAdapter
import com.example.jvmori.discovermovies.ui.adapters.MoviesAdapter
import com.example.jvmori.discovermovies.ui.adapters.SimilarMoviesAdapter
import com.example.jvmori.discovermovies.ui.presenter.collections.CollectionPresenter
import com.example.jvmori.discovermovies.ui.presenter.collections.SavingBasePresenter
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsPresenter
import com.example.jvmori.discovermovies.ui.presenter.details.DetailsView
import com.example.jvmori.discovermovies.ui.view.collections.AddToColBottomDialog
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject
import com.google.android.material.appbar.AppBarLayout


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsFragment : Fragment(), DetailsView {

    @Inject
    lateinit var detailsPresenter: DetailsPresenter
    @Inject
    lateinit var savingBasePresenter : SavingBasePresenter
    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    private var movieResult: MovieResult? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.VISIBLE
        handleAppBarCollapsing()
        toolbarId.setNavigationOnClickListener{
            View.OnClickListener {
                NavHostFragment.findNavController(this).popBackStack()
            }
        }
        movieResult?.let{
            createAddBtnClickListener(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsPresenter.setView(this)
        movieResult = getMovieResult()
        movieResult?.let {
            detailsPresenter.getDetails(it.id)
        }
    }

    private fun createAddBtnClickListener(movieResult: MovieResult){
        addBtn.setOnClickListener {
            val bottomSheetDialogFragment = AddToColBottomDialog(movieResult, savingBasePresenter, collectionPresenter)
            bottomSheetDialogFragment.show(this.requireFragmentManager(), "Bottom Sheet Dialog")
        }
    }

    private fun getMovieResult(): MovieResult? {
        return (arguments?.getSerializable("movieResult") as MovieResult)
    }


    override fun showResults(movieDetails: MovieDetails) {
        setDetailViewUI(movieDetails)
    }

    override fun showCast(cast: List<Cast>) {
        createCastAdapter(cast)
    }

    override fun showCrew(crew: List<Crew>) {
        createCrewAdapter(crew)
    }

    override fun showRecommendations(recommendations: List<MovieResult>) {
        createRecommendationsAdapter(recommendations)
    }

    override fun getVideo(video: VideoResponse) {
        setupVideoView(video.results[0].key)
    }

    override fun showProgressBar() {

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
        detailsPresenter.dispose()
    }

    private fun handleAppBarCollapsing() {
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                backdropImg.visibility = View.INVISIBLE
                backdropLayout.visibility = View.INVISIBLE
            } else {
                backdropImg.visibility = View.VISIBLE
                backdropLayout.visibility = View.VISIBLE
            }
        })
    }

    private fun setDetailViewUI(movieDetails: MovieDetails) {
        collapsingToolbar.title = movieDetails.title;
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
        categoryItem.text = categoryTxt
        overview.text = movieDetails.overview
        ratingItem.text = movieDetails.voteAverage.toString()
        val rating = (movieDetails.voteAverage) * 10
        MoviesAdapter.setStars(rating, starsLayout)
    }

    private fun setupVideoView(videoId: String) {
        lifecycle.addObserver(videoView)
        videoView.initialize({ youTubePlayer ->
            youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }, true)
    }

    private fun createCastAdapter(cast: List<Cast>) {
        val adapter = CastAdapter()
        adapter.setItems(cast)
        creditsRecyclerView.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
        creditsRecyclerView.adapter = adapter
        creditsRecyclerView.setHasFixedSize(true)
    }

    private fun createCrewAdapter(crew: List<Crew>) {
        crewRecyclerView.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
        crewRecyclerView.setHasFixedSize(true)
        val adapter = CrewAdapter()
        adapter.setItems(crew)
        crewRecyclerView.adapter = adapter
    }

    private fun createRecommendationsAdapter(movies: List<MovieResult>) {
        similarRecyclerView.layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
        similarRecyclerView.setHasFixedSize(true)
        val adapter = SimilarMoviesAdapter()
        adapter.setItems(movies)
        similarRecyclerView.adapter = adapter
    }
}
