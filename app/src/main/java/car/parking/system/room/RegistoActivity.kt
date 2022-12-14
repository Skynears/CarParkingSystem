package car.parking.system.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carparkingsystem.R
import com.example.carparkingsystem.databinding.ActivityLoginBinding
import com.example.carparkingsystem.databinding.ActivityRegistoBinding
import com.google.firebase.auth.FirebaseAuth

class RegistoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistoBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)

        binding = ActivityRegistoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSubmit.setOnClickListener {
            val email = binding.ToastSingup1.text.toString()
            val pass = binding.ToastSingup2.text.toString()
            val confirmPass = binding.ToastSingup3.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, R.string.registo_sucessful, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, R.string.ToastSingup6, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.ToastSingin5, Toast.LENGTH_SHORT).show()

            }
        }
    }

}