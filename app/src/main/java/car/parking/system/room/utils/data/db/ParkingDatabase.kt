package car.parking.system.room.utils.data.db


import android.content.Context
import  car.parking.system.room.utils.data.dao.ParkinDao
import  car.parking.system.room.utils.data.entities.Parking
import java.security.AccessControlContext

/*
@Database(entities = [Parking :: class], version = 1, exportSchema = false)
abstract class ParkingDatabase : RoomDatabase(){

    abstract  fun  noteDao(): ParkinDao

    companion object {
        @Volatile
        private var INSTANCE: ParkingDatabase? = null

        fun getDatabase(context: Context): ParkingDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                 val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}*/