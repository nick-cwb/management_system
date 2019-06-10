package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.LocalFile;
import com.isoft.system.mapper.FileMapper;
import com.isoft.system.service.IFileService;
import com.isoft.system.utils.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Service
@DS("master")
public class FileServiceImpl extends ServiceImpl<FileMapper,LocalFile> implements IFileService {

    @Value("${file.path}")
    private String filePath;

    @Override
    public boolean addFile(MultipartFile files, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 处理中文乱码
        request.setCharacterEncoding("UTF-8");
        // 获取文件名称
        String originalFileName = files.getOriginalFilename();

        String str = originalFileName.substring(originalFileName.indexOf("."));
        // 获取唯一文件名
        String fileName = Util.getUUID() + str;

        // 相对路径
        String url = filePath + fileName;

        File subFile = new File(filePath);

        // 判断文件路径是否存在
        if (!subFile.exists()) {
            // 文件路径不存在时，创建保存文件所需要的路径
            subFile.mkdirs();
        }

        files.transferTo(new File(url));

        LocalFile localFile = new LocalFile();

        localFile.setName(originalFileName);
        localFile.setFilePath(fileName);
        localFile.setCreateTime(Util.createTimestamp());

        int flag = baseMapper.insert(localFile);

        if(flag>0){
            return true;
        }else{
            return false;
        }
    }
}
