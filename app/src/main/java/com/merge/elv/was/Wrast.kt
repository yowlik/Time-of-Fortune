package com.merge.elv.was

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merge.elv.R
import com.merge.elv.databinding.ActivityGraklBinding
import com.merge.elv.databinding.ActivityWrastBinding
import com.merge.elv.nas.Fawolh
import com.merge.elv.oas.Gras

class Wrast : AppCompatActivity() {
    lateinit var bin: ActivityWrastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin= ActivityWrastBinding.inflate(layoutInflater)
        setContentView(bin.root)
        bin.hist.setOnClickListener {
            val intent= Intent(this@Wrast, Wrastr::class.java)
            startActivity(intent)
        }
        bin.start.setOnClickListener {
            val intent= Intent(this@Wrast, Gras::class.java)
            startActivity(intent)
        }
    }
}