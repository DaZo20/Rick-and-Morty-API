package da.zo.rickandmortyapi


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import da.zo.rickandmortyapi.characters.presentation.view.CharacterFragment
import da.zo.rickandmortyapi.characters.presentation.view.FilterCharactersFragment
import da.zo.rickandmortyapi.characters.presentation.viewmodel.CharactersViewModel
import da.zo.rickandmortyapi.databinding.ActivityMainBinding
import da.zo.rickandmortyapi.databinding.FragmentCharacterBinding
import da.zo.rickandmortyapi.episodes.presentation.view.EpisodesFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemSelectedListener {
    lateinit var mainBinding: ActivityMainBinding
    private val characterViewModel: CharactersViewModel by viewModels()
    private val charactersFragment: Fragment by lazy { CharacterFragment.newInstance() }
    private val episodesFragment: Fragment by lazy { EpisodesFragment.newInstance() }
    private val filterFragment: Fragment by lazy { FilterCharactersFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initViews(savedState = savedInstanceState)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_characters -> {
                replaceWithCharactersFragment()
                return true
            }
            R.id.navigation_episodes -> {
                replaceWithEpisodesFragment()
                return true
            }
        }
        return false
    }

    private fun initViews(savedState: Bundle?) {
        with(mainBinding.bottomNavigationView) {
            setOnItemSelectedListener(this@MainActivity)
            selectedItemId = savedState?.getInt("opened_fragment") ?: R.id.navigation_characters
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("opened_fragment", mainBinding.bottomNavigationView.selectedItemId)
    }

    private fun replaceWithCharactersFragment() {
        supportFragmentManager.beginTransaction().addToBackStack("characters")
            .replace(R.id.fragment_view, charactersFragment).commit()
    }

    private fun replaceWithEpisodesFragment() {
        supportFragmentManager.beginTransaction().addToBackStack("episodes")
          .replace(R.id.fragment_view, episodesFragment).commit()
    }

}


