package com.example.klhw5lab9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var tv_clock: TextView = findViewById(R.id.tv_clock)
    private var btn_start: Button = findViewById(R.id.btn_start)
    private var flag = false
    private val receiver: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent) {
            val b = intent.extras?: return
            tv_clock?.text = String.format("%02d:%02d:%02d",
                b.getInt("H"),b.getInt("M"),b.getInt("S"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        registerReceiver(receiver, IntentFilter("MyMessage"))
        if (flag)
            btn_start.setText("暫停")
        else
            btn_start.setText("開始")

        btn_start.setOnClickListener(View.OnClickListener {
            flag = !flag
            if (flag) {
                btn_start.setText("暫停")
                Toast.makeText(this@MainActivity, "計時開始", Toast.LENGTH_SHORT).show()
            } else {
                btn_start.setText("開始")
                Toast.makeText(this@MainActivity, "計時暫停", Toast.LENGTH_LONG).show()
            }
            startService(
                Intent(this@MainActivity, MyService::class.java).putExtra(
                    "flag",
                    flag
                )
            )
        })
    }
}