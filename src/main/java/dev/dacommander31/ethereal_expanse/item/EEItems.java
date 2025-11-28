package dev.dacommander31.ethereal_expanse.item;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import dev.dacommander31.ethereal_expanse.component.EEDataComponents;
import dev.dacommander31.ethereal_expanse.item.custom.GatewayCanisterItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EEItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(EtherealExpanse.MOD_ID);

    public static final DeferredItem<GatewayCanisterItem> GATEWAY_CANISTER = ITEMS.registerItem("gateway_canister",
            (properties) -> new GatewayCanisterItem(properties
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .component(EEDataComponents.CONTAINS_GATEWAY, false)
                    .component(DataComponents.MAX_DAMAGE, 3)));

    public static final DeferredItem<Item> LEVIATHAN_BOW = ITEMS.registerItem("leviathan_bow",
            (properties) -> new BowItem(properties.durability(3000)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
