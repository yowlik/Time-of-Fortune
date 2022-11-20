package com.merge.elv.oas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merge.elv.R
import com.merge.elv.databinding.ActivityFawolhBinding
import com.merge.elv.databinding.ActivityGraklsBinding

class Grakls : AppCompatActivity() {
    lateinit var bin: ActivityGraklsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin= ActivityGraklsBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.start.setOnClickListener {
            val intent= Intent(this@Grakls, Gras::class.java)
            startActivity(intent)
        }
    }
}