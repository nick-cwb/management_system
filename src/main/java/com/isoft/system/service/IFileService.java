package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.LocalFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface IFileService extends IService<LocalFile> {

    boolean addFile(MultipartFile files, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
