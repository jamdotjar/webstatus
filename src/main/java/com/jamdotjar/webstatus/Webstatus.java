package com.jamdotjar.webstatus;

import com.jamdotjar.webstatus.status.StatusService;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Webstatus implements ModInitializer {
	public static final String MOD_ID = "webstatus";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static WebServer WEBSTATUS_SERVER;
	private static StatusService STATUS_SERVICE;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("{} initialized", MOD_ID);

		STATUS_SERVICE = new StatusService();

		int port = 8080;

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			STATUS_SERVICE.setServer(server);
			WEBSTATUS_SERVER = new WebServer(port);
			WEBSTATUS_SERVER.start(STATUS_SERVICE);

			LOGGER.info("WebServer started on port {}", port);
		});

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
