package ru.rudXson.base;

import javax.naming.NoPermissionException;
import java.io.File;

public class FileValidator {
    public static void checkFile(String filePath) throws NoPermissionException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        if (!file.canWrite()) {
            throw new NoPermissionException("No write access to file: " + filePath);
        }
    }
}
