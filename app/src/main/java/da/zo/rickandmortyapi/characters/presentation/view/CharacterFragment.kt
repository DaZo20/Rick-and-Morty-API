package da.zo.rickandmortyapi.characters.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import da.zo.rickandmortyapi.R
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.presentation.viewmodel.CharactersViewModel
import da.zo.rickandmortyapi.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    @Inject
    lateinit var characterViewModel: CharactersViewModel
    private var characterFragmentBinding: FragmentCharacterBinding? = null
//    private var data: MutableList<Character> = mutableListOf()
//    private lateinit var adapter: CharacterAdapter
//
//    private lateinit var viewHolder: CharacterAdapter.CharactersViewHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCharacterBinding.inflate(inflater, container, false).also {
        characterFragmentBinding = it
        initViews()
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        characterFragmentBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterViewModel.characters.collect { ch ->
                    if (ch != null) {
                        loadCharacters(data = ch)
                    }
                }
            }
        }
    }

    private fun loadCharacters(data: Characters) {
        (characterFragmentBinding?.rvCharactersData?.adapter as? CharacterAdapter)?.updateData(
            newData = data.results
        )
    }

    private fun initViews() {
        with(characterFragmentBinding?.rvCharactersData) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.adapter = CharacterAdapter()
            this?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager: LinearLayoutManager? =
                        recyclerView.layoutManager as LinearLayoutManager?
                    if (layoutManager?.findLastCompletelyVisibleItemPosition() == recyclerView.adapter?.itemCount?.minus(
                            1
                        )
                    ) {
                        characterViewModel.onEndOfScrollReached()
                    }
                }
            })
        }
    }

    companion object {
        fun newInstance(): CharacterFragment = CharacterFragment().apply { arguments = Bundle() }
    }

//    override fun onQueryTextSubmit(query: String?): Boolean {
//
//        if (query != null) {
//            CharacterAdapter().filteredData(query)
//        }
//        return false
//    }
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        if (newText != null) {
//            CharacterAdapter().filteredData(newText)
//        }
//        return false
//    }
}