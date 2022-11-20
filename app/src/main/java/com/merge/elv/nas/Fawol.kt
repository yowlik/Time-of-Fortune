package com.merge.elv.nas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merge.elv.R
import com.merge.elv.databinding.ActivityFawolBinding
import com.merge.elv.oas.Gras

class Fawol : AppCompatActivity() {
    lateinit var bin:ActivityFawolBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin= ActivityFawolBinding.inflate(layoutInflater)
        setContentView(bin.root)
        bin.hist.setOnClickListener {
            val intent= Intent(this@Fawol,Fawolh::class.java)
            startActivity(intent)
        }
        bin.start.setOnClickListener {
            val intent= Intent(this@Fawol, Gras::class.java)
            startActivity(intent)
        }
    }
}