package dev.dacommander31.ethereal_expanse.mixin;

import dev.dacommander31.ethereal_expanse.item.EEItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(EnderMan.class)
public class EnderManMixin {


    @Inject(method = "hurtServer", at=@At("HEAD"))
    public static boolean hurtServer(ServerLevel level, DamageSource damageSource, float p_376796_) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
            if(damageSource.getWeaponItem() == null) return false;
            return damageSource.getWeaponItem().is(EEItems.LEVIATHAN_BOW);
        }
        return false;
    }


}
