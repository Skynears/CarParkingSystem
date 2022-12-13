package car.parking.system.room

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CaptureRequest.SENSOR_SENSITIVITY
import android.hardware.camera2.CaptureResult.SENSOR_SENSITIVITY
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import car.parking.system.room.db.Contrato
import car.parking.system.room.db.DB
import car.parking.system.room.util.Util
import com.example.carparkingsystem.R
import com.google.android.gms.common.api.GoogleApiActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

// SensorEventListener, LocationListener

 class MenuActivity : AppCompatActivity(){

     /*
    private lateinit var sensorManager: SensorManager
    private lateinit var mSensorManager: SensorManager
    private lateinit var mProximity: Sensor

    private lateinit var c_coordenadas: Cursor

    private val SENSOR_SENSITIVITY = 4

    private lateinit var editLatitude: EditText
    private lateinit var editLongitude: EditText

    private val REQUEST_CODE_OP1 = 1

    private lateinit var mDbHelper: DB
    private lateinit var db: SQLiteDatabase


    //VARIAVEIS GPS - PASSO 3
    private lateinit var locRequest: LocationRequest

    // VARIAVEIS GOOGLEPLAY
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationRequest: LocationRequest

     val MY_PREFS_NAME = "COORDENADAS"
     */

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_teste)

        /*
        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mDbHelper = DB(this)
        db = mDbHelper.writableDatabase

        editLatitude = findViewById(R.id.latitude) as EditText
        editLongitude = findViewById(R.id.longitude) as EditText

        mLocationRequest = LocationRequest()
       // buildGoogleApiClient()


        //sensor
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) */

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

/*
    private fun GuardarCoordenadasGPS(lastLatitude: Double, lastLongitude: Double)
    {
        val prefs = getSharedPreferences("text" MODE_PRIVATE)
        val restoredText = prefs.getString("text", null)
        if (restoredText != null)
        {
            val latitude = prefs.getFloat("Latitude", 41.70.toFloat())
            val longitude = prefs.getFloat("Longitude", -8.82.toFloat())
            val cv = ContentValues()

            //depois alterar para valores sensor
            val locGPS = Location("")
            locGPS.latitude = lastLatitude
            locGPS.longitude = lastLongitude
            val locInserida = Location("")
            locInserida.latitude = latitude.toDouble()
            locInserida.longitude = longitude.toDouble()

            var distancia = locGPS.distanceTo(locInserida)
            //converter em km:
            distancia = distancia / 1000

            cv.put(Contrato.Coordenada.COLUMN_LATITUDE, lastLatitude)
            cv.put(Contrato.Coordenada.COLUMN_LONGITUDE, lastLongitude)
            cv.put(Contrato.Coordenada.COLUMN_DISTANCIA, distancia)

            db.insert(Contrato.Coordenada.TABLE_NAME, null, cv)
            val y = Intent(this@MenuActivity, Menu::class.java)
            startActivity(y)
        }
    }



    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE_OP1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(
                    this@MenuActivity,
                    data.getStringExtra(Util.PARAM_OUTPUT),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    // COORDENADAS
    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        createLocationRequest()
    }

    private fun createLocationRequest() {
        mLocationRequest.setInterval(10000)
        mLocationRequest.setFastestInterval(10000)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_PROXIMITY) {
            //Toast.makeText(getApplicationContext(), ""+event.values[0], Toast.LENGTH_SHORT).show();
            if (event.values[0] >= -car.parking.system.room.SENSOR_SENSITIVITY && event.values[0] <= car.parking.system.room.SENSOR_SENSITIVITY) {
                startLocationUpdates()
            } else {
                //far
                //Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }



    fun onConnected(@Nullable bundle: Bundle?) {

        //startLocationUpdates();
    }


    private fun startLocationUpdates() {
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(this, PERMISSIONS[0])
            !== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, PERMISSIONS[1])
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this as Activity, PERMISSIONS, 0)
        } else {
            // faz pedido de sinal e quando chega dispara o onLocationChanged
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
                )
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, R.string.ToastExemplo5, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        // inciiar o serviço de google play
        mGoogleApiClient.connect()
    }


    fun onLocationChanged(location: Location) {
        val lastLatitude = location.latitude
        val lastLongitude = location.longitude
        GuardarCoordenadasGPS(lastLatitude.toFloat().toDouble(), lastLongitude.toFloat().toDouble())
        Toast.makeText(this, "lat: $lastLatitude ; long: $lastLongitude", Toast.LENGTH_SHORT).show()
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        Toast.makeText(this, R.string.ToastExemplo6, Toast.LENGTH_SHORT).show()
    } */

}

