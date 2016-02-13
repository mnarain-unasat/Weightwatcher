package sr.unasat.weightwatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by mnarain on 2/13/2016.
 */
public class WeightDAO extends SQLiteOpenHelper {
    private static final String DB_NAME = "weightWatcher.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_WI_INFO_NAME = "weight_watcher";
    private static final String TABLE_ID = "id";
    private static final String TABLE_WI_INFO_WEIGHT = "weight";
    private static final String TABLE_WI_INFO_DATE = "date";

    private static final String CREATE_TABLE_WI_INFO = String.format(
            "create table %s(%s INT PRIMARY KEY, %s VARCHAR(255) NOT NUL , %s VARCHAR(255) NOT NULL UNIQUE);",
            TABLE_WI_INFO_NAME, TABLE_ID, TABLE_WI_INFO_WEIGHT, TABLE_WI_INFO_DATE);

    public WeightDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WI_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public long insertOneRecord(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(tableName, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

/*    public Cursor findRecordsRAW(String weight, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String sql = String.format("select * from %s where %s = ? OR %s = ?", TABLE_WI_INFO_NAME, TABLE_WI_INFO_WEIGHT, TABLE_WI_INFO_DATE);
        String[] whereArgs =  {weight, date};
        cursor = db.rawQuery(sql, whereArgs);
        return cursor;
    }*/

    public Cursor findRecords(String weight, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ? OR %s = ?", TABLE_WI_INFO_WEIGHT, TABLE_WI_INFO_DATE);
        String[] whereArgs = {weight, date};
        cursor = db.query(TABLE_WI_INFO_NAME, null, whereClause, whereArgs ,null, null, null);
        return cursor;
    }

    public int updateRecord(ContentValues contentValues, String weight, String date) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ? OR %s = ?", TABLE_WI_INFO_WEIGHT, TABLE_WI_INFO_DATE);
        String[] whereArgs = {weight, date};
        effectedRows = db.update(TABLE_WI_INFO_NAME, contentValues, whereClause, whereArgs);
        return effectedRows;
    }


    public int deleteRecord(String weight, String date) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ? OR %s = ?", TABLE_WI_INFO_WEIGHT, TABLE_WI_INFO_DATE);
        String[] whereArgs = {weight, date};
        effectedRows = db.delete(TABLE_WI_INFO_NAME, whereClause, whereArgs);
        return effectedRows;
    }

}
