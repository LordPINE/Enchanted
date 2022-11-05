/*
 *
 *   Copyright (c) 2022. Favouriteless
 *   Enchanted, a minecraft mod.
 *   GNU GPLv3 License
 *
 *       This file is part of Enchanted.
 *
 *       Enchanted is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       Enchanted is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with Enchanted.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.favouriteless.enchanted.common.rites;

import com.favouriteless.enchanted.api.rites.AbstractCurseRite;
import com.favouriteless.enchanted.common.init.EnchantedBlocks;
import com.favouriteless.enchanted.common.init.EnchantedCurseTypes;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import com.favouriteless.enchanted.common.init.EnchantedRiteTypes;
import com.favouriteless.enchanted.common.util.rite.CirclePart;
import net.minecraft.world.item.Items;

public class RiteOfCurseOfMisfortune extends AbstractCurseRite {

    public RiteOfCurseOfMisfortune() {
        super(EnchantedRiteTypes.CURSE_OF_MISFORTUNE.get(), 2000, 0, EnchantedCurseTypes.MISFORTUNE.get()); // Power, power per tick, curse type
        CIRCLES_REQUIRED.put(CirclePart.MEDIUM, EnchantedBlocks.CHALK_RED.get());
        ITEMS_REQUIRED.put(EnchantedItems.TAGLOCK_FILLED.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.EXHALE_OF_THE_HORNED_ONE.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.BREW_OF_THE_GROTESQUE.get(), 1);
        ITEMS_REQUIRED.put(Items.FERMENTED_SPIDER_EYE, 1);
        ITEMS_REQUIRED.put(Items.GUNPOWDER, 1);
    }


}
