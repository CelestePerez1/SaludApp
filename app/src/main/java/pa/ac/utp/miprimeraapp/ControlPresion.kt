package pa.ac.utp.miprimeraapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ControlPresion : AppCompatActivity() {
    @SuppressLint("SoonBlockedPrivateApi")
    fun colorearNumberPickers(view: android.view.View) {
        val color = android.graphics.Color.parseColor("#000000")
        if (view is android.widget.NumberPicker) {
            try {
                val fieldInput = android.widget.NumberPicker::class.java
                    .getDeclaredField("mInputText")
                fieldInput.isAccessible = true
                val input = fieldInput.get(view) as android.widget.EditText
                input.setTextColor(color)
                view.invalidate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                colorearNumberPickers(view.getChildAt(i))
            }
        }
    }

    fun deshabilitarEdicion(view: android.view.View) {
        if (view is android.widget.NumberPicker) {
            view.descendantFocusability = android.widget.NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        if (view is android.view.ViewGroup) {
            for (i in 0 until view.childCount) {
                deshabilitarEdicion(view.getChildAt(i))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_control_presion)

        val timePicker = findViewById<TimePicker>(R.id.timePicker)

        timePicker.post {
            timePicker.setIs24HourView(true)
            colorearNumberPickers(timePicker)
            deshabilitarEdicion(timePicker)
        }


        val npSistolica = findViewById<NumberPicker>(R.id.npSistolica)

        npSistolica.minValue = 80
        npSistolica.maxValue = 200



      // Deshabilitar edición y colorear
        npSistolica.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        npSistolica.post { colorearNumberPickers(npSistolica) }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}