package org.mcrossplatform.resource;

import static org.junit.Assert.*;

import java.io.Closeable;
import java.io.IOException;

import org.junit.Test;

public class ResourceCloserTest {
	@Test
	public void close_WithNull_NoException() {
		// arrange, act
		ResourceCloser.close(null);
	}
	@Test
	public void close_happyCase_CloseCalled() {
		// arrange
		TestCloseable closeable = new TestCloseable();
		// act
		ResourceCloser.close(closeable);
		// assert
		assertTrue(closeable.closed());
	}
	
	@Test
	public void close_WithException_NoException() {
		// arrange
		TestCloseable closeable = new TestCloseable();
		ResourceCloser.close(closeable);
		// act
		ResourceCloser.close(closeable);
		// assert
		assertTrue(closeable.closed());
	}
	
	private final static class TestCloseable implements Closeable {

		private boolean closed = false;
		@Override
		public void close() throws IOException {
			if (closed) {
				throw new IOException("Already closed,");
			}
			closed = true;
		}

		private boolean closed() {
			return  closed;
		}
	}
}
