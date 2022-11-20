package com.merge.elv.nas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merge.elv.R
import com.merge.elv.databinding.ActivityFawolBinding
import com.merge.elv.databinding.ActivityFawolhBinding
import com.merge.elv.oas.Gras

class Fawolh : AppCompatActivity() {
    lateinit var bin: ActivityFawolhBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin= ActivityFawolhBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.start.setOnClickListener {
            val intent= Intent(this@Fawolh, Gras::class.java)
            startActivity(intent)
        }
    }
}