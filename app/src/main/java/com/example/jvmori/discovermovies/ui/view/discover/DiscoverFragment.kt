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
import com.example.jvmori.discovermovies.ui.adapters.GenreAdapter
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
class DiscoverFragment : Fragment(){

    override fun onAttach(context: Context) {
        (context.applicationContext as MoviesApplication).movieComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    override fun showProgressBar() {
//        progress_circular.visibility = View.VISIBLE
//    }
//
//    override fun hideProgressBar() {
//        progress_circular.visibility = View.GONE
//    }

}
