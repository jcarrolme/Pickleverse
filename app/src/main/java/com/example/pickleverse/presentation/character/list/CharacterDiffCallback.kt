package com.example.pickleverse.presentation.character.list

import androidx.recyclerview.widget.DiffUtil
import com.example.pickleverse.domain.model.CharacterDetail
class CharacterDiffCallback(
    private val oldList: List<CharacterDetail>,
    private val newList: List<CharacterDetail>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        false
}