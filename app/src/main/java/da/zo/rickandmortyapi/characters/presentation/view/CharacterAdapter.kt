package da.zo.rickandmortyapi.characters.presentation.view

//
// Created by DaZo20 on 13/01/2023.
//
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import da.zo.rickandmortyapi.R
import da.zo.rickandmortyapi.characters.domain.model.Character

class CharacterAdapter(
     var data: MutableList<Character> = mutableListOf()
//     var filteredData: MutableList<Character> = mutableListOf()
) : RecyclerView.Adapter<CharacterAdapter.CharactersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.character_adapter_row ,parent,false)
        return CharactersViewHolder(itemView = rootView)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<Character>){
        data.addAll(newData.toMutableList())
        notifyDataSetChanged()
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgProfile: ImageView by lazy { itemView.findViewById(R.id.img_profile) }
        private val tvName: TextView by lazy { itemView.findViewById(R.id.tv_name) }
        private val tvExtra: TextView by lazy { itemView.findViewById(R.id.tv_extras) }

        fun bindData(character: Character) {
            imgProfile.load(character.image)
            tvName.text = character.name
            tvExtra.text = "Specie: ${character.species}\nGender: ${character.gender}\nStatus: ${character.status}"
        }

    }

}