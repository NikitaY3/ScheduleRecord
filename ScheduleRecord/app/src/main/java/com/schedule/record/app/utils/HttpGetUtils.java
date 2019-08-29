package com.schedule.record.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.schedule.record.app.sqlite.AuthoritySQLite;
import com.schedule.record.app.sqlite.FinishSQLite;
import com.schedule.record.app.sqlite.FutureSQLite;
import com.schedule.record.app.sqlite.GeneralUserSQLite;
import com.schedule.record.app.sqlite.PassSQLite;
import com.schedule.record.app.sqlite.TodaySQLite;
import com.schedule.record.app.sqlite.dao.AuthoritySQLiteUserDao;
import com.schedule.record.app.sqlite.dao.FinishSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.FutureSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.GeneralSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.PassSQLiteUserDao;
import com.schedule.record.app.sqlite.dao.TodaySQLiteUserDao;
import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;
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

    private static FutureSQLite helper1;
    private static String DBName1 = "future";
    private static TodaySQLite helper2;
    private static String DBName2 = "today";
    private static FinishSQLite helper3;
    private static String DBName3 = "finish";
    private static PassSQLite helper4;
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


    //解析json数据(user/findbyid的解析)查询用户是否注册
    public static String parseUserJson(String jsonStr, Context context) throws JSONException {

        if (new JSONObject(jsonStr).length() !=  0){

            JSONObject jsonObject = new JSONObject(jsonStr);
            if ((jsonObject.get("code").toString()).equals("111")) {

                String nameid = jsonObject.getJSONObject("data").getString("nameId");
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
        return "账号密码不匹配";
    }

    //解析json数据(authority/query的解析)查询用户可管理的用户和用户授予他人的权限
    public static String parseAuthorityQueryJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if (!jsonObject.getString("data").equals("")){
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            helper0 = new AuthoritySQLite(context, DBName0, null, version);
            AuthoritySQLiteUserDao dao = new AuthoritySQLiteUserDao(helper0);

            //循环取出
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String gnameid = object.getString("gnameId");
                String snameid = object.getString("snameId");

                //插入本地数据库
                AuthoritySQLiteUser things = new AuthoritySQLiteUser(gnameid,snameid);
                dao.insert(things);
            }
        }
        return jsonObject.getString("data");
    }

    //解析json数据(/future/findall的解析)所有未来日程
    public static String parseFutureFindAllJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if (!jsonObject.getString("data").equals("")){
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            //循环取出{"dayId":"1111","repeatType":null,"remind":null,"time":null,"title":null,
            // "important":null,"diary":null,"endday":null}
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String dayid = object.getString("dayId");
                String repeatType = object.getString("repeatType");
                String remind1 = object.getString("remind");
                String time = object.getString("time");
                String title = object.getString("title");
                String important = object.getString("important");
                String diary = object.getString("diary");
                String endday = object.getString("endDay");

                boolean remind = true;
                if (!remind1.equals("true")){
                    remind = false;
                }

                //插入本地数据库
                FutureSQLiteUser things = new FutureSQLiteUser(dayid,repeatType,endday,remind,time,title,important,diary);
                helper1 = new FutureSQLite(context, DBName1, null, version);
                FutureSQLiteUserDao dao = new FutureSQLiteUserDao(helper1);
                dao.insert(things);
            }
        }
        return jsonObject.getString("data");
    }


    //解析json数据(today/findall的解析)查询所有当天日程
    public static String parseTodayFindAllJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if (!jsonObject.getString("data").equals("")){
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String dayid = object.getString("dayId");
                String checkbox1 = object.getString("checkbox");
                String remind1 = object.getString("remind");
                String time = object.getString("time");
                String title = object.getString("title");
                String important = object.getString("important");
                String diary = object.getString("diary");
                String thisday = object.getString("thisDay");

                boolean checkbox = false;
                if (checkbox1.equals("true")){
                    checkbox = true;
                }
                boolean remind = true;
                if (!remind1.equals("true")){
                    remind = false;
                }

                //插入本地数据库
                TodaySQLiteUser things = new TodaySQLiteUser(dayid,checkbox,remind,time,title,important,diary,thisday);
                helper2 = new TodaySQLite(context, DBName2, null, version);
                TodaySQLiteUserDao dao = new TodaySQLiteUserDao(helper2);
                dao.insert(things,context);
            }
        }
        return jsonObject.getString("data");
    }

    //解析json数据(finish/findall的解析)查询所有完成日程
    public static String parseFinishFindAllJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if (!jsonObject.getString("data").equals("")){
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String finishid = object.getString("finishId");
                String dayid = object.getString("dayId");
                String checkbox1 = object.getString("checkbox");
                String remind1 = object.getString("remind");
                String time = object.getString("time");
                String title = object.getString("title");
                String important = object.getString("important");
                String diary = object.getString("diary");

                boolean checkbox = false;
                if (checkbox1.equals("true")){
                    checkbox = true;
                }
                boolean remind = true;
                if (!remind1.equals("true")){
                    remind = false;
                }

                //插入本地数据库
                FinishSQLiteUser things = new FinishSQLiteUser(finishid,dayid,checkbox,remind,time,title,important,diary);
                helper3 = new FinishSQLite(context, DBName3, null, version);
                FinishSQLiteUserDao dao = new FinishSQLiteUserDao(helper3);
                dao.insert(things);
            }
        }
        return jsonObject.getString("data");
    }

    //解析json数据(pass/findall的解析)查询所有失效日程
    public static String parsePassFindAllJson(String jsonStr, Context context) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonStr);

        if (!jsonObject.getString("data").equals("")){
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String dayid = object.getString("dayId");
                String title = object.getString("title");
                String passday = object.getString("passDay");
                String completion1 = object.getString("completion");

                int completion = 0;
                if (!completion1.equals("null")){
                    completion = Integer.parseInt(completion1);
                }

                //插入本地数据库
                PassSQLiteUser things = new PassSQLiteUser(dayid,title,passday,completion,"a");
                helper4 = new PassSQLite(context, DBName4, null, version);
                PassSQLiteUserDao dao = new PassSQLiteUserDao(helper4);
                dao.insert(things);
            }
        }
        return jsonObject.getString("data");
    }
}
