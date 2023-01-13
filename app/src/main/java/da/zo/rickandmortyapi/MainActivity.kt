package da.zo.rickandmortyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import da.zo.rickandmortyapi.characters.presentation.view.CharacterFragment
import da.zo.rickandmortyapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemSelectedListener {
    lateinit var mainBinding: ActivityMainBinding
    private val charactersFragment: Fragment by lazy { CharacterFragment.newInstance()}
   // private val episodesFragment: Fragment by lazy { EpisodesFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initViews(savedState = savedInstanceState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view, charactersFragment).commit()
    }
    private fun replaceWithEpisodesFragment() {
        //supportFragmentManager.beginTransaction()
          //  .replace(R.id.fragment_view, episodesFragment).commit()
    }
}