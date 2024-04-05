package com.ayein.bitebuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayein.bitebuddy.adapter.RecentBuyAdapter
import com.ayein.bitebuddy.databinding.ActivityRecentOrderItemsBinding
import com.ayein.bitebuddy.model.OrderDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class recentOrderItems : AppCompatActivity() {

    private val binding: ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonBack.setOnClickListener {
            finish()
        }
        val recentOrderItems =
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
           if (orderDetails.isNotEmpty()){
               val recentOrderItem = orderDetails[0]
               allFoodNames = recentOrderItem.foodNames as ArrayList<String>
               allFoodImages = recentOrderItem.foodImages as ArrayList<String>
               allFoodPrices = recentOrderItem.foodPrices as ArrayList<String>
               allFoodQuantities = recentOrderItem.foodQuantities as ArrayList<Int>

           }

        }
        setAdapter()
    }

    private fun setAdapter() {
        val rv = binding.recyclerViewRecentBuy
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this, allFoodNames, allFoodImages, allFoodPrices, allFoodQuantities)
        rv.adapter = adapter
    }
}