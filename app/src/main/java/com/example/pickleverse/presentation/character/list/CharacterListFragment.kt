package com.example.pickleverse.presentation.character.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import com.example.pickleverse.databinding.FragmentCharacterListBinding
import com.example.pickleverse.domain.model.CharacterDetail
import com.example.pickleverse.presentation.BaseActivity
import com.example.pickleverse.presentation.character.detail.CharacterDetailFragment
import com.example.pickleverse.presentation.utils.configRecyclerViewFallDown
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by activityViewModels()

    private lateinit var adapter: CharacterListAdapter

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
        initListeners()
        initAdapter()
        initRecycler()
        initSearchView()
    }

    private fun initListeners() {
        binding.ivSearchIcon.setOnClickListener {
            binding.searchView.isVisible = !binding.searchView.isVisible
        }
    }

    private fun initRecycler() {
        (requireActivity() as BaseActivity).configRecyclerViewFallDown(binding.rvCharacterList)
    }

    private fun initAdapter() {
        adapter = CharacterListAdapter(
            onItemClick = { id ->
                onCharacterClicked(id)
            }
        )
        binding.rvCharacterList.adapter = adapter
    }

    private fun updateAdapter(list: List<CharacterDetail>, highlightedLetterList: List<Char>) {
        val diffResult = DiffUtil.calculateDiff(CharacterDiffCallback(adapter.getCharacterList(), list))
        adapter.setLetterList(highlightedLetterList)
        adapter.setCharacterList(list)
        diffResult.dispatchUpdatesTo(adapter)
    }

    private fun onCharacterClicked(id: Int) {
        val fragment = CharacterDetailFragment.newInstance(id)
        fragment.show(requireActivity().supportFragmentManager, CharacterDetailFragment.TAG)
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    searchCharacters(query)
                }
                return true
            }
        })
    }

    private fun searchCharacters(query: String) {
        viewModel.searchCharactersByName(query)
        viewModel.updateHighlightedLetters(query.lowercase().toCharArray().toList())
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        ListUiState.InitialLoading ->
                            onInitialLoading()
                        ListUiState.Loading ->
                            onLoadingState()
                        ListUiState.HideLoading ->
                            onHideLoadingState()
                        is ListUiState.Error ->
                            onErrorState()
                        is ListUiState.Success ->
                            onSuccessState(uiState.list, uiState.highlightedLetterList)
                    }
                }
            }
        }
    }

    private fun onInitialLoading() {
        manageUiVisibility(
            searchBar = false,
            recyclerView = false,
            loading = true,
            emptyLayout = false
        )
    }

    private fun onLoadingState() {
        // Do nothing
    }

    private fun onHideLoadingState() {
        binding.ivLoadingImage.isVisible = false
    }

    private fun onSuccessState(list: List<CharacterDetail>, highlightedLetterList: List<Char>) {
        updateAdapter(list, highlightedLetterList)
        manageUiVisibility(
            searchBar = false,
            recyclerView = true,
            loading = false,
            emptyLayout = false
        )
    }

    private fun onErrorState() {
        manageUiVisibility(
            searchBar = true,
            recyclerView = false,
            loading = false,
            emptyLayout = true
        )
    }

    private fun manageUiVisibility(
        searchBar: Boolean,
        recyclerView: Boolean,
        loading: Boolean,
        emptyLayout: Boolean
    ) {
        binding.apply {
            searchView.isVisible = searchBar
            rvCharacterList.isVisible = recyclerView
            ivLoadingImage.isVisible = loading
            clEmptyList.isVisible = emptyLayout
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}