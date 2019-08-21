package com.schedule.record.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetUtils {

    private static int version = 1;
    private static GeneralUserSQLite helper;
    private static String DBName = "general_user";
    private static AuthoritySQLite helper0;
    private static String DBName0 = "authority";

    private static TodaySQLite helper1;
    private static String DBName1 = "future";
    private static TodaySQLite helper2;
    private static String DBName2 = "today";
    private static TodaySQLite helper3;
    private static String DBName3 = "finish";
    private static TodaySQLite helper4;
    private static String DBName4 = "pass";

    public static Bitmap getImage(String url) throws Exception {
        Bitmap bitmap=null;
        //1、创建一个URL对象：
        URL u=new URL(url);
        //2-openConnection( )来获取HttpURLConnection对象实例
        HttpURLConnection connection=(HttpURLConnection)u.openConnection();
        //3-设置HTTP请求使用的方法
        connection.setRequestMethod("GET");

        int code= connection.getResponseCode();
        if(code==HttpURLConnection.HTTP_OK){
            //4-调用getInputStream()方法获得服务器返回的输入流
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        }
        //关掉 conn.disconnect()
        connection.disconnect();
        return bitmap;
    }

    public static String getJson(String url) throws Exception {
        //1、创建一个URL对象：
        URL u=new URL(url);
        //2-openConnection( )来获取HttpURLConnection对象实例
        HttpURLConnection connection=(HttpURLConnection)u.openConnection();
        //3-设置HTTP请求使用的方法
        connection.setRequestMethod("GET");

        int code= connection.getResponseCode();
        if(code==HttpURLConnection.HTTP_OK){
            //4-调用getInputStream()方法获得服务器返回的输入流
            InputStream inputStream=connection.getInputStream();
            byte[] buffer=new byte[1024];
            StringBuilder sb=new StringBuilder();
            int len;//每次实际读取的字节数
            while((len=inputStream.read(buffer))!=-1){
                sb.append(new String(buffer,0,len));
            }
            inputStream.close();
            return sb.toString();
        }
        //关掉 conn.disconnect()
        connection.disconnect();
        return null;
    }

    //解析json数据(user/findbyid的解析)
    public static String parseUserJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if ((jsonObject.get("code").toString()).equals("111")) {

            String nameid = jsonObject.getJSONObject("data").getString("nameid");
            String name = jsonObject.getJSONObject("data").getString("name");
            String password = jsonObject.getJSONObject("data").getString("password");
            String sex = jsonObject.getJSONObject("data").getString("sex");
            String birthday = jsonObject.getJSONObject("data").getString("birthday");
            String head = jsonObject.getJSONObject("data").getString("head");

            //插入本地数据库
            GeneralSQLiteUser things = new GeneralSQLiteUser(nameid,name,password,sex,birthday,head);
            helper = new GeneralUserSQLite(context, DBName, null, version);
            GeneralSQLiteUserDao dao = new GeneralSQLiteUserDao(helper);
            dao.deleteAll();//保证本地账号唯一性
            dao.insert(things);

            return name;

        }else {
            return jsonObject.get("message").toString();
        }

    }

    //解析json数据(authority/query的解析)
    public static String parseAuthortyJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if (jsonObject.get("code").toString().equals("111")) {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length()>0){
                String gnameId = null, snameId = null;

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    gnameId = jsonObject1.getString("gnameId");
                    snameId = jsonObject1.getString("snameId");

                    //插入本地数据库
                    AuthoritySQLiteUser things = new AuthoritySQLiteUser(gnameId, snameId);
                    helper0 = new AuthoritySQLite(context, DBName0, null, version);
                    AuthoritySQLiteUserDao dao = new AuthoritySQLiteUserDao(helper0);
                    dao.insert(things);
                }
                return "yes";
            }else {
                return "no";
            }

        }else {
            return "no";
        }
    }

    //解析json数据(today/findbyid的解析)
    public static String parseTodayJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        //{"code":111,"message":"操作成功","data":
        // {"dayId":"18711660142","checkbox":false,"remind":true,"time":null,"title":"test",
        // "important":null,"diary":"this is test","thisDay":null}}

        //today/findbyid?dayId=18711660142

        if ((jsonObject.get("code").toString()).equals("111")) {

            String dayId = jsonObject.getJSONObject("data").getString("dayId");
            Boolean checkbox = jsonObject.getJSONObject("data").getBoolean("checkbox");
            Boolean remind = jsonObject.getJSONObject("data").getBoolean("remind");
            String time = jsonObject.getJSONObject("data").getString("time");
            String title = jsonObject.getJSONObject("data").getString("title");
            String important = jsonObject.getJSONObject("data").getString("important");
            String diary = jsonObject.getJSONObject("data").getString("diary");
            String thisDay = jsonObject.getJSONObject("data").getString("thisDay");

            //插入本地数据库
            TodaySQLiteUser things = new TodaySQLiteUser(dayId,checkbox,remind,time,title,important,diary,thisDay);
            helper2 = new TodaySQLite(context, DBName2, null, version);
            TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper2);
            dao.insert(things,context);

            return "yes";

        }else {
            return "no";
        }
    }

}
