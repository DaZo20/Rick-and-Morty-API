package da.zo.rickandmortyapi.characters.presentation.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import da.zo.rickandmortyapi.characters.data.utils.getTextChipChecked
import da.zo.rickandmortyapi.characters.data.utils.getTextRadioButtonChecked
import da.zo.rickandmortyapi.characters.data.utils.setChipChecked
import da.zo.rickandmortyapi.characters.data.utils.setRadioButtonChecked
import da.zo.rickandmortyapi.characters.presentation.viewmodel.CharactersViewModel
import da.zo.rickandmortyapi.databinding.FragmentCharacterBinding
import da.zo.rickandmortyapi.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterCharactersFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var characterViewModel: CharactersViewModel
    private var filterBinding: FragmentFilterBinding? = null
    private val charactersFragment: Fragment by lazy { CharacterFragment.newInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFilterBinding.inflate(inflater, container, false).also {
        filterBinding = it

        filterBinding?.apply {
            characterViewModel.filterValue.observe(viewLifecycleOwner) { item ->
                chipgroupStatus.setChipChecked(item[0])
                radiogrouopGender.setRadioButtonChecked(item[1])
            }

            applyButton.setOnClickListener {

                val bundle: Bundle = Bundle()
                when {
                    chipgroupStatus.getTextChipChecked().isNotEmpty() && radiogrouopGender.getTextRadioButtonChecked().isNotEmpty() -> {
                        bundle.putString("status_and", chipgroupStatus.getTextChipChecked())
                        bundle.putString("gender_and", radiogrouopGender.getTextRadioButtonChecked())
                    }
                    chipgroupStatus.getTextChipChecked().isNotEmpty() -> {
                        bundle.putString("status", chipgroupStatus.getTextChipChecked())
                    }

                    radiogrouopGender.getTextRadioButtonChecked().isNotEmpty() -> {
                        bundle.putString("gender", radiogrouopGender.getTextRadioButtonChecked())
                    }
                }
                characterViewModel.filterValue.value =
                    arrayOf(
                        chipgroupStatus.checkedChipId,
                        radiogrouopGender.checkedRadioButtonId
                    )

                parentFragmentManager.setFragmentResult("filter_key", bundle)

                dismiss()
            }
        }

    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        filterBinding = null
    }

    companion object {
        fun newInstance(): FilterCharactersFragment =
            FilterCharactersFragment().apply { arguments = Bundle() }
    }


}