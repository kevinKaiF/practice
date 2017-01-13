package y2016.m12.d26;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/26
 */
class ParseExcelDelegate {
    public static final int FIRST_NAME_CELL = 0;
    public static final int SECOND_NAME_CELL = 1;
    public static final int ITEM_NUM_CELL = 2;
    private final String XLS_SUFFIX = ".xls";
    private final String XLSX_SUFFIX = ".xlsx";
    private final int FULL_NUM = 100;

    /**
     * 每个一级指标的总分
     */
    private int totalNum;

    /**
     * 二级指标
     */
    private List<EvaluateIndexItem> evaluateIndexItems;

    /**
     * 上一个一级指标名称
     */
    private String prevFirstName;

    /**
     * 是否已解析表头
     */
    private boolean parsedHeader;

    public ParseExcelDelegate() {
        totalNum = 0;
        evaluateIndexItems = new ArrayList<>();
        prevFirstName = null;
        parsedHeader = false;
    }

    void addRowData(String secondName, int itemNum) {
        this.totalNum += itemNum;
        this.evaluateIndexItems.add(new EvaluateIndexItem(secondName, itemNum));
    }

    void handleMultiRowData(List<EvaluateIndex> evaluateIndexList, Row row) {
        this.validateFirstName(prevFirstName, row.getRowNum());
        evaluateIndexList.add(new EvaluateIndex(this.prevFirstName, this.evaluateIndexItems, this.totalNum));
    }

    void clear() {
        // 清空记录
        this.totalNum = 0;
        this.evaluateIndexItems = new ArrayList<>();
        this.prevFirstName = null;
    }

    void validateCell(Cell specifiedCell, int columnIndex, int rowNum) {
        if (specifiedCell == null) {
            throw new RuntimeException(MessageFormat.format("第{0}行第{1}列输入不能为空！", rowNum + 1, columnIndex + 1));
        }
    }

    void validateTotalNum(List<EvaluateIndex> evaluateIndexList) {
        if (CollectionUtils.isEmpty(evaluateIndexList)) {
            throw new RuntimeException("评分指标为空");
        } else {
            int fullNum = 0;
            for (EvaluateIndex evaluateIndex : evaluateIndexList) {
                fullNum += evaluateIndex.getTotalNum();
            }

            if (fullNum != FULL_NUM) {
                throw new RuntimeException(MessageFormat.format("评分总分未达到{0}", FULL_NUM));
            }
        }
    }

    int parseItemNum(int rowNum, String itemNumString) {
        try {
            return Double.valueOf(itemNumString).intValue();
        } catch (NumberFormatException e) {
            handlerNumberFormatException(rowNum);
            return -1;
        }
    }

    private void handlerNumberFormatException(int rowNum) {
        throw new RuntimeException(MessageFormat.format("第{0}行第{1}列评分输入非法！", rowNum + 1, ITEM_NUM_CELL + 1));
    }

    void parseHeader(String itemNumString) {
        try {
            Double.valueOf(itemNumString).intValue();
            throw new RuntimeException("header不存在");
        } catch (NumberFormatException e) {
            this.parsedHeader = true;
        }
    }

    String parseItemNumStringValue(Cell itemNumCell) {
        try {
            return itemNumCell.getStringCellValue();
        } catch (Exception e) {
            return String.valueOf(itemNumCell.getNumericCellValue());
        }
    }

    private void validateFirstName(String prevFirstName, int prevRowNum) {
        if (StringUtils.isBlank(prevFirstName)) {
            throw new RuntimeException(MessageFormat.format("第{0}行第{1}列一级标题不能为空", prevRowNum, FIRST_NAME_CELL + 1));
        }
    }

    /**
     * 查询当前cell所在的合并区域
     * @param mergedRegions
     * @param cell
     * @return 如果cell的CellAddress所在区域在对应的合并区域，将其返回，否则返回null
     */
    CellRangeAddress lookupMergeRegion(List<CellRangeAddress> mergedRegions, Cell cell) {
        String cellAddress = cell.getAddress().formatAsString();
        String cellColumnName = cellAddress.substring(0, 1);
        int curRow = Integer.valueOf(cellAddress.substring(1)).intValue();
        for (CellRangeAddress mergedRegion : mergedRegions) {
            String mergedRegionAddress = mergedRegion.formatAsString();
            String[] mergeRange = mergedRegionAddress.split(":");
            String mergeColumnName = mergeRange[0].substring(0, 1);
            int startRow = Integer.valueOf(mergeRange[0].substring(1));
            int endRow = Integer.valueOf(mergeRange[1].substring(1));
            if (curRow >= startRow && curRow <= endRow && cellColumnName.equals(mergeColumnName)) {
                return mergedRegion;
            }
        }
        return null;
    }

    public Workbook createWorkbook(String suffix, InputStream in) throws IOException {
        if (XLS_SUFFIX.equals(suffix)) {
            return new HSSFWorkbook(in);
        } else if (XLSX_SUFFIX.equals(suffix)) {
            return new XSSFWorkbook(in);
        } else {
            throw new RuntimeException("no support file type : " + suffix);
        }
    }

    public void setPrevFirstName(String prevFirstName) {
        this.prevFirstName = prevFirstName;
    }

    public boolean isParsedHeader() {
        return parsedHeader;
    }

    public static void main(String[] args) {
        String path = "./template.xlsx";
        ParseExcelUtil.parse(path);
    }
}
