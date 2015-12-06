package com.foxelbox.mods;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import java.util.Arrays;

public class FoxelBoxClientModContainer extends DummyModContainer
{
    public static final String MODID = "foxelboxclient";
    public static final String VERSION = "1.0";

    public FoxelBoxClientModContainer() {
        super(new ModMetadata());
        ModMetadata myMeta = super.getMetadata();
        myMeta.authorList = Arrays.asList("Doridian");
        myMeta.description = "FoxelBoxClient";
        myMeta.modId = FoxelBoxClientModContainer.MODID + "_core";
        myMeta.version = FoxelBoxClientModContainer.VERSION;
        myMeta.name = "FoxelBoxClient module";
        myMeta.url = "";
    }

    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
