package ru.itis.downloader.app;

import ru.itis.downloader.utils.Downloader;

import com.beust.jcommander.JCommander;

import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Programm {
	public static void main(String[] argv) {
		Args args = new Args();

        JCommander.newBuilder()
            .addObject(args)
            .build()
            .parse(argv);

        Downloader downloader = new Downloader();
        Path path = args.folder;
        String mode = args.mode;

        if (path != null && mode != null) {
        	if (mode.equals("one-thread")){
        		for (URL url : args.files) {
        			downloader.download(url, path);
        		}
        	} else if (mode.equals("multi-thread")) {
        		Integer count = args.count;
        		if (count != null) {
        			ExecutorService executorService = Executors.newFixedThreadPool(count);
        			for (URL url : args.files) {
                        executorService.submit(() -> {
                            downloader.download(url, path);
                        });
        			}
        		}
        	}
        }
	}
}