package dev.dacommander31.ethereal_expanse.datagen;

import dev.dacommander31.ethereal_expanse.block.EEBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class EEBlockLootTableProvider extends BlockLootSubProvider {
    protected EEBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(EEBlocks.STARLIT_END_STONE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return EEBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
