package ru.pugovishnikova.example.testvkproject.activities

import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.pugovishnikova.example.testvkproject.adapters.ProductAdapter
import ru.pugovishnikova.example.testvkproject.databinding.ActivitySearchBinding
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.utilites.State
import ru.pugovishnikova.example.testvkproject.viewmodels.SearchViewModel

class SearchActivity: AppCompatActivity() {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var productList: RecyclerView
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            this.finish()
        }


        viewModel.requireState().onEach(::handleState)
            .launchIn(this.lifecycleScope)
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val searchInput = binding.searchInput.query.toString()
                viewModel.getData(searchInput)
                return false
            }
        })
    }

    private fun handleState(state: State<List<Product>>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                showRV(false)
                showLoader(true)
                showBackButton(true)
                showNF(false)
            }

            is State.Fail -> {
                showBackButton(true)
                showLoader(false)
                showRV(false)
                showBackButton(false)
                showNF(false)
                Toast.makeText(
                    this,
                    state.exception.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is State.Success -> {
                showLoader(false)
                showNF(state.data.isEmpty())
                showRV(state.data.isNotEmpty())
                showNF(!state.data.isNotEmpty())
                showBackButton(true)
                binding.searchRv.adapter = ProductAdapter(state.data)
                binding.searchRv.layoutManager = LinearLayoutManager(this)
            }
        }
    }

    private fun showNF(isShow: Boolean){
        binding.searchNothingFoundedTv.isVisible = isShow
    }
    private fun showRV(isShow: Boolean) {
        binding.searchRv.isVisible = isShow
    }
    private fun showBackButton(isShow: Boolean) {
        binding.back.isVisible = isShow
    }
    private fun showLoader(isShow: Boolean) {
        binding.searchProgressbar.isVisible = isShow
    }
}