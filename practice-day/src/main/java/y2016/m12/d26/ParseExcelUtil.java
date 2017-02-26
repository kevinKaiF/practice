package y2016.m12.d26;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import y2016.m08.day20160825.Closer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/27
 */
public class ParseExcelUtil {
    public static List<EvaluateIndex> parse(String path) {
        String suffix = path.substring(path.lastIndexOf(".")).toLowerCase();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return parse(inputStream, suffix);
    }

    public static List<EvaluateIndex> parse(InputStream inputStream, String suffix) {
        Workbook workbook = null;
        List<EvaluateIndex> evaluateIndexList = new ArrayList<>();
        try {
            ParseExcelDelegate delegate = new ParseExcelDelegate();
            workbook = delegate.createWorkbook(suffix, inputStream); // 底层读取inputStream所有的数据，并将其关闭，无需再次关闭
            Sheet sheet = workbook.getSheetAt(0); // 读取第一个sheet

            CellRangeAddress currentCellRangeAddress = null;
            Row currRow = null;

            Iterator<Row> iterator = sheet.rowIterator();
            while (iterator.hasNext()) {
                currRow = iterator.next();
                Cell firstNameCell = currRow.getCell(ParseExcelDelegate.FIRST_NAME_CELL);   // 一级标题单元格
                Cell secondNameCell = currRow.getCell(ParseExcelDelegate.SECOND_NAME_CELL); // 二级标题单元格
                Cell itemNumCell = currRow.getCell(ParseExcelDelegate.ITEM_NUM_CELL);       // 三级标题单元格

                String firstName = firstNameCell == null ? null : firstNameCell.getStringCellValue();
                String secondName = secondNameCell == null ? null : secondNameCell.getStringCellValue();
                String itemNumString = itemNumCell == null ? null : delegate.parseItemNumStringValue(itemNumCell);

                if (StringUtils.isBlank(firstName) && StringUtils.isBlank(secondName) && StringUtils.isBlank(itemNumString)) {
                    if (currentCellRangeAddress != null) {
                        currentCellRangeAddress = null;
                        delegate.handleMultiRowData(evaluateIndexList, currRow);
                        delegate.clear();
                    }
                } else if (/*StringUtils.isNotBlank(secondName) && */StringUtils.isNotBlank(itemNumString)) {
                    int itemNum = 0; // 二级指标评分
                    if (!delegate.isParsedHeader()) { // 解析header
                        delegate.parseHeader(itemNumString);
                    } else {
                        itemNum = delegate.parseItemNum(currRow.getRowNum(), itemNumString);
                        CellRangeAddress cellRangeAddress = delegate.lookupMergeRegion(sheet.getMergedRegions(), firstNameCell);
//                        CellRangeAddress cellRangeAddress = null;
                        if (cellRangeAddress != null) {
                            // 如果
                            delegate.validateCell(secondNameCell, ParseExcelDelegate.SECOND_NAME_CELL, currRow.getRowNum());
                            if (currentCellRangeAddress == null) { // 开始进入合并区域
                                delegate.setPrevFirstName(firstName);
                                currentCellRangeAddress = cellRangeAddress;
                                delegate.addRowData(secondName, itemNum);
                            } else if (currentCellRangeAddress.equals(cellRangeAddress)) { // 正处于合并区域
                                currentCellRangeAddress = cellRangeAddress;
                                delegate.addRowData(secondName, itemNum);
                            } else { // 另一个合并区域
                                delegate.handleMultiRowData(evaluateIndexList, currRow);
                                delegate.clear();
                                // 记录新的合并区域
                                currentCellRangeAddress = cellRangeAddress;
                                delegate.addRowData(secondName, itemNum);
                            }
                        } else {  // 非合并区域
                            if (currentCellRangeAddress != null) {
                                currentCellRangeAddress = null;
                                delegate.handleMultiRowData(evaluateIndexList, currRow);
                                delegate.clear();
                            }

                            // 记录新的合并区域
                            delegate.setPrevFirstName(firstName);
                            secondName = secondName == null ? "" : secondName;
                            delegate.addRowData(secondName, itemNum);
                            delegate.handleMultiRowData(evaluateIndexList, currRow);

                            // 清空记录
                            delegate.clear();
                        }
                    }
                } else {
                    delegate.validateCell(firstNameCell, ParseExcelDelegate.FIRST_NAME_CELL, currRow.getRowNum());
                    delegate.validateCell(itemNumCell, ParseExcelDelegate.ITEM_NUM_CELL, currRow.getRowNum());
                }
            }

            if (currentCellRangeAddress != null) { // 处理最后一行
                delegate.handleMultiRowData(evaluateIndexList, currRow);
                delegate.clear();
            }

            delegate.validateTotalNum(evaluateIndexList);

            for (EvaluateIndex evaluateIndex : evaluateIndexList) {
                System.out.println(evaluateIndex);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(workbook);
        }

        return evaluateIndexList;
    }
}
