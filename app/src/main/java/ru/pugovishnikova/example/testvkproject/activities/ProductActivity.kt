package ru.pugovishnikova.example.testvkproject.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.pugovishnikova.example.testvkproject.R
import ru.pugovishnikova.example.testvkproject.databinding.ActivityItemProductBinding
import ru.pugovishnikova.example.testvkproject.databinding.ActivityProductsBinding
import ru.pugovishnikova.example.testvkproject.fragments.ProductFragment

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.containerView.id, ProductFragment.newInstance())
                .commit()
        }
    }
}