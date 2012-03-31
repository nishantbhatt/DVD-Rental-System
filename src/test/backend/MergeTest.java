package test.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import session.backend.BackEndEngine;
import session.backend.FatalBackEndException;
import session.backend.FileType;

public class MergeTest {

	public static void main(String... args) {
		junit.textui.TestRunner.run(suite());
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(MergeTest.class);
	}

	@Test
	public void testMerge1() throws URISyntaxException, IOException {
		/* load the file from the resource */
		File t1 = new File(MergeTest.class.getResource(
				"test-cases/merge/test1/t1.tf").toURI());

		/* load second transaction file */
		File t2 = new File(MergeTest.class.getResource(
				"test-cases/merge/test1/t2.tf").toURI());

		/* load expected transaction file */
		File t3 = new File(MergeTest.class.getResource(
				"test-cases/merge/test1/mtf.ex").toURI());

		/* create temporary file for actual transaction file */
		File mtf = File.createTempFile("mtf", "tf");
		mtf.createNewFile();

		/* merge the result */
		BackEndEngine.merge(
				new String[] { t1.getAbsolutePath(), t2.getAbsolutePath() },
				mtf.getAbsolutePath());

		/* assert that expected output matches with actual output */
		AssertUtils.assertReaders(new BufferedReader(new FileReader(t3)),
				new BufferedReader(new FileReader(mtf)));
	}

	/* When merged transaction file could not be written */
	@Test
	public void testMerge2() throws URISyntaxException, IOException {
		/* load the file from the resource */
		File t1 = new File(MergeTest.class.getResource(
				"test-cases/merge/test2/t1.tf").toURI());

		/* load second transaction file */
		File t2 = new File(MergeTest.class.getResource(
				"test-cases/merge/test2/t2.tf").toURI());
		
		/* create temporary file for actual transaction file */
		File mtf = File.createTempFile("mtf", "tf");
		mtf.setWritable(false);
		mtf.deleteOnExit();

		/* merge the result */
		try {
			BackEndEngine
					.merge(new String[] { t1.getAbsolutePath(),
							t2.getAbsolutePath() }, mtf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			Assert.assertEquals(ex.getFileName(), mtf.getName());
			Assert.assertEquals(ex.getFileType(), FileType.MergedTransactionFile);
		}
	}

	/* When one of the transaction file is not READABLE or NOT EXISTS */
	@Test
	public void testMerge3() throws IOException, URISyntaxException {
		/* load the file from the resource (NOT WRITABLE OR READABLE) */
		File t1 = File.createTempFile("trf", "tf");
		t1.setReadable(false);
		t1.deleteOnExit();

		/* load second transaction file */
		File t2 = new File(MergeTest.class.getResource(
				"test-cases/merge/test3/t2.tf").toURI());

		/* create temporary file for actual transaction file */
		File mtf = File.createTempFile("mtf", "tf");

		try {
			BackEndEngine
					.merge(new String[] { t1.getAbsolutePath(),
							t2.getAbsolutePath() }, mtf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			Assert.assertEquals(ex.getFileName(), t1.getName());
			Assert.assertEquals(ex.getFileType(), FileType.TransactionFile);
		}
	}

	/* When one of the transaction file has improper formats */
	@Test
	public void testMerge4() throws IOException, URISyntaxException {
		/* load the file from the resource (NOT WRITABLE OR READABLE) */
		File t1 = new File(MergeTest.class.getResource(
				"test-cases/merge/test4/t1.tf").toURI());

		/* load second transaction file */
		File t2 = new File(MergeTest.class.getResource(
				"test-cases/merge/test4/t2.tf").toURI());

		/* create temporary file for actual transaction file */
		File mtf = File.createTempFile("mtf", "tf");

		try {
			BackEndEngine
					.merge(new String[] { t1.getAbsolutePath(),
							t2.getAbsolutePath() }, mtf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			Assert.assertEquals(ex.getFileName(), t1.getName());
			Assert.assertEquals(ex.getFileType(), FileType.TransactionFile);
		}
	}
}
