package org.mcrossplatform.resource;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ResourceCloser {
	private static final Logger LOGGER = Logger.getLogger(ResourceCloser.class.getName());
	private ResourceCloser() {
		// not instance
	}
	public static void close(final Closeable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (final IOException e) {
			LOGGER.log(Level.WARNING, String.format("Failed to close %s (may be already closed).", c), e);
		}
	}
}
