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
import com.test.placement_major_project.adapters.BranchSelect_adapter
import com.test.placement_major_project.dataclasses.BranchSelect_data

class It : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.branch_filtered_rcview)

        val recyclerview = findViewById<RecyclerView>(R.id.branch_filtered)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val list = ArrayList<BranchSelect_data>()

        for(i in 1..145) {

            val listing_data =
                FirebaseDatabase.getInstance().getReference().child("21-22").child(i.toString())

            listing_data.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        if(snapshot.child("Branch").getValue() == "IT B.E") {

                            var name = snapshot.child("Name of student").getValue()
                            var company_name = snapshot.child("Placed in Company").getValue()
                            var pacakage = snapshot.child("Salary in LPA").getValue()

                            list.add(BranchSelect_data(name.toString(), company_name.toString(),pacakage.toString()))

                        }
                    }

                    val clist = BranchSelect_adapter(list)
                    recyclerview.adapter = clist

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }

    }
}