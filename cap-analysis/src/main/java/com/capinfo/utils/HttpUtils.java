package com.capinfo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static String doGet(String uriAPI, String encoding) {
        if (encoding == null || encoding.equals("")) {
            encoding = "utf-8";
        }
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpRequst = new HttpGet(uriAPI);

        try {
            CloseableHttpResponse httpResponse = client.execute(httpRequst);// 其中HttpGet是HttpUriRequst的子类
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 获取结果实体
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    // 按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, encoding);
                }
                EntityUtils.consume(entity);

                // 一般来说都要删除多余的字符
                body.replaceAll("\r", "");
                // 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            } else {
                httpRequst.abort();
            }
            httpResponse.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            body = e.getMessage().toString();
        } catch (IOException e) {
            e.printStackTrace();
            body = e.getMessage().toString();
        }
        return body;
    }

    public static String sendXmlPost(String urlStr, String xmlInfo, int timeout) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            // con.setRequestProperty("Pragma:", "no-cache");
            // con.setRequestProperty("Cache-Control", "no-cache");
            // 设置通用的请求属性
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            con.setRequestProperty("Content-Type", "text/xml");
            con.setConnectTimeout(timeout); // 1h
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "utf-8");
            // out.write(new String(xmlInfo.getBytes("UTF-8")));
            out.write(xmlInfo);
            out.flush();
            out.close();
            int code = con.getResponseCode();
            if (code == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } else {
                System.out.println("code:" + code);
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // return ;
                return "posterror:" + code + " sb:" + sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "posterror:";
    }

    public static String sendXmlPost(String urlStr, String xmlInfo)	{
        return sendXmlPost(urlStr, xmlInfo, 60 * 60 * 1000); // 1h
    }

    /**
     * 模拟请求
     *
     * @param url
     *            资源地址
     * @param map
     *            参数列表
     * @param encoding
     *            编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String sendPost(String url, Map<String, String> map, String encoding)
            throws ParseException, IOException {

        if (encoding == null || encoding.equals("")) {
            encoding = "utf-8";
        }
        String body = "";

        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址：" + url);
        System.out.println("请求参数：" + nvps.toString());

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }

    public static String sendPostJson(String url, String json){
        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        String result = "";
        try {
            StringEntity s = new StringEntity(json, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json; charset=utf-8");//发送json数据需要设置contentType
            post.setEntity(s);

            CloseableHttpResponse response = client.execute(post);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();

                result = EntityUtils.toString(response.getEntity());// 返回json格式：

                EntityUtils.consume(entity);
                // 释放链接
                response.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
