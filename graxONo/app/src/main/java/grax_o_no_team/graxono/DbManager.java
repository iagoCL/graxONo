package grax_o_no_team.graxono;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.lang.String;
import java.lang.*;
import java.util.Random;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */

public class DbManager {
    // alter table videogames add column asset string;


    public static final String TABLE_VIDEOS_NAME = "videos";
    public static final String CN__VIDEO_ID = "_id";
    public static final String CN_TITLE = "title";
    public static final String CN_URL = "URL";
    public static final String CN_VIDEO_TOTAL = "totalgeneral";
    public static final String CN_VIDEO_POSITIVES = "generalpositves";
    public static final String CN_ASSET = "image";

    public static final String TABLE_USERS_NAME = "users";
    public static final String CN__USER_ID = "_userid";
    public static final String CN_NAME = "name";
    public static final String CN_PASS = "pass";

    public static final String TABLE_VIDEOUSERS_NAME = "uservideos";
    public static final String CN_USER_TOTAL = "usertotal";
    public static final String CN_USER_POSITIVES = "userpositives";


    // create table videogames(_id integer primary key autoincrement, name string not null, year string, genre string);
    public static final String CREATE_TABLE_VIDEO = " create table " + TABLE_VIDEOS_NAME + " ("
            + CN__VIDEO_ID + " integer primary key autoincrement, "
            + CN_TITLE + " string unique not null, "
            + CN_URL + " string not null, "
            + CN_VIDEO_TOTAL + " integer, "
            + CN_VIDEO_POSITIVES + " integer, "
            + CN_ASSET + " integer);";

    public static final String CREATE_TABLE_USERS = " create table " + TABLE_USERS_NAME + " ("
            + CN__USER_ID + " integer primary key autoincrement, "
            + CN_NAME + " string unique not null, "
            + CN_PASS + " string);";

    public static final String CREATE_TABLE_VIDEOUSERS = " create table " + TABLE_VIDEOUSERS_NAME + " ("
            + CN__USER_ID + " integer, "
            + CN__VIDEO_ID + " integer, "
            + CN_USER_TOTAL + " integer, "
            + CN_USER_POSITIVES + " integer,"
            +"PRIMARY KEY ("+CN__USER_ID+","+CN__VIDEO_ID+"));";

    public static final String CONSULTA_USER = "SELECT "+TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID+", "
            +CN_TITLE+", "+CN_USER_POSITIVES+", "+CN_VIDEO_POSITIVES+", "
            +CN_USER_TOTAL+", "+CN_VIDEO_TOTAL+", "+ CN_ASSET +", "+ CN_URL
            +" FROM "+TABLE_VIDEOUSERS_NAME+", "+TABLE_VIDEOS_NAME
            +" WHERE "+TABLE_VIDEOS_NAME+"."+CN__VIDEO_ID+" = "+ TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID
            +" AND "+TABLE_VIDEOUSERS_NAME+"."+CN__USER_ID+"=?"
            +" GROUP BY "+TABLE_VIDEOUSERS_NAME+"."+CN_USER_TOTAL;

    public static final String CONSULTA_USER_TITLE = "SELECT "+TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID+", "
            +CN_TITLE+", "+CN_USER_POSITIVES+", "+CN_VIDEO_POSITIVES+", "
            +CN_USER_TOTAL+", "+CN_VIDEO_TOTAL+", "+ CN_ASSET+", "+ CN_URL
            +" FROM "+TABLE_VIDEOUSERS_NAME+", "+TABLE_VIDEOS_NAME
            +" WHERE "+TABLE_VIDEOS_NAME+"."+CN__VIDEO_ID+" = "+ TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID
            +" AND "+TABLE_VIDEOUSERS_NAME+"."+CN__USER_ID+"=?"
            +" AND "+TABLE_VIDEOS_NAME+"."+CN_TITLE+" LIKE '%";

    public static final String CONSULTA_USER_TITLE2 = "%' GROUP BY "+TABLE_VIDEOUSERS_NAME+"."+CN_USER_TOTAL;//el ? no funcionaba



