package cn.bid.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-17 AM10:50
 */
public class FormServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        String fileName = req.getParameter("fileName");
//        String encoding = req.getCharacterEncoding();
//        System.out.println(fileName);
//        if (fileName != null && fileName.trim() != "") {
//            String[] fileNames = fileName.split("/");
//            OutputStream out = resp.getOutputStream();
//            // 如果不设置contentType，浏览器直接将图片下载到浏览器页面中
//            // 如果需要通过浏览器下载到本地，不仅要设置contentType还要设置header
//            resp.setContentType("image/*;charset=utf-8");
//            String path = this.getClass().getResource("/").getPath();
//            String filePath = path.substring(0, path.indexOf("WEB-INF")) + "image/1标书文件-原文件.png";
//            FileInputStream in = null;
//            try {
//                setResponseHeader(resp, req, fileNames[0]);
//                in = new FileInputStream(filePath);
//                byte[] bytes = new byte[1024];
//                int count = 0;
//                while ((count = in.read(bytes)) != -1) {
//                    out.write(bytes, 0 , count);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                in.close();
//                out.flush();
//                out.close();
//            }
//
//            out.write(new String(fileName.getBytes(), "ISO-8859-1").getBytes("ISO-8859-1"));
//            out.flush();
//            out.close();
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fileName = req.getParameter("fileName");
        if (fileName != null && fileName.trim() != "") {
            String path = this.getClass().getResource("/").getPath();
            String baseDir = path.substring(0, path.indexOf("WEB-INF")) + "image/";
            String filePath = baseDir + "1标书文件-原文件.png";
            String[] fileNames = fileName.split("/");
            String zipFileName = generateZipFile(fileNames, filePath, baseDir);
            OutputStream out = resp.getOutputStream();
            resp.setContentType("application/zip;charset=utf-8");
            BufferedInputStream in = null;
            try {
                setResponseHeader(resp, req, zipFileName);
                in = new BufferedInputStream(
                        new FileInputStream(baseDir + zipFileName)
                );
                int count = 0;
                byte[] bytes = new byte[1024];
                while ((count = in.read(bytes)) != -1) {
                    out.write(bytes, 0, count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                in.close();
                out.flush();
                out.close();
            }
        }
    }

    private String generateZipFile(String[] fileNames, String filePath, String baseDir) {
        String zipFileName = "批量下载.zip";
        try {
            ZipOutputStream out = null;
            try {
                out = new ZipOutputStream(new FileOutputStream(baseDir + zipFileName));
                for (String newFileName : fileNames) {
                    compressFile(new File(filePath), out, newFileName);
                }

            } finally {
                if (out != null) {
                    try {
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return zipFileName;
    }

    private void setResponseHeader(HttpServletResponse response, HttpServletRequest request, String fileName) throws Exception {
        if (request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > 0) {
            //火狐浏览器
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        }
    }

    private void compressFile(File file, ZipOutputStream out, String newFileName) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            ZipEntry entry = new ZipEntry(newFileName);
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[8192];
            while ((count = bis.read(data, 0, 8192)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
