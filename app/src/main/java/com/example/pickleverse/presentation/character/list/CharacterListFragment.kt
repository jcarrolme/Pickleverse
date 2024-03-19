package com.example.pickleverse.presentation.character.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pickleverse.R
import com.example.pickleverse.databinding.FragmentCharacterListBinding
import com.example.pickleverse.domain.model.Character

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        val view = binding.root
        initUi()
        return view
    }

    private fun initUi() {
        initRecycler()
    }

    private fun initRecycler() {
        val list = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez"
            ),
            Character(
                id = 2,
                name = "Otra persona"
            ),
            Character(
                id = 3,
                name = "Abadango Cluster Princess"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            ),
            Character(
                id = 3,
                name = "Morty Rick"
            )
        )
        val spacing = resources.getDimensionPixelSize(R.dimen.dimen_16dp)
        val adapter = CharacterListAdapter(list)
        binding.rvCharacterList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharacterList.adapter = adapter
        binding.rvCharacterList.addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}