    private DbHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public int addVideo(String title, String URL, Integer asset) {
        return (int) db.insert(TABLE_VIDEOS_NAME, null, generateVideoContentValues(title,URL,0,0,asset));
    }

    public void removeVideo(String name) {
        db.delete(TABLE_VIDEOS_NAME, CN_TITLE + "=?", new String[]{name});
    }

    public void modifyVideo(String title, String URL,Integer total, Integer positives, Integer asset) {
        db.update(TABLE_VIDEOS_NAME, generateVideoContentValues(title, URL, total, positives,asset), CN_TITLE + "=?", new String[]{title});
    }

    public ContentValues generateVideoContentValues(String title, String URL,Integer total, Integer positives, Integer asset) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_TITLE, title);
        contentValues.put(CN_URL, URL);
        contentValues.put(CN_VIDEO_TOTAL, total);
        contentValues.put(CN_VIDEO_POSITIVES, positives);
        contentValues.put(CN_ASSET, asset);
        return contentValues;
    }

    public ContentValues generateUserContentValues(String name, String contra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_NAME, name);
        contentValues.put(CN_PASS, contra);
        return contentValues;
    }

    public ContentValues generateUserVideoContentValues(Integer id_video, Integer id_user,Integer total_user, Integer positives_user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN__USER_ID, id_user);
        contentValues.put(CN__VIDEO_ID, id_video);
        contentValues.put(CN_USER_TOTAL, total_user);
        contentValues.put(CN_USER_POSITIVES, positives_user);
        return contentValues;
    }

    public void exampleDBFill(){
        addUser("Guest","");
        addUser("Iago","1234");
        addUser("Alvaro","56789");
        addUser("Ana","qwerty");
        addUser("Fran","xxx");

        addVideo("Ants eating something?","https://github.com/iagoCL/graxONo/blob/master/videos/ants.mp4?raw=true",  R.raw.ants);
        addVideo("I want to fly","https://github.com/iagoCL/graxONo/blob/master/videos/bird.mp4?raw=true",  R.raw.bird);
        addVideo("Calidoscope","https://github.com/iagoCL/graxONo/blob/master/videos/caleidoscope.mp4?raw=true",  R.raw.caleidoscope);
        addVideo("Cococoocococo","https://github.com/iagoCL/graxONo/blob/master/videos/chicken.mp4?raw=true",  R.raw.chicken);
        addVideo("Magic circles","https://github.com/iagoCL/graxONo/blob/master/videos/circles.mp4?raw=true",  R.raw.circle);
        addVideo("Explosiooooon!!!!!!","https://github.com/iagoCL/graxONo/blob/master/videos/explosion.mp4?raw=true",  R.raw.explosion);
        addVideo("Beautiful sea","https://github.com/iagoCL/graxONo/blob/master/videos/sea.mp4?raw=true",  R.raw.sea);
        addVideo("Relax in a waterfall","https://github.com/iagoCL/graxONo/blob/master/videos/waterfall.mp4?raw=true",  R.raw.waterfall);

        Random rn = new Random();
        for(int i=0,numVideos=8,numUsers = 5; i<400;i++) {
            addAnswer(rn.nextInt(numVideos) + 1, rn.nextInt(numUsers) + 1, rn.nextBoolean());
        }
    }

    public Cursor randomVideo(){
        String[] columns = new String[]{CN__VIDEO_ID, CN_TITLE,CN_URL, CN_VIDEO_TOTAL, CN_VIDEO_POSITIVES};
        return db.query(TABLE_VIDEOS_NAME, columns,null, null, null, null, "RANDOM()", "1");
    }


    public Cursor searchVideo(Integer id_video) {
        String[] columns = new String[]{CN__VIDEO_ID, CN_TITLE,CN_URL, CN_VIDEO_TOTAL, CN_VIDEO_POSITIVES,CN_ASSET};
        return db.query(TABLE_VIDEOS_NAME, columns, CN__VIDEO_ID + "= ?", new String[]{id_video.toString()}, null, null, null);
    }

    public Cursor searchVideoByTitle(String title_video) {
        String[] columns = new String[]{CN__VIDEO_ID, CN_TITLE,CN_URL, CN_VIDEO_TOTAL, CN_VIDEO_POSITIVES,CN_ASSET};
        return db.query(TABLE_VIDEOS_NAME, columns, CN_TITLE + "= ?", new String[]{title_video}, null, null, null);
    }

    public void addAnswer(Integer id_video,Integer id_user,boolean answer){
        Cursor cursorVideo=searchVideo(id_video);
        int suma;
        if(answer) {
            suma = 1;
        }
        else {
            suma = 0;
        }
        if (cursorVideo.moveToFirst()){//Check if the video exist
            modifyVideo(cursorVideo.getString(1),cursorVideo.getString(2),cursorVideo.getInt(3)+1,cursorVideo.getInt(4)+suma,cursorVideo.getInt(5));
            Cursor cursorVideoUser = dataVideoUser(id_video,id_user);
            if(cursorVideoUser.moveToFirst() == false){
                addVideoUser(id_video,id_user);//If does not exist we create it
                modifyVideoUser(id_video,id_user,1,suma);
            }
            else{
                modifyVideoUser(id_video,id_user,cursorVideoUser.getInt(2)+1,cursorVideoUser.getInt(3)+suma);
            }

        }
    }

    public int addUser(String name,String contra){
        return (int) db.insert(TABLE_USERS_NAME, null, generateUserContentValues(name,contra));//devuelve el id del new user de tipo long como int
    }

    public void removeUser(String name){
        db.delete(TABLE_USERS_NAME, CN_NAME + "=?", new String[]{name});
    }

    public int accessUser(String name,String contra){
        Cursor cursor = searchUser(name);
        if (cursor.moveToFirst() == false){
            return -2;//el user no existe
        }else {
            String contraAux = cursor.getString(2);
            if(contraAux.matches(contra)){
                return cursor.getInt(0);//devuelve su ID
            }
            else{
                return -1;//la password es incorrecta
            }
        }
    }

    public Cursor searchUser(String name){
        String[] columns = new String[]{CN__USER_ID,CN_NAME,CN_PASS};
        return db.query(TABLE_USERS_NAME, columns, CN_NAME + "=?", new String[]{name}, null, null, null);
    }

    public Cursor searchUserById(Integer id){
        String[] columns = new String[]{CN_NAME};
        return db.query(TABLE_USERS_NAME, columns, CN__USER_ID + "=?", new String[]{id.toString()}, null, null, null);
    }

    public void addVideoUser(Integer id_video, Integer id_user){
        db.insert(TABLE_VIDEOUSERS_NAME, null, generateUserVideoContentValues(id_video,id_user,0,0));
    }

    public void modifyVideoUser(Integer id_video, Integer id_user, Integer total_user, Integer positives_user){
        db.update(TABLE_VIDEOUSERS_NAME, generateUserVideoContentValues(id_video, id_user, total_user, positives_user), CN__VIDEO_ID + "=? AND "+CN__USER_ID+"=?", new String[]{id_video.toString(),id_user.toString()});
    }

    public Cursor dataVideoUser(Integer id_video, Integer id_user){
        String[] columns = new String[]{CN__VIDEO_ID, CN__USER_ID,CN_USER_TOTAL,CN_USER_POSITIVES};
        return db.query(TABLE_VIDEOUSERS_NAME, columns, CN__VIDEO_ID + "=? AND "+CN__USER_ID+"=?", new String[]{id_video.toString(),id_user.toString()}, null, null, null);
    }



    //para History
    public Cursor searchUserVideos(Integer id_user) {
        return db.rawQuery(CONSULTA_USER,new String[]{id_user.toString()});
    }

    public Cursor searchUserVideosTitle(Integer id_user, String title) {
        return db.rawQuery(CONSULTA_USER_TITLE+title+CONSULTA_USER_TITLE2,new String[]{id_user.toString()});
    }





}