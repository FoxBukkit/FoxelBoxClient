package com.foxelbox.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPlayerFB extends EntityPlayerSP {
    public EntityPlayerFB(Minecraft mcIn, World worldIn, NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_) {
        super(mcIn, worldIn, p_i46278_3_, p_i46278_4_);
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (!this.capabilities.isFlying || this.ridingEntity != null) {
            super.moveEntityWithHeading(strafe, forward);
            return;
        }

        moveFlyingWithPitch(strafe, forward, 1F);
        moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    public void moveFlyingWithPitch(float strafing, float forward, float f2) {
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        System.out.println(iattributeinstance.getAttributeValue());
        float speedFactor = 50.0F * (float)iattributeinstance.getAttributeValue() * this.capabilities.getFlySpeed() * (float)(this.isSprinting() ? 2 : 1);

        motionY = this.isJumping ? speedFactor : 0;
        motionY += this.isSneaking() ? -speedFactor : 0;

        float f3 = MathHelper.sqrt_float(strafing * strafing + forward * forward);
        if(f3 < 0.01F)
        {
            motionX = 0;
            motionZ = 0;
            return;
        }
        if(f3 < 1.0F)
        {
            f3 = 1.0F;
        }
        f3 = f2 / f3;
        strafing *= f3;
        forward *= f3;

        float f4 = MathHelper.sin((rotationYaw * 3.141593F) / 180F);
        float f5 = MathHelper.cos((rotationYaw * 3.141593F) / 180F);

        float f6 = MathHelper.sin((rotationPitch * 3.141593F) / 180F);
        float f7 = MathHelper.cos((rotationPitch * 3.141593F) / 180F);

        float fmul = 0;
        if(forward > 0)
            fmul = -1;
        else if(forward < 0)
            fmul = 1;

        motionX = (strafing * f5 - forward * f4 * f7) * speedFactor;
        motionY += fmul * f6 * speedFactor;
        motionZ = (forward * f5 * f7 + strafing * f4) * speedFactor;
    }
}
