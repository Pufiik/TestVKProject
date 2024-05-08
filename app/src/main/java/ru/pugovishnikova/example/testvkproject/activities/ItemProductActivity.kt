package ru.pugovishnikova.example.testvkproject.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.pugovishnikova.example.testvkproject.databinding.ActivityItemProductBinding
import ru.pugovishnikova.example.testvkproject.fragments.ItemProductFragment


class ItemProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemProductBinding
    private var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getIntExtra(ID, 0)
        val bundle = Bundle()
        bundle.putInt(ID, id)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, ItemProductFragment.getNewInstance(args = bundle))
                .commit()
        }
    }


    companion object {
        const val ID = "id"
    }
}