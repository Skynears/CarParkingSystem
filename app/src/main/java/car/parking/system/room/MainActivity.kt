package car.parking.system.room

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.carparkingsystem.R
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    lateinit var locale: Locale
    private var currentLanguage = "pt"
    private var currentLang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentLanguage = intent.getStringExtra(currentLang).toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.linguagem, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.en){
            setLocale("en")
        }
        if(item.itemId == R.id.pt){
            setLocale("pt")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(
                this,
                MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(
                this@MainActivity, getString(R.string.Language_already_selected), Toast.LENGTH_SHORT).show()
        }
    }

    fun buttonLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun buttonAbout(view: View) {
        val intent = Intent(this, SobreProjetoActivity::class.java)
        startActivity(intent)
    }

    fun buttonExit(view: View) {
        this.finishAffinity()
    }

}