package dev.dacommander31.ethereal_expanse.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.world.entity.animal.Cod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EEAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EtherealExpanse.MOD_ID);

    public static final Supplier<AttachmentType<Boolean>> LEVIATAN_BOMB_ATTACHED = ATTACHMENT_TYPES.register("leviathan_bomb_attached",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("leviathan_bomb_attached")).build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
