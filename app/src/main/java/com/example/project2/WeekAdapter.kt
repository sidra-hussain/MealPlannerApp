package com.example.project2
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeekAdapter(private val days: List<String>) :
    RecyclerView.Adapter<WeekAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View, private val days: List<String>):
        RecyclerView.ViewHolder(rootLayout), View.OnClickListener {
        val myDayText : TextView =rootLayout.findViewById(R.id.dayView)

        init {
            rootLayout.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val day = days[position]
            if (position != 0){
                val context = view.context
                val intent = Intent(context, MealPlanActivity::class.java)
                intent.putExtra("Day", day)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        //create a new viewHolder
        //need to read the xml row
        val layoutInflater: LayoutInflater =
            LayoutInflater.from(parent.context)
        val rootLayout: View =layoutInflater.
        inflate(R.layout.item_day, parent, false)
        val viewHolder=ViewHolder(rootLayout, days)
        return viewHolder
    }

    override fun getItemCount(): Int {
        Log.d("WeekAdapter", "getItemCount: ${days.size}")
        return days.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDay=days[position]
        Log.d("Binder", "currentDay: $currentDay")
        holder.myDayText.text = currentDay

    }
}