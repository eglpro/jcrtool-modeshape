package us.eglpro.jcrtool.modeshape;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.jcr.RepositoryException;

import org.infinispan.schematic.document.ParsingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.ConfigurationException;

public class EngineToolTest {

	public static final String TEST_CONFIG = "src/test/resources/testRepository.json";

	protected String[] args;
	protected EngineTool tool;

	@Before
	public void setUp() throws Exception {
		tool = new EngineTool();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadConfigString() {
		try {
			tool.loadConfig(TEST_CONFIG);
			Problems p = tool.validateConfig();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ParsingException");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("FileNotFoundException");
		}
	}

	@Test
	public void testMain() {
		try {
			tool.loadConfig(TEST_CONFIG);
		} catch (ParsingException e1) {
			e1.printStackTrace();
			fail("Configuration - ParsingException");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail("Configuration - FileNotFoundException");
		}
		tool.startEngine();
		try {
			tool.deployEngine();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			fail("Deploy - ConfigurationException");
		} catch (RepositoryException e) {
			e.printStackTrace();
			fail("Deploy - RepositoryException");
		}

	}

}
