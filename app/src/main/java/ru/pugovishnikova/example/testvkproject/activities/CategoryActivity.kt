package ru.pugovishnikova.example.testvkproject.activities

import android.os.Bundle
import android.widget.ArrayAdapter
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
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.providers.CategoryProvider
import ru.pugovishnikova.example.testvkproject.utilites.State
import ru.pugovishnikova.example.testvkproject.viewmodels.CategoryViewModel

class CategoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<CategoryViewModel>()
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            this.finish()
        }

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
                showAttributes(false)
                showRV(false)
                showLoader(true)
            }

            is State.Fail -> {
                showLoader(false)
                showRV(false)
                showAttributes(true)
                Toast.makeText(
                    this,
                    state.exception.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is State.Success -> {
                showLoader(false)
                showAttributes(true)
                showRV(true)
                val products = binding.categoryRv
                products.adapter = ProductAdapter(state.data)
                products.layoutManager = LinearLayoutManager(this)
            }
        }
    }

    private fun showRV(isShow: Boolean) {
        binding.categoryRv.isVisible = isShow
    }

    private fun showLoader(isShow: Boolean) {
        binding.searchProgressbar.isVisible = isShow
    }

    private fun showAttributes(isShow: Boolean){
        binding.back.isVisible = isShow
        binding.category.isVisible = isShow
        binding.spinner.isVisible = isShow
        binding.search.isVisible = isShow
    }

}