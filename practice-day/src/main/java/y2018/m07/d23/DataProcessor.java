package y2018.m07.d23;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import y2016.m08.day20160825.Closer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataProcessor {
    private static Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    /**
     * 统计条数sql
     */
    private String countSql;

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 分页条数
     */
    private int pageSize;

    /**
     * 线程池
     */
    private ExecutorService executorService;

    /**
     * 线程数目
     */
    private int threadNum;

    /**
     * 是否启动
     */
    private volatile boolean started;

    /**
     * 构造
     *
     * @param dataSource    数据源
     * @param countSql      统计条数sql
     * @param params        统计条数sql的参数
     * @param pageSize      分页的条数
     * @param threadNum     线程池数目
     * @throws SQLException
     */
    public DataProcessor(DataSource dataSource, String countSql, Object[] params, int pageSize, int threadNum) throws SQLException {
        this.dataSource = dataSource;
        this.countSql = countSql;
        this.pageSize = pageSize;
        this.totalPage = countTotalPage(params);
        this.threadNum = threadNum;
        this.executorService = new ThreadPoolExecutor(threadNum, threadNum, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }

    /**
     * 仅用于测试，可删除
     *
     * @param totalPage
     * @param pageSize
     * @param threadNum
     */
    public DataProcessor(int totalPage, int pageSize, int threadNum) {
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.threadNum = threadNum;
        this.executorService = new ThreadPoolExecutor(threadNum, threadNum, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }

    private int countTotalPage(Object[] params) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(countSql);
            if (params != null && params.length > 0) {
                for (Object param : params) {
                    preparedStatement.setObject(1, param);
                }
            }
            resultSet = preparedStatement.executeQuery();
            int totalPage = 0;
            if (resultSet.next()) {
                totalPage = resultSet.getInt(0);
            }

            return totalPage;
        } finally {
            Closer.closeQuietly(resultSet);
            Closer.closeQuietly(preparedStatement);
            Closer.closeQuietly(connection);
        }

    }

    public void execute(final ProcessorTask processorTask)  {
        if (!started) {
            logger.warn("dataProcessor尚未启动");
            return;
        }

        try {
            Semaphore semaphore = new Semaphore(threadNum);
            for (int i = 0; i < totalPage; i++) {
                final int finalI = i;
                semaphore.acquire();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processorTask.process(finalI, pageSize);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            semaphore.release();
                        }
                    }
                });
            }

            while (semaphore.availablePermits() != threadNum) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public void start() {
        if (!started) {
            started = true;
        } else {
            logger.warn("dataProcessor已经启动");
        }
    }

    public void stop() {
        if (started) {
            started = false;
            executorService.shutdownNow();
        } else {
            logger.warn("dataProcessor已经关停");
        }
    }



    interface ProcessorTask {
        /**
         * 编写你处理的任务
         *
         * @param pageNumber    分页页数
         * @param pageSize      分页条数
         */
        void process(int pageNumber, int pageSize);
    }


    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor(10, 10, 2);
        dataProcessor.start();
        dataProcessor.execute(new ProcessorTask() {
            @Override
            public void process(int pageNumber, int pageSize) {
                System.out.println(100);
            }
        });

        dataProcessor.stop();
    }


}
