package halo.android.uti.sample

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.test.pkg1.getInlineString
import com.test.pkg1.pkgString
import com.test2.KotlinTest
import com.test.pkg2.getInlineString as inlineStr2
import com.test.pkg2.pkgString as pkgString2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = this.findViewById(R.id.tv) as TextView
        KotlinTest()
        Handler().postDelayed({
            tv.text = getInlineString()
        }, 5000)

        Handler().postDelayed({
            tv.text = inlineStr2()
        }, 10000)


        Handler().postDelayed({
            tv.text = pkgString()
        }, 15000)

        Handler().postDelayed({
            tv.text = pkgString2()
        }, 20000)

    }
}

