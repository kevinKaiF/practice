package y2017.m01.d12;

import y2017.m01.d11.WordBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/12
 */
public class ZipCompressHandler {
    private List<File> wordFiles;
    private ExecutorService executorService;
    private String classPathDir;
    private String filePrefixName;
    private Map<String, ByteArrayOutputStream> streamMap;

    public ZipCompressHandler(String filePrefixName) {
        this.wordFiles = new ArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
        this.filePrefixName = filePrefixName;
        this.streamMap = new HashMap<>();
    }

    public void handle(List<ZipCompressEntity> zipCompressEntityList) throws IOException {
        final CountDownLatch latch = new CountDownLatch(zipCompressEntityList.size());
        // 1.生成word文件
        for (final ZipCompressEntity zipCompressEntity : zipCompressEntityList) {
            final File file = createFile(zipCompressEntity);
            wordFiles.add(file);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    latch.countDown();
                    WordBuilder builder = new WordBuilder(zipCompressEntity.getDataMap());
                    builder.build(file);
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            // ignore
        }
        System.out.println("finish build word!");

        try {
            Thread.sleep(5000);
            System.out.println("finish build word!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2.生成压缩文件
        File zipFile = getZipFile();
        ZipCompress.zip(zipFile, wordFiles);
        wordFiles.add(zipFile);
        System.out.println("finish build zip!");

        FileInputStream fileInputStream = new FileInputStream(zipFile);
        int len = 0;
        int size = 0;
        while ((len = fileInputStream.read()) != -1) {
            size += len;
        }

        System.out.println("size : " + size);
        fileInputStream.close();

        // 3.删除所有的临时文件
        asyncRemoveFile();
    }

    public void handle1(List<ZipCompressEntity> zipCompressEntityList) throws IOException {
        final CountDownLatch latch = new CountDownLatch(zipCompressEntityList.size());
        // 1.生成word文件
        for (final ZipCompressEntity zipCompressEntity : zipCompressEntityList) {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            streamMap.put(zipCompressEntity.getReviewerName() + ".docx", outputStream);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    latch.countDown();
                    WordBuilder builder = new WordBuilder(zipCompressEntity.getDataMap());
                    builder.build(outputStream);
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            // ignore
        }
        System.out.println("finish build word!");

        try {
            Thread.sleep(5000);
            System.out.println("finish build word!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2.生成压缩文件
        File zipFile = getZipFile();
        ZipCompress.zip(zipFile, streamMap);
        System.out.println("finish build zip!");

        FileInputStream fileInputStream = new FileInputStream(zipFile);
        int len = 0;
        int size = 0;
        while ((len = fileInputStream.read()) != -1) {
            size += len;
        }

        System.out.println("size : " + size);
        fileInputStream.close();

        // 3.删除所有的临时文件
        asyncRemoveFile();
    }

    private File createFile(ZipCompressEntity zipCompressEntity) throws IOException {
        final File file = new File(getClassPathDir(), getFullFileName(zipCompressEntity));
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private void asyncRemoveFile() {
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                if (!CollectionUtils.isEmpty(wordFiles)) {
//                    for (File wordFile : wordFiles) {
//                        wordFile.delete();
//                    }
//                }
//            }
//        });

        executorService.shutdown();
    }

    private File getZipFile() throws IOException {
        String zipFileName = filePrefixName + "-" + System.currentTimeMillis() + ".zip";
        File zipFile = new File(getClassPathDir(), zipFileName);
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        return zipFile;
    }

    private String getFullFileName(ZipCompressEntity zipCompressEntity) {
        return filePrefixName + "-" + zipCompressEntity.getReviewerName() + ".docx";
    }

    public String getClassPathDir() {
        if (classPathDir == null) {
            classPathDir = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
        }
        return classPathDir;
    }

    public static void main(String[] args) throws IOException {
        List<ZipCompressEntity> zipCompressEntities = new ArrayList<>();
        zipCompressEntities.add(new ZipCompressEntity("XX项目评审1", new EnumMap<WordBuilder.DataKey, String>(WordBuilder.DataKey.class)));
        zipCompressEntities.add(new ZipCompressEntity("XX项目评审2", new EnumMap<WordBuilder.DataKey, String>(WordBuilder.DataKey.class)));
        zipCompressEntities.add(new ZipCompressEntity("XX项目评审3", new EnumMap<WordBuilder.DataKey, String>(WordBuilder.DataKey.class)));

        ZipCompressHandler zipCompressHandler = new ZipCompressHandler("TEST");
        zipCompressHandler.handle1(zipCompressEntities);
    }
}
