package com.example.fragementssample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ExpancesAdaptor(val context: Context, val data: List<Expencies>) :
    RecyclerView.Adapter<ExpancesAdaptor.ExpencesViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ExpencesViewHolder {
        return ExpencesViewHolder(LayoutInflater.from(context).inflate(R.layout.singal_row_list, p0, false))
    }

    override fun getItemCount(): Int {
        return data.size

    }

    override fun onBindViewHolder(p0: ExpencesViewHolder, p1: Int) {
        var expencies=data[p1]
        p0.bind(expencies)

    }

    class ExpencesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mDateTextView: TextView? = null
        var mWhereTextView: TextView? = null
        var mHowMuchTextView: TextView? = null

        init {
            mDateTextView = view.findViewById(R.id.textView_date)
            mWhereTextView = view.findViewById(R.id.textView_where)
            mHowMuchTextView = view.findViewById(R.id.textView_howmuch)
        }

        fun bind(expencies: Expencies) {
            var date= expencies.date
            var amount=expencies.howMuch
            var where=expencies.where

            mDateTextView?.text = date
            mHowMuchTextView?.text=amount
            mWhereTextView?.text=where

            Log.e("TAG","date $date amount $amount where $where")


        }

    }
}