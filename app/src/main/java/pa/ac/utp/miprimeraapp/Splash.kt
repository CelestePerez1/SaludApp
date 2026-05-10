package pa.ac.utp.miprimeraapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(supportActionBar?.hide() as Bundle?
        )

        // Delay de 2 segundos y luego ir a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }, 2000)

        setContentView(R.layout.activity_splash)
    }
}