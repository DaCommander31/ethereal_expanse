package dev.dacommander31.ethereal_expanse.entity;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EEEntities {
    public static DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(EtherealExpanse.MOD_ID);
    

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
