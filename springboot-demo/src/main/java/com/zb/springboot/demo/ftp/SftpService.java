//package com.zb.springboot.demo.ftp;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * SFTP文件传输服务
// * @author lee
// */
//@Slf4j
//@Service
//public class SftpService {
//
//    @Autowired
//    private SftpRemoteFileTemplate remoteFileTemplate;
//
//    @Autowired
//    private SftpConfiguration.SftpGateway sftpGateway;
//
//    /**
//     * 单文件上传
//     *
//     * @param file File
//     */
//    public void uploadFile(File file) {
//        sftpGateway.upload(file);
//    }
//
//    /**
//     * 单文件上传 byte字节流直接上传
//     *
//     * @param bytes bytes
//     */
//    public void uploadFile(byte[] bytes, String name) {
//        sftpGateway.upload(bytes, name);
//    }
//    /**
//     * 查询某个路径下所有文件
//     *
//     * @param path
//     * @return List<String>
//     */
//    public List<String> listAllFile(String path) {
//        return remoteFileTemplate.execute(session -> {
//            Stream<String> names = Arrays.stream(session.listNames(path));
//            names.forEach(name -> log.info("Name = " + name));
//            return names.collect(Collectors.toList());
//        });
//    }
//
//    /**
//     * 下载文件
//     *
//     * @param fileName 文件名
//     * @param savePath 本地文件存储位置
//     * @return File
//     */
//    public File downloadFile(String fileName, String savePath) {
//        return remoteFileTemplate.execute(session -> {
//            boolean existFile = session.exists(fileName);
//            if (existFile) {
//                InputStream is = session.readRaw(fileName);
//                return convertInputStreamToFile(is, savePath);
//            } else {
//                log.info("file : {} not exist", fileName);
//                return null;
//            }
//        });
//    }
//
//    /**
//     * 文件是否存在
//     *
//     * @param filePath 文件名
//     * @return boolean
//     */
//    public boolean existFile(String filePath) {
//        return remoteFileTemplate.execute(session ->
//                session.exists(filePath));
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param fileName 待删除文件名
//     * @return boolean
//     */
//    public boolean deleteFile(String fileName) {
//        return remoteFileTemplate.execute(session -> {
//            boolean existFile = session.exists(fileName);
//            if (existFile) {
//                return session.remove(fileName);
//            } else {
//                log.info("file : {} not exist", fileName);
//                return false;
//            }
//        });
//    }
//
//    /**
//     * 批量上传 (MultipartFile)
//     *
//     * @param files List<MultipartFile>
//     * @throws IOException
//     */
//    public void uploadFiles(List<MultipartFile> files, boolean deleteSource) throws IOException {
//        for (MultipartFile multipartFile : files) {
//            if (multipartFile.isEmpty()) {
//                continue;
//            }
//            File file = convert(multipartFile);
//            sftpGateway.upload(file);
//            if (deleteSource) {
//                file.delete();
//            }
//        }
//    }
//
//    /**
//     * 批量上传 (MultipartFile)
//     *
//     * @param files List<MultipartFile>
//     * @throws IOException
//     */
//    public void uploadFiles(List<MultipartFile> files) throws IOException {
//        uploadFiles(files, true);
//    }
//
//    /**
//     * 单文件上传 (MultipartFile)
//     *
//     * @param multipartFile MultipartFile
//     * @throws IOException
//     */
//    public void uploadFile(MultipartFile multipartFile) throws IOException {
//        sftpGateway.upload(convert(multipartFile));
//    }
//
//
//    private static File convertInputStreamToFile(InputStream inputStream, String savePath) {
//        OutputStream outputStream = null;
//        File file = new File(savePath);
//        try {
//            outputStream = new FileOutputStream(file);
//            int read;
//            byte[] bytes = new byte[1024];
//            while ((read = inputStream.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, read);
//            }
//            log.info("convert InputStream to file done, savePath is : {}", savePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return file;
//    }
//
//    private static File convert(MultipartFile file) throws IOException {
//        File convertFile = new File(file.getOriginalFilename());
//        convertFile.createNewFile();
//        FileOutputStream fos = new FileOutputStream(convertFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convertFile;
//    }
//}
