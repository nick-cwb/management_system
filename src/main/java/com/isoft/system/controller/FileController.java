package com.isoft.system.controller;

import com.isoft.system.service.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${file.path}")
    private String filePath;

    @Resource
    IFileService service;

    /**
     * 文件下载
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void fileDownLoad(final HttpServletRequest request, final HttpServletResponse response,@PathVariable Integer id) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/force-download");
        String fileName = service.getById(id).getFilePath();
        File file = new File(filePath+fileName);
        InputStream in = new FileInputStream(file);
        String fileNames = URLEncoder.encode(file.getName(), "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+fileNames);
        response.setContentLength(in.available());
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while((len = in.read(b))!=-1){
            out.write(b, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }


    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public void uploadFile(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
        boolean flag = service.addFile( file, request,response);
        response.setContentType("text/html;charset = utf-8");
        return;
    }

}
