package dev.dacommander31.ethereal_expanse.component;

import com.mojang.serialization.Codec;
import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class EEDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, EtherealExpanse.MOD_ID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CONTAINS_GATEWAY = register("contains_gateway",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> GATEWAY_EXIT = register("gateway_exit",
            builder -> builder.persistent(BlockPos.CODEC).networkSynchronized(ByteBufCodecs.fromCodec(BlockPos.CODEC)));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                           UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
