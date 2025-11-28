package dev.dacommander31.ethereal_expanse.datagen;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = EtherealExpanse.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(EEWorldGenProvider::new);
        event.createProvider(EEBlockTagProvider::new);
        event.createProvider(EEModelProvider::new);
        event.createProvider(EERecipeProvider.Runner::new);
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(EEBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider
        ));
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        event.createProvider(EEWorldGenProvider::new);
        event.createProvider(EEBlockTagProvider::new);
        event.createProvider(EEModelProvider::new);
        event.createProvider(EERecipeProvider.Runner::new);
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(EEBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider
        ));
    }
}
