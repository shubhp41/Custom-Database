package shubh;

import java.io.File;
import java.io.Serial;

public class tableExists {
    @Serial
    public static boolean tableExists(String tableName) {
        File file = new File(tableName + ".sp");
        if (file.exists()) {
            return true;
        }
        // Check if any file in the directory starts with the table name
        File directory = new File(".");
        File[] files = directory.listFiles();
        for (File f : files) {
            if (f.isFile() && f.getName().startsWith(tableName)) {
                return true;
            }
        }
        return false;
    }
}
