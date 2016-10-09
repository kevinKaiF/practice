package y2016.m05.day20160524;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-05-24 AM11:12
 */
public class TestScanXml {
    //    private final Pattern sqlPattern = Pattern.compile(".*FROM\\s*([^)(]]+)\\s*WHERE\\s*\\w+(?!NOT\\s+LIKE|not\\s+like)(?=NOT\\s+LIKE|not\\s+like)]");
    private final Pattern sqlPattern = Pattern.compile("(.*FROM\\s*([^\\)\\(]+)\\s*WHERE[^)]+\\s+NOT\\s+LIKE\\s+.*(?!AND|and))+");
//    private final Pattern sqlPattern = Pattern.compile(".*FROM.*");
//    private final Pattern sqlPattern = Pattern.compile("SELECT.*FROM\\s*([^\\)\\(]+)\\s*WHERE[^)]+\\s+NOT\\s+LIKE\\s+.*(?!AND|and)");
    private final Pattern notLikePattern = Pattern.compile("NOT\\s+LIKE|not\\s+like");

    @Test
    public void testScanXml() {
        File directory = new File("C:\\Users\\Administrator\\Desktop\\constant");
        File[] xmlFiles = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".xml") || name.endsWith(".XML")) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        if (xmlFiles != null && xmlFiles.length > 0) {
            // 暂时定为map
            Map<String, Map<String, Set<String>>> notLikeMap = new HashMap<String, Map<String, Set<String>>>();
            for (File xmlFile : xmlFiles) {
                parseXml(xmlFile, notLikeMap);
            }
        }
    }

    private void parseXml(File xmlFile, Map<String, Map<String, Set<String>>> notLikeMap) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(xmlFile);
            Element element = document.getRootElement();
            List<Element> selects = new ArrayList<Element>();
            selects.addAll(element.getChildren("SELECT"));
            selects.addAll(element.getChildren("select"));

            if (selects.size() > 0) {
                for (Element select : selects) {
                    String selectSql = select.getValue();
                    Matcher matcher = sqlPattern.matcher(selectSql);
                    while (matcher.find()) {
                        String matchStr = matcher.group();
                        System.out.println(matchStr);
                        String[] splitArr = matchStr.split("WHERE|where");
                        Map<String, String> tableMap = parseTable(splitArr[0]);
                        parseWhere(splitArr[1], tableMap, notLikeMap);
                    }
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testPattern() {
        String sql = "\n" +
                "    select * from (\n" +
                "\t\tSELECT\n" +
                "\t\t\ta.comp_id AS compId,\n" +
                "\t\t\ta.comp_name AS compName,\n" +
                "\t\t\tifnull(a.`总项目量`,0) AS projectCount,\n" +
                "\t\t\tifnull(b.`成交金额`,0) AS dealPrice,\n" +
                "\t\t\tifnull(c.总采购品数 ,0) AS purchaseCategory,\n" +
                "\t\t\tifnull(d.`归档项目公开数`,0) AS fileProjectOpenCount,\n" +
                "\t\t\tifnull(e.`归档项目非公开数`,0) fileProjectNoOpenCount,\n" +
                "\t\t\tifnull(f.`归档项目总数`,0) AS fileProjectCount,\n" +
                "\t\t\tifnull(g.`新建项目公开数`,0) AS newProjectOpenCount,\n" +
                "\t\t\tifnull(h.`新建项目非公开数`,0) AS newProjectNotOpenCount,\n" +
                "\t\t\tifnull(i.`新建项目总数`,0) AS newProjectCount,\n" +
                "\t\t\tifnull(j.`公开项目成交金额`,0) AS openProjectDealPrice,\n" +
                "\t\t\tifnull(k.`非公开项目成交金额`,0) AS notOpenProjectDealPrice\n" +
                "\t\tFROM\n" +
                "\t\t\t(\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\t\tt.comp_id,\n" +
                "\t\t\t\t\tt.comp_name,\n" +
                "\t\t\t\t\tt.create_time,\n" +
                "\t\t\t\t\tIFNULL(t1.count1, 0) + IFNULL(t1.count2, 0) AS 总项目量\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\t(\n" +
                "\t\t\t\t\t\tSELECT\n" +
                "\t\t\t\t\t\t\tcomp_id,\n" +
                "\t\t\t\t\t\t\tcomp_name,\n" +
                "\t\t\t\t\t\t\tcreate_time\n" +
                "\t\t\t\t\t\tFROM\n" +
                "\t\t\t\t\t\t\tbs_yc_project\n" +
                "\t\t\t\t\t\tWHERE\n" +
                "\t\t\t\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\t\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\t\t\t\tAND source_project_type = 2 \n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\tGROUP BY\n" +
                "\t\t\t\t\t\t\tcomp_id\n" +
                "\t\t\t\t\t) t\n" +
                "\t\t\t\tLEFT JOIN (\n" +
                "\t\t\t\t\tSELECT\n" +
                "\t\t\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\t\tARCHIVE_TIME >= #{statisticsDateBegin} AND ARCHIVE_TIME <= #{statisticsDateEnd} AND PROJECT_STATUS = 9) count1,\n" +
                "\t\t\t\t\t\tsum(\n" +
                "\t\t\t\t\t\t\tCREATE_TIME <= #{statisticsDateEnd} AND (ARCHIVE_TIME > #{statisticsDateEnd} or PROJECT_STATUS <= 8)) count2\n" +
                "\t\t\t\t\tFROM\n" +
                "\t\t\t\t\t\tbs_yc_project\n" +
                "\t\t\t\t\tWHERE\n" +
                "\t\t\t\t\t\tNAME NOT LIKE '%test%'\n" +
                "\t\t\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\tGROUP BY\n" +
                "\t\t\t\t\t\tCOMP_ID\n" +
                "\t\t\t\t) t1 ON t.comp_id = t1.COMP_ID\n" +
                "\t\t) a\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\typ.COMP_ID,\n" +
                "\t\t\t\typ.COMP_NAME,\n" +
                "\t\t\t\tsum(ypqc.DEAL_TOTAL_PRICE) 成交金额\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project yp\n" +
                "\t\t\tINNER JOIN bs_yc_supplier_project_change yspc ON yp.id = yspc.PRO_ID\n" +
                "\t\t\tINNER JOIN bs_yc_pro_quote_change ypqc ON yspc.PRO_SUPPLIER_ID = ypqc.PRO_SUPPLIER_ID\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\typ.ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND yp.PROJECT_STATUS = 9\n" +
                "\t\t\tAND yspc.SUPPLIER_BID_STATUS = 6\n" +
                "\t\t\tAND yp.source_project_type = 2\n" +
                "\t\t\tAND ypqc.bid_status = 3\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND yp.NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp.NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.SUPPLIER_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%必联%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%bwd%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%北京世纪君远%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%供应商%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%悦采%'\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\typ.COMP_ID\n" +
                "\t\t) b ON a.COMP_ID = b.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\typ.COMP_ID,\n" +
                "\t\t\t\typ.COMP_NAME,\n" +
                "\t\t\t\tCOUNT(DISTINCT ypqc.directory_id) 总采购品数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project yp\n" +
                "\t\t\tINNER JOIN bs_yc_supplier_project_change yspc ON yp.id = yspc.PRO_ID\n" +
                "\t\t\tINNER JOIN bs_yc_pro_quote_change ypqc ON yspc.PRO_SUPPLIER_ID = ypqc.PRO_SUPPLIER_ID\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\typ.ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND yp.PROJECT_STATUS = 9\n" +
                "\t\t\tAND yspc.SUPPLIER_BID_STATUS = 6\n" +
                "\t\t\tAND yp.source_project_type = 2\n" +
                "\t\t\tAND ypqc.bid_status = 3\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND yp. NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp. NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.SUPPLIER_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%必联%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%bwd%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%北京世纪君远%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%供应商%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%悦采%'\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\typ.COMP_ID\n" +
                "\t\t) c ON a.COMP_ID = c.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\tcount(*) 归档项目公开数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\tAND ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND PROJECT_STATUS = 9\n" +
                "\t\t\tAND bid_show_type = 1\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tCOMP_ID\n" +
                "\t\t) d ON a.COMP_ID = d.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\tcount(*) 归档项目非公开数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\tAND ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND PROJECT_STATUS = 9\n" +
                "\t\t\tAND (bid_show_type != 1 OR bid_show_type IS NULL)\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tCOMP_ID\n" +
                "\t\t) e ON a.COMP_ID = e.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\tcount(*) 归档项目总数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\tAND ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND PROJECT_STATUS = 9\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tCOMP_ID\n" +
                "\t\t) f ON a.COMP_ID = f.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\tcount(*) 新建项目公开数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\tAND CREATE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND bid_show_type = 1\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tCOMP_ID\n" +
                "\t\t) g ON a.COMP_ID = g.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\tcount(*) 新建项目非公开数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\tAND CREATE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND (bid_show_type != 1 OR bid_show_type IS NULL)\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tCOMP_ID\n" +
                "\t\t) h ON a.COMP_ID = h.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tCOMP_ID,\n" +
                "\t\t\t\tCOMP_NAME,\n" +
                "\t\t\t\tcount(*) 新建项目总数\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\tCOMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND SOURCE_PROJECT_TYPE = 2\n" +
                "\t\t\tAND CREATE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\tCOMP_ID\n" +
                "\t\t) i ON a.COMP_ID = i.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\typ.COMP_ID,\n" +
                "\t\t\t\typ.COMP_NAME,\n" +
                "\t\t\t\tsum(ypqc.DEAL_TOTAL_PRICE) 公开项目成交金额\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project yp\n" +
                "\t\t\tINNER JOIN bs_yc_supplier_project_change yspc ON yp.id = yspc.PRO_ID\n" +
                "\t\t\tINNER JOIN bs_yc_pro_quote_change ypqc ON yspc.PRO_SUPPLIER_ID = ypqc.PRO_SUPPLIER_ID\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\typ.ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND yp.PROJECT_STATUS = 9\n" +
                "\t\t\tAND yspc.SUPPLIER_BID_STATUS = 6\n" +
                "\t\t\tAND yp.source_project_type = 2\n" +
                "\t\t\tAND yp.BID_SHOW_TYPE = 1\n" +
                "\t\t\tAND ypqc.bid_status = 3\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND yp. NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp. NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.SUPPLIER_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%必联%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%bwd%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%北京世纪君远%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%供应商%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%悦采%'\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\typ.COMP_ID\n" +
                "\t\t) j ON a.COMP_ID = j.COMP_ID\n" +
                "\t\tLEFT JOIN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\typ.COMP_ID,\n" +
                "\t\t\t\typ.COMP_NAME,\n" +
                "\t\t\t\tsum(ypqc.DEAL_TOTAL_PRICE) 非公开项目成交金额\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tbs_yc_project yp\n" +
                "\t\t\tINNER JOIN bs_yc_supplier_project_change yspc ON yp.id = yspc.PRO_ID\n" +
                "\t\t\tINNER JOIN bs_yc_pro_quote_change ypqc ON yspc.PRO_SUPPLIER_ID = ypqc.PRO_SUPPLIER_ID\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\typ.ARCHIVE_TIME BETWEEN #{statisticsDateBegin} AND #{statisticsDateEnd}\n" +
                "\t\t\tAND yp.PROJECT_STATUS = 9\n" +
                "\t\t\tAND yspc.SUPPLIER_BID_STATUS = 6\n" +
                "\t\t\tAND yp.source_project_type = 2\n" +
                "\t\t\tAND (yp.BID_SHOW_TYPE != 1 or yp.BID_SHOW_TYPE is null)\n" +
                "\t\t\tAND ypqc.bid_status = 3\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp.COMP_NAME NOT LIKE '%必联%'\n" +
                "\t\t\tAND yp. NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yp. NAME NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.SUPPLIER_NAME NOT LIKE '%test%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%必联%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%测试%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%bwd%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%北京世纪君远%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%供应商%'\n" +
                "\t\t\tAND yspc.supplier_name NOT LIKE '%悦采%'\n" +
                "\t\t\t\n" +
                "\t\t\tGROUP BY\n" +
                "\t\t\t\typ.COMP_ID\n" +
                "\t\t) k ON a.COMP_ID = k.COMP_ID ) a\n" +
                "\t\torder by ${sortColumn},compId asc\n" +
                "    ";
        Matcher matcher = sqlPattern.matcher(sql);
        while (matcher.find()) {
            System.out.println(matcher.group());
//            System.out.println(matcher.group(1));
            System.out.println(matcher.groupCount());
//            String[] splitArr = matchStr.split("WHERE|where");
//            Map<String, String> tableMap = parseTable(splitArr[0]);
//            parseWhere(splitArr[1], tableMap);
        }
    }

    @Test
    public void testNotLikePattern() {
        String s = "123 NOT LIKE sdsd";
        Matcher matcher = notLikePattern.matcher(s);
        System.out.println(matcher.find());
    }

    private void parseWhere(String whereStr, Map<String, String> tableMap, Map<String, Map<String, Set<String>>> map) {
        String[] arr = whereStr.split("AND|and");
        String tableName = null;
        for (String str : arr) {
            if ((str.contains("NOT") && str.contains("LIKE")) || (str.contains("not") && str.contains("like"))) {
                String[] subArr = str.split("NOT\\s+LIKE|not\\s+like");
                final String tableDotCol = subArr[0].trim();
                final String content = subArr[1].trim();
                if (tableDotCol.contains(".")) {
                    String[] ar = tableDotCol.split("\\.");
                    String tableFullName = tableMap.get(ar[0]);
                    final String columnName = ar[1].toUpperCase(); // 将字段转为大写
                    Map<String, Set<String>> columnMap = map.get(tableFullName);

                    if (columnMap == null) {
                        Set<String> set = new HashSet<String>();
                        set.add(content);

                        columnMap = new HashMap<String, Set<String>>();
                        columnMap.put(columnName, set);
                        map.put(tableFullName, columnMap);
                    } else {
                        Set<String> set = columnMap.get(columnName);
                        if(set == null) {
                            set = new HashSet<String>();
                            columnMap.put(columnName, set);
                        }
                        set.add(content);
                    }
                } else {
                    if(tableName == null) {
                        Iterator<String> iterator = tableMap.keySet().iterator();
                        if(iterator.hasNext()) {
                            tableName = iterator.next();
                        }
                    }
                    String columnName = tableDotCol.toUpperCase();  // 将字段转为大写
                    Map<String, Set<String>> columnMap = map.get(tableName);

                    if (columnMap == null) {
                        Set<String> set = new HashSet<String>();
                        set.add(content);

                        columnMap = new HashMap<String, Set<String>>();
                        columnMap.put(columnName, set);
                        map.put(tableName, columnMap);
                    } else {
                        Set<String> list = columnMap.get(columnName);
                        if(list == null) {
                            list = new HashSet<String>();
                            columnMap.put(columnName, list);
                        }
                        list.add(content);
                    }

                }
            }
        }
        System.out.println(map.toString());
    }

    private Map<String, String> parseTable(String tableStr) {
        Map<String, String> tableMap = new HashMap<String, String>();
        String[] arr = tableStr.split("JOIN|join");
        System.out.println(Arrays.toString(arr));

        for (String str : arr) {
            String[] subArr = str.split("\\s+");
            String prevTable = null;
            boolean isTable = false;

            for (String s : subArr) {
                if (s.length() != 0 && !s.equalsIgnoreCase("FROM")) {
                    if (isTable) {
                        if (s.equalsIgnoreCase("LEFT")
                                || s.equalsIgnoreCase("RIGHT")
                                || s.equalsIgnoreCase("INNER")
                                || s.equalsIgnoreCase("ON")) {
                            tableMap.put(prevTable, prevTable);
                        } else {
                            tableMap.put(s, prevTable);
                        }
                        break;
                    } else {
                        prevTable = s;
                        isTable = true;
                    }
                }
            }

            // 只有一个表名
            if(isTable) {
                tableMap.put(prevTable, prevTable);
            }
        }
        System.out.println(tableMap.toString());
        return tableMap;
    }
}
