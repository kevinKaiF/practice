package y2016.m09.d13;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : ${fdate}
 */
public enum ClickType {
    LINK("a", "innerHTML"),
    BUTTON("button", "value"),
    IMAGE("img", "src"),
    SEARCH("search_text", "value");

    private String tag;
    private String attr;

    ClickType(String tag, String attr) {
        this.tag = tag;
        this.attr = attr;
    }

    public String getTag() {
        return tag;
    }

    public String getAttr() {
        return attr;
    }
}
