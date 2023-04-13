package com.test.placement_major_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.placement_major_project.R
import com.test.placement_major_project.dataclasses.BranchSelect_data

class BranchSelect_adapter (private val campus_select: ArrayList<BranchSelect_data>) : RecyclerView.Adapter<BranchSelect_adapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.campus_filter_cardview, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val groupdata  = campus_select[position]

        holder.student_name.text = groupdata.name
        holder.company_name.text = groupdata.comp_name
        holder.packages.text = groupdata.ctc

    }


    override fun getItemCount(): Int {
        return campus_select.size

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val student_name  = itemView.findViewById(R.id.student_name) as TextView
        val company_name = itemView.findViewById(R.id.student_company) as TextView
        val packages = itemView.findViewById(R.id.student_ctc) as TextView


    }

}