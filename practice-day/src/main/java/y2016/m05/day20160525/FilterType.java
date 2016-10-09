package y2016.m05.day20160525;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : ${fdate}
 */
public enum FilterType {
    FILTER(1),
    TRANSFER(2);

    private Integer code;
    FilterType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static FilterType parse(int code) {
        FilterType[] filterTypes = FilterType.values();
        for(FilterType filterType : filterTypes) {
            if(filterType.getCode() == code) {
                return filterType;
            }
        }
        return null;
    }
}
