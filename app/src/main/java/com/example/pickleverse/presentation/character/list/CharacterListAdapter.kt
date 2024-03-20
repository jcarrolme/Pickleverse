package com.example.pickleverse.presentation.character.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickleverse.databinding.ItemCharacterBinding
import com.example.pickleverse.domain.model.Character
import com.example.pickleverse.presentation.utils.GlideImageLoader

class CharacterListAdapter(
    private val characterList: List<Character>
) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characterList.size

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        private val imageLoader: GlideImageLoader = GlideImageLoader()

        fun bind(item: Character) {
            binding.apply {
                item.image?.let { imageLoader.loadSimpleImage(it, ivImage, true) }
                tvName.text = item.name
                tvStatus.text = item.status
            }
        }
    }
}