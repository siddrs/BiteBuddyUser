package com.ayein.bitebuddy.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayein.bitebuddy.DetailsActivity
import com.ayein.bitebuddy.databinding.MenuItemBinding
import com.ayein.bitebuddy.model.MenuItem
import com.bumptech.glide.Glide

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


//    private var foodName: String?= null
//    private var foodPrice: String?= null
//    private var foodImage: String?= null
//    private var foodQuantity: String?= null
//    private lateinit var auth: FirebaseAuth


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuItems.size

    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }


        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]
            // an intent to open detail activity and pass data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemImage", menuItem.foodImage)
                putExtra("MenuItemPrice", menuItem.foodPrice)
            }
            // Start Detail Activity
            requireContext.startActivity(intent)
        }


        // Set Data into Recycler View (Items, Names, Price)
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)
            }
//            binding.menuAddToCartButton.setOnClickListener {
//                addItemToCart()
//            }
        }

//        private fun addItemToCart() {
//            val database = FirebaseDatabase.getInstance().reference
//            val auth = FirebaseAuth.getInstance()
//            val userID = auth.currentUser?.uid ?: ""
//
//            // Create a cart Item Object
//            val cartItem = CartItems(
//                foodName.toString(),
//                foodPrice.toString(),
//                foodImage.toString(),
//                "1"
//            )
//            // Save Data to Cart Firebase
//            database.child("user").child(userID).child("CartItems").push().setValue(cartItem)
//                .addOnSuccessListener {
//                    Toast.makeText(
//                        requireContext,
//                        "Item has been Added to Cart 😀",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }.addOnFailureListener {
//                Toast.makeText(requireContext, "Item has not been Added ☹️️", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
    }


    interface OnClickListener {
        fun onItemClick(position: Int) {

        }
    }
}

