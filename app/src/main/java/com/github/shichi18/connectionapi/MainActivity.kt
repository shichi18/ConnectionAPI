package com.github.shichi18.connectionapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val numtext = findViewById<EditText>(R.id.numText)
        val address1 = findViewById<TextView>(R.id.addressText1)
        val address2 = findViewById<TextView>(R.id.addressText2)
        val address3 = findViewById<TextView>(R.id.addressText3)

        button.setOnClickListener {
            val asyncConnect = AsyncConnect()
            asyncConnect.execute("start")
        }
    }
}
