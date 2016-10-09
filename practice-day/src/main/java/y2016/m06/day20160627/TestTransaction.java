package y2016.m06.day20160627;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-06-27 PM01:16
 */
public class TestTransaction {
    private DataSource getDataSource() {
        int maxWait = 5 * 1000;
        int minIdle = 0;
        int initialSize = 0;
        int maxActive = 32;
        String url = "jdbc:mysql://10.4.0.20:3306/dc?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";
        String userName = "tianyuan";
        String password = "s7GaCUG5";
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            dataSource.setUseUnfairLock(true);
            dataSource.setNotFullTimeoutRetryCount(2);
            dataSource.setInitialSize(initialSize);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxActive(maxActive);
            dataSource.setMaxWait(maxWait);
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.addConnectionProperty("useServerPrepStmts", "false");
            dataSource.addConnectionProperty("rewriteBatchedStatements", "true");
            dataSource.addConnectionProperty("allowMultiQueries", "true");
            dataSource.addConnectionProperty("readOnlyPropagatesToServer", "false");
            dataSource.setValidationQuery("select 1");
            dataSource.setExceptionSorter("com.alibaba.druid.pool.vendor.MySqlExceptionSorter");
            dataSource.setValidConnectionCheckerClassName("com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker");
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 事务级别
     * Connection.TRANSACTION_NONE,                 不支持事务
     * Connection.TRANSACTION_READ_UNCOMMITTED,     允许脏读，非重复读取，幻读，读取尚未commit之前的数据
     * Connection.TRANSACTION_READ_COMMITTED,       禁止脏读，允许非重复读取，幻读，读取commit之后的数据，未commit的事务禁止其他事务访问该行
     * Connection.TRANSACTION_REPEATABLE_READ,      禁止脏读，非重复读取，允许幻读，读取事务禁止写事务，写事务禁止其他任何事务
     * Connection.TRANSACTION_SERIALIZABLE          禁止脏读，非重读读取，幻读，事务串行执行，禁止并发
     */
    @Test
    public void testTransaction() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            @Override
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                int isolation = connection.getTransactionIsolation();
                System.out.println(isolation);
                return null;
            }
        });

    }
}
