package ru.vladlin.ram_reader.ui.fragmentDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.terrakok.modo.exit
import com.google.android.material.snackbar.Snackbar
import ru.vladlin.ram_reader.App
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.databinding.FragmentDetailBinding
import ru.vladlin.ram_reader.glide.getImageRequestOption
import ru.vladlin.ram_reader.lifecycle.ViewModelFactory
import ru.vladlin.ram_reader.lifecycle.injectViewModel
import ru.vladlin.ram_reader.ui.MainActivity
import javax.inject.Inject

class FragmentDetail : Fragment(), View.OnClickListener {
    private val modo = App.modo

    companion object {
        const val ID_KEY = "ik"

        fun create(id: Long): FragmentDetail {
            val args = bundleOf(ID_KEY to id)
            return FragmentDetail().apply {
                arguments = args
            }
        }
    }

    private lateinit var viewModel: ViewModelDetail
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var appCompactActivity: AppCompatActivity

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val fragmentListComponent by lazy {
        return@lazy (requireActivity() as MainActivity).feedComponent
    }

    val listImageController = ListImageController()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentListComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)

/*        val toolbar = binding.root.findViewById<Toolbar>(R.id.toolbar)
        appCompactActivity.setSupportActionBar(toolbar)
        appCompactActivity.supportActionBar?.title = ""
        appCompactActivity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        appCompactActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        toolbar.setTitle(resources.getString(R.string.character))*/

        binding.listImages.layoutManager = LinearLayoutManager(requireContext())
        binding.listImages.setController(listImageController)

        binding.repeatBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        viewModel.id = getIdKey()

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty())
            {
                binding.post.visibility = View.VISIBLE
                binding.errLayout.visibility = View.GONE
            }
            else
            {
                binding.post.visibility = View.GONE
                binding.errLayout.visibility = View.VISIBLE

                binding.error.setText(it)
            }
        })

        viewModel.data.observe(viewLifecycleOwner, Observer {
            val post = it.first
            val isSaved = it.second

            if (isSaved)
            {
                binding.saveBtn.setText(resources.getString(R.string.remove_save))
                binding.saveBtn.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_cloud_done,
                    0,
                    0,
                    0
                )
            }
            else
            {
                binding.saveBtn.setText(resources.getString(R.string.save))
                binding.saveBtn.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_cloud_download,
                    0,
                    0,
                    0
                )
            }

            if (post != null)
            {
                binding.post.visibility = View.VISIBLE
                binding.title.setText(post.name)
                binding.body.setText(post.gender)

                if (post.gender.isNullOrEmpty())
                    binding.body.visibility = View.GONE
                else
                    binding.body.visibility = View.VISIBLE

                val mainImage = binding.root.findViewById<ImageView>(R.id.main_image)

                Glide
                    .with(this)
                    .load(post.image)
                    .apply(getImageRequestOption())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .dontTransform()
                    .into(mainImage);


                binding.saveBtn.setOnClickListener(this)
            }
            else
            {
                binding.post.visibility = View.GONE
            }
        })

        viewModel.errorSnackbar.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                .show()
        })
    }

    fun getIdKey(): Long = arguments?.getLong(ID_KEY) ?: 0

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            android.R.id.home -> {
                //router.exit()
                modo.exit()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?)
    {
        when (view?.id)
        {
            R.id.save_btn -> {
                viewModel.saveCharacter()
            }
            R.id.repeat_btn -> {
                viewModel.id = getIdKey()
            }
        }
    }

}