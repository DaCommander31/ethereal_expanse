package dev.dacommander31.ethereal_expanse.block;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import dev.dacommander31.ethereal_expanse.sound.EESounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class EEBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EtherealExpanse.MOD_ID);


    public static final DeferredBlock<Block> STARLIT_END_STONE = registerBlock("starlit_end_stone",
            (properties) -> new Block(properties.
                    mapColor(MapColor.COLOR_CYAN).
                    instrument(NoteBlockInstrument.BASEDRUM).
                    requiresCorrectToolForDrops().
                    strength(3.0F, 9.0F)
                    .sound(EESounds.STARLIT_END_STONE_SOUNDS)
            )
    );

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        EEItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties
                .useBlockDescriptionPrefix()
                .setId(ResourceKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, name))
                )));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
