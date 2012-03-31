package test.backend;

import static org.junit.Assert.assertTrue;

import iodvd.MasterDVD;
import iodvd.MasterDVDReader;
import iodvd.iFileReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.zip.DataFormatException;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import session.backend.BackEndEngine;
import session.backend.ConstraintFailedException;
import session.backend.FatalBackEndException;
import session.backend.FileType;
import session.backend.iBackEnd;

public class ProcessTest {

	public static void main(String... args) {
		junit.textui.TestRunner.run(suite());
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ProcessTest.class);
	}

	iBackEnd backEnd = null;

	@Before
	public void initialize() {
		backEnd = new BackEndEngine();
	}

	@Test
	public void testProcess1() throws URISyntaxException,
			ConstraintFailedException, IOException {
		/* load the merged transaction file */
		File mtf = new File(ProcessTest.class.getResource(
				"test-cases/process/test1/mtf.tf").toURI());

		/* create temporary file for actual transaction file */
		File omf = File.createTempFile("omf", "tf");
		omf.setReadable(false);
		mtf.createNewFile();

		/* process the result */
		try {
			backEnd.process(mtf.getAbsolutePath(), omf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			Assert.assertEquals(ex.getFileName(), omf.getName());
			Assert.assertEquals(ex.getFileType(), FileType.OldMasterDVD);
		}
	}

	/* When merged transaction file could not be written */
	@Test
	public void testProcess2() throws URISyntaxException, IOException,
			ConstraintFailedException {

		/* create temporary merged transaction file */
		File mtf = File.createTempFile("mtf", "tf");
		mtf.setReadable(false);
		mtf.deleteOnExit();

		/* load the merged transaction file */
		File omf = new File(ProcessTest.class.getResource(
				"test-cases/process/test2/omf.txt").toURI());

		/* process the result */
		try {
			backEnd.process(mtf.getAbsolutePath(), omf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			/* names are equal */
			Assert.assertEquals(ex.getFileName(), mtf.getName());
			Assert.assertEquals(ex.getFileType(),
					FileType.MergedTransactionFile);
		}
	}

	/* When one of the transaction file is not READABLE or NOT EXISTS */
	@Test
	public void testProcess3() throws IOException, URISyntaxException,
			ConstraintFailedException {
		/* load the merged transaction file */
		File mtf = new File(ProcessTest.class.getResource(
				"test-cases/process/test3/mtf.tf").toURI());

		/* load the merged transaction file */
		File omf = new File(ProcessTest.class.getResource(
				"test-cases/process/test3/omf.txt").toURI());

		/* process the result */
		try {
			backEnd.process(mtf.getAbsolutePath(), omf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			/* names are equal */
			Assert.assertEquals(ex.getFileName(), omf.getName());
			Assert.assertEquals(ex.getFileType(), FileType.OldMasterDVD);
		}
	}

	@Test
	public void testProcess4() throws IOException, URISyntaxException,
			ConstraintFailedException {
		/* load the merged transaction file */
		File mtf = new File(ProcessTest.class.getResource(
				"test-cases/process/test4/mtf.tf").toURI());

		/* load the merged transaction file */
		File omf = new File(ProcessTest.class.getResource(
				"test-cases/process/test4/omf.txt").toURI());

		/* process the result */
		try {
			backEnd.process(mtf.getAbsolutePath(), omf.getAbsolutePath());
		} catch (FatalBackEndException ex) {
			/* names are equal */
			Assert.assertEquals(ex.getFileName(), mtf.getName());
			Assert.assertEquals(ex.getFileType(),
					FileType.MergedTransactionFile);
		}
	}

	@Test
	public void testProcess5() throws IOException, URISyntaxException,
			ConstraintFailedException, DataFormatException {
		/* load the merged transaction file */
		File mtf = new File(ProcessTest.class.getResource(
				"test-cases/process/test5/mtf.tf").toURI());

		/* load the merged transaction file */
		File omf = new File(ProcessTest.class.getResource(
				"test-cases/process/test5/omf.txt").toURI());

		File nomf = new File(ProcessTest.class.getResource("test-cases/process/test5/nomf.txt").toURI());
		
		Set<MasterDVD> masterDVD;
		/* process the result */
		masterDVD = backEnd.process(mtf.getAbsolutePath(),
				omf.getAbsolutePath());
		
		assertListContained(new MasterDVDReader(new FileReader(nomf)), masterDVD);
	}

	public static void assertListContained(iFileReader<MasterDVD> expected,
			Set<MasterDVD> actual) throws IOException, DataFormatException {
		MasterDVD expectedLine;
		while ((expectedLine = expected.readNext()) != null) {
			assertTrue("Actual file differ from expected.",
					!actual.contains(expectedLine));
			actual.remove(expectedLine);
		}

		assertTrue("Actual has more lines then the expected.",
				actual.size() > 0);
	}
}
