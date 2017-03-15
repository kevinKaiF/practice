package y2017.m01.d10;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import y2016.m05.day20160520.JsonUtils;
import y2016.m08.day20160825.Closer;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/10
 */
public abstract class ExcelBuilder {
    private static final int STATE_LEFT_RIGHT = 0;
    private static final int STATE_FULL = 1;
    private static final int STATE_BOTTOM = 2;
    private static final int STATE_TOP = 3;
    private static final short DEFAULT_HEIGHT = 600;
    private static final short MAX_HEIGHT = 3200;
    private static final int TOTAL_WIDTH = 22500;
    public static final short DEFAULT_TITLE_FONT_SIZE = (short) 16;
    private Workbook workbook;
    private Sheet sheet;
    private int rowNum;
    private short height;
    private int columnSize;

    public ExcelBuilder() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        rowNum = 0;
        height = DEFAULT_HEIGHT;
    }

    private Row createRow() {
        return createRow(height);
    }

    private Row createRow(short height) {
        Row row = sheet.createRow(rowNum++);
        row.setHeight(height);
        return row;
    }

    private Cell createCell(Row row, int cellIndex, String value) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        return cell;
    }

    private Cell createCell(Row row, int cellIndex, double value) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        return cell;
    }

    private void appendTitle() {
        Row row = createRow();
        addBorder(row, 0, "XXX项目详细评审表", STATE_TOP);
        Cell cell = row.getCell(0);
        CellStyle cellStyle = createCellStyle(false, false, false, false);
        Font font = workbook.createFont();
        font.setFontHeightInPoints(DEFAULT_TITLE_FONT_SIZE);
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cell.setCellStyle(cellStyle);

        mergeRegion(row.getRowNum(), row.getRowNum(), 0, columnSize - 1);
    }

    private void mergeRegion(int fromRowIndex, int toRowIndex, int fromColumnIndex, int toColumnIndex) {
        sheet.addMergedRegion(new CellRangeAddress(fromRowIndex, toRowIndex, fromColumnIndex, toColumnIndex));
    }

    private void appendProjectCode() {
        Row row = createRow();
        addBorder(row, 0, "项目编号：0xxxx", STATE_LEFT_RIGHT);
        mergeRegion(row.getRowNum(), row.getRowNum(), 0, columnSize - 1);
    }

    private void appendOpeningBidTime() {
        Row row = createRow();
        addBorder(row, 0, "开标时间：2017-01-02", STATE_LEFT_RIGHT);
        mergeRegion(row.getRowNum(), row.getRowNum(), 0, columnSize - 1);
    }

    /**
     * 合并区域添加边框样式
     *
     * @param row
     * @param index
     * @param value
     * @param borderState 0表示只有此行的左右需要边框，
     *                    1表示此行需要边框，
     *                    2表示此行的左右以及bottom需要边框，
     *                    3表示此行的左右以及top需要边框
     */
    private void addBorder(Row row, int index, String value, int borderState) {
        for (int i = index; i < columnSize; i++) {
            Cell cell = row.createCell(i);
            if (i == index) {
                if (borderState == STATE_LEFT_RIGHT) {
                    cell.setCellStyle(createCellStyle(false, false, false, true));
                } else if (borderState == STATE_FULL) {
                    cell.setCellStyle(createCellStyle(true, false, true, true));
                } else if (borderState == STATE_BOTTOM) {
                    cell.setCellStyle(createCellStyle(false, false, true, true));
                } else {
                    cell.setCellStyle(createCellStyle(true, false, false, true));
                }
                cell.setCellValue(value);
            } else if (i == columnSize - 1) {
                if (borderState == STATE_LEFT_RIGHT) {
                    cell.setCellStyle(createCellStyle(false, true, false, false));
                } else if (borderState == STATE_FULL) {
                    cell.setCellStyle(createCellStyle(true, true, true, false));
                } else if (borderState == STATE_BOTTOM) {
                    cell.setCellStyle(createCellStyle(false, true, true, false));
                } else {
                    cell.setCellStyle(createCellStyle(true, true, false, false));
                }
            } else {
                if (borderState == STATE_FULL) {
                    cell.setCellStyle(createCellStyle(true, false, true, false));
                } else if (borderState == STATE_BOTTOM) {
                    cell.setCellStyle(createCellStyle(false, false, true, false));
                } else if (borderState == STATE_TOP) {
                    cell.setCellStyle(createCellStyle(true, false, false, false));
                }
            }
        }
    }


    private void appendTemplateJson() {
        String json = "{\n" +
                "\t\"items\" : [{\n" +
                "\t\t\"exportName\" : \"专家A\",\n" +
                "\t\t\"data\" : {\n" +
                "\t\t\t\"1001\" : 300,\n" +
                "\t\t\t\"1002\" : 400,\n" +
                "\t\t\t\"1003\" : 200\n" +
                "\t\t}\n" +
                "\t},{\n" +
                "\t\t\"exportName\" : \"专家B\",\n" +
                "\t\t\"data\" : {\n" +
                "\t\t\t\"1001\" : 320,\n" +
                "\t\t\t\"1002\" : 350,\n" +
                "\t\t\t\"1003\" : 220\n" +
                "\t\t}\n" +
                "\t}],\n" +
                "\t\"bidders\" : [{\n" +
                "\t\t\"name\" : \"亿阳信通\",\n" +
                "\t\t\"id\" : 1001\n" +
                "\t}, {\n" +
                "\t\t\"name\" : \"上海贝尔\",\n" +
                "\t\t\"id\" : 1002\n" +
                "\t}, {\n" +
                "\t\t\"name\" : \"北京必联\",\n" +
                "\t\t\"id\" : 1003\n" +
                "\t}]\n" +
                "}";
        Map map = JsonUtils.json2Object(json, Map.class, null);
        List<Map> items = (List) map.get("items");
        List<Map> bidders = (List) map.get("bidders");
        List<Object> ids = new ArrayList<>();
        // 1.build header
        Row row = createRow();
        CellStyle headerStyle = createCellStyle(true, true, true, true);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        createCell(row, 0, "序号").setCellStyle(headerStyle);
        createCell(row, 1, "结果").setCellStyle(headerStyle);
        for (int i = 0, len = bidders.size(); i < len; i++) {
            Map bidder = bidders.get(i);
            createCell(row, 2 + i, String.valueOf(bidder.get("name"))).setCellStyle(headerStyle);
            ids.add(bidder.get("id"));
        }

//        this.columnSize = bidders.size() + 2;

        // 2.build body
        CellStyle cellStyle = createCellStyle(true, true, true, true);
        for (int i = 0; i < items.size(); i++) {
            Map item = items.get(i);
            Row ro = createRow();
            createCell(ro, 0, i + 1).setCellStyle(cellStyle);
            createCell(ro, 1, String.valueOf(item.get("exportName"))).setCellStyle(cellStyle);
            Map data = (Map) item.get("data");
            for (int j = 0; j < ids.size(); j++) {
                createCell(ro, 2 + j, parseValue(ids, data, j)).setCellStyle(cellStyle);
            }
        }
    }

    private Double parseValue(List<Object> ids, Map data, int index) {
        return Double.valueOf(String.valueOf(data.get(String.valueOf(ids.get(index)))));
    }

    private void appendOpinion() {
        Row row = createRow(MAX_HEIGHT);
        createCell(row, 0, "备注：");
        String value = "专家A：XX意见\n专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见专家A：XX意见\n" +
                "专家B：XX意见";
        addBorder(row, 1, value, STATE_FULL);
        mergeRegion(row.getRowNum(), row.getRowNum(), 1, columnSize - 1);
    }

    private void appendSignature() {
        Row row = createRow();
        addBorder(row, 0, "签名", STATE_LEFT_RIGHT);
        mergeRegion(row.getRowNum(), row.getRowNum(), 0, columnSize - 1);

        Row row1 = createRow();
        String[] names = {"test1", "test2", "test3", "test1", "test2", "test3", "test1", "test2", "test3", "test1", "test2", "test3", "test1", "test2", "test3", "test1", "test2", "test3"};
        int i = 0;
        for (; i < names.length; i++) {
            if (i > 0 && i % columnSize == 0) {
                row1 = createRow();
            }

            createCell(row1, i % columnSize, names[i]).setCellStyle(createCellStyle(true, true, true, true));
        }
        addBorder(row1, i % columnSize, "", STATE_FULL);
        mergeRegion(row1.getRowNum(), row1.getRowNum(), i % columnSize, columnSize - 1);
    }

    private void appendCreateTime() {
        Row row = createRow();
        addBorder(row, 0, "日期：" + new Date(), STATE_BOTTOM);
        mergeRegion(row.getRowNum(), row.getRowNum(), 0, columnSize - 1);
    }

    /**
     * 创建边框样式
     *
     * @param top
     * @param right
     * @param bottom
     * @param left
     * @return
     */
    private CellStyle createCellStyle(boolean top, boolean right, boolean bottom, boolean left) {
        CellStyle cellStyle = workbook.createCellStyle();
        doAddBorderStyle(cellStyle, top, right, bottom, left);
        doAddFontStyle(cellStyle);
        doAddTextAlignStyle(cellStyle);
        return cellStyle;
    }

    private void doAddTextAlignStyle(CellStyle cellStyle) {
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
    }

    private void doAddFontStyle(CellStyle cellStyle) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        cellStyle.setFont(font);
    }

    private void doAddBorderStyle(CellStyle cellStyle, boolean top, boolean right, boolean bottom, boolean left) {
        if (bottom) {
            cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (top) {
            cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (left) {
            cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (right) {
            cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        }
    }

    public void build(String path) {
        beforeBuild();
        appendTitle();
        appendProjectCode();
        appendOpeningBidTime();
        appendTemplateJson();
        appendOpinion();
        appendSignature();
        appendCreateTime();
        write(path);
    }

    private void beforeBuild() {
        calcColumnSize();
        for (int i = 0; i < columnSize; i++) {
            sheet.setColumnWidth(i, TOTAL_WIDTH / columnSize);
        }
    }

    protected abstract void calcColumnSize();


    private void write(String path) {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(path));
            workbook.write(outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(outputStream);
            Closer.closeQuietly(workbook);
        }
    }

//    public static void main(String[] args) {
//        String path = "./testExcel.xlsx";
//        ExcelBuilder builder = new ExcelBuilder();
//        builder.build(path);
//    }


}
