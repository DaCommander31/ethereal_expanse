package dev.dacommander31.ethereal_expanse.datagen;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.block.EEBlocks;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.stream.Stream;

public class EEModelProvider extends ModelProvider {
    public static final List<DeferredHolder<Block, ? extends Block>> EXCLUDED_BLOCKS = List.of(
            EEBlocks.STARLIT_END_STONE,
            EEBlocks.OPEN_STARLIT_BLOSSOM,
            EEBlocks.CLOSED_STARLIT_BLOSSOM
    );

    public static final List<DeferredHolder<Item, ? extends Item>> EXCLUDED_ITEMS = List.of(
            EEItems.GATEWAY_CANISTER,
            getItemHolder(EEBlocks.OPEN_STARLIT_BLOSSOM),
            getItemHolder(EEBlocks.CLOSED_STARLIT_BLOSSOM)
    );

    private static DeferredHolder<Item, ? extends Item> getItemHolder(DeferredHolder<Block, ? extends Block> block) {
        return DeferredHolder.create(ResourceKey.create(Registries.ITEM, block.getId()));
    }


    public EEModelProvider(PackOutput output) {
        super(output, EtherealExpanse.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        blockModels.createCrossBlock(EEBlocks.OPEN_STARLIT_BLOSSOM.get(), BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED);
//        blockModels.createCrossBlock(EEBlocks.CLOSED_STARLIT_BLOSSOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);

//        itemModels.generateLayeredItem(
//                ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, "item/open_starlit_blossom"),
//                ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, "block/open_starlit_blossom"),
//                ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, "block/open_starlit_blossom_emissive"));
//
//        itemModels.generateFlatItem(EEBlocks.CLOSED_STARLIT_BLOSSOM.asItem(), ModelTemplates.FLAT_ITEM);
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
