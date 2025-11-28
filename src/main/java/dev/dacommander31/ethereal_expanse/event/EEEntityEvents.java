package dev.dacommander31.ethereal_expanse.event;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.awt.*;

@EventBusSubscriber(modid = EtherealExpanse.MOD_ID)
public class EEEntityEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDeathEvent event) {
        if(event.getSource().getWeaponItem().is(EEItems.LEVIATHAN_BOW)) {
            //TODO: Add bomb planting
        }
    }

}
