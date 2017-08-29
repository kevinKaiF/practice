package y2017.m08.d19;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/8/19
 */
public class TestString {
    @Test
    public void testString() {
        String queryAreaTemplate = "SELECT\n"
                                   + "   t3.ID AS purchaseId,\n"
                                   + "   t3.AREA AS area,\n"
                                   + "   t3.CITY AS city,\n"
                                   + "   tcrd.`VALUE` AS county\n"
                                   + "FROM\n"
                                   + "   (\n"
                                   + "      SELECT\n"
                                   + "         t2.ID, t2.AREA, t2.COUNTY, tcrd.`VALUE` AS CITY\n"
                                   + "      FROM\n"
                                   + "         (\n"
                                   + "            SELECT\n"
                                   + "               t1.ID, t1.CITY, t1.COUNTY, tcrd.`VALUE` AS AREA\n"
                                   + "            FROM\n"
                                   + "               (SELECT ID, COUNTRY, AREA, CITY, COUNTY FROM t_reg_company WHERE ID IN (%s) AND TYPE = 12 AND COUNTRY IS NOT NULL) t1\n"
                                   + "            JOIN t_reg_center_dict tcrd ON t1.AREA = tcrd.`KEY`\n"
                                   + "            WHERE\n"
                                   + "               tcrd.TYPE = 'country'\n"
                                   + "         ) t2\n"
                                   + "      JOIN t_reg_center_dict tcrd ON t2.CITY = tcrd.`KEY`\n"
                                   + "      WHERE\n"
                                   + "         tcrd.TYPE = 'country'\n"
                                   + "   ) t3\n"
                                   + "JOIN t_reg_center_dict tcrd ON t3.COUNTY = tcrd.`KEY`\n"
                                   + "WHERE\n"
                                   + "   tcrd.TYPE = 'country'";
        List<Long> ids = new ArrayList<>();
        ids.add(100L);
        ids.add(100L);
        ids.add(100L);

        System.out.println(String.format(queryAreaTemplate, StringUtils.collectionToCommaDelimitedString(ids)));
    }
}
