package com.example.pickleverse.presentation.character.detail

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pickleverse.R
import com.example.pickleverse.databinding.FragmentCharacterDetailBinding
import com.example.pickleverse.presentation.utils.GlideImageLoader
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CharacterDetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels()

    private val imageLoader: GlideImageLoader = GlideImageLoader()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCharacterDetailBinding.inflate(LayoutInflater.from(context))
        return super.onCreateDialog(savedInstanceState).apply {
            setContentView(binding.root)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    companion object {
        const val TAG = "CharacterDetailFragment"
        private const val ID = "id"

        fun newInstance(
            id: Int
        ): CharacterDetailFragment {
            return CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
        }
    }
}