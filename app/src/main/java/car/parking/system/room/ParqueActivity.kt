package car.parking.system.room

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.carparkingsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

// Firebase CarParkingSystem API: "AIzaSyDtzuhCi8rzWwmMmDA5a_ZpNMqoQblgDKo"

class ParqueActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    internal var parque: Parque? = null
    var ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parque)

        //val database: = Firebase.database.reference
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                parque = dataSnapshot.getValue() as Parque
                //textView.text = parque?.getName()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        }
        ref.addListenerForSingleValueEvent(menuListener)
            fun onDataChange(dataSnapshot: DataSnapshot) {
                parque = dataSnapshot.getValue(Parque::class.java)
                //textView.text = parque?.Estado
               // textView.text = parque?.VDisp
               // textView.text = parque?.VInd
               // textView.text = parque?.Temp
               // textView.text = parque?.Humid

            }
        }

    data class Parque(
        var Estado: String?="",
        var VDisp : String? = "",
        var VInd: String? = "",
        var Temp: String? = "",
        var Humid: String? = "",

        ) {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "Estado do Parque" to Estado,
                "Vagas Disponiveis" to VDisp,
                "Vagas Inisponiveis" to VInd,
                "Temperatura" to Temp,
                "Humidade" to Humid
            )
        }
    }
}

