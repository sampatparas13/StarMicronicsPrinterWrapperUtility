package com.sampatparas.starprinterwrapperutility
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sampatparas.starprinterutility.interfaces.PrinterListCallBack
import com.sampatparas.starprinterutility.model.SearchResultInfo
import com.sampatparas.starprinterutility.searchPrinter.SearchPrinterUtils

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchPrinterUtils(this, object : PrinterListCallBack {
            override fun onSuccessSearchResult(result: MutableList<SearchResultInfo>?) {
                Log.e(TAG, "searchListSize "+result?.size)
            }
            override fun onFlailedResult(message: String?) {
                Log.e(TAG, " onFlailedResult $message")
            }
        }).startSearchAll()
    }
}