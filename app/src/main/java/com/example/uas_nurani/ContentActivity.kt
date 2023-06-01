package com.example.uas_nurani

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_nurani.databinding.ActivityContentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ContentActivity : AppCompatActivity() {

    private lateinit var kueRecyclerview: RecyclerView
    private lateinit var kueList: MutableList<Image>
    private lateinit var kueAdapter:ImageAdapter
    private lateinit var binding: ActivityContentBinding

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**initialized*/
        kueRecyclerview = findViewById(R.id.imageRecyclerView)
        kueRecyclerview.setHasFixedSize(true)
        kueRecyclerview.layoutManager = LinearLayoutManager(this@ContentActivity)

        kueList = ArrayList()
        kueAdapter = ImageAdapter(this@ContentActivity,kueList)
        kueRecyclerview.adapter = kueAdapter
        /**getData firebase*/

        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("kue")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ContentActivity, error.message, Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                kueList.clear()
                if (snapshot.exists()){
                    for (teacherSnapshot in snapshot.children){
                        val upload = teacherSnapshot.getValue(Image::class.java)
                        upload!!.key = teacherSnapshot.key
                        kueList.add(upload)
                    }
                    kueAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}