package y2016.m11.d04;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/10
 */
public class TestMeta {
    private static DataSource getDataSource() {
        int maxWait = 5 * 1000;
        int minIdle = 0;
        int initialSize = 0;
        int maxActive = 32;
        String url = "jdbc:mysql://192.168.2.170:3306/dc?useUnicode=true&characterEncoding=gbk&allowMultiQueries=true";
        String userName = "root";
        String password = "123456";
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
     * 获取metaData中的table元数据
     *
     * metaData中一共有这么多列
     * TABLE_CAT                        一个catlog拥有多个schema（oracle，mysql不支持catlog）
     * TABLE_SCHEM                      一个schema拥有多个table
     * TABLE_NAME                       表名称
     * TABLE_TYPE                       表类型，"TABLE","VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY","LOCAL TEMPORARY", "ALIAS", "SYNONYM"
     * REMARKS                          表的备注信息
     * TYPE_CAT                         catlog的类型，可以为null
     * TYPE_SCHEM                       schema的类型，可以为null
     * TYPE_NAME                        name的类型，可以为null
     * SELF_REFERENCING_COL_NAME        typed table的指定列名称
     * REF_GENERATION                   指定SELF_REFERENCING_COL_NAM创建值，"SYSTEM", "USER", "DERIVED"
     * @return
     */
    private static Object getTableMeta() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                ResultSet resultSet = connection.getMetaData().getTables("bjerp", null, "cti_call_log", new String[] {"TABLE"});
                try {
                    PrintWriter printWriter = new PrintWriter("C:\\Users\\Administrator\\Desktop\\tableMeta.txt");
                    printWriter.write("TABLE_CAT\tTABLE_SCHEM\tTABLE_NAME\tTABLE_TYPE\tTABLE_REMARKS\tTYPE_CAT\tTYPE_SCHEM\tTYPE_NAME\tSELF_REFERENCING_COL_NAME\tREF_GENERATION\n\n");
                    // 必须使用next方法来指定resultSet的游标，否则会抛出异常信息
                    // error example
//                    resultSet.getString(1);
                    printWriter.println(resultSet.getMetaData());
                    if (resultSet.next()) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        printWriter.println(metaData.toString());
                        StringBuffer sb = new StringBuffer();
                        sb.append(resultSet.getString("TABLE_CAT")).append("\t\t\t");
                        sb.append(resultSet.getString("TABLE_SCHEM")).append("\t\t\t");
                        sb.append(String.format("%1$-40s", resultSet.getString("TABLE_NAME")));
                        sb.append(String.format("%1$-10s", resultSet.getString("TABLE_TYPE")));
                        sb.append(String.format("%1$-10s", resultSet.getString("REMARKS")));
                        sb.append(String.format("%1$-10s", resultSet.getString("TYPE_CAT")));
                        sb.append(String.format("%1$-10s", resultSet.getString("TYPE_SCHEM")));
                        sb.append(String.format("%1$-10s", resultSet.getString("TYPE_NAME")));
                        sb.append(String.format("%1$-10s", resultSet.getString("SELF_REFERENCING_COL_NAME")));
                        sb.append(resultSet.getString("REF_GENERATION"));
                        printWriter.println(sb.toString());
                        System.out.println(resultSet.getMetaData().toString());
                    } else {
                        System.out.println("not found!");
                    }
                    printWriter.flush();
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        return null;
    }

    public static void main(String[] args) {
        getTableMeta();
    }
}
