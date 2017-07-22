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

import static org.junit.Assert.*;

import java.io.Closeable;
import java.io.IOException;

import org.junit.Test;
import org.mcrossplatform.core.resource.ResourceCloser;

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
