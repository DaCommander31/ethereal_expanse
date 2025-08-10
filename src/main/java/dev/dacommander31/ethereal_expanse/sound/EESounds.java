package dev.dacommander31.ethereal_expanse.sound;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EESounds {
    public static DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, EtherealExpanse.MOD_ID);

    public static final Supplier<SoundEvent> STARLIT_END_STONE_STEP = registerSoundEvent("block.starlit_end_stone.step");

    public static final DeferredSoundType STARLIT_END_STONE_SOUNDS = new DeferredSoundType(1f, 1f,
            () -> SoundEvents.SCULK_BLOCK_BREAK, EESounds.STARLIT_END_STONE_STEP, () -> SoundEvents.SCULK_BLOCK_PLACE,
            () -> SoundEvents.SCULK_BLOCK_HIT, () -> SoundEvents.SCULK_BLOCK_FALL);

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
