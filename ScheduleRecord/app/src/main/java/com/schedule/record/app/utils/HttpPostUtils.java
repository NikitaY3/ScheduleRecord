package com.schedule.record.app.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpPostUtils {

    /**
     * 发送Post请求到服务器
     * @param strUrlPath:接口地址
     * @param params:请求体内容
     * @param encode:编码格式
     * @return 获取到的服务端返回数据
     */
    public static String submitPostData(String strUrlPath, Map<String, Object> params, String encode) {

        byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体,将string转bit

        try {
            URL url = new URL(strUrlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存

            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            outputStream.close();

            //获得服务器的响应码
            int response = httpURLConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                //处理服务器的响应结果
                return dealResponseResult(inptStream);
            }
        } catch (IOException e) {
            return "";
        }
        return "yes";
    }

    /**
     * 封装请求体信息
     * @param params:请求体内容
     * @param encode:编码格式
     * @return 将object转换为byte[]
     */
    private static StringBuffer getRequestData(Map<String, Object> params, String encode) {
        //存储封装好的请求体信息
        StringBuffer stringBuffer = new StringBuffer();

        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(String.valueOf(entry.getValue()), encode))
                        .append("&");
            }

            //删除最后的一个"&"
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    /**
     * 处理服务器的响应结果（将输入流转化成字符串）
     * @param inputStream:服务器的响应输入流
     * @return 对服务端数据处理
     */
    private static String dealResponseResult(InputStream inputStream) {
        String resultData;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len ;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        //Log.d("resultData:{}", resultData);
        return resultData;
    }





//    @SuppressLint("NewApi")
//    public static<T> Optional<byte[]> objectToBytes(T obj){
//        byte[] bytes = null;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream sOut;
//        try {
//            sOut = new ObjectOutputStream(out);
//            sOut.writeObject(obj);
//            sOut.flush();
//            bytes = out.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Optional.ofNullable(bytes);
//    }
//
//
//    public static<T> byte[] objectToBytes1(T obj){
//        byte[] bytes = null;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream sOut;
//        try {
//            sOut = new ObjectOutputStream(out);
//            sOut.writeObject(obj);
//            sOut.flush();
//            bytes = out.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bytes;
//    }



















//    //利用Gson实现对象序列化为Json
//    public static String toJson(Object object) {
//        GsonBuilder builder = new GsonBuilder();
//        // 不转换没有 @Expose 注解的字段
//        builder.excludeFieldsWithoutExposeAnnotation();
//        //对Date类型进行注册事件
//        builder.registerTypeAdapter(Date.class, new UtilDateSerializer());
//        Gson gson = builder.create();
//        return gson.toJson(object);
//    }
//
//    static class UtilDateSerializer implements JsonSerializer<Date> {
//        @Override
//        public JsonElement serialize(Date src, Type typeOfSrc,
//                                     JsonSerializationContext context) {
//            //拼凑UTC时间类型
//            return new JsonPrimitive("/Date(" + src.getTime() + "+0800)/");
//        }
//    }
//
//    public String httpPost(String url, String json) throws Exception {
//        String response = null;
//        int timeoutConnection = 3000;
//        int timeoutSocket = 5000;
//        HttpParams httpParameters = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParameters,timeoutConnection);
//        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//        HttpClient httpClient = new DefaultHttpClient(httpParameters);
//        HttpPost httpPost = new HttpPost(url);
//        // 添加http头信息
//        httpPost.addHeader("Content-Type", "application/json");
//        httpPost.addHeader("User-Agent", "imgfornote");
//        httpPost.setEntity(new StringEntity(json,"UTF-8"));
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//        int statusCode = httpResponse.getStatusLine().getStatusCode();
//        if (statusCode == HttpStatus.SC_OK) {
//            response = EntityUtils.toString(httpResponse.getEntity());
//        } else {
//            response = String.valueOf(statusCode);
//        }
//        return response;
//    }
}


