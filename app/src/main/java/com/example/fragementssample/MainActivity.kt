package com.example.fragementssample

import android.app.DatePickerDialog
import android.app.Dialog
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import dbhelper.DatabaseManeger
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Calendar
import kotlin.collections.ArrayList
import kotlin.collections.MutableList

class MainActivity : AppCompatActivity() {
    lateinit var date: String
    lateinit var where: String
    lateinit var howMuch: String
    var expencies: MutableList<Expencies> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adaptor: ExpancesAdaptor

    lateinit var dbManager: DatabaseManeger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        dbManager = DatabaseManeger(this, DatabaseManeger.DB_NAME, DatabaseManeger.DB_VERSION, DatabaseErrorHandler {
            Log.e("TAG", "DB ERROR!!!")
        })


        adaptor = ExpancesAdaptor(this, expencies)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adaptor
        expencies.clear()

        updateUI()

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            showAddDiloge()
            adaptor.notifyDataSetChanged()
            Log.e("TAG", expencies.size.toString())

        }
    }

    fun updateUI() {

        if (expencies.isEmpty()) {
            var coursor = dbManager.getAllExpences()
            if (coursor.moveToFirst()) {
                do {
                    expencies.add(
                        Expencies(
                            coursor.getString(1),
                            coursor.getString(2),
                            coursor.getString(3)
                        )
                    )

                } while (coursor.moveToNext())
            }
        }

    }

    private fun showAddDiloge() {
        val dialog = Dialog(this@MainActivity)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        window.setLayout(ConstraintLayout.LayoutParams.FILL_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_expance_dialog)
        val mAddButton = dialog.findViewById<Button>(R.id.button_add)
        val mCancelButton = dialog.findViewById<Button>(R.id.button_cancel)
        val mTextViewDate = dialog.findViewById<TextView>(R.id.textView_date)
        val mEditTextWhere = dialog.findViewById<EditText>(R.id.editText_where)
        val mEditTextHowMuch = dialog.findViewById<EditText>(R.id.editText_howMuch)





        mAddButton.setOnClickListener {
            where = mEditTextWhere.text.toString()
            howMuch = mEditTextHowMuch.text.toString()
            // expencies.add(Expencies(date, where, howMuch))

            var res = dbManager.addExpences(date, where, howMuch)
            Toast.makeText(this, res.toString(), Toast.LENGTH_LONG).show()
            updateUI()
//            var coursor = dbManager.getAllExpences()
//            if (coursor.moveToFirst()) {
//                do {
//                    expencies.add(
//                        Expencies(
//                            coursor.getString(1),
//                            coursor.getString(2),
//                            coursor.getString(3)
//                        )
//                    )
//
//                } while (coursor.moveToNext())
//            }


            Log.e("TAG mainActivity", "date-$date where-$where howmuch-$howMuch")

            dialog.dismiss()


        }

        mCancelButton.setOnClickListener { dialog.dismiss() }

        val mSelectDateButton = dialog.findViewById<Button>(R.id.button_select_date)
        mSelectDateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val moy = monthOfYear + 1
                mTextViewDate.text = "$dayOfMonth-$moy-$year"
                date = "$dayOfMonth-$moy-$year"
            }, year, month, day)

            dpd.show()
        }

        dialog.show()


    }


}
