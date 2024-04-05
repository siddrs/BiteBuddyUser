package com.ayein.bitebuddy

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ayein.bitebuddy.databinding.ActivityDetailsBinding
import com.ayein.bitebuddy.model.CartItems
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodImage: String? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase Auth
        auth = FirebaseAuth.getInstance()


        foodName = intent.getStringExtra("MenuItemName")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding) {
            detailFoodName.text = foodName
            detailFoodPrice.text = foodPrice
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.addToCartButton.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart(){
        val database = FirebaseDatabase.getInstance().reference
        val userID = auth.currentUser?.uid?:""

        // Create cartItem Object
        val cartItem = CartItems(foodName.toString(), foodPrice.toString(), foodImage.toString(), 1)

        // Save to DB
        database.child("user").child(userID).child("cart-items").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Item Added to Cart 😀", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Item Has Not been Added to Cart ☹️", Toast.LENGTH_SHORT).show()
        }
        

    }
}