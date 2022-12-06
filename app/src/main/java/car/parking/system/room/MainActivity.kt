package car.parking.system.room

import android.Manifest
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
import android.location.Location
import android.location.LocationListener
import android.location.LocationRequest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import car.parking.system.room.db.DB
import car.parking.system.room.db.Contrato
import car.parking.system.room.util.Util
import com.example.carparkingsystem.R


class MainActivity : AppCompatActivity(), LocationListener, SensorEventListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    var c_coordenadas: Cursor? = null
    private var mSensorManager: SensorManager? = null
    private var mProximity: Sensor? = null
    var editLatitude: EditText? = null
    var editLongitude: EditText? = null
    private val REQUEST_CODE_OP1 = 1
    var mDbHelper: DB? = null
    var db: SQLiteDatabase? = null

    //double lastLatitude = 0;
    //double lastLongitude = 0;
    //VARIAVEIS GPS - PASSO 3
    var locRequest: LocationRequest? = null

    // VARIAVEIS GOOGLEPLAY
    private var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        mDbHelper = DB(this)
        db = mDbHelper.getWritableDatabase()
        editLatitude = findViewById(R.id.editlatitude) as EditText?
        editLongitude = findViewById(R.id.editlongitude) as EditText?
        mLocationRequest = LocationRequest()
        buildGoogleApiClient()

        //sensor
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mProximity = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    fun botao1(v: View?) {
        GuardarCoordenadas()
    }

    private fun GuardarCoordenadas() {
        var bolValido = true
        val editLatitude = findViewById(R.id.editlatitude) as EditText
        if (editLatitude.text.toString() == "") {
            bolValido = false
            Toast.makeText(this@MainActivity, R.string.ToastExemplo, Toast.LENGTH_SHORT).show()
        }
        /*Toast.makeText(principal.this, editLatitude.getText().toString(), Toast.LENGTH_SHORT).show();
        TextView txtResumo = (TextView) findViewById(R.id.editlatitude);
        txtResumo.setText(editLatitude.getText().toString());*/
        val editLongitude = findViewById(R.id.editlongitude) as EditText
        if (editLongitude.text.toString() == "") {
            bolValido = false
            Toast.makeText(this@principal, R.string.ToastExemplo1, Toast.LENGTH_SHORT).show()
        }
        /*Toast.makeText(principal.this, editLongitude.getText().toString(), Toast.LENGTH_SHORT).show();
        TextView txtResumo1 = (TextView) findViewById(R.id.editlongitude);
        txtResumo1.setText(editLatitude.getText().toString());*/if (!bolValido) {
            Toast.makeText(this@principal, R.string.ToastExemplo2, Toast.LENGTH_SHORT).show()
        } else {

            //       ContentValues cv = new ContentValues();
            val latitude = editLatitude.text.toString().toFloat()
            val longitude = editLongitude.text.toString().toFloat()


//
            /*        cv.put(Contrato.Coordenada.COLUMN_LATITUDE, latitude);
            cv.put(Contrato.Coordenada.COLUMN_LONGITUDE, longitude);
            cv.put(Contrato.Coordenada.COLUMN_DISTANCIA, distancia);

            db.insert(Contrato.Coordenada.TABLE_NAME, null, cv);*/
            val editor: SharedPreferences.Editor = getSharedPreferences(
                MY_PREFS_NAME, MODE_PRIVATE
            ).edit()
            editor.putString("text", "")
            editor.putFloat("Latitude", latitude)
            editor.putFloat("Longitude", longitude)
            editor.apply()
            Toast.makeText(this@MainActivity, "Latitude--> SP:$latitude", Toast.LENGTH_SHORT).show()
            Toast.makeText(this@MainActivity, "Longitude--> SP:$longitude", Toast.LENGTH_SHORT).show()
        }
    }

    private fun GuardarCoordenadasGPS(lastLatitude: Double, lastLongitude: Double) {
        val prefs: SharedPreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val restoredText = prefs.getString("text", null)
        if (restoredText != null) {
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
            db!!.insert(Contrato.Coordenada.TABLE_NAME, null, cv)
            val y = Intent(this@MainActivity, ShMainActivity::class.java)
            startActivity(y)
        }
    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE_OP1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(
                    this@MainActivity,
                    data.getStringExtra(Util.PARAM_OUTPUT),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcão1 -> {
                Toast.makeText(this@MainActivity, R.string.opcaoParque, Toast.LENGTH_SHORT).show()
                val i = Intent(this@MainActivity, ::class.java)
                startActivity(i)
                true
            }
            R.id.opcão2 -> {
                Toast.makeText(this@MainActivity, R.string.opcaoSHP, Toast.LENGTH_SHORT).show()
                val r = Intent(this@MainActivity, ShMainActivity::class.java)
                startActivity(r)
                true
            }
            R.id.opcão3 -> {
                Toast.makeText(this@MainActivity, R.string.opcaoMapa, Toast.LENGTH_SHORT).show()
                val a = Intent(this@MainActivity, ::class.java)
                startActivity(a)
                true
            }
            else -> super.onOptionsItemSelected(item)
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

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
            //Toast.makeText(getApplicationContext(), ""+event.values[0], Toast.LENGTH_SHORT).show();
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                startLocationUpdates()
            } else {
                //far
                //Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL)
    }

    protected fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onConnected(@Nullable bundle: Bundle?) {

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

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {}
    override fun onConnectionSuspended(i: Int) {}
    protected fun onStart() {
        super.onStart()
        // inciiar o serviço de google play
        mGoogleApiClient!!.connect()
    }

    fun onLocationChanged(location: Location) {
        val lastLatitude = location.latitude
        val lastLongitude = location.longitude
        GuardarCoordenadasGPS(lastLatitude.toFloat().toDouble(), lastLongitude.toFloat().toDouble())
        Toast.makeText(this, "lat: $lastLatitude ; long: $lastLongitude", Toast.LENGTH_SHORT).show()
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        Toast.makeText(this, R.string.ToastExemplo6, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SENSOR_SENSITIVITY = 4
        const val MY_PREFS_NAME = "COORDENADAS"
    }
}