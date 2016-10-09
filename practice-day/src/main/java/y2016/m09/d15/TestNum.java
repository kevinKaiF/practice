package y2016.m09.d15;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;

import javax.sql.DataSource;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-09-19 PM02:09
 */
public class TestNum {
    @Test
    public void testNum() {
        System.out.println(0xFF | 0x00);
        System.out.println(0xFF & 0x00);
        System.out.println(0xFF ^ 0xFF);
        System.out.println(0xFF ^ 0x00);
        System.out.println(~0xFF);
    }

    public SqlSessionFactoryBean createSqlSessionFactory(DataSource dataSource,String dsKey) {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean(dsKey);
        sqlSessionFactory.setDataSource(dataSource);
        try {
            sqlSessionFactory.setMapperLocations(null/*getCurrentScanResource()*/);
            sqlSessionFactory
                    .setTypeAliasesPackage("classpath:cn.bidlink.**.model");

            Interceptor[] plugins = new Interceptor[1];
            Interceptor inter = null/*new OffsetLimitInterceptor()*/;

            if(dataSource instanceof DruidDataSource){
                DruidDataSource druidDataSource = (DruidDataSource) dataSource;
                String dbType = druidDataSource.getDbType();
                if("mysql".equalsIgnoreCase(dbType)) {//MySQL方言设置
//                    inter.setDialect(cn.bidlink.framework.dao.ibatis.dialect.MySQLDialect.class.newInstance());
                } else if("oracle".equalsIgnoreCase(dbType)){//Oracle方言设置
//                    inter.setDialect(cn.bidlink.framework.dao.ibatis.dialect.OracleDialect.class.newInstance());
                }
                // TODO 其他库支持
            }

//			inter.setDialect(cn.bidlink.framework.dao.ibatis.dialect.MySQLDialect.class
//					.newInstance());
//            inter.setFilterParam(DsConstants.IS_ENABLE_DB_WALLFILTER);
            plugins[0] = inter;

            sqlSessionFactory.setPlugins(plugins);
            sqlSessionFactory.afterPropertiesSet();
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        return sqlSessionFactory;
    }
}
