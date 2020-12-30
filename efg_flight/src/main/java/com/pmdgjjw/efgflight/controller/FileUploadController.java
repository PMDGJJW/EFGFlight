package com.pmdgjjw.efgflight.controller;

import com.pmdgjjw.entity.Result;
import com.pmdgjjw.util.FTPUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/doUpload")
public class FileUploadController {

    @RequestMapping("/file")
    public Result ftpUpload(@RequestParam("file")MultipartFile file,String fileType){

        try {

            String fileName =  file.getOriginalFilename();

            String substring = fileName.substring(fileName.lastIndexOf("."));

            String newFileName = UUID.randomUUID()+substring;

            InputStream inputStream = file.getInputStream();

            String b = FTPUtil.uploadFile(newFileName, inputStream,fileType);
            if ( "连接失败".equals(b)){
                return new Result(true,200,b);
            }
            else if ( "false".equals(b)){
                return new Result(true,200,b);
            }else {
                return new Result(true,200,b);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,200,"上传失败");
        }

    }

    @RequestMapping("/dwFile")
    public Result ftpDW(String fileName,HttpServletResponse response ){
        boolean b = FTPUtil.downloadFile(fileName,response);

        if (b){
            return new Result(true,200,"下载成功");
        }else {
            return new Result(true,200,"下载失败");
        }

    }

    @RequestMapping(value = "/getPhoto/{fileType}/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable("fileName") String fileName,@PathVariable("fileType") String fileType, HttpServletResponse response ){
        return  FTPUtil.getFile(fileName,fileType);

    }

}
