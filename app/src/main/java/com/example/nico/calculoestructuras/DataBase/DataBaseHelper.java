package com.example.nico.calculoestructuras.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.xmlparser.Ejercicio;

import java.util.ArrayList;

/**
 * Created by Nico on 9/11/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

   /* private static String DB_PATH = "/data/data/com.example.nico.calculoestructuras/databases/";
    private static String DB_NAME = "db_calc";*/

    //Nombre tablas
    private final static String DATABASE_NAME_BARRA_TABLE = "Barras";
    private final static String DATABASE_NAME_NUDO_TABLE = "Nudos";
    private final static String DATABASE_NAME_CONECTIV_TABLE = "Conectividades";
    private final static String DATABASE_NAME_VINCULOS_TABLE = "Vinculos";
    private final static String DATABASE_NAME_CARGAENNUDO_TABLE = "CargasEnNudos";
    private final static String DATABASE_NAME_CARGAENBARRA_TABLE = "CargasEnBarras";

    //Sentencias de creaci�n
    private final static String DATABASE_CREATE_STATEMENT_NUDO="CREATE TABLE [Nudos] (" +
            "[idnudo] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[coordx] FLOAT  NOT NULL, " +
            "[coordy] FLOAT  NOT NULL)";
    private final static String DATABASE_CREATE_STATEMENT_BARRA="CREATE TABLE [Barras] (" +
            "[idbarra] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[elasticidad] FLOAT  NOT NULL," +
            "[inercia] FLOAT  NOT NULL," +
            "[area] FLOAT  NOT NULL)";
    private final static String DATABASE_CREATE_STATEMENT_VINCULO="CREATE TABLE [Vinculos] " +
            "([idvinculo] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[nudovinculado] INTEGER   NOT NULL REFERENCES nudo(idnudo)," +
            "[restx] FLOAT," +
            "[resty] FLOAT," +
            "[restgiro] FLOAT)";
    private final static String DATABASE_CREATE_STATEMENT_CONECTIVIDAD="CREATE TABLE [Conectividades] (" +
            "[idconectividad] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[barraconectada] INTEGER   NOT NULL REFERENCES barras(idbarra)," +
            "[niconec] INTEGER   NOT NULL REFERENCES nudos(idnudo), " +
            "[nfconec] INTEGER NOT NULL REFERENCES nudos (idnudo))";
    private final static String DATABASE_CREATE_STATEMENT_CARGAENNUDO="CREATE TABLE [CargasEnNudos] (" +
            "[idcargaNudo] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[nudoCargado] INTEGER   NOT NULL REFERENCES nudos(idnudo)," +
            "[cargaenx] FLOAT, " +
            "[cargaeny] FLOAT, "+
            "[cargaenz] FLOAT)";
    private final static String DATABASE_CREATE_STATEMENT_CARGASENBARRAS="CREATE TABLE [CargasEnBarras] (" +
            "[idcargabarra] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[barracargada] INTEGER   NOT NULL REFERENCES barra(idbarra)," +
            "[cargadistribuida] FLOAT, " +
            "[cargapuntualenx] FLOAT, "+
            "[cargapuntualeny] FLOAT, "+
            "[cargapuntualdistxy] FLOAT, "+
            "[cargapuntualenz] FLOAT, " +
            "[cargapuntualdistz] FLOAT)";

    //Sentencias de eliminacion
    private final static String DATABASE_NAME = "databaseMath";
    private final static String DATABASE_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME;
    private final static String TABLE_NUDOS_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_NUDO_TABLE;
    private final static String TABLE_BARRAS_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_BARRA_TABLE;
    private final static String TABLE_CONECTIV_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_CONECTIV_TABLE;
    private final static String TABLE_VINCULOS_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_VINCULOS_TABLE;
    private final static String TABLE_CARGAENNUDO_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_CARGAENNUDO_TABLE;
    private final static String TABLE_CARGAENBARRA_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_CARGAENBARRA_TABLE;

    private Context context;

    private static DataBaseHelper databaseInstance;

    public static DataBaseHelper getDatabaseInstance(Context context)
    {
        if(databaseInstance == null)
        {
            databaseInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return databaseInstance;
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    private DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE_STATEMENT_NUDO);
        db.execSQL(DATABASE_CREATE_STATEMENT_BARRA);
        db.execSQL(DATABASE_CREATE_STATEMENT_CONECTIVIDAD);
        db.execSQL(DATABASE_CREATE_STATEMENT_VINCULO);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGAENNUDO);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGASENBARRAS);
    }


    private void crearTablaNudo()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_NUDO);
    }

    private void crearTablaBarra()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_BARRA);
    }

    private void crearTablaConec()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_CONECTIVIDAD);
    }
    private void crearTablaVinculo()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_VINCULO);
    }
    private void crearTablaCargaEnNudo()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_CARGAENNUDO);
    }
    private void crearTablaCargaEnBarra()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_CARGASENBARRAS);
    }

    private void borrarTablaNudo()
    {
        databaseInstance.getWritableDatabase().execSQL(TABLE_NUDOS_DROP_STATEMENT);
    }

    private void borrarTablaBarra()
    {
        databaseInstance.getWritableDatabase().execSQL(TABLE_BARRAS_DROP_STATEMENT);
    }

    private void borrarTablaConec()
    {
        databaseInstance.getWritableDatabase().execSQL(TABLE_CONECTIV_DROP_STATEMENT);
    }

    private void borrarTablaViculo(){
        databaseInstance.getWritableDatabase().execSQL(TABLE_VINCULOS_DROP_STATEMENT);
    }
    private void borrarTablaCargaEnNudo(){
        databaseInstance.getWritableDatabase().execSQL(TABLE_CARGAENNUDO_DROP_STATEMENT);
    }
    private void borrarTablaCargaEnBarra(){
        databaseInstance.getWritableDatabase().execSQL(TABLE_CARGAENBARRA_DROP_STATEMENT);
    }

    public void inicializarBD()
    {
        this.borrarTablaConec();
        this.borrarTablaNudo();
        this.borrarTablaBarra();
        this.borrarTablaViculo();
        this.borrarTablaCargaEnBarra();
        this.borrarTablaCargaEnNudo();
        this.crearTablaNudo();
        this.crearTablaBarra();
        this.crearTablaConec();
        this.crearTablaVinculo();
        this.crearTablaCargaEnBarra();
        this.crearTablaCargaEnNudo();

    }

    public void ejercicioToDataBase(Ejercicio ejercicio){

        //Carga de nudos en base de datos
        for (Nudo n:ejercicio.nudos) {
            ContentValues values = new ContentValues();
            values.put("coordx", n.getX());
            values.put("coordy", n.getY());
            databaseInstance.getWritableDatabase().insert(DATABASE_NAME_NUDO_TABLE, null, values);
        }

        //Carga de barras en base de datos
        for (Barra b:ejercicio.barras) {
            ContentValues values = new ContentValues();
            values.put("elasticidad", b.getElasticidad());
            values.put("inercia", b.getInercia());
            values.put("area", b.getArea());
            databaseInstance.getWritableDatabase().insert(DATABASE_NAME_BARRA_TABLE, null, values);
        }

        //Carga de conectividades en base de datos
        for (Conectividad c:ejercicio.conectividades){
            ContentValues values = new ContentValues();
            values.put("barraconectada", c.getNumBarra());
            values.put("niconec", c.getNumNudoInicial());
            values.put("nfconec", c.getNumNudoFinal());
            databaseInstance.getWritableDatabase().insert(DATABASE_NAME_CONECTIV_TABLE, null, values);
        }

        //Carga de vinculos en base de datos
        for (Vinculo v:ejercicio.vinculos) {
            ContentValues values = new ContentValues();
            values.put("nudovinculado", v.getNumNudo());
            values.put("restx", v.getRestX());
            values.put("resty", v.getRestY());
            values.put("restgiro", v.getRestGiro());
            databaseInstance.getWritableDatabase().insert(DATABASE_NAME_VINCULOS_TABLE, null, values);
        }

        //Carga de cargas en nudos en base de datos
        for (CargaEnNudo c:ejercicio.cargaNudo) {
            ContentValues values = new ContentValues();
            values.put("nudoCargado", c.getNumNudo());
            values.put("cargaenx", c.getCargaEnX());
            values.put("cargaeny", c.getCargaEnY());
            values.put("cargaenz", c.getCargaEnZ());
            databaseInstance.getWritableDatabase().insert(DATABASE_NAME_CARGAENNUDO_TABLE, null, values);
        }

        //Carga de cargas en barras en base de datos
        for (CargaEnBarra c:ejercicio.cargaBarra) {
            ContentValues values = new ContentValues();
            values.put("barracargada", c.getNumBarra());
            values.put("cargadistribuida", c.getCargaDistribuida());
            values.put("cargapuntualenx", c.getCargaPuntualEnX());
            values.put("cargapuntualeny", c.getCargaPuntualEnY());
            values.put("cargapuntualenz", c.getCargaPuntualEnZ());
            values.put("cargapuntualdistxy", c.getCargaPuntualDistXY());
            values.put("cargapuntualdistz", c.getCargaPuntualDistZ());
            databaseInstance.getWritableDatabase().insert(DATABASE_NAME_CARGAENBARRA_TABLE, null, values);
        }

    }

    public Ejercicio dataBaseToEjercicio() {
        return new Ejercicio(
                getNudosFromDB(),
                getBarrasFromDB(),
                getConecFromDB(),
                getVinculoFromDB(),
                getCargaEnNudoFromDB(),
                getCargaEnBarraFromDB()
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DATABASE_DROP_STATEMENT);
        db.execSQL(TABLE_BARRAS_DROP_STATEMENT);
        db.execSQL(TABLE_NUDOS_DROP_STATEMENT);
        db.execSQL(TABLE_CONECTIV_DROP_STATEMENT);
        db.execSQL(TABLE_VINCULOS_DROP_STATEMENT);
        db.execSQL(TABLE_CARGAENBARRA_DROP_STATEMENT);
        db.execSQL(TABLE_CARGAENNUDO_DROP_STATEMENT);
        db.execSQL(DATABASE_CREATE_STATEMENT_BARRA);
        db.execSQL(DATABASE_CREATE_STATEMENT_NUDO);
        db.execSQL(DATABASE_CREATE_STATEMENT_VINCULO);
        db.execSQL(DATABASE_CREATE_STATEMENT_CONECTIVIDAD);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGASENBARRAS);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGAENNUDO);
    }

    public ArrayList<Barra> getBarrasFromDB()
    {
        Cursor cursor = null;
        ArrayList<Barra> array = new ArrayList<>();
        cursor = getReadableDatabase().query(DATABASE_NAME_BARRA_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Barra b = new Barra(cursor.getInt(cursor.getColumnIndex("idbarra")),cursor.getDouble(cursor.getColumnIndex("elasticidad")),cursor.getDouble(cursor.getColumnIndex("area")),cursor.getDouble(cursor.getColumnIndex("inercia")));
            array.add(b);
        }
        cursor.close();
        this.close();
        return array;
    }
    public ArrayList<Nudo> getNudosFromDB()
    {
        ArrayList<Nudo> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_NUDO_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Nudo n = new Nudo(cursor.getInt(cursor.getColumnIndex("idnudo")), cursor.getDouble(cursor.getColumnIndex("coordx")),cursor.getDouble(cursor.getColumnIndex("coordy")));
            array.add(n);
        }
        cursor.close();
        this.close();
        return array;
    }
    public ArrayList<Conectividad> getConecFromDB()
    {
        ArrayList<Conectividad> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_CONECTIV_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Conectividad c = new Conectividad(cursor.getInt(cursor.getColumnIndex("barraconectada")),cursor.getInt(cursor.getColumnIndex("niconec")), cursor.getInt(cursor.getColumnIndex("nfconec")));
            array.add(c);
        }
        cursor.close();
        this.close();
        return array;
    }

    public ArrayList<Vinculo> getVinculoFromDB(){
        ArrayList<Vinculo> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_VINCULOS_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {

            Vinculo v = new Vinculo( cursor.getInt(cursor.getColumnIndex("nudovinculado")));
            if(cursor.isNull(cursor.getColumnIndex("restx")))
                v.setRestX(Double.NaN);
            else{
                v.setRestX(cursor.getDouble(cursor.getColumnIndex("restx")));
            }
            if(cursor.isNull(cursor.getColumnIndex("resty")))
                v.setRestY(Double.NaN);
            else{
                v.setRestY(cursor.getDouble(cursor.getColumnIndex("resty")));
            }
            if(cursor.isNull(cursor.getColumnIndex("restgiro")))
                v.setRestGiro(Double.NaN);
            else{
                v.setRestGiro(cursor.getDouble(cursor.getColumnIndex("restgiro")));
            }
//            if(cursor.getDouble(cursor.getColumnIndex("restx"))!=0){
//                v.setRestX(cursor.getDouble(cursor.getColumnIndex("restx")));
//            }
//            if(cursor.getDouble(cursor.getColumnIndex("resty"))!=0){
//                v.setRestY(cursor.getDouble(cursor.getColumnIndex("resty")));
//            }
//            if(cursor.getDouble(cursor.getColumnIndex("restgiro"))!=0){
//                v.setRestGiro(cursor.getDouble(cursor.getColumnIndex("restgiro")));
//            }
            array.add(v);
        }
        cursor.close();
        this.close();
        return array;
    }

    public ArrayList<CargaEnBarra> getCargaEnBarraFromDB(){
        ArrayList<CargaEnBarra> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_CARGAENBARRA_TABLE, null, null, null, null, null, null, null);
        while(cursor.moveToNext()) {

            CargaEnBarra v = new CargaEnBarra( cursor.getInt(cursor.getColumnIndex("barracargada")));
            if(cursor.getDouble(cursor.getColumnIndex("cargadistribuida"))!= 0){
                v.setCargaDistribuida(cursor.getDouble(cursor.getColumnIndex("cargadistribuida")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualenx"))!=0){
                v.setCargaPuntualEnX(cursor.getDouble(cursor.getColumnIndex("cargapuntualenx")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualeny"))!=0){
                v.setCargaPuntualEnY(cursor.getDouble(cursor.getColumnIndex("cargapuntualeny")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualdistxy"))!=0){
                v.setCargaPuntualDistXY(cursor.getDouble(cursor.getColumnIndex("cargapuntualdistxy")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualenz")) !=0){
                v.setCargaPuntualEnZ(cursor.getDouble(cursor.getColumnIndex("cargapuntualenz")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualdistz")) !=0){
                v.setCargaPuntualDistZ(cursor.getDouble(cursor.getColumnIndex("cargapuntualdistz")));
            }
            array.add(v);
        }
        cursor.close();
        this.close();
        return array;
    }
    public ArrayList<CargaEnNudo> getCargaEnNudoFromDB(){
        ArrayList<CargaEnNudo> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_CARGAENNUDO_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {

            CargaEnNudo v = new CargaEnNudo( cursor.getInt(cursor.getColumnIndex("nudoCargado")));
            if(cursor.getDouble(cursor.getColumnIndex("cargaenx")) != 0) {
                v.setCargaEnX(cursor.getDouble(cursor.getColumnIndex("cargaenx")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargaeny")) != 0) {
                v.setCargaEnY(cursor.getDouble(cursor.getColumnIndex("cargaeny")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargaenz")) != 0) {
                v.setCargaEnZ(cursor.getDouble(cursor.getColumnIndex("cargaenz")));
            }
            array.add(v);
        }
        cursor.close();
        this.close();
        return array;
    }
    public void insertBarra(ContentValues values)
    {
        getWritableDatabase().insert(DATABASE_NAME_BARRA_TABLE, null, values);
        this.close();
    }
    public void insertNudo(ContentValues values)
    {
        getWritableDatabase().insert(DATABASE_NAME_NUDO_TABLE, null, values);
        this.close();
    }

    public void updateNudo(ContentValues values, int nroNudo) {
        getWritableDatabase().update(DATABASE_NAME_NUDO_TABLE, values, "idnudo = ?", new String[]{String.valueOf(nroNudo)});
    }
    public void insertConec(ContentValues values)
    {
        getWritableDatabase().insert(DATABASE_NAME_CONECTIV_TABLE, null, values);
        this.close();
    }

    public void updateBarra(ContentValues values, int nroBarra) {
        getWritableDatabase().update(DATABASE_NAME_BARRA_TABLE, values, "idbarra = ?", new String[]{String.valueOf(nroBarra)});
    }

    public void updateConec(ContentValues values, int nroConec){
        getWritableDatabase().update(DATABASE_NAME_CONECTIV_TABLE, values,"idconectividad = ?", new String[]{String.valueOf(nroConec)} );
    }

    public void updateVinc(ContentValues values, int nroVinc){
        getWritableDatabase().update(DATABASE_NAME_VINCULOS_TABLE, values, "idvinculo = ?", new String[]{String.valueOf(nroVinc)});
    }

    public void updateCargaNudo(ContentValues values, int nroCarga){
        getWritableDatabase().update(DATABASE_NAME_CARGAENNUDO_TABLE, values, "nudoCargado = ?", new String[]{String.valueOf(nroCarga)});
    }

    public void updateCargaBarra(ContentValues values, int nroCarga){
        getWritableDatabase().update(DATABASE_NAME_CARGAENBARRA_TABLE, values, "barracargada = ?", new String[]{String.valueOf(nroCarga)});
    }

    public void insertVinculo(ContentValues values){
        getWritableDatabase().insert(DATABASE_NAME_VINCULOS_TABLE, null, values);
        this.close();
    }
    public void insertCargaEnBarra(ContentValues values){
        getWritableDatabase().insert(DATABASE_NAME_CARGAENBARRA_TABLE,null,values);
        this.close();
    }
    public void insertCargaEnNudo(ContentValues values){
        getWritableDatabase().insert(DATABASE_NAME_CARGAENNUDO_TABLE,null,values);
        this.close();
    }

    public void getNudos() {
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_NUDO_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Log.d("NUDO: ", " x: " + cursor.getDouble(cursor.getColumnIndex("coordx")) + " y: " + cursor.getDouble(cursor.getColumnIndex("coordy")));
        }
        cursor.close();
        this.close();
    }


    public void getBarras() {
        Cursor cursor = null;

        cursor = getReadableDatabase().query(DATABASE_NAME_BARRA_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Log.d("BARRA: ", "id: " + cursor.getInt(cursor.getColumnIndex("idbarra")) + " i: " + cursor.getDouble(cursor.getColumnIndex("inercia")) + " e: " + cursor.getDouble(cursor.getColumnIndex("elasticidad")) + " a: " + cursor.getDouble(cursor.getColumnIndex("area")));
        }
        cursor.close();
        this.close();
    }

    public Barra findBarraFromDB(int idbarra) {
        Cursor cursor = null;
        Barra b = new Barra(1,1.1,1.1,1.1);
        String [] args = new String[1];
        args[0] = " " + idbarra;
        cursor =getReadableDatabase().rawQuery("SELECT * FROM " + DATABASE_NAME_BARRA_TABLE + " WHERE idbarra=?", args);
        while(cursor.moveToNext()) {
            b=new Barra(cursor.getInt(cursor.getColumnIndex("idbarra")),cursor.getDouble(cursor.getColumnIndex("elasticidad")),cursor.getDouble(cursor.getColumnIndex("area")),cursor.getDouble(cursor.getColumnIndex("inercia")));

        }
        cursor.close();
        this.close();
        return b;
    }
    public int findLastIDBarraInserted() {
        Cursor cursor = null;
        int index=0;
        Barra b = new Barra(1,1.1,1.1,1.1);
        cursor =getReadableDatabase().rawQuery("SELECT MAX(idbarra) FROM " + DATABASE_NAME_BARRA_TABLE,null);
        while(cursor.moveToNext()) {

            index = cursor.getInt(0);
        }
        cursor.close();
        this.close();
        return index;
    }




    public void getConec() {
        Cursor cursor = null;

        cursor = getReadableDatabase().query(DATABASE_NAME_CONECTIV_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            Log.d("CONECT: ", "id: " + cursor.getInt(cursor.getColumnIndex("idconectividad")) + " B: " + cursor.getInt(cursor.getColumnIndex("barraconectada")) + " NI: " + cursor.getInt(cursor.getColumnIndex("niconec")) + " NF: " + cursor.getInt(cursor.getColumnIndex("nfconec")));
        }
        cursor.close();
        this.close();
    }


}
