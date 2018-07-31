package y2018.m06.d08;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO
 *
 * @author kevin
 * @since 2018-06-11 20:37
 */
public class TestDivide {
    private Logger logger = LoggerFactory.getLogger(TestDivide.class);
    @Test
    public void testDivide() {
        int maxValue = Integer.MAX_VALUE;
        int dividedValue = 40000;
        int subDividedValue = 4000;
        int total = maxValue / dividedValue;
        int mod = maxValue % dividedValue;
        System.out.println(total + ":" + mod);
        List<Range> rangeList = new ArrayList<>();
        int i = 0;
        for (; i < total; i++) {
            rangeList.add(new Range(i * dividedValue, (i + 1) * dividedValue - 1));
        }
        rangeList.add(new Range(i * dividedValue, Integer.MAX_VALUE));
        for (Range range : rangeList) {
            System.out.println(range);
        }
    }

    @Test
    public void testTreeMap() {
        TreeMap<Integer, Object> treeMap = new TreeMap<>();
        treeMap.put(100, 1);
        treeMap.put(300, 1);
        treeMap.put(400, 1);
        treeMap.put(700, 1);
        Integer integer = treeMap.floorKey(260);
        System.out.println(integer);
        integer = treeMap.higherKey(300);
        System.out.println(integer);
        integer = treeMap.ceilingKey(300);
        System.out.println(integer);
    }

    class Range {
        private int from;
        private int to;

        public Range(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Range{");
            sb.append("from=").append(from);
            sb.append(", to=").append(to);
            sb.append('}');
            return sb.toString();
        }
    }

