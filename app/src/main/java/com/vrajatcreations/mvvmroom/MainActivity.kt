package com.vrajatcreations.mvvmroom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vrajatcreations.mvvmroom.databinding.ActivityMainBinding
import com.vrajatcreations.mvvmroom.db.Subscriber
import com.vrajatcreations.mvvmroom.db.SubscriberDatabase
import com.vrajatcreations.mvvmroom.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this, {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, {
            Log.i("TAG", "displaySubscribersList: $it")
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
        adapter = MyRecyclerViewAdapter { selectedItem: Subscriber ->
            listItemClicked(
                selectedItem
            )
        }
        binding.subscriberRecyclerView.adapter = adapter


    }

    private fun listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this, "SubscriberItem Name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}