package car.parking.system.room

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carparkingsystem.R
import com.google.firebase.auth.FirebaseAuth


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_teste)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_PE -> {
                Toast.makeText(this@MenuActivity, R.string.opcaoParque, Toast.LENGTH_SHORT).show()
                val i = Intent(this@MenuActivity, ParqueActivity::class.java)
                startActivity(i)
                true
            }
            R.id.btn_SHP -> {
                Toast.makeText(this@MenuActivity, R.string.opcaoSHP, Toast.LENGTH_SHORT).show()
                val r = Intent(this@MenuActivity, ShMainActivity::class.java)
                startActivity(r)
                true
            }
            R.id.btn_map -> {
                Toast.makeText(this@MenuActivity, R.string.opcaoMapa, Toast.LENGTH_SHORT).show()
                val a = Intent(this@MenuActivity, MapsActivity::class.java)
                startActivity(a)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun buttonLogout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}