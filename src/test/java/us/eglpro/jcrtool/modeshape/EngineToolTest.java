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
import org.modeshape.jcr.JcrRepository;
import org.modeshape.jcr.ModeShapeEngine;

public class EngineToolTest {

	public static final String TEST_CONFIG = "src/test/resources/testRepository.json";
	public static final String TEST_WORKSPACE_NAME = "defaultWorkspace";
	
	
	protected String[] args;
	protected EngineTool tool;

	@Before
	public void setUp() throws Exception {
		tool = new EngineTool();
	}

	@After
	public void tearDown() throws Exception {
		ModeShapeEngine eng = tool.getEngine();
		if( eng != null) {
			eng.shutdown();
		}
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
		JcrRepository r = null;
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
			r = tool.deployEngine();
		} catch (ConfigurationException e) {
			e.printStackTrace();
			fail("Deploy - ConfigurationException");
		} catch (RepositoryException e) {
			e.printStackTrace();
			fail("Deploy - RepositoryException");
		}

		try {
			// NOTE: The respository isn't created on the filesystem, until here:
			javax.jcr.Session session = r.login(TEST_WORKSPACE_NAME);
		} catch (RepositoryException e) {
			e.printStackTrace();
			fail("Login - RepositoryException");
		}
		 

	}

}
