package com.sampatparas.starprinterutility.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sampatparas.starprinterutility.R
import com.sampatparas.starprinterutility.adapter.SearchResultAdapter
import com.sampatparas.starprinterutility.customView.TestReceiptView
import com.sampatparas.starprinterutility.interfaces.PrinterListCallBack
import com.sampatparas.starprinterutility.model.SearchResultInfo
import com.sampatparas.starprinterutility.printerUtils.StarPrinterUtils
import com.sampatparas.starprinterutility.searchPrinter.PrinterConnectionTypesDialogFragment
import com.sampatparas.starprinterutility.searchPrinter.SearchPrinterUtils

import kotlinx.android.synthetic.main.activity_testing_print.*

class TestingPrintActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "TestingPrintActivity"
    var searchResultArray: ArrayList<SearchResultInfo> = ArrayList()
    var adapter: SearchResultAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing_print)
        setOnClickListener()
    }


    private fun setOnClickListener() {
        printerName.setOnClickListener(this);
        buttonTestPrint.setOnClickListener(this);
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonTestPrint -> {
                val test = TestReceiptView(this)
                test.setData("1234")
                StarPrinterUtils.printReceipts(
                    this, test
                ) { status, message ->
                    Log.e("TestingPrintActivity", "printer :: $message")
                }
            }
            R.id.printerName -> {
                val dialog: PrinterConnectionTypesDialogFragment =
                    PrinterConnectionTypesDialogFragment.newInstance { selectedType ->
                        textViewConnectionName.text = selectedType
                        searchResultArray.clear()
                        SearchPrinterUtils(this, object : PrinterListCallBack {
                            @SuppressLint("NotifyDataSetChanged")
                            override fun onSuccessSearchResult(result: MutableList<SearchResultInfo>?) {
                                recyclerViewListPrinter.visibility = View.VISIBLE
                                TextViewDataNotFound.visibility = View.GONE
                                if (adapter != null) {
                                    adapter?.notifyDataSetChanged()
                                } else {
                                    setAdapter()
                                }
                            }

                            override fun onFlailedResult(message: String?) {
                                TextViewDataNotFound.text = message
                                TextViewDataNotFound.visibility = View.VISIBLE
                                recyclerViewListPrinter.visibility = View.GONE
                            }

                        }).searchPrinter(selectedType)
                    }
                dialog.show(supportFragmentManager, "")
            }
        }
    }

    private fun setAdapter() {
        adapter = SearchResultAdapter(searchResultArray, supportFragmentManager)
        recyclerViewListPrinter.adapter = adapter
    }
}