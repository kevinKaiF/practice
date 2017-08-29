package y2017.m01.d11;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import y2016.m05.day20160520.JsonUtils;
import y2016.m08.day20160825.Closer;

import java.io.*;
import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/1/11
 */
public class WordBuilder {
    private XWPFDocument document;
    private Map<DataKey, String> dataMap;
    private Map templateJsonMap;
    private int columnSize;

    public WordBuilder(EnumMap<DataKey, String> dataMap) {
        this.dataMap = dataMap;
        this.document = new XWPFDocument();
        this.columnSize = 5;
        this.templateJsonMap = JsonUtils.json2Object(dataMap.get(DataKey.TEMPLATE_JSON), Map.class, null);
    }

    public void build(String path) {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            build(bufferedOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void build(File file) {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            build(bufferedOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void build(OutputStream outputStream) {
        beforeBuild();
        appendTitle();
        appendProjectCode();
        appendOpeningBidTime();
        appendTemplateJson();
        appendOpinion();
        appendSignature();
        appendCreateTime();
        write(outputStream);
    }

    private void write(OutputStream outputStream) {
        try {
            document.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(outputStream);
            Closer.closeQuietly(document);
        }
    }

    private void appendCreateTime() {
        addRunText(createParagraph(), "日 期：");
    }

    private void appendSignature() {
        XWPFParagraph paragraph = createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
        paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
        addRunText(paragraph, "签名");
    }

    private void appendOpinion() {
        addRunText(createParagraph(), "评审意见");
    }

    protected void appendTemplateJson(){};

    private void appendOpeningBidTime() {
        addRunText(createParagraph(), "开标时间");
    }

    private void appendProjectCode() {
        addRunText(createParagraph(), "项目信息");
    }

    private void appendTitle() {
        XWPFParagraph paragraph = createParagraph();
        addRunText(paragraph, "XX详细评审表");
    }

    private XWPFParagraph createParagraph() {
        XWPFParagraph paragraph = document.createParagraph();
        setSingleLineSpacing(paragraph);
        return paragraph;
    }

    private void addRunText(XWPFParagraph paragraph, String value) {
        XWPFRun run = paragraph.createRun();
        run.setText(value);
    }

    private void beforeBuild() {
        columnSize = parseColumnSize(templateJsonMap);
    }

    protected int parseColumnSize(Map templateJsonMap) {
        return 5;
    };



    /**
     * 设置行间距
     *
     * @param para
     */
    private void setSingleLineSpacing(XWPFParagraph para) {
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null) ppr = para.getCTP().addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(300));
    }

    public enum DataKey {
        TITLE,
        PROJECT_CODE,
        OPENING_BID_TIME,
        TEMPLATE_JSON,
        OPINION,
        SIGNATURE,
        CREATE_TIME
    }

    public static void main(String[] args) {
        WordBuilder builder = new WordBuilder(new EnumMap<DataKey, String>(DataKey.class));
        builder.build("./test.doc");
    }

}
