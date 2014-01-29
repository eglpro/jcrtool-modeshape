package us.eglpro.jcrtool.modeshape;

import java.io.File;
import java.io.FileNotFoundException;

import javax.jcr.RepositoryException;

import org.infinispan.schematic.document.ParsingException;
import org.modeshape.jcr.ConfigurationException;
import org.modeshape.jcr.JcrRepository;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;

public class EngineTool {

	protected ModeShapeEngine engine;
	protected RepositoryConfiguration config;

	public static void main(String[] args) {
		// FIXME: Parse command line args for additional options
		String configPath = args[0];
		EngineTool eng = new EngineTool();
		try {
			eng.loadConfig(configPath);
			eng.startEngine();
		} catch (ParsingException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(2);
		}
		
	}

	public EngineTool() {
		engine = new ModeShapeEngine();
	}

	/**
	 * 
	 * @see {@link #deployEngine}
	 */
	public void startEngine() {
		engine.start();
	}

	/**
	 * Deploy the configured {@link ModeShapeEngine} (Delegate method) 
	 * 
	 * @return the deployed {@link JcrRepository}
	 * @throws ConfigurationException
	 * @throws RepositoryException
	 * @see {@link #loadConfig(File)}, {@link #loadConfig(String)},
	 *      {@link ModeShapeEngine#deploy(RepositoryConfiguration)}
	 */
	public JcrRepository deployEngine() throws ConfigurationException,
			RepositoryException {
		return engine.deploy(getConfig());
	}

	public ModeShapeEngine getEngine() {
		return engine;
	}

	public void setEngine(ModeShapeEngine engine) {
		this.engine = engine;
	}

	public RepositoryConfiguration getConfig() {
		return config;
	}

	public void setConfig(RepositoryConfiguration config) {
		this.config = config;
	}

	public void loadConfig(String configFile) throws ParsingException,
			FileNotFoundException {
		File f = new File(configFile);
		loadConfig(f);
	}

	public void loadConfig(File configFile) throws ParsingException,
			FileNotFoundException {
		RepositoryConfiguration c = RepositoryConfiguration.read(configFile);
		setConfig(c);
	}

	/**
	 * Validate the loaded configuration (Delegate method)
	 * 
	 * @return the result of the validation, will not be null
	 * @see {@link RepositoryConfiguration#validate()},
	 *      {@link RepositoryConfiguration#validate(org.infinispan.schematic.document.Changes)}
	 */
	public org.modeshape.common.collection.Problems validateConfig() {
		return config.validate();
	}

}
