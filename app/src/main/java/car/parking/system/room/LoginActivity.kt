package car.parking.system.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carparkingsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.app.Activity
import android.content.ContentValues.TAG
import com.example.carparkingsystem.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth


        binding.btnSingin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val pass = binding.edPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, R.string.ToastSingin5, Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun buttonReg(view: View) {
        val intent = Intent(this, RegistoActivity::class.java)
        startActivity(intent)
    }


    fun buttonMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}