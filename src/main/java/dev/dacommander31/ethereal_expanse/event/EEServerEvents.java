package dev.dacommander31.ethereal_expanse.event;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.util.EETickingTaskManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = EtherealExpanse.MOD_ID)
public class EEServerEvents {
    @SubscribeEvent
    public static void onTick(ServerTickEvent.Post event) {
        EETickingTaskManager.tick();
    }
}
