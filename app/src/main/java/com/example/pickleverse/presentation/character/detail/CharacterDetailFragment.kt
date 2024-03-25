package com.example.pickleverse.presentation.character.detail

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.pickleverse.R
import com.example.pickleverse.databinding.FragmentCharacterDetailBinding
import com.example.pickleverse.domain.model.CharacterDetail
import com.example.pickleverse.presentation.utils.GlideImageLoader
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class CharacterDetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by activityViewModels()

    private val imageLoader: GlideImageLoader = GlideImageLoader()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        initConfig()

        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        params.height = getScreenHeight()
        binding.root.layoutParams = params

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }
    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }


    private fun initConfig() {
        arguments?.getInt(ARG_ID)?.let { id->
            viewModel.getCharacterDetail(id)
        }
        initUiState()
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when(uiState) {
                        DetailUiState.Loading ->
                            onLoadingState()
                        DetailUiState.HideLoading ->
                            onHideLoadingState()
                        is DetailUiState.Error ->
                            onErrorState()
                        is DetailUiState.Success ->
                            onSuccessState(uiState.item)
                    }
                }
            }
        }
    }

    private fun onLoadingState() {
        // Do nothing
    }

    private fun onHideLoadingState() {
        // Do nothing
    }

    private fun onErrorState() {
        // Do Nothing
    }

    private fun onSuccessState(item: CharacterDetail) {
        setUiValues(item)
    }

    private fun initViews() {
        configBottomSheetBehavior()
    }

    private fun setUiValues(item: CharacterDetail) {
        binding.apply {
            item.image?.let { image ->
                imageLoader.loadSimpleImage(
                    url = image,
                    placeToLoadImage = ivImage,
                    shouldCacheImage = true
                )
            }
            tvName.text = item.name
            tvGender.text = item.gender
            tvOrigin.text = item.origin?.name ?: ""
            tvSpecies.text = item.species
            tvStatus.text = item.status
            tvLocation.text = item.location?.name ?: ""
        }
    }

    private fun configBottomSheetBehavior() {
        val behavior = BottomSheetBehavior.from(binding.root.parent as View)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.apply {
                            root.setBackgroundResource(R.color.background_main)
                            ivImage.setBackgroundResource(0)
                        }
                    }
                    else -> {
                        binding.apply {
                            root.setBackgroundResource(R.drawable.rounded_top_corners)
                            ivImage.setBackgroundResource(R.drawable.rounded_top_corners)
                        }
                    }
                }
            }
            override fun onSlide(p0: View, p1: Float) {
                // Do Nothing
            }
        })
    }

    companion object {
        const val TAG = "CharacterDetailFragment"
        private const val ARG_ID = "id"

        fun newInstance(
            id: Int
        ): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
        }
    }
}