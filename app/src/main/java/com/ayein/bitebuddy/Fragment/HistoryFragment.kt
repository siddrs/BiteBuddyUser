package com.ayein.bitebuddy.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayein.bitebuddy.adapter.BuyAgainAdapter
import com.ayein.bitebuddy.databinding.FragmentHistoryBinding
import com.ayein.bitebuddy.model.OrderDetails
import com.ayein.bitebuddy.recentOrderItems
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {

    //
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userID: String
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // Retrieve and display the user order history
        retrieveBuyHistory()


        // // // // This shit is causing a crash, disabling for now.
        binding.recentBuyItem.setOnClickListener {
            showRecentBuyItems()
        }

       binding.receivedButton.setOnClickListener {
           updateOrderStatus()
       }


        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("completed-order").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    // Fix this Thing (App Crashes on Clicking Recent Buy)
    private fun showRecentBuyItems() {
        listOfOrderItem.firstOrNull()?.let { listOfOrderItem ->
            val intent = Intent(requireContext(), recentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", listOfOrderItem)
            startActivity(intent)
        }
//        val intent = Intent(requireContext(), recentOrderItems::class.java)
//        val json = Gson().toJson(listOfOrderItem)
//        intent.putExtra("RecentBuyOrderItem", json)
//        startActivity(intent)
    }

    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.VISIBLE
        userID = auth.currentUser?.uid ?: ""

        val buyItemReference: DatabaseReference = database.reference.child("user").child(userID).child("buy-history")
        val sortingQuery = buyItemReference.orderByChild("currentTime")

        sortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()

                if (listOfOrderItem.isNotEmpty()) {
                    // Display the Most Recent Order Details
                    setDataInRecentBuyItem()
                    // Setup the RecyclerView with Previous Order Details
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle Database Error If Needed

            }

        })

    }
    // Function to display the most recent order details
    private fun setDataInRecentBuyItem() {
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                buyAgainFoodName.text = it.foodNames?.firstOrNull() ?: ""
                buyAgainFoodPrice.text = it.foodPrices?.firstOrNull() ?: ""
                //
                val image = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)

                val isOrderAccepted = listOfOrderItem[0].orderAccepted
                Log.d("TAG", "setDataInRecentBuyItem: $isOrderAccepted")
                if (isOrderAccepted){
                   orderStatus.background.setTint(Color.parseColor("#66BB6A"))
                    receivedButton.visibility = View.VISIBLE
                }

            }
        }
    }


    // Function to setup the Recycler View with Previous Order Details
    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()

        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                listOfOrderItem[i].foodPrices?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    listOfOrderItem[i].foodImages?.firstOrNull()?.let {
                        buyAgainFoodImage.add(it)
                    }
                }

                val rv = binding.BuyAgainRecyclerView
                rv.layoutManager = LinearLayoutManager(requireContext())
                buyAgainAdapter = BuyAgainAdapter(
                    buyAgainFoodName,
                    buyAgainFoodPrice,
                    buyAgainFoodImage,
                    requireContext()
                )
                rv.adapter = buyAgainAdapter
            }
        }
    }

}