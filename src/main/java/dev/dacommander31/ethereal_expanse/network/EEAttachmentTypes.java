package dev.dacommander31.ethereal_expanse.network;

import com.mojang.serialization.Codec;
import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EEAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EtherealExpanse.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> LEVIATHAN_BOMB_TIME = ATTACHMENT_TYPES.register(
            "leviathan_bomb_time",
            () -> AttachmentType.builder(
                    () -> {EtherealExpanse.LOGGER.info("NEW DATA"); return 0;})
                    .serialize(Codec.INT.fieldOf("leviathan_bomb_time"))
                    .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
