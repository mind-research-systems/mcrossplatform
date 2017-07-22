/*
 * #%L
 * service
 * %%
 * Copyright (C) 2017 MRS Internet Service GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.mcrossplatform.core.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileResourceLocator {
  private static final Logger LOGGER = Logger.getLogger(FileResourceLocator.class.getName());
  private static final String[] DEFAULT_PROPERTIES_FOLDERS = new String[] { "./",
      "./src/test/resources", "./src/main/resources" };
  private static final List<String> SEARCH_FOLDERS = new ArrayList<String>();

  static {
    resetResourceFlders();
  }

  public static boolean fileExists(final String fileName) {
    return fileExists(null, fileName);
  }

  public static boolean fileExists(final String resourcefolder, final String fileName) {
    return findFile(resourcefolder, fileName, true) != null;
  }

  public static File findFile(final String fileName, final boolean mayReturnNull) {
    return findFile(null, fileName, mayReturnNull);
  }

  /**
   * Finds a file with fileName in a given resource folder.
   * 
   * @param resourcefolder name of the folder
   * @param fileName name of the file
   * @param mayReturnNull or throw exception
   * @return File
   */
  public static File findFile(final String resourcefolder, final String fileName,
      final boolean mayReturnNull) {
    final List<String> folders;
    if (resourcefolder == null) {
      folders = SEARCH_FOLDERS;
    } else {
      folders = Arrays.asList(resourcefolder);
    }
    for (final String folder : folders) {
      final File f = new File(folder, fileName);
      if (f.exists()) {
        return f;
      }
    }
    if (!mayReturnNull) {
      final String message = String.format("File: %s not found in path: %s.", fileName, folders);
      LOGGER.log(Level.SEVERE, message);
      throw new ResourceException(message);

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
