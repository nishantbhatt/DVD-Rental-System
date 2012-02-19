package utilsdvd;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * This is general utility class to provide some general functionalities in DVD Rental System.
 * @author 100413064
 *
 */
class Tools {
	
	/**
	 * Returns the path where program the program(JAR FILE or CLASS) is stored.
	 * @return Returns the absolute path.
	 * @throws UnsupportedEncodingException When PATH URL cannot be decode to UTF-8 format.
	 */
	public static String getCurrentPath() throws UnsupportedEncodingException {
		/* get the path of THIS class and convert it to UTF-8 by removing %20 and other non UTF-8 formatting */
		String dir = URLDecoder.decode(Tools.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath(), "UTF-8");

		/* Recursively search until we get a directory */
		File f = new File(dir);
		while (!f.isDirectory())
			f = f.getParentFile();

		/* returns the absolute path */
		return f.getAbsolutePath();
	}
}
