package car.parking.system.room

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import car.parking.system.room.MainActivity
import com.example.carparkingsystem.R


class SobreProjetoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sobre_projeto)
    }


    fun buttonMain3(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
