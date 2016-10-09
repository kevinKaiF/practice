package y2016.m05.day20160525;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-05-25 AM10:36
 */
public enum OpType {
    EQ(FilterType.FILTER, 1),
    NEQ(FilterType.FILTER, 2),
    GT(FilterType.FILTER, 3),
    LT(FilterType.FILTER, 4),
    GTE(FilterType.FILTER, 5),
    LTE(FilterType.FILTER, 6),
    ISNULL(FilterType.FILTER, 7),
    NOTNULL(FilterType.FILTER, 8),
    LIKE(FilterType.FILTER, 9),
    NOTLIKE(FilterType.FILTER, 10),

    TONUMBER(FilterType.TRANSFER, 11),
    TOUPPERCASE(FilterType.TRANSFER, 12),
    TOLOWWERCASE(FilterType.TRANSFER, 13);


    private Enum filterType;
    private Integer code;

    OpType(FilterType filterType, Integer code) {
        this.filterType = filterType;
        this.code = code;
    }

    public Enum getFilterType() {
        return filterType;
    }

    public Integer getCode() {
        return code;
    }

    public OpType parse(int code) {
        OpType[] opTypes = OpType.values();
        for(OpType opType : opTypes) {
            if(opType.getCode() == code) {
                return opType;
            }
        }
        return null;
    }

}
