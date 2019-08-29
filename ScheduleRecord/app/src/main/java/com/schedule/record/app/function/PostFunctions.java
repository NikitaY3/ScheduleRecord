package com.schedule.record.app.function;

import android.os.Handler;
import android.os.Message;

import com.schedule.record.app.sqlite.user.AuthoritySQLiteUser;
import com.schedule.record.app.sqlite.user.FinishSQLiteUser;
import com.schedule.record.app.sqlite.user.FutureSQLiteUser;
import com.schedule.record.app.sqlite.user.GeneralSQLiteUser;
import com.schedule.record.app.sqlite.user.PassSQLiteUser;
import com.schedule.record.app.sqlite.user.TodaySQLiteUser;
import com.schedule.record.app.utils.HttpPostUtils;

import java.util.HashMap;
import java.util.Map;

public class PostFunctions {

    public PostFunctions() {
    }

    //Post（注册用户账号）
    public String SaveUserPost(String nameid, String password, String name, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();
        params.put("nameid", nameid);
        params.put("password", password);
        params.put("name", name);

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/user/save?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 210;
                if (!strResult[0].equals("")){
                    msg.what = 21;
                }
                uiHandler.sendMessage(msg);

            }
        }.start();
        return strResult[0];
    }

    //Post（更新用户资料）
    public String UpdateUserPost(GeneralSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        params.put("nameId",user.getNameid());
        params.put("name",user.getName());
        params.put("password",user.getPassword());
        params.put("sex",user.getSex());
        params.put("birthday",user.getBirthday());
        params.put("head",user.getHead());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/user/update?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 210;
                if (!strResult[0].equals("")){
                    msg.what = 21;
                }
                uiHandler.sendMessage(msg);

            }
        }.start();
        return strResult[0];
    }

    //Post(插入用户权限)
    public String SaveAuthorityPost(AuthoritySQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        params.put("gnameId",user.getGnameid());
        params.put("snameId",user.getSnameid());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();

                String strUrlPath = "http://120.77.222.242:10024/authority/save?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);

            }
        }.start();
        return strResult[0];
    }

    //Post(插入未来日程)
    public String SaveFuturePost(FutureSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        String remind = "1";
        if (!user.isRemind()) {  remind = "0";  }

        params.put("dayId",user.getDayId());
        params.put("repeatType",user.getRepeatType());
        params.put("endDay",user.getEndDay());
        params.put("remind",remind);
        params.put("time",user.getTime());
        params.put("title",user.getTitle());
        params.put("important",user.getImportant());
        params.put("diary",user.getDiary());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/future/save";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(更新未来日程)
    public String UpdateFuturePost(FutureSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        String remind ;
        if (user.isRemind()){  remind = "1";  }else {  remind = "0";  }

        params.put("dayId",user.getDayId());
        params.put("repeatType",user.getRepeatType());
        params.put("endDay",user.getEndDay());
        params.put("remind",remind);
        params.put("time",user.getTime());
        params.put("title",user.getTitle());
        params.put("important",user.getImportant());
        params.put("diary",user.getDiary());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/future/update?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(插入当天日程)
    public String SaveTodayPost(TodaySQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        String checkbox,remind ;
        if (user.isCheckbox()){  checkbox = "1";  }else {  checkbox = "0"; }
        if (user.isRemind()){  remind = "1";  }else {  remind = "0";  }

        params.put("dayId",user.getDayid());
        params.put("checkbox",checkbox);
        params.put("remind",remind);
        params.put("time",user.getTime());
        params.put("title",user.getTitle());
        params.put("important",user.getImportant());
        params.put("diary",user.getDiary());
        params.put("thisDay",user.getThisday());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/today/save?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(更新当天日程)
    public String UpdateTodayPost(TodaySQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        String checkbox,remind ;
        if (user.isCheckbox()){  checkbox = "1";  }else {  checkbox = "0"; }
        if (user.isRemind()){  remind = "1";  }else {  remind = "0";  }

        params.put("dayId",user.getDayid());
        params.put("checkbox",checkbox);
        params.put("remind",remind);
        params.put("time",user.getTime());
        params.put("title",user.getTitle());
        params.put("important",user.getImportant());
        params.put("diary",user.getDiary());
        params.put("thisDay",user.getThisday());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/today/update?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(插入完成日程)
    public String SaveFinishPost(FinishSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        String checkbox,remind ;
        if (user.getCheckbox()){  checkbox = "1";  }else {  checkbox = "0"; }
        if (user.getRemind()){  remind = "1";  }else {  remind = "0";  }

        params.put("finishId",user.getFinishId());
        params.put("dayId",user.getDayId());
        params.put("checkbox",checkbox);
        params.put("remind",remind);
        params.put("time",user.getTime());
        params.put("title",user.getTitle());
        params.put("important",user.getImportant());
        params.put("diary",user.getDiary());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/finish/save?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(更新完成日程)
    public String UpdateFinishPost(FinishSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        String checkbox,remind ;
        if (user.getCheckbox()){  checkbox = "1";  }else {  checkbox = "0"; }
        if (user.getRemind()){  remind = "1";  }else {  remind = "0";  }

        params.put("finishId",user.getFinishId());
        params.put("dayId",user.getDayId());
        params.put("checkbox",checkbox);
        params.put("remind",remind);
        params.put("time",user.getTime());
        params.put("title",user.getTitle());
        params.put("important",user.getImportant());
        params.put("diary",user.getDiary());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/finish/update?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(插入失效日程)
    public String SavePassPost(PassSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        params.put("dayId",user.getDayid());
        params.put("title",user.getTitle());
        params.put("passDay",user.getPassday());
        params.put("completion", String.valueOf(user.getCompletion()));
        params.put("important",user.getImportant());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/pass/save?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

    //Post(更新失效日程)
    public String UpdatePassPost(PassSQLiteUser user, final Handler uiHandler){
        //参数
        final Map<String,Object> params = new HashMap<>();

        params.put("dayId",user.getDayid());
        params.put("title",user.getTitle());
        params.put("passDay",user.getPassday());
        params.put("completion", String.valueOf(user.getCompletion()));
        params.put("important",user.getImportant());

        final String[] strResult = new String[1];
        new Thread() {
            @Override
            public void run() {
                super.run();
                //服务器请求路径
                String strUrlPath = "http://120.77.222.242:10024/pass/update?";
                strResult[0] = HttpPostUtils.submitPostData(strUrlPath,params, "utf-8");

                Message msg = new Message();
                msg.what = 21;
                uiHandler.sendMessage(msg);
            }
        }.start();
        return strResult[0];
    }

}
