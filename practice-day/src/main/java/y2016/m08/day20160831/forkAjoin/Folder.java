package y2016.m08.day20160831.forkAjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-31 PM05:03
 */
public class Folder {
    private List<Folder> subFolders;
    private List<Document> documents;

    public Folder(List<Folder> subFolder, List<Document> documents) {
        this.subFolders = subFolder;
        this.documents = documents;
    }

    public List<Folder> getSubFolders() {
        return Collections.unmodifiableList(new ArrayList<Folder>(subFolders));
    }

    public List<Document> getDocuments() {
        return Collections.unmodifiableList(new ArrayList<Document>(documents));
    }

    public static Folder fromDirectory(File dir) {
        List<Folder> subFolders = new ArrayList<>();
        List<Document> documents = new ArrayList<>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                subFolders.add(Folder.fromDirectory(file));
            } else {
                documents.add(Document.fromFile(file));
            }
        }

        return new Folder(subFolders, documents);
    }

}
