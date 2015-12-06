package com.foxelbox.mods;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({
        "com.foxelbox.mods"
})
@IFMLLoadingPlugin.Name(value = "FoxelBoxClient")
@IFMLLoadingPlugin.SortingIndex(value = 1001)
public class FoxelBoxClientMod implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
                "com.foxelbox.mods.FoxelClientTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "com.foxelbox.mods.FoxelBoxClientModContainer";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
