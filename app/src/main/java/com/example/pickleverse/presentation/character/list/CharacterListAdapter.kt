package com.example.pickleverse.presentation.character.list

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pickleverse.databinding.ItemCharacterBinding
import com.example.pickleverse.domain.model.Character
import com.example.pickleverse.presentation.utils.GlideImageLoader

class CharacterListAdapter: RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    private var characterList: List<Character> = emptyList()
    private var highlightedLetterList: List<Char> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characterList.size

    fun setCharacterList(newCharacterList: List<Character>) {
        characterList = newCharacterList
    }

    fun setLetterList(newLetterList: List<Char>) {
        highlightedLetterList = newLetterList
    }

    fun getCharacterList(): List<Character> = characterList

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        private val imageLoader: GlideImageLoader = GlideImageLoader()

        fun bind(item: Character) {
            binding.apply {
                item.image?.let { imageLoader.loadSimpleImage(it, ivImage, true) }
                tvName.text = highlightSearchedLetters(item.name.orEmpty())
                tvStatus.text = item.status
            }
        }
    }

    private fun highlightSearchedLetters(name: String): SpannableString {
        val spannableName = SpannableString(name)
        for (letter in highlightedLetterList) {
            val letterIndex = name.indexOf(letter, ignoreCase = true)
            if (letterIndex != -1) {
                letterIndex.let {
                    spannableName.setSpan(ForegroundColorSpan(Color.WHITE),
                        it, letterIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        return spannableName
    }
}