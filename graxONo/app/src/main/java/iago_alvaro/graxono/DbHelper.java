package iago_alvaro.graxono;


import  android.database.sqlite.*;
import  android.content.*;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */


public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "base_gracioso_o_no";
    private static final int DATABASE_SCHEME_VERSION = 2;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_SCHEME_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DbManager.CREATE_TABLE_USERS);
        db.execSQL(DbManager.CREATE_TABLE_VIDEO);
        db.execSQL(DbManager.CREATE_TABLE_VIDEOUSERS);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2){}
            //db.execSQL(DbManager.UPDATE_TABLE_V2);
        /*if (oldVersion < 3)
            // ACTUALIZAR A VERSIÓN 3
        if (oldVersion < 4)
            // ACTUALIZAR A VERSIÓN 4*/
    }

    public static void DB_erase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public static void BD_backup() throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        String pathData = "//data//"+BuildConfig.APPLICATION_ID+"//databases//"+DATABASE_NAME+"";
        String backupDBPath = "backupdatabase.db";
        File currentDB = new File(data, pathData );
        File backupDB = new File(sd, backupDBPath);
        if (currentDB.exists()) {
            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        }
    }
}
