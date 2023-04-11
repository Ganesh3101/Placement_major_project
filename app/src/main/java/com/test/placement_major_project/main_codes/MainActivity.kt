package com.test.placement_major_project.main_codes

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.placement_major_project.R
import com.test.placement_major_project.adapters.BranchAdapter
import com.test.placement_major_project.dataclasses.Branch


class MainActivity : AppCompatActivity() {

    lateinit var courseGRV: GridView
    lateinit var courseList: List<Branch>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        courseGRV = findViewById(R.id.idGRV)
        courseList = ArrayList<Branch>()

        // on below line we are adding data to
        // our course list with image and course name.
        courseList = courseList + Branch("C++", R.drawable.home)
        courseList = courseList + Branch("C++", R.drawable.home)
        courseList = courseList + Branch("main", R.drawable.home)
        courseList = courseList + Branch("C++", R.drawable.home)
        courseList = courseList + Branch("C++", R.drawable.baseline_computer_24)
        courseList = courseList + Branch("C++", R.drawable.home)



        // on below line we are initializing our course adapter
        // and passing course list and context.
        val courseAdapter = BranchAdapter(courseList = courseList, this@MainActivity)

        // on below line we are setting adapter to our grid view.
        courseGRV.adapter = courseAdapter

        // on below line we are adding on item
        // click listener for our grid view.
        courseGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
            Toast.makeText(
                applicationContext, courseList[position].courseName + " selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
