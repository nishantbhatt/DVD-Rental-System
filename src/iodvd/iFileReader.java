package iodvd;

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * generic interface to provide the reader functionality.
 * 
 * @author 100413064
 * 
 * @param <T>
 *            Type of object to be read.
 */
public interface iFileReader<T> {

	/**
	 * Reads the object found next within the data source.
	 * 
	 * @return Object read.
	 * @throws IOException
	 *             When I/O exception occurs while reading data source.
	 * @throws DataFormatException
	 *             When data source is in wrong format.
	 */
	public T readNext() throws IOException, DataFormatException;

	/**
	 * Closes the underlying reader stream and free-up all the resources.
	 * 
	 * @throws IOException
	 *             When I/O exception occurs while closing underlying reader.
	 */
	public void close() throws IOException;
}
