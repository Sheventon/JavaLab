package ru.itis.javalab;

import com.beust.jcommander.IStringConverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class UrlConverter implements IStringConverter<List<URL>> {

    @Override
    public List<URL> convert(String str) {
        String[] strs = str.split(";");
        List<URL> urls = new ArrayList<>();
        for (String s : strs) {
            try {
                urls.add(new URL(s));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException();
            }
        }
        return urls;
    }
}
