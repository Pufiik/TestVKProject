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
    private lateinit var search: SearchView
    private lateinit var searchInput: String
    private lateinit var nothingFounded: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = binding.searchRv
        nothingFounded = binding.searchNothingFoundedTv
        search = binding.searchInput

        viewModel.requireState().onEach(::handleState)
            .launchIn(this.lifecycleScope)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchInput = search.query.toString()
                viewModel.getData(searchInput)
                return false
            }
        })
    }

    private fun handleState(state: State<List<Product>>) {
        when (state) {
            is State.Idle -> Unit
            is State.Loading -> {
                productList.isVisible = false
                showLoader(true)
            }

            is State.Fail -> {
                showLoader(false)
                Toast.makeText(
                    this,
                    state.exception.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            is State.Success -> {
                showLoader(false)
                nothingFounded.isVisible = state.data.isEmpty()
                productList.isVisible = state.data.isNotEmpty()
                val articles = binding.searchRv
                articles.adapter = ProductAdapter(state.data)
                articles.layoutManager = LinearLayoutManager(this)
            }
        }
    }
    private fun showLoader(isShow: Boolean) {
        binding.searchProgressbar.isVisible = isShow
    }
}