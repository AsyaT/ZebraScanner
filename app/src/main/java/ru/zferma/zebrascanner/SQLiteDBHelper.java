package ru.zferma.zebrascanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Barcodes.db";
    public static final String DATABAR_TABLE_NAME = "databars";
    public static final String DATABAR_COLUMN_ID = "id";
    public static final String DATABAR_GTIN = "gtin";
    public static final String DATABAR_WEIGHT = "weight";
    public static final String DATABAR_LOT_NUMBER = "lotnumber";
    public static final String DATABAR_PRODUCTION_DATE = "productiondate";
    public static final String DATABAR_EXPIRATION_DATE = "expirationdate";
    public static final String DATABAR_SERIAL_NUMBER = "serialnumber";
    public static final String DATABAR_INTERNAL_PRODUCER = "producer";
    public static final String DATABAR_INTERNAL_EQUIPMENT = "equipment";


    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABAR_TABLE_NAME + " (" +
                DATABAR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DATABAR_GTIN + " TEXT, " +
                DATABAR_WEIGHT + " REAL, " +
                DATABAR_LOT_NUMBER + " TEXT, " +
                DATABAR_PRODUCTION_DATE + " NUMERIC, " +
                DATABAR_EXPIRATION_DATE + " NUMERIC, " +
                DATABAR_SERIAL_NUMBER + " TEXT, " +
                DATABAR_INTERNAL_PRODUCER + " INTEGER, " +
                DATABAR_INTERNAL_EQUIPMENT + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABAR_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertDatabar (String gtin,
                                  String netWeight,
                                  String lotNumber,
                                  Date productionDate,
                                  Date expirationDate,
                                  String serialNumber,
                                  Byte internalProducer,
                                  Short internalEquipment)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABAR_GTIN, gtin);
        contentValues.put(DATABAR_WEIGHT, netWeight);
        contentValues.put(DATABAR_LOT_NUMBER, lotNumber);
        contentValues.put(DATABAR_PRODUCTION_DATE, String.valueOf(productionDate));
        contentValues.put(DATABAR_EXPIRATION_DATE, String.valueOf(expirationDate));
        contentValues.put(DATABAR_SERIAL_NUMBER, serialNumber);
        contentValues.put(DATABAR_INTERNAL_PRODUCER, internalProducer);
        contentValues.put(DATABAR_INTERNAL_EQUIPMENT, internalEquipment);
        db.insert(DATABAR_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public String getData(int id) {
        String s1="null";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + DATABAR_TABLE_NAME + " where " + DATABAR_COLUMN_ID + "=" + id + "", null );
        if (res.moveToFirst())
        {
            do
            {
                s1 = res.getString(res.getColumnIndex(DATABAR_GTIN));

            }while (res.moveToNext());
        }

        return s1;
    }
}
