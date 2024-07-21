package com.iamcal.playground;

import net.fabricmc.api.ModInitializer;
import com.iamcal.playground.registries.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Playground implements ModInitializer {
	public static final String MOD_ID = "playground";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		Items.initialize();
	}
}