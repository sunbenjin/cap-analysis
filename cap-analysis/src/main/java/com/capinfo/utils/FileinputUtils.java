package com.capinfo.utils;

import com.capinfo.exception.MyException;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FileinputUtils {

    public static void updateFiles(Map<String, String> map, String uploadPath) {
        String msg = "";
        try {
            Gson gson = new Gson();
            msg = HttpUtils.sendPostJson(uploadPath, gson.toJson(map));
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
