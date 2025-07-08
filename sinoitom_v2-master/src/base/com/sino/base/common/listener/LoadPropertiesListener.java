package com.sino.base.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

/**
 * 加载application.properties
 * @author .
 */
public class LoadPropertiesListener implements ServletContextListener {

	public final static String APPLICATION_PROPERTIES = "application.properties";
	public static String FILE_SEPARATOR = File.separator;
	public static String UPLOAD_DEVIMG_URL = "/opt/SinoMSP/icon/devImg/";
	public static String UPLOAD_BGIMG_URL = "/opt/SinoMSP/icon/bgImg/";

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties properties = System.getProperties();
		Optional<BufferedInputStream> fileInputStream = Optional.empty();
        String applicationProperties = sce.getServletContext().getRealPath("/") + File.separator + "WEB-INF" + File.separator + "conf" + File.separator + APPLICATION_PROPERTIES;
        Path path = Paths.get(applicationProperties);
        if (!Files.exists(path)) {
			applicationProperties = System.getProperty("user.dir").replace("bin", "conf") + FILE_SEPARATOR + APPLICATION_PROPERTIES;
		}
		try {
			fileInputStream = Optional.of(new BufferedInputStream(new FileInputStream(applicationProperties)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileInputStream.ifPresent(input -> {
			try {
				properties.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		UPLOAD_DEVIMG_URL = System.getProperty("upload.devimg.url");
		UPLOAD_BGIMG_URL = System.getProperty("upload.bgimg.url");
	}
}
