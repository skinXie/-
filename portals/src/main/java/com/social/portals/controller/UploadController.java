package com.social.portals.controller;

import common.feign.UserFeign;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {

    @Autowired
    UserFeign userFeign;

    @PostMapping("/img/upload")
    public String upoloadImg(@RequestParam("image") MultipartFile file,
                             @RequestParam("userId") int userId,
                             HttpServletRequest request) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = userId+"head";
        String filePath = getClass().getClassLoader().getResource("static/img/head/").getPath().replaceFirst("/", "");
        File dest = new File(filePath + fileName + ".jpg");
        try {
            file.transferTo(dest);
            String headUrl = "static/img/head/" + fileName + ".jpg";
            userFeign.updateUserHeadUrl(headUrl, userId + "");
            return headUrl;
        } catch (IOException e) {
        }
        return "上传失败！";
    }
}
