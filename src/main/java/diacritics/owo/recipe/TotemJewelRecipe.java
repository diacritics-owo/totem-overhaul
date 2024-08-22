package diacritics.owo.recipe;

import com.ibm.icu.impl.Pair;
import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import diacritics.owo.item.JewelItem;
import diacritics.owo.jewel.Jewel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import java.util.Optional;

public class TotemJewelRecipe extends SpecialCraftingRecipe {
  public TotemJewelRecipe(CraftingRecipeCategory craftingRecipeCategory) {
    super(craftingRecipeCategory);
  }

  public Optional<Pair<ItemStack, RegistryKey<Jewel>>> inputs(
      CraftingRecipeInput craftingRecipeInput) {
    ItemStack totem = ItemStack.EMPTY;
    RegistryKey<Jewel> jewel = null;

    for (int i = 0; i < craftingRecipeInput.getSize(); i++) {
      if (!craftingRecipeInput.getStackInSlot(i).isEmpty()) {
        if (craftingRecipeInput.getStackInSlot(i).isOf(Items.TOTEM_OF_UNDYING)) {
          if (!totem.isEmpty()) {
            return Optional.empty();
          }

          totem = craftingRecipeInput.getStackInSlot(i);

          if (totem.get(TotemOverhaulDataComponentTypes.JEWEL) != null) {
            return Optional.empty();
          }
        } else if (craftingRecipeInput.getStackInSlot(i).getItem() instanceof JewelItem jewelItem) {
          if (jewel != null) {
            return Optional.empty();
          }

          jewel = jewelItem.getJewelKey();
        } else {
          return Optional.empty();
        }
      }
    }

    return (!totem.isEmpty() && jewel != null) ? Optional.of(Pair.of(totem, jewel))
        : Optional.empty();
  }

  @Override
  public boolean matches(CraftingRecipeInput input, World world) {
    return !this.inputs(input).isEmpty();
  }

  @Override
  public ItemStack craft(CraftingRecipeInput craftingRecipeInput,
      RegistryWrapper.WrapperLookup wrapperLookup) {
    Optional<Pair<ItemStack, RegistryKey<Jewel>>> inputs = this.inputs(craftingRecipeInput);

    if (inputs.isPresent()) {
      ItemStack result = inputs.get().first.copy();
      result.set(TotemOverhaulDataComponentTypes.JEWEL, inputs.get().second);
      return result;
    }

    return ItemStack.EMPTY;
  }

  public RecipeSerializer<?> getSerializer() {
    return TotemOverhaulRecipeSerializers.TOTEM_JEWEL;
  }

  public boolean fits(int width, int height) {
    return width >= 3 && height >= 3;
  }
}
