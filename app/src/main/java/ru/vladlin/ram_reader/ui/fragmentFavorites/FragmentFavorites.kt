package ru.vladlin.ram_reader.ui.fragmentFavorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.modo.forward
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import ru.vladlin.ram_reader.App.Companion.modo
import ru.vladlin.ram_reader.databinding.FragmentListBinding
import ru.vladlin.ram_reader.eventbus.UpdateList
import ru.vladlin.ram_reader.lifecycle.ViewModelFactory
import ru.vladlin.ram_reader.lifecycle.injectViewModel
import ru.vladlin.ram_reader.ui.MainActivity
import ru.vladlin.ram_reader.ui.Screens
import ru.vladlin.ram_reader.ui.epoxy.CardModel
import ru.vladlin.ram_reader.ui.epoxy.EpoxyController
import javax.inject.Inject

class FragmentFavorites : Fragment(), CardModel.Listener {

    private lateinit var viewModel: ViewModelFavorites
    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val fragmentListComponent by lazy {
        return@lazy (requireActivity() as MainActivity).feedComponent
    }

    val epoxyController: EpoxyController = EpoxyController(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadData()
        }

        binding.epoxyRecyclerView.setController(epoxyController)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)
        viewModel.loadData()

        viewModel.dataFeed.observe(viewLifecycleOwner, {
            epoxyController.setData(it.first, it.second)
        })

        viewModel.isRefreshed.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.errorSnackbar.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                .show()
        })
    }

    override fun savedClick(id: Long) {
        viewModel.saveCharacter(id)
    }

    override fun readMoreClick(id: Long) {
        modo.forward(Screens.Detail(id))
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun eventBusUpdatePost(event: UpdateList?) {
        event?.targetMode.let {
            if (it == 1) {
                viewModel.loadData()
            }
        }
    }
}