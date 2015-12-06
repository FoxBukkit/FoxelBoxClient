package com.foxelbox.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;

public class PlayerControllerFB extends PlayerControllerMP {
    private Minecraft _mc;
    private NetHandlerPlayClient _netClientHandler;

    public PlayerControllerFB(Minecraft mcIn, NetHandlerPlayClient p_i45062_2_) {
        super(mcIn, p_i45062_2_);
        this._mc = mcIn;
        this._netClientHandler = p_i45062_2_;
    }

    @Override
    public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter p_178892_2_) {
        return new EntityPlayerFB(this._mc, worldIn, this._netClientHandler, p_178892_2_);
    }
}
