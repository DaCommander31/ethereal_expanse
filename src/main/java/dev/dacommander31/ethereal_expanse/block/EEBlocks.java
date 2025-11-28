package dev.dacommander31.ethereal_expanse.block;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.block.custom.StarlitBlossomBlock;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import dev.dacommander31.ethereal_expanse.sound.EESounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

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

    public static final DeferredBlock<StarlitBlossomBlock> OPEN_STARLIT_BLOSSOM = registerBlock("open_starlit_blossom",
            ((properties) -> new StarlitBlossomBlock(StarlitBlossomBlock.Type.OPEN, properties
                    .mapColor(MapColor.COLOR_PURPLE)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks())));

    public static final DeferredBlock<StarlitBlossomBlock> CLOSED_STARLIT_BLOSSOM = registerBlock("closed_starlit_blossom",
            (properties -> new StarlitBlossomBlock(StarlitBlossomBlock.Type.CLOSED, properties
                    .mapColor(MapColor.COLOR_MAGENTA)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks())));

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
