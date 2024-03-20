package com.example.pickleverse.presentation.character.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pickleverse.R
import com.example.pickleverse.databinding.FragmentCharacterListBinding
import com.example.pickleverse.domain.model.Character
import com.example.pickleverse.presentation.BaseActivity
import com.example.pickleverse.presentation.utils.configRecyclerViewFallDown
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
        initUiState()
        initUi()
    }

    private fun initConfig() {
        viewModel.getCharacters()
    }

    private fun initUi() {
        initRecycler()
        initSearchView()
    }

    private fun initRecycler() {
        (requireActivity() as BaseActivity).configRecyclerViewFallDown(binding.rvCharacterList)
    }

    private fun initAdapter(list: List<Character>) {
        val adapter = CharacterListAdapter(list)
        binding.rvCharacterList.adapter = adapter
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchCharacters(it) }
                return true
            }
        })
    }

    private fun searchCharacters(query: String) {

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
                            onSuccessState(uiState.list)
                    }
                }
            }
        }
    }

    private fun onLoadingState() {
        Toast.makeText(context, "START", Toast.LENGTH_SHORT).show()
    }

    private fun onHideLoadingState() {
        Toast.makeText(context, "HIDE", Toast.LENGTH_SHORT).show()
    }

    private fun onSuccessState(list: List<Character>) {
        initAdapter(list)
        binding.searchView.isVisible = true
        // TODO: HideLoading
    }

    private fun onErrorState() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}