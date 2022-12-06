package car.parking.system.room.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.carparkingsystem.R;

import car.parking.system.room.util.Util;
import car.parking.system.room.db.ShPreferences;


/**
 * Created by ASUS on 03/10/2017.
 */

public class ShPreferences {
    @Override
    protected void onCreate(bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_main);

        android.content.SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_1), Context.MODE_PRIVATE);
        float latitude = sharedPref.getFloat(Util.LATITUDE, null);
        float longitude =  sharedPref.getFloat(Util.LONGITUDE, null);
        Toast.makeText(ShPreferences.this,
                "; latitude: " + String.valueOf(latitude) +
                        "; longitude: " + String.valueOf(longitude).show();

    }
}
