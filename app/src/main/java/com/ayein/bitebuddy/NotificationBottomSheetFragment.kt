package com.ayein.bitebuddy
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayein.bitebuddy.adapter.NotificationAdapter
import com.ayein.bitebuddy.databinding.FragmentNotificationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomSheetFragment : BottomSheetDialogFragment() {

    //
    private lateinit var binding: FragmentNotificationBottomSheetBinding


    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomSheetBinding.inflate(layoutInflater, container, false)

        val notifications = listOf("Order has been cancelled", "Order has been placed", "Order is ready")
        val notificationImages = listOf(R.drawable.attention, R.drawable.attention, R.drawable.attention)
        val adapter = NotificationAdapter(ArrayList(notifications), ArrayList(notificationImages))
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter
        return binding.root





    }

    companion object {

    }
}