package com.jaya.utils;

import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {


    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    public String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Path.of(filePath)));
    }

    public List<String> readFileLines(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }

    public void writeFile(String filePath, String content) throws IOException {
        Files.write(Path.of(filePath), content.getBytes());
    }

    public void appendFile(String filePath, String content) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(content);
        }
    }

    public void deleteFile(String filePath) throws IOException {
        Files.deleteIfExists(Path.of(filePath));
    }

    public long getFileSize(String filePath) {
        return new File(filePath).length();
    }
}
