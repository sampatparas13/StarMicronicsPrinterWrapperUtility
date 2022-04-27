package com.sampatparas.starprinterwrapperutility

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sampatparas.starprinterutility.searchPrinter.SearchPrinterUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val s = SearchPrinterUtils(
            this
        ) { result ->
            for (info in result) {
                Toast.makeText(this@MainActivity, "" + info.modelName, Toast.LENGTH_SHORT).show()
                Log.e(
                    MainActivity::class.java.simpleName,
                    "callback: " + info.modelName
                )
            }
        }
        s.startSearch("LANsdasd")
    }
}