package y2016.m08.day20160831.forkAjoin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-31 PM05:37
 */
public class FolderSearchTask extends RecursiveTask<Long> {
    private final Folder folder;
    private final String searchWord;

    public FolderSearchTask(Folder folder, String searchWord) {
        this.folder = folder;
        this.searchWord = searchWord;
    }

    @Override
    protected Long compute() {
        long count = 0L;
        List<RecursiveTask<Long>> forks = new LinkedList<>();
        for (Folder subFolder : folder.getSubFolders()) {
            // 这地方相当于递归
            FolderSearchTask task = new FolderSearchTask(subFolder, searchWord);
            forks.add(task);
            task.fork();
        }

        for (Document document : folder.getDocuments()) {
            DocumentSearchTask task = new DocumentSearchTask(document, searchWord);
            forks.add(task);
            task.fork();
        }

        for (RecursiveTask<Long> task : forks) {
            count = count + task.join();
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        String folderPath = "E:\\workspace\\databus";
        String searchWord = "public";
        WordCounter wordCounter = new WordCounter();
        Folder folder = Folder.fromDirectory(new File(folderPath));
        long start = System.currentTimeMillis();
        System.out.println(wordCounter.countOccurrencesInParallel(folder, searchWord));
        long end = System.currentTimeMillis();
        System.out.println("parallel : " + (end - start));

        start = System.currentTimeMillis();
        System.out.println(wordCounter.countOccurrencesOnSingleThread(folder, searchWord));
        end = System.currentTimeMillis();
        System.out.println("single : " + (end - start));
    }
}
