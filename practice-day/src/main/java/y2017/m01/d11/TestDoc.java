package y2017.m01.d11;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import y2016.m08.day20160825.Closer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/11
 */
public class TestDoc {
    @Test
    public void testWriteTable() {
        XWPFDocument doc = new XWPFDocument();
        //创建一个5行5列的表格
        XWPFTable table = doc.createTable(5, 5);
        //这里增加的列原本初始化创建的那5行在通过getTableCells()方法获取时获取不到，但通过row新增的就可以。
//    table.addNewCol(); //给表格增加一列，变成6列
        table.createRow(); //给表格新增一行，变成6行
        List<XWPFTableRow> rows = table.getRows();

        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center")); // 表格居中
        tblWidth.setW(new BigInteger("8400") );
        tblWidth.setType(STTblWidth.DXA);
//        //表格属性
//        CTTblPr tablePr = table.getCTTbl().addNewTblPr();
//        //表格宽度
//        CTTblWidth width = tablePr.addNewTblW();
//        width.setW(BigInteger.valueOf(9000));
        XWPFTableRow row;
        List<XWPFTableCell> cells;
        XWPFTableCell cell;
        int rowSize = rows.size();
        int cellSize;


        for (int i = 0; i < rowSize; i++) {
            row = rows.get(i);
//            //新增单元格
//            row.addNewTableCell();
            //设置行的高度
            row.setHeight(500);
            //行属性
//       CTTrPr rowPr = row.getCtRow().addNewTrPr();
            //这种方式是可以获取到新增的cell的。
//       List<CTTc> list = row.getCtRow().getTcList();
            cells = row.getTableCells();
            cellSize = cells.size();
            for (int j = 0; j < cellSize; j++) {
                cell = cells.get(j);
//                if ((i + j) % 2 == 0) {
//                    //设置单元格的颜色
//                    cell.setColor("ff0000"); //红色
//                } else {
//                    cell.setColor("0000ff"); //蓝色
//                }
                if ( i == 0) {
                    cell.setColor("777777");
                }
                //单元格属性
                CTTcPr cellPr = cell.getCTTc().addNewTcPr();
                cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
//                if (j == 3) {
                //设置宽度

//                    cellPr.addNewTcW().setW(BigInteger.valueOf(1000));
//                }
                cell.setText("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
            }
        }

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
//        run.addCarriageReturn();
//        run.addBreak();
        run.setText("日期");

        OutputStream os = null;
        try {
            //文件不存在时会自动创建
            os = new FileOutputStream("F:\\table.docx");
            //写入文件
            doc.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(os);
        }
    }
}
