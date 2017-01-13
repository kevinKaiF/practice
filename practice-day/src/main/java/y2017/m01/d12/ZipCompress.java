package y2017.m01.d12;

import org.springframework.util.CollectionUtils;
import y2016.m08.day20160825.Closer;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/12
 */
public class ZipCompress {
    public static void zip(String zipFileName, File inputFile) {
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(
                    zipFileName));
            bo = new BufferedOutputStream(out);
            zip(out, inputFile, inputFile.getName(), bo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(out);
            Closer.closeQuietly(bo);
        }

    }

    public void zip(String zipFileName, List<File> files) throws IOException {
        zip(new File(zipFileName), files);
    }

    public static void zip(File zipFile, List<File> files) throws IOException {
        if (!CollectionUtils.isEmpty(files)) {
            BufferedOutputStream bo = null;
            try {
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                        zipFile));
                bo = new BufferedOutputStream(out);

                for (File inputFile : files) {
                    zip(out, inputFile, inputFile.getName(), bo);
                }

                bo.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Closer.closeQuietly(bo);
            }
        }
    }

    private static void zip(ZipOutputStream out, File file, String base,
                            BufferedOutputStream bo) throws IOException {
        if (file.isDirectory()) {
            File[] fl = file.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
        } else {
            BufferedInputStream bufferedInputStream = null;
            try {
                out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                int len = 0;
                while ((len = bufferedInputStream.read()) != -1) {
                    bo.write(len);
                }
                bo.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            } finally {
                Closer.closeQuietly(bufferedInputStream);
            }
        }
    }

    private static void zip(ZipOutputStream out, ByteArrayOutputStream byteArrayOutputStream, String base,
                            BufferedOutputStream bo) throws IOException {
        try {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            bo.write(byteArrayOutputStream.toByteArray());
            bo.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            Closer.closeQuietly(byteArrayOutputStream);
        }
    }

    public static void zip(File zipFile, Map<String, ByteArrayOutputStream> streamMap) {
        if (!CollectionUtils.isEmpty(streamMap)) {
            BufferedOutputStream bo = null;
            try {
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                        zipFile));
                bo = new BufferedOutputStream(out);

                for (Map.Entry<String, ByteArrayOutputStream> streamEntry : streamMap.entrySet()) {
                    zip(out, streamEntry.getValue(), streamEntry.getKey(), bo);
                }

                bo.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Closer.closeQuietly(bo);
            }
        }
    }
    public static void zip(ByteArrayOutputStream byteArrayOutputStream, Map<String, ByteArrayOutputStream> streamMap) {
        if (!CollectionUtils.isEmpty(streamMap)) {
            BufferedOutputStream bo = null;
            try {
                ZipOutputStream out = new ZipOutputStream(byteArrayOutputStream);
                bo = new BufferedOutputStream(out);

                for (Map.Entry<String, ByteArrayOutputStream> streamEntry : streamMap.entrySet()) {
                    zip(out, streamEntry.getValue(), streamEntry.getKey(), bo);
                }

                bo.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Closer.closeQuietly(bo);
            }
        }
    }


}
