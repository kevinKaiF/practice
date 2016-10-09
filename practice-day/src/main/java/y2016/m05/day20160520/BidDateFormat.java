package y2016.m05.day20160520;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-20 PM05:41
 */
public class BidDateFormat extends DateFormat {
    private final static Calendar calendar = Calendar.getInstance();

    private final static NumberFormat numFormat = NumberFormat.getInstance();

    private String pattern;

    public BidDateFormat(String pattern) {
        super.setCalendar(calendar);
        super.setNumberFormat(numFormat);
        this.pattern = pattern;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo,
                               FieldPosition fieldPosition) {
        toAppendTo.append(new DateTime(date.getTime()).toString(pattern));
        return toAppendTo;
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(source)
                .toDate();
    }

    @Override
    public Date parse(String source) throws ParseException {
        return parse(source,null);
    }
}
