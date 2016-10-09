package y2016.m08.day20160831.forkAjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-31 PM05:02
 */
public class DocumentSearchTask extends RecursiveTask<Long> {
    private final Document document;
    private final String searchWord;

    public DocumentSearchTask(Document document, String searchWord) {
        this.document = document;
        this.searchWord = searchWord;
    }

    @Override
    protected Long compute() {
        WordCounter wordCounter = new WordCounter();
        return wordCounter.occurrencesCount(document, searchWord);
    }
}
