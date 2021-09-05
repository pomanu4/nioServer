package ua.mycompany.project.nextStep;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationFactory;

import com.google.gson.Gson;

public class MainTest {
	
	private static final Logger logger = LogManager.getLogger(MainTest.class.getName());
	private static final Logger mLoggerX = LogManager.getLogger("mainLogger");

	public static void main(String[] args) throws IOException, URISyntaxException {
		
		try {
			
			ServerSocketRunner runner = new ServerSocketRunner(29888);
			runner.start();
			
		}catch (Exception e) {
			logger.error(e);
		}
	}
	
	
	

}
