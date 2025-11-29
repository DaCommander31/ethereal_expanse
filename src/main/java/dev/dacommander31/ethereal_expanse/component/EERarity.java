package dev.dacommander31.ethereal_expanse.component;

import dev.dacommander31.ethereal_expanse.EtherealExpanse;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class EERarity {
    public static final EnumProxy<Rarity> LEGENDARY_RARITY_ENUM_PROXY = new EnumProxy<>(
            Rarity.class, -1, ResourceLocation.fromNamespaceAndPath(EtherealExpanse.MOD_ID, "legendary").toString(),
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_PURPLE)
    );
}
