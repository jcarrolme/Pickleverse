package com.example.pickleverse.presentation.character.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pickleverse.R
import com.example.pickleverse.databinding.FragmentCharacterListBinding
import com.example.pickleverse.domain.model.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initConfig()
        initRecycler(listOf())
        initUiState()
    }

    private fun initConfig() {
        viewModel.getCharacters()
    }

    private fun initUi() {

    }

    private fun initRecycler(list: List<Character>) {
        val spacing = resources.getDimensionPixelSize(R.dimen.dimen_16dp)
        val adapter = CharacterListAdapter(list)
        binding.rvCharacterList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharacterList.adapter = adapter
        binding.rvCharacterList.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        ListUiState.Loading ->
                            onLoadingState()
                        ListUiState.HideLoading ->
                            onHideLoadingState()
                        is ListUiState.Error ->
                            onErrorState()
                        is ListUiState.Success ->
                            onSuccessState()
                    }
                }
            }
        }
    }

    private fun onLoadingState() {
        // Nothing
    }

    private fun onHideLoadingState() {
        // Nothing
    }

    private fun onSuccessState() {
        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
    }

    private fun onErrorState() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}