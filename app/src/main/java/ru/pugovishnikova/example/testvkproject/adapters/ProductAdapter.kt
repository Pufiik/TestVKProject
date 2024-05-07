package ru.pugovishnikova.example.testvkproject.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.pugovishnikova.example.testvkproject.activities.ItemProductActivity
import ru.pugovishnikova.example.testvkproject.databinding.ProductItemBinding
import ru.pugovishnikova.example.testvkproject.models.Product

class ProductAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.title.text = product.title
            binding.description.text = product.description
            binding.price.text = product.price.toString()
            binding.image.apply {
                Glide.with(this)
                    .load(product.thumbnail)
                    .into(this)
            }
            binding.more.setOnClickListener {
                val newIntent = Intent(binding.root.context, ItemProductActivity::class.java).apply {
                    putExtra(ItemProductActivity.ID, product.id)
                }
                binding.root.context.startActivity(newIntent)
            }
        }
    }
}