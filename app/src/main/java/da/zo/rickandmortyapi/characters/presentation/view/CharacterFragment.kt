package da.zo.rickandmortyapi.characters.presentation.view


import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import da.zo.rickandmortyapi.characters.domain.model.Characters
import da.zo.rickandmortyapi.characters.presentation.viewmodel.CharactersViewModel
import da.zo.rickandmortyapi.databinding.FragmentCharacterBinding
import da.zo.rickandmortyapi.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    @Inject
    lateinit var characterViewModel: CharactersViewModel
    private var characterFragmentBinding: FragmentCharacterBinding? = null
    private val filterFragment: Fragment by lazy { FilterCharactersFragment.newInstance() }
    private var filterBinding: FragmentFilterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCharacterBinding.inflate(inflater, container, false).also {
        characterFragmentBinding = it
        filterBinding?.root
        initViews()
        characterFragmentBinding?.filterButton?.setOnClickListener {
            (filterFragment as BottomSheetDialogFragment).show(parentFragmentManager,"filter")
        }

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
            getNameSearchView()
            getBundleOfFilterFragment()
            scrollListener()
        }
    }


    private fun scrollListener() {
        with(characterFragmentBinding?.rvCharactersData) {
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

    private fun getNameSearchView() {
        characterFragmentBinding?.svSearch?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val adapter = characterFragmentBinding?.rvCharactersData?.adapter as CharacterAdapter
                adapter.data.clear()
                characterViewModel.fetchCharactersByName(query.toString())
                characterFragmentBinding?.svSearch?.setQuery("",false)
                characterFragmentBinding?.svSearch?.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    private fun getBundleOfFilterFragment() {
        parentFragmentManager.setFragmentResultListener("filter_key",this, FragmentResultListener { requestKey, result ->
            val statusAnd = result.getString("status_and")
            val genderAnd = result.getString("gender_and")
            val status = result.getString("status")
            val gender = result.getString("gender")


            val adapter = characterFragmentBinding?.rvCharactersData?.adapter as CharacterAdapter
            adapter.data.clear()

            when {
                statusAnd != null && genderAnd != null -> {
                    characterViewModel.fetchCharactersByStatusAndGender(
                        statusAnd.toString(),
                        genderAnd.toString()
                    )
                }
                status != null -> {
                    characterViewModel.fetchCharactersByStatus(status.toString())
                }
                gender != null -> {
                    characterViewModel.fetchCharactersByGender(gender.toString())
                }
            }

        })


    }

    companion object {
        fun newInstance(): CharacterFragment = CharacterFragment().apply { arguments = Bundle() }
    }

}


