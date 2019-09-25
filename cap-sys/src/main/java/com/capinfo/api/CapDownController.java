package com.capinfo.api;

import com.capinfo.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;

@Controller
@RequestMapping("/down")
public class CapDownController {
    @RequestMapping("/downApp")
    public void downApp(HttpServletRequest request, HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("/app/hongjingyun_release_1.0.0.apk");
           inputStream = classPathResource.getInputStream();

            File file = File.createTempFile("hongjingyun_release_1.0.0",".apk");
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream,file);
            FileUtils.downFile(file, request, response);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

    }
}
