package iago_alvaro.graxono;

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
    public static final String CN_TITULO = "titulo";
    public static final String CN_URL = "URL";
    public static final String CN_VIDEO_TOTAL = "totalgeneral";
    public static final String CN_VIDEO_POSITIVOS = "positivosgeneral";
    public static final String CN_ASSET = "imagen";

    public static final String TABLE_USERS_NAME = "usuarios";
    public static final String CN__USER_ID = "_userid";
    public static final String CN_NOMBRE = "nombre";
    public static final String CN_PASS = "pass";

    public static final String TABLE_VIDEOUSERS_NAME = "videosusuario";
    //public static final String CN__USER_ID = "_userid";
    //public static final String CN__VIDEO_ID = "_videoid";
    public static final String CN_USUARIO_TOTAL = "totalusuario";
    public static final String CN_USUARIO_POSITIVOS = "positivosusuario";


    // create table videogames(_id integer primary key autoincrement, name string not null, year string, genre string);
    public static final String CREATE_TABLE_VIDEO = " create table " + TABLE_VIDEOS_NAME + " ("
            + CN__VIDEO_ID + " integer primary key autoincrement, "
            + CN_TITULO + " string unique not null, "
            + CN_URL + " string not null, "
            + CN_VIDEO_TOTAL + " integer, "
            + CN_VIDEO_POSITIVOS + " integer, "
            + CN_ASSET + " integer);";

    public static final String CREATE_TABLE_USERS = " create table " + TABLE_USERS_NAME + " ("
            + CN__USER_ID + " integer primary key autoincrement, "
            + CN_NOMBRE + " string unique not null, "
            + CN_PASS + " string);";

    public static final String CREATE_TABLE_VIDEOUSERS = " create table " + TABLE_VIDEOUSERS_NAME + " ("
            + CN__USER_ID + " integer, "
            + CN__VIDEO_ID + " integer, "
            + CN_USUARIO_TOTAL + " integer, "
            + CN_USUARIO_POSITIVOS + " integer,"
            +"PRIMARY KEY ("+CN__USER_ID+","+CN__VIDEO_ID+"));";

    public static final String CONSULTA_USUARIO = "SELECT "+TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID+", "
            +CN_TITULO+", "+CN_USUARIO_POSITIVOS+", "+CN_VIDEO_POSITIVOS+", "
            +CN_USUARIO_TOTAL+", "+CN_VIDEO_TOTAL+", "+ CN_ASSET +", "+ CN_URL
            +" FROM "+TABLE_VIDEOUSERS_NAME+", "+TABLE_VIDEOS_NAME
            +" WHERE "+TABLE_VIDEOS_NAME+"."+CN__VIDEO_ID+" = "+ TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID
            +" AND "+TABLE_VIDEOUSERS_NAME+"."+CN__USER_ID+"=?"
            +" GROUP BY "+TABLE_VIDEOUSERS_NAME+"."+CN_USUARIO_TOTAL;

    public static final String CONSULTA_USUARIO_TITULO = "SELECT "+TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID+", "
            +CN_TITULO+", "+CN_USUARIO_POSITIVOS+", "+CN_VIDEO_POSITIVOS+", "
            +CN_USUARIO_TOTAL+", "+CN_VIDEO_TOTAL+", "+ CN_ASSET+", "+ CN_URL
            +" FROM "+TABLE_VIDEOUSERS_NAME+", "+TABLE_VIDEOS_NAME
            +" WHERE "+TABLE_VIDEOS_NAME+"."+CN__VIDEO_ID+" = "+ TABLE_VIDEOUSERS_NAME+"."+CN__VIDEO_ID
            +" AND "+TABLE_VIDEOUSERS_NAME+"."+CN__USER_ID+"=?"
            +" AND "+TABLE_VIDEOS_NAME+"."+CN_TITULO+" LIKE '%";

    public static final String CONSULTA_USUARIO_TITULO2 = "%' GROUP BY "+TABLE_VIDEOUSERS_NAME+"."+CN_USUARIO_TOTAL;//el ? no funcionaba



    private DbHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public int insertarVideo(String titulo, String URL, Integer asset) {
        return (int) db.insert(TABLE_VIDEOS_NAME, null, generarContentValuesVideo(titulo,URL,0,0,asset));
    }

    public void eliminarVideo(String name) {
        db.delete(TABLE_VIDEOS_NAME, CN_TITULO + "=?", new String[]{name});
    }

    public void modificarVideo(String titulo, String URL,Integer total, Integer positivos, Integer asset) {
        db.update(TABLE_VIDEOS_NAME, generarContentValuesVideo(titulo, URL, total, positivos,asset), CN_TITULO + "=?", new String[]{titulo});
    }

    public ContentValues generarContentValuesVideo(String titulo, String URL,Integer total, Integer positivos, Integer asset) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_TITULO, titulo);
        contentValues.put(CN_URL, URL);
        contentValues.put(CN_VIDEO_TOTAL, total);
        contentValues.put(CN_VIDEO_POSITIVOS, positivos);
        contentValues.put(CN_ASSET, asset);
        return contentValues;
    }

    public ContentValues generarContentValuesUsuario(String nombre, String contra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_NOMBRE, nombre);
        contentValues.put(CN_PASS, contra);
        return contentValues;
    }

    public ContentValues generarContentValuesVideoUsuario(Integer id_video, Integer id_user,Integer total_usuario, Integer positivos_usuario) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN__USER_ID, id_user);
        contentValues.put(CN__VIDEO_ID, id_video);
        contentValues.put(CN_USUARIO_TOTAL, total_usuario);
        contentValues.put(CN_USUARIO_POSITIVOS, positivos_usuario);
        return contentValues;
    }

    public void rellenarDB(){
        insertarUsuario("Invitado","");
        insertarUsuario("Iago","1234");
        insertarUsuario("Alvaro","56789");
        insertarUsuario("Ana","qwerty");
        insertarUsuario("Fran","xxx");

        insertarVideo("Dos vacas lecheras","https://scontent-mad1-1.cdninstagram.com/t50.2886-16/23133663_965103896976649_3045585288396013568_n.mp4",R.raw.icono500);// 1
        insertarVideo("Aprendiendo con la moto", "https://github.com/iazul/GraxONo/blob/master/funnycomedy.ly/Aprendiendo_con_la_moto.mp4?raw=true",R.raw.funnycomedy_ly_aprendiendo_con_la_moto);// 2
        insertarVideo("Badum Tss", "https://github.com/iazul/GraxONo/blob/master/funnycomedy.ly/Badum_Tss.mp4?raw=true", R.raw.funnycomedy_ly_badum_tss );// 3
        insertarVideo("El peligro del minigolf", "https://github.com/iazul/GraxONo/blob/master/funnycomedy.ly/El_peligro_del_minigolf.mp4?raw=true", R.raw.funnycomedy_ly_el_peligro_del_minigolf );// 4
        insertarVideo("Futuros nadadores", "https://github.com/iazul/GraxONo/blob/master/funnycomedy.ly/Futuros_nadadores.mp4?raw=true", R.raw.funnycomedy_ly_futuros_nadadores );// 5
        insertarVideo("Golf deporte de riesgo", "https://github.com/iazul/GraxONo/blob/master/funnycomedy.ly/Golf_deporte_de_riesgo.mp4?raw=true", R.raw.funnycomedy_ly_golf_deporte_de_riesgo );// 6
        insertarVideo("Que ricas las natillas", "https://github.com/iazul/GraxONo/blob/master/funnycomedy.ly/Que_ricas_las_natillas.mp4?raw=true", R.raw.funnycomedy_ly_que_ricas_las_natillas );// 7
        insertarVideo("Bailando con patines", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Bailando_con_patines.mp4?raw=true", R.raw.funnyhoodvidz_bailando_con_patines );// 8
        insertarVideo("Cinturon negro", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Cinturon_negro.mp4?raw=true", R.raw.funnyhoodvidz_cinturon_negro );// 9
        insertarVideo("Corre por tu vida", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Corre_por_tu_vida.mp4?raw=true", R.raw.funnyhoodvidz_corre_por_tu_vida );// 10
        insertarVideo("El doggo que habla", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/El_doggo%20que_habla.mp4?raw=true", R.raw.funnyhoodvidz_el_doggo_que_habla );// 11
        insertarVideo("El tonto y el aún más tonto", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/El_tonto_y_el_aun_mas_tonto.mp4?raw=true", R.raw.funnyhoodvidz_el_tonto_y_el_aun_mas_tonto );// 12
        insertarVideo("Fallando al ser sexy", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Fallando_al_ser_sexy.mp4?raw=true", R.raw.funnyhoodvidz_fallando_al_ser_sexy );// 13
        insertarVideo("La reacción del perro", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/La_reacci%C3%B3n_del_perro.mp4?raw=true", R.raw.funnyhoodvidz_la_reaccion_del_perro );// 14
        insertarVideo("Michael Jackson", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Michael_Jackson.mp4?raw=true", R.raw.funnyhoodvidz_michael_jackson );// 15
        insertarVideo("Nadie se mete con el peque", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Nadie_se_mete_con_el_peque.mp4?raw=true", R.raw.funnyhoodvidz_nadie_se_mete_con_el_peque );// 16
        insertarVideo("No se puede tener mas mala suerte", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/No_se_puede_tener_mas_mala_suerte.mp4?raw=true", R.raw.funnyhoodvidz_no_se_puede_tener_mas_mala_suerte );// 17
        insertarVideo("Perro enseñando a saltar a un bebe", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Perro_enseñando_a_saltar_a_un_bebe.mp4?raw=true", R.raw.funnyhoodvidz_perro_ensenando_a_saltar_a_un_bebe );// 18
        insertarVideo("Profesional del salto de trampolin", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Profesional_del_salto_de_trampolin.mp4?raw=true", R.raw.funnyhoodvidz_profesional_del_salto_de_trampolin );// 19
        insertarVideo("Puro talento con la flauta", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Puro_talento_con_la_flauta.mp4?raw=true", R.raw.funnyhoodvidz_puro_talento_con_la_flauta );// 20
        insertarVideo("Raqueta electrica", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Raqueta_electrica.mp4?raw=true", R.raw.funnyhoodvidz_raqueta_electrica );// 21
        insertarVideo("Toma los loretes", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Toma_los_loretes.mp4?raw=true", R.raw.funnyhoodvidz_toma_los_loretes );// 22
        insertarVideo("Vaya ritmo tiene el perro", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/Vaya_ritmo_tiene_el_perro.mp4?raw=true", R.raw.funnyhoodvidz_vaya_ritmo_tiene_el_perro );// 23
        insertarVideo("We wish you a Merry Christmas", "https://github.com/iazul/GraxONo/blob/master/funnyhoodvidz/We_wish_you_a_Merry_Christmas.mp4?raw=true", R.raw.funnyhoodvidz_we_wish_you_a_merry_christmas );// 24
        insertarVideo("Asi se hace un kebab", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/Asi_se_hace_un_kebab.mp4?raw=true", R.raw.xurxocarreno_asi_se_hace_un_kebab );// 25
        insertarVideo("Chuleton de gallina chueca", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/Chuleton_de_gallina_chueca.mp4?raw=true", R.raw.xurxocarreno_chuleton_de_gallina_chueca );// 26
        insertarVideo("Dustiiin Dustinnnn", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/Dustiiin_Dustinnnn.mp4?raw=true", R.raw.xurxocarreno_dustiiin_dustinnnn );// 27
        insertarVideo("El Power Ranger drogadicto", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/El_Power_Ranger_drogadicto.mp4?raw=true", R.raw.xurxocarreno_el_power_ranger_drogadicto );// 28
        insertarVideo("Hamburguesa de dinosaurio", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/Hamburguesa_de_dinosaurio.mp4?raw=true", R.raw.xurxocarreno_hamburguesa_de_dinosaurio );// 29
        insertarVideo("Kim Jong Un y la comida", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/Kim_Jong_Un_y%20la_comida.mp4?raw=true", R.raw.xurxocarreno_kim_jong_un_y_la_comida );// 30
        insertarVideo("Zoomies", "https://github.com/iazul/GraxONo/blob/master/xurxocarreno/Zoomies.mp4?raw=true", R.raw.xurxocarreno_zoomies );// 31

        Random rn = new Random();
        for(int i=0,numVideos=31,numUsers = 5; i<980;i++)
            responder(rn.nextInt(numVideos)+1,rn.nextInt(numUsers)+1,rn.nextBoolean());
        for(int i=0; i<31;i++)
            responder(1,1,rn.nextBoolean());
    }

    public Cursor videoAlAzar(){
        String[] columnas = new String[]{CN__VIDEO_ID, CN_TITULO,CN_URL, CN_VIDEO_TOTAL, CN_VIDEO_POSITIVOS};
        return db.query(TABLE_VIDEOS_NAME, columnas,null, null, null, null, "RANDOM()", "1");
    }


    public Cursor consultarVideo(Integer id_video) {
        String[] columnas = new String[]{CN__VIDEO_ID, CN_TITULO,CN_URL, CN_VIDEO_TOTAL, CN_VIDEO_POSITIVOS,CN_ASSET};
        return db.query(TABLE_VIDEOS_NAME, columnas, CN__VIDEO_ID + "= ?", new String[]{id_video.toString()}, null, null, null);
    }

    public Cursor buscarVideo(String titulo_video) {
        String[] columnas = new String[]{CN__VIDEO_ID, CN_TITULO,CN_URL, CN_VIDEO_TOTAL, CN_VIDEO_POSITIVOS,CN_ASSET};
        return db.query(TABLE_VIDEOS_NAME, columnas, CN_TITULO + "= ?", new String[]{titulo_video}, null, null, null);
    }

    public void responder(Integer id_video,Integer id_usuario,boolean respuesta){
        Cursor cursorVideo=consultarVideo(id_video);
        int suma;
        if(respuesta) {
            suma = 1;
        }
        else {
            suma = 0;
        }
        if (cursorVideo.moveToFirst()){//comprobamos que existe el video
            modificarVideo(cursorVideo.getString(1),cursorVideo.getString(2),cursorVideo.getInt(3)+1,cursorVideo.getInt(4)+suma,cursorVideo.getInt(5));
            Cursor cursorVideoUsuario = datosVideoUsuario(id_video,id_usuario);
            if(cursorVideoUsuario.moveToFirst() == false){
                insertarVideoUsuario(id_video,id_usuario);//si no existe la entrada la creo
                modificarVideoUsuario(id_video,id_usuario,1,suma);
            }
            else{
                modificarVideoUsuario(id_video,id_usuario,cursorVideoUsuario.getInt(2)+1,cursorVideoUsuario.getInt(3)+suma);
            }

        }
    }

    public int insertarUsuario(String nombre,String contra){
        return (int) db.insert(TABLE_USERS_NAME, null, generarContentValuesUsuario(nombre,contra));//devuelve el id del nuevo usuario de tipo long como int
    }

    public void eliminarUsuario(String nombre){
        db.delete(TABLE_USERS_NAME, CN_NOMBRE + "=?", new String[]{nombre});
    }

    public int accederUsuario(String nombre,String contra){
        Cursor cursor = buscarUsuario(nombre);
        if (cursor.moveToFirst() == false){
            return -2;//el usuario no existe
        }else {
            String contraAux = cursor.getString(2);
            if(contraAux.matches(contra)){
                return cursor.getInt(0);//devuelve su ID
            }
            else{
                return -1;//la contraseña es incorrecta
            }
        }
    }

    public Cursor buscarUsuario(String nombre){
        String[] columnas = new String[]{CN__USER_ID,CN_NOMBRE,CN_PASS};
        return db.query(TABLE_USERS_NAME, columnas, CN_NOMBRE + "=?", new String[]{nombre}, null, null, null);
    }

    public Cursor buscarUsuarioPorId(Integer id){
        String[] columnas = new String[]{CN_NOMBRE};
        return db.query(TABLE_USERS_NAME, columnas, CN__USER_ID + "=?", new String[]{id.toString()}, null, null, null);
    }

    public void insertarVideoUsuario(Integer id_video, Integer id_usuario){
        db.insert(TABLE_VIDEOUSERS_NAME, null, generarContentValuesVideoUsuario(id_video,id_usuario,0,0));
    }

    public void modificarVideoUsuario(Integer id_video, Integer id_usuario, Integer total_usuario, Integer positivos_usuario){
        db.update(TABLE_VIDEOUSERS_NAME, generarContentValuesVideoUsuario(id_video, id_usuario, total_usuario, positivos_usuario), CN__VIDEO_ID + "=? AND "+CN__USER_ID+"=?", new String[]{id_video.toString(),id_usuario.toString()});
    }

    public Cursor datosVideoUsuario(Integer id_video, Integer id_usuario){
        String[] columnas = new String[]{CN__VIDEO_ID, CN__USER_ID,CN_USUARIO_TOTAL,CN_USUARIO_POSITIVOS};
        return db.query(TABLE_VIDEOUSERS_NAME, columnas, CN__VIDEO_ID + "=? AND "+CN__USER_ID+"=?", new String[]{id_video.toString(),id_usuario.toString()}, null, null, null);
    }



    //para historial
    public Cursor consultarVideosUsuario(Integer id_usuario) {
        return db.rawQuery(CONSULTA_USUARIO,new String[]{id_usuario.toString()});
    }

    public Cursor consultarVideosUsuarioTitulo(Integer id_usuario, String titulo) {
        return db.rawQuery(CONSULTA_USUARIO_TITULO+titulo+CONSULTA_USUARIO_TITULO2,new String[]{id_usuario.toString()});
    }





}