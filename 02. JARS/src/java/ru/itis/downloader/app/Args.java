package ru.itis.downloader.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.PathConverter;

import ru.itis.downloader.utils.UrlConverter;
import ru.itis.downloader.utils.ModeValidator;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;

@Parameters(separators = "=")
public class Args {
	@Parameter(names = {"--mode"}, validateWith = ModeValidator.class)
	public String mode;

	@Parameter(names = {"--count"})
	public Integer count;

	@Parameter(names = {"--files"}, listConverter = UrlConverter.class)
	public List<URL> files;

	@Parameter(names = {"--folder"}, converter = PathConverter.class)
	public Path folder;
}