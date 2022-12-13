package car.parking.system.room.db;

import static android.provider.Settings.System.getString;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;
import com.example.carparkingsystem.R;
import car.parking.system.room.util.Util;
import car.parking.system.room.db.Contrato;
import car.parking.system.room.db.DB;
import android.content.Context;
import android.content.SharedPreferences;
// import android.support.v7.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.view.View;


/*
public class ShPreferences {
    val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    Override fun onCreate(bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_main);

        android.content.SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_1), Context.MODE_PRIVATE);
        float latitude = sharedPref.getFloat(Util.LATITUDE, null);
        float longitude =  sharedPref.getFloat(Util.LONGITUDE, null);
        Toast.makeText(ShPreferences.this, "; latitude: " + String.valueOf(latitude) + "; longitude: " + String.valueOf(longitude).show();

    }
}*/
