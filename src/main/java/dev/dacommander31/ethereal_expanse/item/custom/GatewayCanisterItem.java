package dev.dacommander31.ethereal_expanse.item.custom;

import dev.dacommander31.ethereal_expanse.component.EEDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class GatewayCanisterItem extends Item {

    public GatewayCanisterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack handItem = context.getItemInHand();
        BlockPos clickedPos = context.getClickedPos();
        if (level.getBlockState(context.getClickedPos()).is(Blocks.END_GATEWAY)
                && Boolean.FALSE.equals(handItem.get(EEDataComponents.CONTAINS_GATEWAY))) {
            if (!level.isClientSide()) {
                context.getItemInHand().set(EEDataComponents.CONTAINS_GATEWAY, true);
                if (level.getBlockEntity(clickedPos) instanceof TheEndGatewayBlockEntity) {
                    BlockPos exitPos = TheEndGatewayBlockEntity.findExitPosition(level, clickedPos);
                    context.getItemInHand().set(EEDataComponents.GATEWAY_EXIT, exitPos);
                }
                level.playSound(null, clickedPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.AMBIENT);
                level.playSound(null, clickedPos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.AMBIENT);
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack handItem = player.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

        if (blockHitResult.getType() == HitResult.Type.BLOCK
                && level.getBlockState(blockHitResult.getBlockPos()).is(Blocks.END_GATEWAY))
            return InteractionResult.PASS;

        if (level.dimension() == Level.END) {
            if (handItem.has(EEDataComponents.GATEWAY_EXIT) && Boolean.TRUE.equals(handItem.get(EEDataComponents.CONTAINS_GATEWAY))) {
                Vec3 exitPos = handItem.get(EEDataComponents.GATEWAY_EXIT).getBottomCenter();
                player.teleportTo(exitPos.x, exitPos.y, exitPos.z);

                handItem.remove(EEDataComponents.GATEWAY_EXIT);
                handItem.set(EEDataComponents.CONTAINS_GATEWAY, false);
                if (!handItem.has(DataComponents.DAMAGE) && !player.isCreative()) handItem.set(DataComponents.DAMAGE, 0);
                handItem.hurtAndBreak(1, player, hand);

                level.playSound(null, player, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1f, 1f);
                level.playSound(null, player, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
                ParticleUtils.spawnParticles(level, player.blockPosition(), 50, 0.25, 0.25, true, ParticleTypes.PORTAL);
                player.getCooldowns().addCooldown(handItem, 600);
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.PASS;
    }
}
