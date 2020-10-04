package ru.itis.javalab;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.UUID;

public class Downloader {
    public void download(URL url, Path folderName) {
        InputStream in = null;
        try {
            Random r = new Random();
            in = url.openStream();
            Files.copy(in, Paths.get(folderName + "/" + UUID.randomUUID() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException();
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}