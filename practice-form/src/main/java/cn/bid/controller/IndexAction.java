package cn.bid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/15
 */
@Controller
public class IndexAction {

    @RequestMapping("index.do")
    public ModelAndView index(ModelAndView modelAndView, @RequestParam Map param) {
        System.out.println(param);
        modelAndView.setViewName("hello");
        modelAndView.addObject("hello", "hello world");
        return modelAndView;
    }

    @RequestMapping("form.do")
    public String form(@RequestParam(value = "image", required = false) MultipartFile multipartFile, @RequestParam Map param, HttpServletRequest request) {
        System.out.println(param);
        System.out.println(request.getParameter("fileName"));
        return "forward:index.do";
    }

    @RequestMapping("download.do")
    public void download(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OutputStream out = null;
        try {
            req.setCharacterEncoding("UTF-8");
            String path = this.getClass().getResource("/").getPath();
            String baseDir = path.substring(0, path.indexOf("WEB-INF")) + "image/";
            String newFileName = "1标书文件-原文件.png";
            String filePath = baseDir + newFileName;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String zipFileName = generateZipFile(newFileName, filePath, byteArrayOutputStream);
            setResponseHeader(resp, req, zipFileName);
            out = resp.getOutputStream();
            out.write(byteArrayOutputStream.toByteArray());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String generateZipFile(String newFileName, String filePath, ByteArrayOutputStream byteArrayOutputStream) {
        String zipFileName = "批量下载=我= .zip";
        ZipOutputStream out = null;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
            int available = bufferedInputStream.available();
            byte[] bytes = new byte[available];
            bufferedInputStream.read(bytes);
            out = new ZipOutputStream(byteArrayOutputStream);
            out.putNextEntry(new ZipEntry(newFileName));
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return zipFileName;
    }

    private void setResponseHeader(HttpServletResponse response, HttpServletRequest request, String fileName) throws Exception {
//        response.setContentType("application/zip;charset=utf-8");
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (userAgent.indexOf("msie") > 0 || userAgent.indexOf("rv:11.0") > -1) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        }
    }

    /**
     * 获取ie浏览器的版本号
     *
     * @param request
     * @return
     */
    private static Double getIEVersion(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT");
        Pattern iePattern = Pattern.compile("MSIE\\s+(\\d+\\.\\d+)");
        Matcher matcher = iePattern.matcher(userAgent);
        Double ieVersion = null;
        if (matcher.find()) {
            ieVersion = Double.valueOf(matcher.group(1));
        }
        return ieVersion;
    }

    public String encode(String fileName) {
        char[] chars = fileName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char chr : chars) {
            boolean needEncode = needEncode(chr);
            if (needEncode) {
                sb.append(toHexString(chr));
            } else {
                sb.append(fileName);
            }
        }
        return sb.toString();
    }

    private boolean needEncode(char chr) {
        String reservedChars = "$-_.+!*'(),@=&";
        if (chr > 127) {
            return true;
        }

        if (Character.isLetterOrDigit(chr) || reservedChars.indexOf(chr) >= 0) {
            return false;
        }
        return true;
    }

    private String toHexString(char chr) {
        return "%" + Integer.toHexString(chr);
    }


    @InitBinder
    public void log() {
        System.out.println("executing init binder");
    }
}
