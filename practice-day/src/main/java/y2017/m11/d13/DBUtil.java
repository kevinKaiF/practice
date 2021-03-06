package y2017.m11.d13;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/10/12
 */
public class DBUtil {
    public static long count(DataSource dataSource, String sql, List<Object> params) {
        List<Map<String, Object>> results = query(dataSource, sql, params);
        return (long) results.get(0).values().iterator().next();
    }

    public static List<Map<String, Object>> query(DataSource dataSource, String sql, List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    map.put(columnLabel, resultSet.getObject(columnLabel));
                }
                results.add(map);
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement, connection);
        }
    }

    public static List<Map<String, Object>> query(Connection connection, String sql, List<Object> params) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    map.put(columnLabel, resultSet.getObject(columnLabel));
                }
                results.add(map);
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement);
        }
    }

    public static int execute(Connection connection, String sql, List<Object> params) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement);
        }
    }

    public static <T> T query(DataSource dataSource, String sql, List<Object> params, ResultSetCallback<T> callback) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            return callback.execute(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement, connection);
        }
    }

    public static <T> T query(Connection connection, String sql, List<Object> params, ResultSetCallback<T> callback) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                int size = params.size();
                for (int i = 0; i < size; i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            return callback.execute(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement);
        }
    }

    public static void batchExecute(DataSource dataSource, String sql, List<Map<String, Object>> results) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (Map<String, Object> result : results) {
                int i = 0;
                for (Object value : result.values()) {
                    preparedStatement.setObject(i + 1, value);
                    i++;
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement, connection);
        }
    }

    public static int execute(Connection connection, String sql) {
        return execute(connection, sql, null);
    }

    public static List<Map<String, Object>> query(Connection connection, String querySql) {
        return query(connection, querySql, null);
    }


    public interface ResultSetCallback<T> {
        T execute(ResultSet resultSet) throws SQLException;
    }


    private static void closeQuietly(ResultSet resultSet, PreparedStatement preparedStatement) {
        closeQuietly(resultSet, preparedStatement, null);
    }

    public static void closeQuietly(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static Object convertValue(String origin, int jdbcType) {
        if (origin != null) {
            try {
                switch (jdbcType) {
                    case 12:
                        return origin;
                    case 1:
                        return origin;
                    case -4:
                        return null; //不支持BLOB
                    case -1:
                        return origin;
                    case 4:
                        return !StringUtils.isEmpty(origin) ? Integer.valueOf(origin) : null;
                    case -6:
                        return !StringUtils.isEmpty(origin) ? Integer.valueOf(origin) : null;
                    case 5:
                        return !StringUtils.isEmpty(origin) ? Integer.valueOf(origin) : null;
                    case -7:
                        return !StringUtils.isEmpty(origin) ? Integer.valueOf(origin) : null;
                    case -5:
                        return !StringUtils.isEmpty(origin) ? Long.valueOf(origin) : null;
                    case 7:
                        return !StringUtils.isEmpty(origin) ? Float.valueOf(origin) : null;
                    case 8:
                        return !StringUtils.isEmpty(origin) ? Double.valueOf(origin) : null;
                    case 3:
                        return !StringUtils.isEmpty(origin) ? new BigDecimal(origin) : null;
                    case 91:
                        return !StringUtils.isEmpty(origin) ? Date.valueOf(origin) : null;
                    case 92:
                        return !StringUtils.isEmpty(origin) ? Time.valueOf(origin) : null;
                    case 93:
                        return !StringUtils.isEmpty(origin) ? DateTime.parse(origin, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate() : null;
                    case 2004:
                    case 2005:
                        return origin;
                    default:
                        throw new RuntimeException("not support jdbcType : " + jdbcType);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