    @Test
    public void testJson() {
        String s = "[{\"name\":\"张三\",\"phone\":\"13950000640\",\"status\":\"结清\",\"bank\":\"工行开元支行\",\"loan_type\":\"公积金贷款\",\"id_card\":\"35012619841015****\",\"mailing_address\":\"杭州市西湖区湖畔花园9-2-901\",\"contract_number\":\"032612004700\",\"loan_amount\":50000000,\"monthly_repay_amount\":208300,\"periods\":20,\"house_address\":\"湖畔花园9-2-901\",\"start_date\":\"2010-10-25\",\"end_date\":\"2030-10-25\",\"repay_type\":\"等额本金\",\"deduct_day\":1,\"bank_account\":\"623408540202314****\",\"bank_account_name\":\"刘历\",\"loan_interest_percent\":\"4.5\",\"penalty_interest_percent\":\"6.75\",\"commercial_contract_number\":\"\",\"commercial_bank\":\"\",\"commercial_amount\":0,\"second_bank_account\":\"\",\"second_bank_account_name\":\"\",\"second_id_card\":\"\",\"second_phone\":\"\",\"second_corporation_name\":\"\",\"remain_amount\":50000000,\"remain_periods\":0,\"last_repay_date\":0,\"overdue_capital\":0,\"overdue_interest\":0,\"overdue_penalty\":0,\"overdue_days\":0},{\"name\":\"张四\",\"phone\":\"13950000640\",\"status\":\"结清\",\"bank\":\"工行开元支行\",\"loan_type\":\"公积金贷款\",\"id_card\":\"35012619841015****\",\"mailing_address\":\"杭州市西湖区湖畔花园9-2-901\",\"contract_number\":\"032612004700\",\"loan_amount\":50000000,\"monthly_repay_amount\":208300,\"periods\":20,\"house_address\":\"湖畔花园9-2-901\",\"start_date\":\"2010-10-25\",\"end_date\":\"2030-10-25\",\"repay_type\":\"等额本金\",\"deduct_day\":1,\"bank_account\":\"623408540202314****\",\"bank_account_name\":\"刘历\",\"loan_interest_percent\":\"4.5\",\"penalty_interest_percent\":\"6.75\",\"commercial_contract_number\":\"\",\"commercial_bank\":\"\",\"commercial_amount\":0,\"second_bank_account\":\"\",\"second_bank_account_name\":\"\",\"second_id_card\":\"\",\"second_phone\":\"\",\"second_corporation_name\":\"\",\"remain_amount\":50000000,\"remain_periods\":0,\"last_repay_date\":0,\"overdue_capital\":0,\"overdue_interest\":0,\"overdue_penalty\":0,\"overdue_days\":0}]";
        String text = "{\"mix\":\"[]\"}";
        List<String> list = new ArrayList<>();
        String s1 = JSON.toJSONString(list);
        Map<String, String> map = new HashMap<>();
        map.put("mix", s1);
        System.out.println(JSON.toJSONString(map));
        String text1 = "{\"mix\":\"[]\"}";
        Object parse = JSON.parse(text);
//        s = "{\"M001007\":\"18728343343\",\"M001012\":\"1200\",\"M001013\":\"10\",\"M001014\":\"15\",\"M001015\":\"NORMAL\",\"M001018\":\"上海市长宁区293弄2号\",\"M001020\":\"上海尚城金融有限公司\",\"M001021\":\"1000\",\"M001023\":\"1000\",\"M001024\":\"0.05\",\"M001026\":\"0.05\",\"M001028\":\"100\",\"M001029\":\"201802030000\",\"M001030\":\"201802030000\",\"M001045\":[{\"name\":\"张三\",\"phone\":\"13950000640\",\"status\":\"结清\",\"bank\":\"工行开元支行\",\"loan_type\":\"公积金贷款\",\"id_card\":\"35012619841015****\",\"mailing_address\":\"杭州市西湖区湖畔花园9-2-901\",\"contract_number\":\"032612004700\",\"loan_amount\":50000000,\"monthly_repay_amount\":208300,\"periods\":20,\"house_address\":\"湖畔花园9-2-901\",\"start_date\":\"2010-10-25\",\"end_date\":\"2030-10-25\",\"repay_type\":\"等额本金\",\"deduct_day\":1,\"bank_account\":\"623408540202314****\",\"bank_account_name\":\"刘历\",\"loan_interest_percent\":\"4.5\",\"penalty_interest_percent\":\"6.75\",\"commercial_contract_number\":\"\",\"commercial_bank\":\"\",\"commercial_amount\":0,\"second_bank_account\":\"\",\"second_bank_account_name\":\"\",\"second_id_card\":\"\",\"second_phone\":\"\",\"second_corporation_name\":\"\",\"remain_amount\":50000000,\"remain_periods\":0,\"last_repay_date\":0,\"overdue_capital\":0,\"overdue_interest\":0,\"overdue_penalty\":0,\"overdue_days\":0},{\"name\":\"张四\",\"phone\":\"13950000640\",\"status\":\"结清\",\"bank\":\"工行开元支行\",\"loan_type\":\"公积金贷款\",\"id_card\":\"35012619841015****\",\"mailing_address\":\"杭州市西湖区湖畔花园9-2-901\",\"contract_number\":\"032612004700\",\"loan_amount\":50000000,\"monthly_repay_amount\":208300,\"periods\":20,\"house_address\":\"湖畔花园9-2-901\",\"start_date\":\"2010-10-25\",\"end_date\":\"2030-10-25\",\"repay_type\":\"等额本金\",\"deduct_day\":1,\"bank_account\":\"623408540202314****\",\"bank_account_name\":\"刘历\",\"loan_interest_percent\":\"4.5\",\"penalty_interest_percent\":\"6.75\",\"commercial_contract_number\":\"\",\"commercial_bank\":\"\",\"commercial_amount\":0,\"second_bank_account\":\"\",\"second_bank_account_name\":\"\",\"second_id_card\":\"\",\"second_phone\":\"\",\"second_corporation_name\":\"\",\"remain_amount\":50000000,\"remain_periods\":0,\"last_repay_date\":0,\"overdue_capital\":0,\"overdue_interest\":0,\"overdue_penalty\":0,\"overdue_days\":0}],\"M002000\":\"201802030000\",\"M002010\":\"上海\",\"M002012\":\"国企\",\"M004011\":\"201602030000\",\"M004012\":\"24\",\"M005012\":\"12000\",\"M005014\":\"120\",\"M005022\":\"12\",\"M006006\":\"10\",\"M006025\":\"10\",\"M017006\":\"201602030000\",\"M017009\":\"201802030000\",\"M017013\":\"上海\",\"M017014\":\"上海长宁区230弄4号\",\"M017018\":[{\"result\":\"未通过检验\",\"comment\":null,\"check_item\":\"idcard_check\"},{\"result\":\"未提供邮箱\",\"comment\":null,\"check_item\":\"email_check\"},{\"result\":\"通过检验\",\"comment\":null,\"check_item\":\"address_check\"},{\"result\":\"通话记录正常\",\"comment\":null,\"check_item\":\"call_data_check\"},{\"result\":\"匹配失败\",\"comment\":null,\"check_item\":\"idcard_match\"},{\"result\":\"匹配失败\",\"comment\":null,\"check_item\":\"name_match\"},{\"result\":\"否\",\"comment\":null,\"check_item\":\"is_name_and_idcard_in_court_black\"},{\"result\":\"否\",\"comment\":null,\"check_item\":\"is_name_and_idcard_in_finance_black\"},{\"result\":\"否\",\"comment\":null,\"check_item\":\"is_name_and_mobile_in_finance_black\"},{\"result\":\"6\",\"comment\":null,\"check_item\":\"mobile_silence_3m\"},{\"result\":\"6\",\"comment\":null,\"check_item\":\"mobile_silence_6m\"},{\"result\":\"5\",\"comment\":null,\"check_item\":\"arrearage_risk_3m\"},{\"result\":\"4\",\"comment\":null,\"check_item\":\"arrearage_risk_6m\"},{\"result\":\"10\",\"comment\":null,\"check_item\":\"binding_risk\"}],\"M018000\":[{\"key\":\"friend_num_李磊3m\",\"value\":\"148\"},{\"key\":\"good_friend_num_3m\",\"value\":\"20\"},{\"key\":\"friend_city_center_3m\",\"value\":\"杭州\"},{\"key\":\"is_city_match_friend_city_center_3m\",\"value\":\"否\"},{\"key\":\"inter_peer_num_3m\",\"value\":\"41\"},{\"key\":\"friend_num_6m\",\"value\":\"274\"},{\"key\":\"good_friend_num_6m\",\"value\":\"28\"},{\"key\":\"friend_city_center_6m\",\"value\":\"杭州\"},{\"key\":\"is_city_match_friend_city_center_6m\",\"value\":\"否\"},{\"key\":\"inter_peer_num_6m\",\"value\":\"66\"}],\"M018001\":[{\"key\":\"peer_num_top3_3m\",\"top_item\":[{\"peer_number\":\"18576638512\",\"peer_num_loc\":\"深圳\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt\":71,\"call_time\":6385,\"dial_cnt\":53,\"dialed_cnt\":18,\"dial_time\":4896,\"dialed_time\":1489},{\"peer_number\":\"18768419999\",\"peer_num_loc\":\"杭州\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt\":69,\"call_time\":15545,\"dial_cnt\":38,\"dialed_cnt\":31,\"dial_time\":11824,\"dialed_time\":3721},{\"peer_number\":\"18016021182\",\"peer_num_loc\":\"上海\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt\":46,\"call_time\":1830,\"dial_cnt\":27,\"dialed_cnt\":19,\"dial_time\":1208,\"dialed_time\":622}]},{\"key\":\"peer_num_top3_6m\",\"top_item\":[{\"peer_number\":\"18768419999\",\"peer_num_loc\":\"杭州\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt\":223,\"call_time\":50544,\"dial_cnt\":129,\"dialed_cnt\":94,\"dial_time\":32287,\"dialed_time\":18257},{\"peer_number\":\"18576638512\",\"peer_num_loc\":\"深圳\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt\":217,\"call_time\":23100,\"dial_cnt\":147,\"dialed_cnt\":70,\"dial_time\":14155,\"dialed_time\":8945},{\"peer_number\":\"18016021182\",\"peer_num_loc\":\"上海\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt\":111,\"call_time\":6985,\"dial_cnt\":69,\"dialed_cnt\":42,\"dial_time\":5256,\"dialed_time\":1729}]}],\"M018031\":[{\"peer_num\":\"10086\",\"sms_cnt_1w\":63,\"sms_cnt_1m\":160,\"sms_cnt_3m\":566,\"sms_cnt_6m\":780,\"send_cnt_3m\":8,\"send_cnt_6m\":14,\"receive_cnt_3m\":558,\"receive_cnt_6m\":766}],\"M018030\":[{\"city\":\"温州\",\"p_relation\":\"\",\"peer_num\":\"13101111111\",\"group_name\":\"未知\",\"company_name\":\"未知\",\"call_cnt_1w\":1,\"call_cnt_1m\":3,\"call_cnt_3m\":20,\"call_cnt_6m\":41,\"call_time_3m\":2252,\"call_time_6m\":3808,\"dial_cnt_3m\":19,\"dial_cnt_6m\":26,\"dial_time_3m\":2210,\"dial_time_6m\":2862,\"dialed_cnt_3m\":1,\"dialed_cnt_6m\":15,\"dialed_time_3m\":42,\"dialed_time_6m\":946,\"call_cnt_morning_3m\":3,\"call_cnt_morning_6m\":7,\"call_cnt_noon_3m\":1,\"call_cnt_noon_6m\":1,\"call_cnt_afternoon_3m\":4,\"call_cnt_afternoon_6m\":9,\"call_cnt_evening_3m\":12,\"call_cnt_evening_6m\":24,\"call_cnt_night_3m\":0,\"call_cnt_night_6m\":0,\"call_cnt_weekday_3m\":11,\"call_cnt_weekday_6m\":21,\"call_cnt_weekend_3m\":6,\"call_cnt_weekend_6m\":13,\"call_cnt_holiday_3m\":3,\"call_cnt_holiday_6m\":7,\"call_if_whole_day_3m\":false,\"call_if_whole_day_6m\":false,\"trans_start\":\"2017-04-1819: 29: 38\",\"trans_end\":\"2017-10-0707: 48: 15\",\"max_call_time_6m\":298,\"min_call_time_6m\":7,\"avg_call_time_6m\":92}]}";
//        JSON.parse(s);

    }

