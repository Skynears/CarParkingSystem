package car.parking.system.room.db;

import android.provider.BaseColumns;

public final class Contrato {
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INT_TYPE = " INTEGER ";
    private static final String FLOAT_TYPE = " FLOAT ";

    public Contrato() {
    }

    // UMA INNER CLASS POR CADA TABELA QUE EU PRECISO

    public static abstract class Coordenada implements BaseColumns {
        public static final String TABLE_NAME = "coordenada";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_DISTANCIA = "distancia";

        public static final String[] PROJECTION = {Coordenada._ID, Coordenada.COLUMN_LATITUDE, Coordenada.COLUMN_LONGITUDE, Coordenada.COLUMN_DISTANCIA};


        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " +Coordenada.TABLE_NAME + "(" +
                        Coordenada._ID + INT_TYPE + " PRIMARY KEY," +
                        Coordenada.COLUMN_LATITUDE + FLOAT_TYPE + "," +
                        Coordenada.COLUMN_LONGITUDE + FLOAT_TYPE + "," +
                        Coordenada.COLUMN_DISTANCIA + FLOAT_TYPE +")";


        public static final String SQL_DROP_ENTRIES =
                "DROP TABLE " + Coordenada.TABLE_NAME + ";";


    }
}