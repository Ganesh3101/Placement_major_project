package com.test.placement_major_project.main_codes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.test.placement_major_project.R
import com.test.placement_major_project.adapters.campus_filter_adapter
import com.test.placement_major_project.dataclasses.Campus_filter_data

class Campus_filter_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campus_filter_list)


        val recyclerview = findViewById<RecyclerView>(R.id.campus_company_select)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val list = ArrayList<Campus_filter_data>()

        for(i in 1..145) {

            val listing_data =
                FirebaseDatabase.getInstance().getReference().child("Sheet1").child(i.toString())

            listing_data.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        if(snapshot.child("Campus Selected").getValue() != "") {

                            var name = snapshot.child("Name of Student").getValue()
                            var company_name = snapshot.child("Campus Selected").getValue()
                            var cgpa = snapshot.child("CGPI").getValue()
                            var pacakage = snapshot.child("PACKAGE").getValue()

                            list.add(Campus_filter_data(name.toString(), company_name.toString(),cgpa.toString(),pacakage.toString()))

                        }
                    }

                    val clist = campus_filter_adapter(list)
                    recyclerview.adapter = clist

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }

    }
}