    @Test
    public void testThreadMonitor() {
        ThreadMonitor threadMonitor = new ThreadMonitor();
        threadMonitor.monitor();
    }


    @Test

    public void testAviator() {
        List<String> rules = Arrays.asList( "double(value)>=0.0 && double(value)<0.1 ? 10",
                "double(value)>=0.1 && double(value)<0.2 ? 20",
                "double(value)>=0.2 && double(value)<0.3 ? 30",
                "double(value)>=0.3 && double(value)<0.4 ? 40",
                "double(value)>=0.4 && double(value)<0.5 ? 50",
                "double(value)>=0.5 && double(value)<0.6 ? 60",
                "double(value)>=0.6 && double(value)<0.7 ? 70",
                "double(value)>=0.7 && double(value)<0.8 ? 80",
                "double(value)>=0.8 && double(value)<0.9 ? 90",
                "double(value)>=0.9 && double(value)<1.0 ? 100",
                "double(value)>=1.0 ? 100 : 1");
        String rule = Joiner.on(":").skipNulls().join(rules);
        Expression expression = AviatorEvaluator.compile(rule);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("value", 0.2f);
        Object execute = expression.execute(map);
        System.out.println(execute);
    }

    @Test
    public void testAsiic() {
        System.out.println(((int) 'a'));    // 97
        System.out.println(((int) 'A'));    // 65
    }
}
