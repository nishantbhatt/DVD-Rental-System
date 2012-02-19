package iodvd;

import java.io.IOException;

/**
 * A generic interface to provide functionality to write an object.
 * 
 * @author 100413064
 * 
 * @param <T>
 *            Type of an object to be written.
 */
public interface iFileWriter<T> {
	/**
	 * Write an object to the data source.
	 * 
	 * @param obj
	 *            Object to be written.
	 * @throws IOException
	 *             When I/O exception occurs while writing an object to
	 *             data-sourse.
	 */
	public void writeObject(T obj) throws IOException;

	/**
	 * Closes the underlying writer stream and free-up all the recourses.
	 * @throws IOException When I/O exception occurs while closing the underlying writer stream.
	 */
	public void close() throws IOException;
}
