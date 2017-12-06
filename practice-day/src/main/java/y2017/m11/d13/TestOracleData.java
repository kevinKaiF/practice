package y2017.m11.d13;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import y2016.m08.day20160825.Closer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/13
 */
public class TestOracleData {
    private OracleDataSource oracleDataSource;
    @Before
    public void getOracleDataSource() throws SQLException {
        oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL("jdbc:oracle:thin:@10.1.0.10:1521:import");
        oracleDataSource.setUser("sms");
        oracleDataSource.setPassword("sms");
    }

    @Test
    public void testGetOracleData() throws SQLException {
        String sql = "SELECT ROWID FROM MLOG$_DATE_TEST";
        List<Map<String, Object>> query = DBUtil.query(oracleDataSource, sql, null);
        System.out.println(query);

        sql = "SELECT * FROM DATE_TEST WHERE ROWID = ?";
        List<Object> params = new ArrayList<>();
        params.add(query.get(0).get("ROWID"));
        List<Map<String, Object>> query1 = DBUtil.query(oracleDataSource, sql, params);
        System.out.println(query1);

        sql = "DELETE FROM DATE_TEST WHERE ROWID = ?";
        DBUtil.execute(oracleDataSource.getConnection(), sql, params);
    }

    @Test
    public void testGetClob() throws SQLException {
        String sql = "SELECT LOG_B FROM YUGONG_TEST WHERE ID = 23";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = oracleDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Reader reader = null;
                InputStream inputStream = null;
                try {
                    inputStream = resultSet.getBinaryStream("LOG_B");
                    if (inputStream != null) {
//                        byte[] x = IOUtils.toByteArray(inputStream);
//                        System.out.println(x);
//                        System.out.println(new String(x));
                        System.out.println(IOUtils.toString(inputStream));
                    }
//                    reader = resultSet.getCharacterStream("LOG_B");
//                    if (reader != null) {
//                        System.out.println(IOUtils.toString(reader));
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.closeQuietly(reader);
                    IOUtils.closeQuietly(inputStream);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(resultSet);
            Closer.closeQuietly(preparedStatement);
            Closer.closeQuietly(connection);
        }
    }

    @Test
    public void testSetClob() throws SQLException {
        String sql = "UPDATE SMS.YUGONG_TEST SET LOG_B=?  WHERE ID = '23'";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = oracleDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
//            String filePath = "F:\\xshell\\PS.txt";
//            FileInputStream inputStream = FileUtils.openInputStream(new File(filePath));
//            BLOB blob = new BLOB((OracleConnection) connection, IOUtils.toByteArray(inputStream));
            preparedStatement.setObject(1, "357".getBytes());
//            inputStream.close();
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            Closer.closeQuietly(preparedStatement);
            Closer.closeQuietly(connection);
        }
    }

    @Test
    public void testDate() {
        Date date1 = Date.valueOf("2017-10-11");
        System.out.println(date1.toString());
        java.util.Date date = DateTime.parse("1463328000000", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        System.out.println(date);
    }
}
