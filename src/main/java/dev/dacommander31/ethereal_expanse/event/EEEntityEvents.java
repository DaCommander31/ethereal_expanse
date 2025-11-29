package dev.dacommander31.ethereal_expanse.event;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import dev.dacommander31.ethereal_expanse.network.EEAttachmentTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
            entity.setData(EEAttachmentTypes.LEVIATHAN_BOMB_TIME, 60);
            EtherealExpanse.LOGGER.info("damaged by leviathan bow");
        }
    }

}
