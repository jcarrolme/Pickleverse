package com.example.pickleverse.presentation.utils

import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickleverse.R
import com.example.pickleverse.presentation.BaseActivity
import com.example.pickleverse.presentation.character.list.CharacterListAdapter
import com.example.pickleverse.presentation.character.list.GridSpacingItemDecoration

fun BaseActivity.configRecyclerViewFallDown(recyclerView: RecyclerView) {
    val layoutManager = GridLayoutManager(this, 2)
    recyclerView.layoutManager = layoutManager

    // Animation
    val animation: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(
            applicationContext,
            R.anim.recyclerview_animation_fall_down
        )
    recyclerView.layoutAnimation = animation
    val spacing = resources.getDimensionPixelSize(R.dimen.dimen_16dp)
    recyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
}