package car.parking.system.room

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.carparkingsystem.R

class SobreEquipaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sobre_equipa)
    }
    fun buttonMain4(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}