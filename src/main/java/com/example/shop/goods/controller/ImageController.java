package com.example.shop.goods.controller;

import com.example.shop.util.R;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class ImageController {

    /**
     * 上传文件存放目录（相对于项目工作目录而言）
     */
    private static final String fileDir = "file";
    private static final String dest = System.getProperty("user.dir") + File.separator + fileDir;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 上传成果会返回该文件的访问相对路径
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.of(400, "文件为空", null);
        }
        if (file.getContentType() != null && !file.getContentType().startsWith("image")) {
            return R.of(400, "只允许上传图片", null);
        }
        // 获取文件名和后缀
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            return R.of(400, "文件名为空", null);
        }
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        String destFileName = UUID.randomUUID() + fileSuffix;
        String destPath = dest + File.separator + destFileName;
        File destFile = new File(destPath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            // 该方法需使用绝对路径
            file.transferTo(destFile);
        } catch (IOException e) {
            return R.of(500, e.getMessage(), null);
        }
        return R.ok(fileDir + "/" + destFileName);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        File file = new File(dest + File.separator + filename);
        if (file.exists() && !file.isDirectory()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] fileBytes = new byte[(int) file.length()];
                inputStream.read(fileBytes);
                return ResponseEntity.ok().contentType(MediaType.valueOf("image/jpeg")).body(fileBytes);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
