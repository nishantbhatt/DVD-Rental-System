package test.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.IOException;

public class AssertUtils {
	public static void assertReaders(BufferedReader expected,
			BufferedReader actual) throws IOException {
		String expectedLine;
		while ((expectedLine = expected.readLine()) != null) {
			String actualLine = actual.readLine();
			assertNotNull("Expected had more lines then the actual.",
					actualLine);
			assertEquals(expectedLine, actualLine);
		}

		assertNull("Actual had more lines then the expected.",
				actual.readLine());
	}
}
