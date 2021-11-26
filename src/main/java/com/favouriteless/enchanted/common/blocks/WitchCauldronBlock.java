/*
 * Copyright (c) 2021. Favouriteless
 * Enchanted, a minecraft mod.
 * GNU GPLv3 License
 *
 *     This file is part of Enchanted.
 *
 *     Enchanted is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Enchanted is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Enchanted.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.favouriteless.enchanted.common.blocks;

import com.favouriteless.enchanted.common.tileentity.WitchCauldronTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nullable;

public class WitchCauldronBlock extends Block implements ITileEntityProvider {

    public static final IntegerProperty LEVEL = CauldronBlock.LEVEL;
    public static final BooleanProperty HOT = BooleanProperty.create("hot");
    public static final IntegerProperty COOKSTATE = IntegerProperty.create("cookstate", 0, 3); // 0 = no recipe, 1 = cooking, 2 = succeeded, 3 = failed

    public WitchCauldronBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LEVEL, 0).setValue(HOT, false));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        TileEntity te = world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        if(te instanceof WitchCauldronTileEntity) {
            WitchCauldronTileEntity cauldron = (WitchCauldronTileEntity)te;

            if(state.getValue(COOKSTATE) == 2) {
                cauldron.takeContents(player);
                return ActionResultType.SUCCESS;
            }
            else if(state.getValue(COOKSTATE) == 3 && stack.getItem() == Items.BUCKET) {
                cauldron.takeFailedContents(player, stack);
                return ActionResultType.SUCCESS;
            }
            else if (stack.getItem() == Items.WATER_BUCKET) {
                if (!world.isClientSide) {
                    if (cauldron.addWater(FluidAttributes.BUCKET_VOLUME)) {
                        world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!player.isCreative()) player.setItemInHand(hand, Items.BUCKET.getDefaultInstance());
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader blockReader) {
        return new WitchCauldronTileEntity();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL, HOT, COOKSTATE);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClientSide && entity instanceof ItemEntity // Valid item on server
                && (state.getValue(COOKSTATE) == 0 || state.getValue(COOKSTATE) == 1)) { // Cauldron not finished
            TileEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof WitchCauldronTileEntity) {
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                ((WitchCauldronTileEntity)tileEntity).addItem((ItemEntity)entity);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return VoxelShapes.box(0.1D, 0, 0.1D, 0.9D, 0.9D, 0.9D);
    }
}

