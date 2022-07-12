package pixelparty.utils;

import java.io.File;
import java.util.Objects;

public class FolderUtil {

    public static File getFolder(File file, String folderName){
        for(File f: Objects.requireNonNull(file.listFiles())){
            if(f.getName().equalsIgnoreCase(folderName)){
                return f;
            }
        }
        return null;
    }
}
