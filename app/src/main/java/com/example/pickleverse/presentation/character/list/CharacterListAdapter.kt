package com.example.pickleverse.presentation.character.list

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.pickleverse.R
import com.example.pickleverse.databinding.ItemCharacterBinding
import com.example.pickleverse.domain.model.CharacterDetail
import com.example.pickleverse.presentation.utils.GlideImageLoader
import java.util.regex.Pattern

class CharacterListAdapter(
    private val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    private var characterList: List<CharacterDetail> = emptyList()
    private var highlightedLetters: String = ""
    private val imageLoader: GlideImageLoader = GlideImageLoader()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characterList.size

    fun setCharacterList(newCharacterList: List<CharacterDetail>) {
        characterList = newCharacterList
    }

    fun setLetterList(newLetter: String) {
        highlightedLetters = newLetter
    }

    fun getCharacterList(): List<CharacterDetail> = characterList

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterDetail) {
            binding.apply {
                item.image?.let { imageLoader.loadSimpleImage(it, ivImage, true) }
                tvName.text = highlightSearchTerm(item.name, highlightedLetters)
                itemView.setOnClickListener {
                    item.id?.let{ onItemClick(it) }
                }
            }
        }
    }

    fun highlightSearchTerm(name: String?, searchTerm: String): SpannableString {
        val spannableName = SpannableString(name)
        val pattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(name)

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            spannableName.setSpan(
                ForegroundColorSpan(Color.WHITE),
                start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableName
    }
}