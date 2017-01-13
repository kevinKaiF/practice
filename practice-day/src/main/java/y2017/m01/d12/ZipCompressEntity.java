package y2017.m01.d12;

import y2017.m01.d11.WordBuilder;

import java.util.EnumMap;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/12
 */
public class ZipCompressEntity {
    private String reviewerName;
    private EnumMap<WordBuilder.DataKey, String> dataMap;

    public ZipCompressEntity(String reviewerName, EnumMap<WordBuilder.DataKey, String> dataMap) {
        this.reviewerName = reviewerName;
        this.dataMap = dataMap;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public EnumMap<WordBuilder.DataKey, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(EnumMap<WordBuilder.DataKey, String> dataMap) {
        this.dataMap = dataMap;
    }
}
