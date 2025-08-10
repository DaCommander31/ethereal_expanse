package dev.dacommander31.ethereal_expanse.datagen;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.block.EEBlocks;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.stream.Stream;

public class EEModelProvider extends ModelProvider {
    public static final List<DeferredHolder<Block, ? extends Block>> EXCLUDED_BLOCKS = List.of(
            EEBlocks.STARLIT_END_STONE
    );

    public static final List<DeferredHolder<Item, ? extends Item>> EXCLUDED_ITEMS = List.of(
            EEItems.GATEWAY_CANISTER
    );


    public EEModelProvider(PackOutput output) {
        super(output, EtherealExpanse.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return EEBlocks.BLOCKS.getEntries().stream().filter(block -> !EXCLUDED_BLOCKS.contains(block));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return EEItems.ITEMS.getEntries().stream().filter(item -> !EXCLUDED_ITEMS.contains(item));
    }
}
