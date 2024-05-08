package ru.pugovishnikova.example.testvkproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.pugovishnikova.example.testvkproject.databinding.FragmentProductBinding
import ru.pugovishnikova.example.testvkproject.viewmodels.ProductViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pugovishnikova.example.testvkproject.adapters.ProductAdapter
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.utilites.State

class ProductFragment: Fragment() {
    private val viewModel by viewModels<ProductViewModel>()
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requireState().onEach(::handleState).launchIn(viewLifecycleOwner.lifecycleScope)
        if (savedInstanceState == null){
            viewModel.getData()
        }
    }


    private fun handleState(state: State<List<Product>>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                showLoader(true)
                showButton(false)
                showRV(false)
                showMore(false)
            }

            is State.Fail -> {
                showLoader(false)
                showRV(false)
                showButton(true)
                showMore(false)
                binding.retry.setOnClickListener {
                    viewModel.getData()
                }
                Toast.makeText(
                    requireContext(),
                    "Unexpected error ${state.exception.message}, try again later",
                    Toast.LENGTH_LONG
                ).show()
            }

            is State.Success -> {
                showLoader(false)
                showRV(true)
                showButton(false)
                showMore(true)
                val adapter = ProductAdapter(state.data)
                val rv = binding.productsRv
                rv.adapter = adapter
                rv.layoutManager = LinearLayoutManager(requireContext())
                binding.btnNext.setOnClickListener {
                    viewModel.getData()
                }
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        binding.productProgressbar.isVisible = isShow
    }

    private fun showRV(isShow: Boolean) {
        binding.productsRv.isVisible = isShow
    }

    private fun showMore(isShow: Boolean){
        binding.btnNext.isVisible = isShow
    }
    private fun showButton(isShow: Boolean) {
        binding.retry.isVisible = isShow
    }

    companion object{
        fun newInstance() = ProductFragment()
    }
}