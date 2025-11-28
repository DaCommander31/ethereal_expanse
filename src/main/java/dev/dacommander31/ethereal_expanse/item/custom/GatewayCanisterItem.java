package dev.dacommander31.ethereal_expanse.item.custom;

import dev.dacommander31.ethereal_expanse.component.EEDataComponents;
import dev.dacommander31.ethereal_expanse.util.EETickingTaskManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class GatewayCanisterItem extends Item {

    public GatewayCanisterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack handItem = context.getItemInHand();
        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();
        if (level.dimension() == Level.END && level.getBlockState(context.getClickedPos()).is(Blocks.END_GATEWAY)
                && Boolean.FALSE.equals(handItem.get(EEDataComponents.CONTAINS_GATEWAY))) {
            if (!level.isClientSide()) {
                context.getItemInHand().set(EEDataComponents.CONTAINS_GATEWAY, true);
                if (level.getBlockEntity(clickedPos) instanceof TheEndGatewayBlockEntity) {
                    BlockPos exitPos = TheEndGatewayBlockEntity.findExitPosition(level, clickedPos);
                    context.getItemInHand().set(EEDataComponents.GATEWAY_EXIT, exitPos);

                    EETickingTaskManager.add(() -> emitLinkEffects((ServerLevel) level, player, clickedPos), 10);

                    level.playSound(null, clickedPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.AMBIENT);
                    level.playSound(null, clickedPos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.AMBIENT);

                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack handItem = player.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            if (blockHitResult.getType() == HitResult.Type.BLOCK
                    && level.getBlockState(blockHitResult.getBlockPos()).is(Blocks.END_GATEWAY))
                return InteractionResult.PASS;

            if (level.dimension() == Level.END) {
                if (handItem.has(EEDataComponents.GATEWAY_EXIT) && Boolean.TRUE.equals(handItem.get(EEDataComponents.CONTAINS_GATEWAY))) {
                    Vec3 exitPos = handItem.get(EEDataComponents.GATEWAY_EXIT).getBottomCenter();

                    emitWarpEffects(serverLevel, player);
                    //serverLevel.getChunkSource().addTicket(new Ticket(TicketType.PORTAL, 2), new ChunkPos((int) exitPos.x, (int) exitPos.y));

                    player.teleportTo(exitPos.x, exitPos.y, exitPos.z);
                    player.fallDistance = 0;

                    emitArrivalEffects(serverLevel, player);

                    handItem.remove(EEDataComponents.GATEWAY_EXIT);
                    handItem.set(EEDataComponents.CONTAINS_GATEWAY, false);
                    if (!handItem.has(DataComponents.DAMAGE) && !player.isCreative())
                        handItem.set(DataComponents.DAMAGE, 0);
                    handItem.hurtAndBreak(1, player, hand);

                    player.getCooldowns().addCooldown(handItem, 600);
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private void emitLinkEffects(ServerLevel level, Player player, BlockPos clickedPos) {
        level.sendParticles(ParticleTypes.REVERSE_PORTAL, true, true,
                clickedPos.getCenter().x, clickedPos.getCenter().y, clickedPos.getCenter().z,
                5, 0, 0, 0, 0.1);

        emitLinkParticles(level, player, clickedPos, 0xa67ad0);
        emitLinkParticles(level, player, clickedPos, 0x1b5eba);
        emitLinkParticles(level, player, clickedPos, 0x33685f);
        emitLinkParticles(level, player, clickedPos, 0x38334a);
    }

    private void emitLinkParticles(ServerLevel level, Player player, BlockPos clickedPos, int color) {
        RandomSource randomsource = level.random;
        AABB aabb = player.getBoundingBox();
        Vec3 vec3 = aabb.getMinPosition()
                .add(randomsource.nextDouble() * aabb.getXsize(), randomsource.nextDouble() * aabb.getYsize(), randomsource.nextDouble() * aabb.getZsize());
        Vec3 vec31 = Vec3.atLowerCornerOf(clickedPos).add(randomsource.nextDouble(), randomsource.nextDouble(), randomsource.nextDouble());

        TrailParticleOption trailParticleOption = new TrailParticleOption(vec3,
                color, level.random.nextInt(40) + 10);
        level.sendParticles(trailParticleOption, true, true,
                vec31.x, vec31.y, vec31.z, 100,
                0, 0, 0, 0);
    }

    private void emitWarpEffects(ServerLevel level, Player player) {
        level.sendParticles(ParticleTypes.FLASH, false, true,
                player.getX(), player.getY() + 1, player.getZ(), 10, 0.5, 0.5, 0.5, 0);
        level.sendParticles(ParticleTypes.PORTAL, false, true,
                player.getX(), player.getY() + 1, player.getZ(), 100, 0, 0, 0, 3);

        level.playSound(null, player, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
    }

    private void emitArrivalEffects(ServerLevel level, Player player) {
        level.sendParticles(ParticleTypes.FLASH, false, true,
                player.getX(), player.getY() + 1, player.getZ(), 10, 0.5, 0.5, 0.5, 0);
        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)), false, true,
                player.getX(), player.getY() + 1, player.getZ(), 30, 0, 0, 0, 0.3);
        level.sendParticles(ParticleTypes.REVERSE_PORTAL, false, true,
                player.getX(), player.getY() + 1, player.getZ(), 100, 0, 0, 0, 3);

        level.playSound(null, player, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1f, 1f);
        level.playSound(null, player, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
        level.playSound(null, player, SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        BlockPos gatewayPos = stack.get(EEDataComponents.GATEWAY_EXIT);
        if (stack.has(EEDataComponents.GATEWAY_EXIT)) {
            if (gatewayPos != null) {
                tooltipAdder.accept(Component.translatable("item.ethereal_expanse.gateway_canister.tooltip.linked_coords",
                        Component.literal("[" + gatewayPos.getX() + ", " + gatewayPos.getY() + ", " + gatewayPos.getZ() + "]").
                                withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltipAdder.accept(Component.translatable("item.ethereal_expanse.gateway_canister.tooltip.none_linked")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
