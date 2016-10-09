package y2016.m05.day20160523;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-23 AM11:45
 */
public class TestTableMeta {
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
    private Object getTableMeta() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                ResultSet resultSet = connection.getMetaData().getTables("dc", null, "DC_REQUIREMENT", new String[] {"TABLE"});
                try {
                    PrintWriter printWriter = new PrintWriter("C:\\Users\\Administrator\\Desktop\\constant\\tableMeta.txt");
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
                        System.out.println("meizhaodao");
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

    /*
     *  <LI><B>TABLE_CAT</B> String => table catalog (may be <code>null</code>)
     *  <LI><B>TABLE_SCHEM</B> String => table schema (may be <code>null</code>)
     *  <LI><B>TABLE_NAME</B> String => table name
     *  <LI><B>COLUMN_NAME</B> String => column name
     *  <LI><B>DATA_TYPE</B> int => SQL type from java.sql.Types
     *  <LI><B>TYPE_NAME</B> String => Data source dependent type name,
     *  for a UDT the type name is fully qualified
     *  <LI><B>COLUMN_SIZE</B> int => column size.
     *  <LI><B>BUFFER_LENGTH</B> is not used.
     *  <LI><B>DECIMAL_DIGITS</B> int => the number of fractional digits. Null is returned for data types where
     * DECIMAL_DIGITS is not applicable.
     *  <LI><B>NUM_PREC_RADIX</B> int => Radix (typically either 10 or 2)
     *  <LI><B>NULLABLE</B> int => is NULL allowed.
     *      <UL>
     *      <LI> columnNoNulls - might not allow <code>NULL</code> values
     *      <LI> columnNullable - definitely allows <code>NULL</code> values
     *      <LI> columnNullableUnknown - nullability unknown
     *      </UL>
     *  <LI><B>REMARKS</B> String => comment describing column (may be <code>null</code>)
     *  <LI><B>COLUMN_DEF</B> String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be <code>null</code>)
     *  <LI><B>SQL_DATA_TYPE</B> int => unused
     *  <LI><B>SQL_DATETIME_SUB</B> int => unused
     *  <LI><B>CHAR_OCTET_LENGTH</B> int => for char types the
     *       maximum number of bytes in the column
     *  <LI><B>ORDINAL_POSITION</B> int => index of column in table
     *      (starting at 1)
     *  <LI><B>IS_NULLABLE</B> String  => ISO rules are used to determine the nullability for a column.
     *       <UL>
     *       <LI> YES           --- if the column can include NULLs
     *       <LI> NO            --- if the column cannot include NULLs
     *       <LI> empty string  --- if the nullability for the
     * column is unknown
     *       </UL>
     *  <LI><B>SCOPE_CATALOG</B> String => catalog of table that is the scope
     *      of a reference attribute (<code>null</code> if DATA_TYPE isn't REF)
     *  <LI><B>SCOPE_SCHEMA</B> String => schema of table that is the scope
     *      of a reference attribute (<code>null</code> if the DATA_TYPE isn't REF)
     *  <LI><B>SCOPE_TABLE</B> String => table name that this the scope
     *      of a reference attribute (<code>null</code> if the DATA_TYPE isn't REF)
     *  <LI><B>SOURCE_DATA_TYPE</B> short => source type of a distinct type or user-generated
     *      Ref type, SQL type from java.sql.Types (<code>null</code> if DATA_TYPE
     *      isn't DISTINCT or user-generated REF)
     *   <LI><B>IS_AUTOINCREMENT</B> String  => Indicates whether this column is auto incremented
     *       <UL>
     *       <LI> YES           --- if the column is auto incremented
     *       <LI> NO            --- if the column is not auto incremented
     *       <LI> empty string  --- if it cannot be determined whether the column is auto incremented
     *       </UL>
     *   <LI><B>IS_GENERATEDCOLUMN</B> String  => Indicates whether this is a generated column
     *       <UL>
     *       <LI> YES           --- if this a generated column
     *       <LI> NO            --- if this not a generated column
     *       <LI> empty string  --- if it cannot be determined whether this is a generated column
     *       </UL>
     *  </OL>
     */
    private Object getTableColumns() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                ResultSet resultSet = connection.getMetaData().getColumns(null, null, null, null);
                try {
                    PrintWriter printWriter = new PrintWriter("C:\\Users\\Administrator\\Desktop\\constant\\tableColumns.txt");
                    String tableName = null;
                    boolean flag = false;
                    if (resultSet.next()) {
//                        ResultSetMetaData metaData = resultSet.getMetaData();
//                        printWriter.println(metaData.toString());
                        if(tableName == null) {
                            tableName = resultSet.getString("TABLE_NAME");
                        } else {
                            String currName = resultSet.getString("TABLE_NAME");
                            if(!tableName.equals(currName)) {
                                tableName = currName;
                                flag = true;
                            }
                        }

                        if(flag) {
                            printWriter.println();
                            flag = false;
                        }

                        StringBuffer sb = new StringBuffer();
                        sb.append(resultSet.getString("TABLE_CAT")).append("\t\t");
                        sb.append(resultSet.getString("TABLE_SCHEM")).append("\t\t");
                        sb.append(String.format("%1$-30s", tableName));
                        sb.append(String.format("%1$-30s", resultSet.getString("COLUMN_NAME")));
                        sb.append(String.format("%1$-30s", resultSet.getString("DATA_TYPE")));
                        printWriter.println(sb.toString());
                    } else {
                        System.out.println("没找到");
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

    public void getPrimaryKey() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            @Override
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                return null;
            }
        });
    }

    /**
     * <LI><B>TABLE_CAT</B> String => table catalog (may be <code>null</code>)
     *  <LI><B>TABLE_SCHEM</B> String => table schema (may be <code>null</code>)
     *  <LI><B>TABLE_NAME</B> String => table name
     *  <LI><B>NON_UNIQUE</B> boolean => Can index values be non-unique.
     *      false when TYPE is tableIndexStatistic
     *  <LI><B>INDEX_QUALIFIER</B> String => index catalog (may be <code>null</code>);
     *      <code>null</code> when TYPE is tableIndexStatistic
     *  <LI><B>INDEX_NAME</B> String => index name; <code>null</code> when TYPE is
     *      tableIndexStatistic
     *  <LI><B>TYPE</B> short => index type:
     *      <UL>
     *      <LI> tableIndexStatistic - this identifies table statistics that are
     *           returned in conjuction with a table's index descriptions
     *      <LI> tableIndexClustered - this is a clustered index
     *      <LI> tableIndexHashed - this is a hashed index
     *      <LI> tableIndexOther - this is some other style of index
     *      </UL>
     *  <LI><B>ORDINAL_POSITION</B> short => column sequence number
     *      within index; zero when TYPE is tableIndexStatistic
     *  <LI><B>COLUMN_NAME</B> String => column name; <code>null</code> when TYPE is
     *      tableIndexStatistic
     *  <LI><B>ASC_OR_DESC</B> String => column sort sequence, "A" => ascending,
     *      "D" => descending, may be <code>null</code> if sort sequence is not supported;
     *      <code>null</code> when TYPE is tableIndexStatistic
     *  <LI><B>CARDINALITY</B> int => When TYPE is tableIndexStatistic, then
     *      this is the number of rows in the table; otherwise, it is the
     *      number of unique values in the index.
     *  <LI><B>PAGES</B> int => When TYPE is  tableIndexStatisic then
     *      this is the number of pages used for the table, otherwise it
     *      is the number of pages used for the current index.
     *  <LI><B>FILTER_CONDITION</B> String => Filter condition, if any.
     *      (may be <code>null</code>)
     */
    public void getIndexInfo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        jdbcTemplate.execute(new ConnectionCallback<Object>() {
            @Override
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                ResultSet resultSet = connection.getMetaData().getIndexInfo(null, null, "", true, true);
                while (resultSet.next()) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(String.format("%-10s", resultSet.getString("TABLE_CAT")));
                }
                return null;
            }
        });
    }

    @Test
    public void testGetPrimaryKey() {
        getPrimaryKey();
    }


    @Test
    public void testGetDataSource() throws SQLException {
        DataSource dataSource = getDataSource();
        System.out.println(dataSource.getConnection().toString());
    }

    @Test
    public void testGetTableMeta() {
        getTableMeta();
    }

    @Test
    public void testGetTableColumns() {
        getTableColumns();
    }
}
