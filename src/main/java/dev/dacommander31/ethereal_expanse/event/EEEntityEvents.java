package dev.dacommander31.ethereal_expanse.event;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = EtherealExpanse.MOD_ID)
public class EEEntityEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();
        ItemStack weaponItem = damageSource.getWeaponItem();

        if (weaponItem != null && weaponItem.is(EEItems.LEVIATHAN_BOW)) {
            //TODO: Add bomb planting
            CompoundTag data = new CompoundTag();
            CompoundTag tag = new CompoundTag();
            tag.putShort("Fuse", (short) 40);
            data.put("LeviathanBomb", tag);
            entity.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(data));
            EtherealExpanse.LOGGER.info("damaged by leviathan bow");
        }
    }

}
