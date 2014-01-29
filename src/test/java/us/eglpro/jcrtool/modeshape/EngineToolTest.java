package us.eglpro.jcrtool.modeshape;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EngineToolTest {

	public static final String TEST_CONFIG = "src/test/resources/testRepository.json";

	protected String[] args;
	protected EngineTool tool;
	
	@Before
	public void setUp() throws Exception {
		args = new String[]{TEST_CONFIG}; 
		EngineTool.main(args);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() {
		
	}

}
