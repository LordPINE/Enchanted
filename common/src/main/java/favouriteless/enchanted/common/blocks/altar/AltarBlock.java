package favouriteless.enchanted.common.blocks.altar;

import favouriteless.enchanted.common.blocks.entity.AltarBlockEntity;
import favouriteless.enchanted.common.init.registry.EnchantedBlockEntityTypes;
import favouriteless.enchanted.common.multiblock.MultiBlockTools;
import favouriteless.enchanted.common.multiblock.altar.AltarMultiBlock;
import favouriteless.enchanted.common.multiblock.altar.AltarPartIndex;
import favouriteless.enchanted.platform.CommonServices;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends BaseEntityBlock {

    public static final EnumProperty<AltarPartIndex> FORMED = EnumProperty.create("formed", AltarPartIndex.class);
    public static final BooleanProperty FACING_X = BooleanProperty.create("facing_x");

    public AltarBlock() {
        super(Properties.copy(Blocks.STONE).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops());
        this.registerDefaultState(defaultBlockState().setValue(FORMED, AltarPartIndex.UNFORMED).setValue(FACING_X, true));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!world.isClientSide())
            MultiBlockTools.formMultiblock(AltarMultiBlock.INSTANCE, world, pos);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!world.isClientSide()) {
            if(state != newState && state.getValue(FORMED) != AltarPartIndex.UNFORMED)
                MultiBlockTools.breakMultiblock(AltarMultiBlock.INSTANCE, world, pos, state);
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
        builder.add(FACING_X);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(state.getValue(FORMED) != AltarPartIndex.UNFORMED) {
            if (!level.isClientSide) {
                BlockPos cornerPos = AltarMultiBlock.INSTANCE.getBottomLowerLeft(level, pos, state);
                BlockState cornerState = level.getBlockState(cornerPos);

                if (cornerState.getValue(FORMED) == AltarPartIndex.P000) {
                    BlockEntity blockEntity = level.getBlockEntity(cornerPos);
                    if(blockEntity instanceof AltarBlockEntity be)
                        CommonServices.PLATFORM.openMenuScreen((ServerPlayer)player, be, be.getBlockPos());
                }
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(FORMED) == AltarPartIndex.P000 ? new AltarBlockEntity(pos, state) : null;
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == EnchantedBlockEntityTypes.ALTAR.get() ? AltarBlockEntity::tick : null;
    }


}
