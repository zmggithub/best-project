package com.ccbhousing.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class CcbController {

    @RequestMapping("importExcel")
    public String importExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = "";
        MultipartHttpServletRequest rq = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> file_list = rq.getFileMap();
        if (file_list != null && file_list.size() > 0) {
            for (String key : file_list.keySet()) {
                MultipartFile file = file_list.get(key);
                String filename = file.getOriginalFilename();
                System.out.println(filename);
                if(file != null){
                    String docmentType = FilenameUtils.getExtension(file.getOriginalFilename());// 文件类型
                    UUID uuid = UUID.randomUUID();
                    filePath = "E:/CCB/"+ uuid +"."+ docmentType;
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath)); //拷贝文件
                }

            }
        }


        OutputStream output = response.getOutputStream();
        response.setContentType("application/octet-stream");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        String exportFile = "公租房项目日报" +format + ".xls";
        response.setHeader("Content-disposition","attachment;filename=" + URLEncoder.encode(exportFile, "UTF-8"));

        File file = new File(filePath);
        if (file.exists()) {
            byte[] data = IOUtils.toByteArray(new FileInputStream(file));
            output.write(data);
            output.flush();
            response.flushBuffer();
            output.close();
        }
        return "redirect:/export.jsp";
    }



}
