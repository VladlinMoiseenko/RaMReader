package ru.vladlin.ram_reader.ui.fragmentPaging

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.vladlin.ram_reader.databinding.FragmentCharactersListBinding
import ru.vladlin.ram_reader.lifecycle.ViewModelFactory
import ru.vladlin.ram_reader.lifecycle.injectViewModel
import ru.vladlin.ram_reader.ui.MainActivity
import javax.inject.Inject

class FragmentPaging : Fragment(), PagingEpoxyModel.Listener {

    private lateinit var viewModel: ViewModelPaging
    private lateinit var binding: FragmentCharactersListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val fragmentListComponent by lazy {
        return@lazy (requireActivity() as MainActivity).feedComponent
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListComponent.inject(this)
        viewModel = injectViewModel(viewModelFactory)

        lifecycleScope.launch {
            viewModel.loadData(null, null, null)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater)

        val epoxyController = PagingController(this)

        viewModel.dataFeed.observe(viewLifecycleOwner, {
            epoxyController.setIdSavedCharacters(it.second)
            lifecycleScope.launch {
                epoxyController.submitData(it.first)
            }
        })

        binding.characterListRecycler.setController(epoxyController)

        return binding.root
    }

    override fun onCharacterClicked(id: Long) {
        viewModel.saveCharacter(id)
    }

}