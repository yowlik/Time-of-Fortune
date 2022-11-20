package com.merge.elv.was

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merge.elv.R
import com.merge.elv.databinding.ActivityFawolhBinding
import com.merge.elv.databinding.ActivityWrastrBinding
import com.merge.elv.oas.Gras

class Wrastr : AppCompatActivity() {
    lateinit var bin: ActivityWrastrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin= ActivityWrastrBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.start.setOnClickListener {
            val intent= Intent(this@Wrastr, Gras::class.java)
            startActivity(intent)
        }
    }
}