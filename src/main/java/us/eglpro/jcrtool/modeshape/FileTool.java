package us.eglpro.jcrtool.modeshape;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class FileTool {

	public static final String FILE_NODE_TYPE = "nt:file";

	/**
	 * @param localFile
	 *            the file to upload
	 * @param folder
	 *            the node to upload the file to
	 * @param session
	 * @return the file, as a JCR node
	 * @throws FileNotFoundException
	 *             if {@code localFile} cannot be resolved to a file
	 * @throws RepositoryException
	 *             if an error occurs when adding the file to the repository
	 *             {@link Node} {@code folder}
	 * @see {@link Session#save()} which should be called after this method
	 *      returns, to persist the changes to the repository
	 */
	public static Node uploadFile(File localFile, Node folder)
			throws FileNotFoundException, RepositoryException {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(localFile.lastModified());
		String name = localFile.getName();
		return uploadFile(localFile, name, c, folder);
	}

	/**
	 * @param localFile
	 *            the file to upload
	 * @param nodeFileName
	 *            the name for the file as a node
	 * @param lastModified
	 *            the last modified time to set for the file as a node
	 * @param folder
	 *            the node to upload the file to
	 * @return the file, as a JCR node
	 * @throws FileNotFoundException
	 *             if {@code localFile} cannot be resolved to a file
	 * @throws RepositoryException
	 *             if an error occurs when adding the file to the repository
	 *             {@link Node} {@code folder}
	 * @see {@link Session#save()} which should be called after this method
	 *      returns, to persist the changes to the repository
	 */
	public static Node uploadFile(File localFile, String nodeFileName,
			Calendar lastModified, Node folder) throws FileNotFoundException,
			RepositoryException {
		return uploadFile(FILE_NODE_TYPE, localFile, lastModified,
				nodeFileName, folder);
	}

	/**
	 * @param fileNodeType
	 *            the node type for the file as a node (should be a subtype of
	 *            {@code "nt:file"})
	 * @param localFile
	 *            the file to upload
	 * @param lastModified
	 *            the last modified time to set for the file as a node
	 * @param nodeFileName
	 *            the name for the file as a node
	 * @param folder
	 *            the node to upload the file to
	 * @return the file, as a JCR node
	 * @throws FileNotFoundException
	 *             if {@code localFile} cannot be resolved to a file
	 * @throws RepositoryException
	 *             if an error occurs when adding the file to the repository
	 *             {@link Node} {@code folder}
	 * @see {@link Session#save()} which should be called after this method
	 *      returns, to persist the changes to the repository
	 */
	public static Node uploadFile(String fileNodeType, File localFile,
			Calendar lastModified, String nodeFileName, Node folder)
			throws FileNotFoundException, RepositoryException {
		Node file = null;
		Session session = folder.getSession();
		InputStream stream = new BufferedInputStream(new FileInputStream(
				localFile));
		file = folder.addNode(nodeFileName, fileNodeType);
		// FIXME: parameterize these values:
		Node fileContent = file.addNode("jcr:content", "nt:resource");
		Binary binary = session.getValueFactory().createBinary(stream);
		// FIXME: parameterize these values:
		fileContent.setProperty("jcr:data", binary);
		fileContent.setProperty("jcr:lastModified", lastModified);
		return file;

	}
}
