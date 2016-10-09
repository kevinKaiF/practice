package y2016.m08.day20160831.forkAjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-31 PM05:02
 */
public class WordCounter {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    private String[] wordIn(String line) {
        return line.split("\\s+");
    }

    public long occurrencesCount(Document document, String searchWord) {
        long counter = 0;
        if (document.getLines() != null) {
            for (String line : document.getLines()) {
                for (String word : wordIn(line)) {
                    if (searchWord.equals(word)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public long countOccurrencesInParallel(Folder folder, String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

    public long countOccurrencesOnSingleThread(Folder folder, String searchedWord) {
        long count = 0;
        for (Folder subFolder : folder.getSubFolders()) {
            count = count + countOccurrencesOnSingleThread(subFolder, searchedWord);
        }

        for (Document document : folder.getDocuments()) {
            count = count + occurrencesCount(document, searchedWord);
        }
        return count;
    }
}
