package y2016.m12.d26;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/26
 */
public class EvaluateIndexItem {
    private String secondName;
    private int itemNum;

    public EvaluateIndexItem(String secondName, int itemNum) {
        this.secondName = secondName;
        this.itemNum = itemNum;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    @Override
    public String toString() {
        return "EvaluateIndexItem{" +
                "secondName='" + secondName + '\'' +
                ", itemNum=" + itemNum +
                '}';
    }
}
