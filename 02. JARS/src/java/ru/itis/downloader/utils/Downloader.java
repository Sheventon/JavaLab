package ru.itis.downloader.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class Downloader {
    public void download(URL url, Path folderName) {
        InputStream in = null;
        try {
            Random r = new Random();
            in = url.openStream();
            Files.copy(in, Paths.get(folderName + "/file" + r.nextInt(100000) + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
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