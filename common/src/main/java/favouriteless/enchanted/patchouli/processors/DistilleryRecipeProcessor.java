package favouriteless.enchanted.patchouli.processors;

import favouriteless.enchanted.common.recipes.DistillingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class DistilleryRecipeProcessor implements IComponentProcessor {

	private DistillingRecipe recipe;

	@Override
	public void setup(Level level, IVariableProvider variables) {
		ResourceLocation recipeId = new ResourceLocation(variables.get("recipe").asString());
		RecipeManager recipeManager = level.getRecipeManager();
		recipe = (DistillingRecipe)recipeManager.byKey(recipeId).orElseThrow(() -> new IllegalArgumentException("Could not find recipe for: " + recipeId));;
	}

	@Override
	public IVariable process(Level level, String key) {

		int index = Integer.parseInt(String.valueOf(key.charAt(key.length() - 1)));
		if(key.startsWith("in")) {
            NonNullList<ItemStack> stacks = recipe.getItemsIn();
			if(index >= 0 && index < stacks.size()) {
				return IVariable.from(stacks.get(index));
			}
			return IVariable.from(ItemStack.EMPTY);
		}
		else if(key.startsWith("out")) {
            NonNullList<ItemStack> stacks = recipe.getItemsOut();
			if(index >= 0 && index < stacks.size()) {
				return IVariable.from(stacks.get(index));
			}
			return IVariable.from(ItemStack.EMPTY);
		}

		return null;
	}
}
