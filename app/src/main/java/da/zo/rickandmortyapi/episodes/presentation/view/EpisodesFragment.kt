package da.zo.rickandmortyapi.episodes.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import da.zo.rickandmortyapi.R
import da.zo.rickandmortyapi.databinding.FragmentEpisodesBinding
import da.zo.rickandmortyapi.episodes.domain.model.Episode
import da.zo.rickandmortyapi.episodes.domain.model.Episodes
import da.zo.rickandmortyapi.episodes.presentation.viewmodel.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    @Inject
    lateinit var episodeViewModel: EpisodesViewModel
    private var episodesBinding: FragmentEpisodesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEpisodesBinding.inflate(inflater, container, false).also {
        episodesBinding = it
        initViews()
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        episodesBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                episodeViewModel.episodes.collect { ep ->
                    if (ep != null) {
                        loadEpisodes(data = ep)
                    }
                }
            }
        }
    }

    private fun loadEpisodes(data: Episodes) {
        (episodesBinding?.rvEpisodesData?.adapter as? EpisodeAdapter)?.updateData(newData = data.results)
    }

    private fun initViews() {
        with(episodesBinding?.rvEpisodesData) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.adapter = EpisodeAdapter()
            this?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager: LinearLayoutManager? =
                        recyclerView.layoutManager as LinearLayoutManager?
                    if (layoutManager?.findLastCompletelyVisibleItemPosition() == recyclerView.adapter?.itemCount?.minus(1)) {
                        episodeViewModel.onEndOfScrollReached()
                    }
                }
            })
        }
    }

    companion object {
        fun newInstance(): EpisodesFragment = EpisodesFragment().apply { arguments = Bundle() }
    }
}