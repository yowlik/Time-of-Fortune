package com.merge.elv.oas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merge.elv.R
import com.merge.elv.databinding.ActivityFawolBinding
import com.merge.elv.databinding.ActivityGraklBinding
import com.merge.elv.nas.Fawolh

class Grakl : AppCompatActivity() {
    lateinit var bin: ActivityGraklBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin= ActivityGraklBinding.inflate(layoutInflater)
        setContentView(bin.root)
        bin.hist.setOnClickListener {
            val intent= Intent(this@Grakl, Grakls::class.java)
            startActivity(intent)
        }
        bin.start.setOnClickListener {
            val intent= Intent(this@Grakl, Gras::class.java)
            startActivity(intent)
        }
    }
}