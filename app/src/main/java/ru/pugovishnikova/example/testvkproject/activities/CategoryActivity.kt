package ru.pugovishnikova.example.testvkproject.activities

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pugovishnikova.example.testvkproject.adapters.ProductAdapter
import ru.pugovishnikova.example.testvkproject.databinding.ActivityCategoryBinding
import ru.pugovishnikova.example.testvkproject.databinding.ActivitySearchBinding
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.providers.CategoryProvider
import ru.pugovishnikova.example.testvkproject.utilites.State
import ru.pugovishnikova.example.testvkproject.viewmodels.CategoryViewModel
import ru.pugovishnikova.example.testvkproject.viewmodels.SearchViewModel

class CategoryActivity: AppCompatActivity() {
    private val viewModel by viewModels<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.requireState().onEach(::handleState)
            .launchIn(this.lifecycleScope)

        val data = CategoryProvider.categories
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter
        binding.search.setOnClickListener {
            viewModel.getData(binding.spinner.selectedItem.toString())
        }

    }

    private fun handleState(state: State<List<Product>>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                binding.productList.isVisible = false
//                showLoader(true)
            }

            is State.Fail -> {
//                showLoader(false)
                Toast.makeText(
                    this,
                    state.exception.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is State.Success -> {
//                showLoader(false)
//                nothingFounded.isVisible = state.data.isEmpty()
                binding.productList.isVisible = state.data.isNotEmpty()
                val products = binding.productList
                products.adapter = ProductAdapter(state.data)
                products.layoutManager = LinearLayoutManager(this)
            }
        }
    }


}