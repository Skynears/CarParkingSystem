package car.parking.system.room.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.carparkingsystem.R;


public class DB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "app.db";

    public DB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contrato.Coordenada.SQL_CREATE_ENTRIES);

        db.execSQL("INSERT INTO " + Contrato.Coordenada.TABLE_NAME + " (" + Contrato.Coordenada._ID +
                ", " +
                Contrato.Coordenada.COLUMN_LATITUDE + "," + Contrato.Coordenada.COLUMN_LONGITUDE + "," + Contrato.Coordenada.COLUMN_DISTANCIA +
                ") values (1, 11, 11, 0)");
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contrato.Coordenada.SQL_DROP_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
