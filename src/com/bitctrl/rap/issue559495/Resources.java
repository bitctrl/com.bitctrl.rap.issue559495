package com.bitctrl.rap.issue559495;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.ClientFileLoader;
import org.eclipse.rap.rwt.service.ResourceManager;

public class Resources {

	private static final String BASE_DIR = "/src-web/";
	private static final String PREFIX = "/map/";

	private static final List<String> RESOURCES = Arrays.asList(
			"leaflet/leaflet-src.js.map",
			"leaflet/images/layers.png",
			"leaflet/images/layers-2x.png",
			"leaflet/images/marker-icon.png",
			"leaflet/images/marker-icon-2x.png",
			"leaflet/images/marker-shadow.png");

	private static final List<String> SCRIPTS = Arrays.asList(
			"leaflet/leaflet-src.js",
			"map.js");

	private static final List<String> STYLES = Arrays.asList(
			"leaflet/leaflet.css");

	static void initialize() {
		registerResources(RESOURCES);
		registerResources(SCRIPTS);
		registerResources(STYLES);

		loadStyles(STYLES);
		loadScripts(SCRIPTS);
	}

	private static void registerResources(List<String> resources) {
		ResourceManager resourceManager = RWT.getResourceManager();
		for (String resource : resources) {
			if (!resourceManager.isRegistered(resource)) {
				String path = BASE_DIR + resource;
				try (InputStream input = Resources.class.getResourceAsStream(path)) {
					String name = PREFIX + resource;
					resourceManager.register(name, input);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	private static void loadScripts(List<String> scripts) {
		ClientFileLoader loader = RWT.getClient().getService(ClientFileLoader.class);
		ResourceManager resourceManager = RWT.getResourceManager();
		for (String script : scripts) {
			String name = PREFIX + script;
			String url = resourceManager.getLocation(name);
			loader.requireJs(url);
		}
	}

	private static void loadStyles(List<String> styles) {
		ClientFileLoader loader = RWT.getClient().getService(ClientFileLoader.class);
		ResourceManager resourceManager = RWT.getResourceManager();
		for (String style : styles) {
			String name = PREFIX + style;
			String url = resourceManager.getLocation(name);
			loader.requireCss(url);
		}
	}

}
