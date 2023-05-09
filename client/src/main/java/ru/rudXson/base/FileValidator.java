package ru.rudXson.base;

import java.io.File;

public class FileValidator {
    public static void checkFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        if (!file.canWrite()) {
            throw new IllegalArgumentException("No write access to file: " + filePath);
        }
    }
}
