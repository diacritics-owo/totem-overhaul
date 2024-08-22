package diacritics.owo.recipe;

import diacritics.owo.TotemOverhaul;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class TotemOverhaulRecipeSerializers {
  public static final RecipeSerializer<TotemJewelRecipe> TOTEM_JEWEL =
      register("crafting_special_totemjewel", new SpecialRecipeSerializer<>(TotemJewelRecipe::new));

  public static <T extends Recipe<?>> RecipeSerializer<T> register(String identifier,
      RecipeSerializer<T> recipeSerializer) {
    return Registry.register(Registries.RECIPE_SERIALIZER, TotemOverhaul.identifier(identifier),
        recipeSerializer);
  }

  public static void initialize() {}
}
