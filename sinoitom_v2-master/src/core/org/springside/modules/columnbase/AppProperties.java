package org.springside.modules.columnbase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhanghj
 * @version 1.0
 * @company HuatengBase Co. Ltd
 * @category
 * @since 2019年11月14日 下午1:50:27
 */
public class AppProperties {

	/**
	 * Load property file
	 *
	 * @param fileName
	 * @return Properties
	 */
	public static Properties loadPropertiesFromClassPath(String fileName) {
		final Properties props = new Properties();
		InputStream input = null;
		try {
			input = AppProperties.class.getResourceAsStream(fileName);
			if (input == null) {
				//  log.warn("Unable to find{} ",fileName);
				return null;
			}
			//load a properties file from class path, inside static method
			props.load(input);
		} catch (IOException ex) {
			//  log.error(ex.toString());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//   log.error(e.toString());
				}
			}
		}

		return props;
	}

	public static Properties loadProperties(final String fileName) {
		final Properties properties = new Properties();
		try (InputStream in = AppProperties.class.getClassLoader().getResourceAsStream(fileName);) {
			properties.load(in);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("ERROR: Unable to load: " + fileName, e);
		}
	}
}
