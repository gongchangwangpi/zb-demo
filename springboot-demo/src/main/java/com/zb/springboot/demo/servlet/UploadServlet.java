package com.zb.springboot.demo.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/9/7
 */
@Slf4j
@WebServlet(urlPatterns = "/servlet/upload")
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            log.warn("request is not multipart");
            return;
        }
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        try {
            Map<String, List<FileItem>> parameterMap = servletFileUpload.parseParameterMap(req);

            for (Map.Entry<String, List<FileItem>> entry : parameterMap.entrySet()) {
                String paramName = entry.getKey();
                List<FileItem> fileItems = entry.getValue();
                for (FileItem fileItem : fileItems) {
                    if (fileItem.isFormField()) {
                        // 普通表单元素
                        String data = fileItem.getString("UTF-8");
                        log.info("{} = {}", paramName, data);
                    } else {
                        // 文件
                        String fileName = fileItem.getName();
                        log.info("{} is {}", paramName, fileName);
                        fileItem.write(new File("D:\\Test\\" + fileName));
                    }
                }
            }

            resp.getOutputStream().write("OK".getBytes());
        } catch (Exception e) {
            log.error("文件上传失败", e);
            resp.getOutputStream().write("Fail".getBytes());
        }
    }
}
