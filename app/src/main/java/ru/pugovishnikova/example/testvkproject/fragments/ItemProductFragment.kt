package ru.pugovishnikova.example.testvkproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pugovishnikova.example.testvkproject.activities.ItemProductActivity
import ru.pugovishnikova.example.testvkproject.databinding.FragmentItemProductBinding
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.utilites.State
import ru.pugovishnikova.example.testvkproject.viewmodels.ItemProductViewModel

class ItemProductFragment: Fragment() {
    private val viewModel by viewModels<ItemProductViewModel>()
    private var _binding: FragmentItemProductBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requireState().onEach(::handleState).launchIn(viewLifecycleOwner.lifecycleScope)
        if (savedInstanceState == null) {
            viewModel.getData(arguments?.getInt(ItemProductActivity.ID)!!)
        }
    }


    private fun handleState(state: State<Product>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                showLoader(true)
                showFields(false)
                showButton(false)
            }

            is State.Fail -> {
                showFields(false)
                showLoader(false)
                showButton(true)
                binding.retry.setOnClickListener {
                    viewModel.getData(arguments?.getInt(ItemProductActivity.ID)!!)
                }
                Toast.makeText(
                    requireContext(),
                    "Unexpected error ${state.exception.message}, try again later",
                    Toast.LENGTH_LONG
                ).show()
            }

            is State.Success -> {
                showFields(true)
                showLoader(false)
                showButton(false)
                binding.title.text = state.data.title
                binding.description.text = state.data.description
                binding.price.text = state.data.price.toString()
                binding.brand.text = state.data.brand
                binding.stock.text = state.data.stock.toString()
                binding.rating.text = state.data.rating.toString()
                binding.category.text = state.data.category
                binding.image.apply {
                    Glide.with(this)
                        .load(state.data.thumbnail)
                        .into(this)
                }
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        binding.productProgressbar.isVisible = isShow
    }

    private fun showFields(isShow: Boolean) {
        binding.title.isVisible = isShow
        binding.description.isVisible = isShow
        binding.price.isVisible = isShow
        binding.brand.isVisible = isShow
        binding.stock.isVisible = isShow
        binding.rating.isVisible = isShow
        binding.category.isVisible = isShow
        binding.image.isVisible = isShow
    }

    private fun showButton(isShow: Boolean) {
        binding.retry.isVisible = isShow
    }


    companion object{
        fun getNewInstance(args: Bundle?): ItemProductFragment{
            val itemProductFragment = ItemProductFragment()
            itemProductFragment.arguments = args
            return itemProductFragment
        }
    }
}