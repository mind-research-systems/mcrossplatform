package org.mcrossplatform.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileResourceLocator {
	private static final Logger LOGGER = Logger.getLogger(FileResourceLocator.class.getName());
	private static final String[] DEFAULT_PROPERTIES_FOLDERS = new String[]{"./","./src/test/resources","./src/main/resources"};
	private static final List<String> SEARCH_FOLDERS = new ArrayList<String>();
	static {
		resetResourceFlders();
	}
	
	public static boolean fileExists(final String fileName) {
		return fileExists(null, fileName);
	}
	public static boolean fileExists(final String resourcefolder, final String fileName) {
		return findFile(resourcefolder, fileName, true) !=null;
	}
	public static File findFile(final String fileName, final boolean mayReturnNull) {
		return findFile(null, fileName,mayReturnNull);
	}
	public static File findFile(final String resourcefolder, final String fileName, final boolean mayReturnNull) {
		final List<String> folders = resourcefolder==null ? SEARCH_FOLDERS : Arrays.asList(resourcefolder);
		for (final String folder : folders) {
			final File f = new File(folder,fileName);
			if (f.exists()) {
				return f;
			}
		}
		if (!mayReturnNull) {
			final String message = String.format("File: %s not found in path: %s.", fileName, folders);
			LOGGER.log(Level.SEVERE,message);
			throw new RuntimeException(message);
			
		}
		return null;
	}
	
	public static void addResourceFolder(String folder) {
		SEARCH_FOLDERS.add(folder);
	}
	
	public static void resetResourceFlders() {
		SEARCH_FOLDERS.clear();
		SEARCH_FOLDERS.addAll(Arrays.asList(DEFAULT_PROPERTIES_FOLDERS));
	}
	
	private FileResourceLocator() {
		// private ctor
	}

}
