package dev.dacommander31.ethereal_expanse;

import dev.dacommander31.ethereal_expanse.block.EEBlocks;
import dev.dacommander31.ethereal_expanse.component.EEDataComponents;
import dev.dacommander31.ethereal_expanse.item.EEItems;
import dev.dacommander31.ethereal_expanse.sound.EESounds;
import dev.dacommander31.ethereal_expanse.worldgen.biome.EETerrablender;
import dev.dacommander31.ethereal_expanse.worldgen.biome.surface.EESurfaceRules;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.SurfaceRuleManager;

@Mod(EtherealExpanse.MOD_ID)
public class EtherealExpanse {
    public static final String MOD_ID = "ethereal_expanse";
    public static final Logger LOGGER = LogUtils.getLogger();



    public EtherealExpanse(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        EEItems.register(modEventBus);
        EEBlocks.register(modEventBus);
        EESounds.register(modEventBus);
        EETerrablender.registerBiomes();
        EEDataComponents.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.END, MOD_ID, EESurfaceRules.makeRules());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(EEItems.GATEWAY_CANISTER);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
