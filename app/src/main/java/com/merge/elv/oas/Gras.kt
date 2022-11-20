package com.merge.elv.oas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.merge.elv.R
import com.merge.elv.databinding.ActivityGrasBinding
import com.merge.elv.nas.Fawol
import com.merge.elv.was.Wrast

class Gras : AppCompatActivity() {
    val raf= mutableListOf<String>("1","2","3")
    lateinit var bar:ActivityGrasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bar= ActivityGrasBinding.inflate(layoutInflater)
        setContentView(bar.root)
        bar.spin.setOnClickListener {
            var i1=raf.random()
            var i2=raf.random()
            var i3=raf.random()
            chekers(i1,bar.card1)
            chekers(i2,bar.card2)
            chekers(i3,bar.card3)
            if(i1==i2&&i2==i3){
                if(i1=="1"){
                    val intent= Intent(this@Gras, Fawol::class.java)
                    startActivity(intent)
                }
                else if(i1=="2"){
                    val intent= Intent(this@Gras, Grakl::class.java)
                    startActivity(intent)
                }
                else if(i1=="3"){
                    val intent= Intent(this@Gras, Wrast::class.java)
                    startActivity(intent)
                }

            }
        }
    }


    fun chekers(i:String,im:ImageView){
        if(i=="1"){
            im.setBackgroundResource(R.drawable.cart1)
        }
        else if(i=="2"){
            im.setBackgroundResource(R.drawable.cart2)
        }
        else if(i=="3"){
            im.setBackgroundResource(R.drawable.cart3)
        }
    }
}