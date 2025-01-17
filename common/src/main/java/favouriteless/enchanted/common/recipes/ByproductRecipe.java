package favouriteless.enchanted.common.recipes;

import favouriteless.enchanted.common.init.registry.EnchantedRecipeTypes;
import favouriteless.enchanted.util.ItemStackHelper;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ByproductRecipe implements Recipe<Container> {

    protected final RecipeType<?> type;
    protected final ResourceLocation id;

    protected final Ingredient ingredient;
    protected final ItemStack result;

    public ByproductRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result) {
        this.type = EnchantedRecipeTypes.BYPRODUCT.get();
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
    }

    public Ingredient getInput() {
        return this.ingredient;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 4;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return EnchantedRecipeTypes.BYPRODUCT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }


    public static class Serializer implements RecipeSerializer<ByproductRecipe> {

        @Override
        @NotNull
        public ByproductRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            ItemStack result = ItemStackHelper.fromJson(GsonHelper.getAsJsonObject(json, "result"), true);

            return new ByproductRecipe(id, ingredient, result);
        }

        @Override
        @NotNull
        public ByproductRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();

            return new ByproductRecipe(id, ingredient, result);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, ByproductRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }

    }
}
