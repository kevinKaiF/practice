package y2016.m12.d26;

import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/26
 */
public class EvaluateIndex {
    private String firstName;
    private List<EvaluateIndexItem> items;
    private int totalNum;

    public EvaluateIndex(String firstName, List<EvaluateIndexItem> items, int totalNum) {
        this.firstName = firstName;
        this.items = items;
        this.totalNum = totalNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<EvaluateIndexItem> getItems() {
        return items;
    }

    public void setItems(List<EvaluateIndexItem> items) {
        this.items = items;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "EvaluateIndex{" +
                "firstName='" + firstName + '\'' +
                ", items=" + items +
                ", totalNum=" + totalNum +
                '}';
    }
}
