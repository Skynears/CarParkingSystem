package car.parking.system.room;


import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.location.Location
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.carparkingsystem.R
import android.widget.*
import car.parking.system.room.db.Contrato
import car.parking.system.room.db.DB

class ShMainActivity : AppCompatActivity() {

    var mDbHelper: DB? = null
    var db: SQLiteDatabase? = null
    var c_coordenadas: Cursor? = null
    var lista: ListView? = null
    var adapter: SimpleCursorAdapter? = null

    val LatitudeFixa = 41.8789835.toFloat()
    val LongitudeFixa = -9.0423427.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sh_main)
        mDbHelper = DB(this)
        db = mDbHelper!!.getWritableDatabase()
        lista = findViewById(R.id.listador) as ListView
        PreencheLista()
        registerForContextMenu(lista)


            /*String valor = getIntent().getStringExtra(Util.PARAM_NOME);
        Toast.makeText(second.this, valor, Toast.LENGTH_SHORT).show();*/
        }

        fun botao(v: View?) {
            val editFiltro = findViewById(R.id.editFiltro) as EditText
            PreencheListaComFiltro(editFiltro.text.toString())
        }

        open fun PreencheLista() {
            c_coordenadas = db!!.query(
                Contrato.Coordenada.TABLE_NAME,
                Contrato.Coordenada.PROJECTION,
                null,
                null,
                null,
                null,
                "_ID DESC",
                null
            )
            //sqLiteDatabase.query(tableName, tableColumns, whereClause, whereArgs, groupBy, having, orderBy);
            adapter = SimpleCursorAdapter(
                this,
                R.layout.sh_activity_row,
                c_coordenadas,
                arrayOf<String>(
                    Contrato.Coordenada.COLUMN_LATITUDE,
                    Contrato.Coordenada.COLUMN_LONGITUDE,
                    Contrato.Coordenada.COLUMN_DISTANCIA
                ),
                intArrayOf(R.id.latitude, R.id.longitude, R.id.distancia)
            )
            lista!!.adapter = adapter
        }

        open fun PreencheListaComFiltro(filtro: String) {
            var mListView: ListView
            c_coordenadas = db!!.query(
                Contrato.Coordenada.TABLE_NAME,
                Contrato.Coordenada.PROJECTION,
                "distancia < ?",
                arrayOf(filtro),
                null,
                null,
                "distancia ASC",
                null
            )
            //sqLiteDatabase.query(tableName, tableColumns, whereClause, whereArgs, groupBy, having, orderBy);
            adapter = SimpleCursorAdapter(
                this,
                R.layout.sh_activity_row,
                c_coordenadas,
                arrayOf<String>(
                    Contrato.Coordenada.COLUMN_LATITUDE,
                    Contrato.Coordenada.COLUMN_LONGITUDE,
                    Contrato.Coordenada.COLUMN_DISTANCIA
                ),
                intArrayOf(R.id.latitude, R.id.longitude, R.id.distancia)
            )
            lista!!.adapter = adapter
        }

        override fun onCreateContextMenu(
            menu: ContextMenu, v: View,
            menuInfo: ContextMenuInfo?
        ) {
            if (v.id == R.id.listador) {
                val info = menuInfo as AdapterContextMenuInfo?
                menu.setHeaderTitle(R.string.ToastExemplo7)
                menu.add(Menu.NONE, 1, 1, R.string.ToastExemplo8)
                menu.add(Menu.NONE, 2, 2, R.string.ToastExemplo9)
            }
        }

        @SuppressLint("Range")
        override fun onContextItemSelected(item: MenuItem): Boolean {
            val info = item.menuInfo as AdapterContextMenuInfo
            when (item.title.toString()) {
                "Apagar" -> {
                    val itemPosition = info.position
                    Toast.makeText(
                        this@ShMainActivity,
                        R.string.ToastExemplo10 + itemPosition,
                        Toast.LENGTH_SHORT
                    ).show()
                    c_coordenadas!!.moveToPosition(itemPosition)
                    val intIDCordenada =
                        c_coordenadas!!.getInt(c_coordenadas!!.getColumnIndex(Contrato.Coordenada._ID))
                    Toast.makeText(this@ShMainActivity, "$intIDCordenada ", Toast.LENGTH_SHORT).show()
                    apagaRegisto(intIDCordenada)
                    Toast.makeText(this@ShMainActivity, R.string.ToastExemplo11, Toast.LENGTH_SHORT).show()
                }
                "Atualizar" -> {
                    val view = lista!!.getChildAt(info.position)
                    val editTextLatitude = view.findViewById<View>(R.id.latitude) as EditText
                    val sLatitude = editTextLatitude.text.toString()
                    val editTextLongitude = view.findViewById<View>(R.id.longitude) as EditText
                    val sLongitude = editTextLongitude.text.toString()
                    val intIDCordenada1 =
                        c_coordenadas!!.getInt(c_coordenadas!!.getColumnIndex(Contrato.Coordenada._ID))
                    val latitude = sLatitude.toFloat()
                    val longitude = sLongitude.toFloat()
                    updateRegisto(intIDCordenada1, latitude, longitude)
                    super.onContextItemSelected(item)
                }
                else -> super.onContextItemSelected(item)
            }
            return true
        }

        open fun apagaRegisto(id_coordenada: Int) {
            db!!.delete(
                Contrato.Coordenada.TABLE_NAME,
                " _ID = ?",
                arrayOf(id_coordenada.toString() + "")
            )
            refresh()
        }

        open fun updateRegisto(id_coordenada: Int, latitude: Float, longitude: Float) {
            val cv = ContentValues()
            cv.put(Contrato.Coordenada.COLUMN_LATITUDE, latitude)
            cv.put(Contrato.Coordenada.COLUMN_LONGITUDE, longitude)
            val locGPS = Location("")
            locGPS.latitude = LatitudeFixa.toDouble()
            locGPS.longitude = LongitudeFixa.toDouble()
            val locInserida = Location("")
            locInserida.latitude = latitude.toDouble()
            locInserida.longitude = longitude.toDouble()
            var distancia = locGPS.distanceTo(locInserida)
            //converter em km:
            distancia = distancia / 1000
            cv.put(Contrato.Coordenada.COLUMN_DISTANCIA, distancia)
            db!!.update(
                Contrato.Coordenada.TABLE_NAME,
                cv,
                " _ID = ?",
                arrayOf(id_coordenada.toString() + "")
            )
            refresh()
        }

        fun refresh() {
            getCursor()
            adapter?.swapCursor(c_coordenadas)
            PreencheLista()
        }

        fun getCursor() {
            val sql = "Select" + Contrato.Coordenada.TABLE_NAME.toString() + " = " +
                    Contrato.Coordenada._ID.toString() + " , " +
                    Contrato.Coordenada.COLUMN_LATITUDE.toString() + " , " +
                    Contrato.Coordenada.COLUMN_LONGITUDE.toString() + " FROM " +
                    Contrato.Coordenada.TABLE_NAME.toString() + " , "
        }


        override fun onDestroy() {
            super.onDestroy()
            if (!c_coordenadas!!.isClosed) {
                c_coordenadas!!.close()
                c_coordenadas = null
            }
            if (!db!!.isOpen) {
                db!!.close()
                db = null
            }
        }
    }
