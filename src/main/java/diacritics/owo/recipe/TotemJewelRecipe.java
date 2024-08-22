package diacritics.owo.recipe;

import com.ibm.icu.impl.Pair;
import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import diacritics.owo.item.JewelItem;
import diacritics.owo.jewel.Jewel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import java.util.Optional;

public class TotemJewelRecipe extends SpecialCraftingRecipe {
  public TotemJewelRecipe(CraftingRecipeCategory craftingRecipeCategory) {
    super(craftingRecipeCategory);
  }

  public Optional<Pair<ItemStack, Jewel>> inputs(CraftingRecipeInput craftingRecipeInput) {
    ItemStack totem = ItemStack.EMPTY;
    Jewel jewel = null;

    for (int i = 0; i < craftingRecipeInput.getSize(); i++) {
      if (!craftingRecipeInput.getStackInSlot(i).isEmpty()) {
        if (craftingRecipeInput.getStackInSlot(i).isOf(Items.TOTEM_OF_UNDYING)) {
          if (!totem.isEmpty()) {
            return Optional.empty();
          }

          totem = craftingRecipeInput.getStackInSlot(i);
        } else if (craftingRecipeInput.getStackInSlot(i).getItem() instanceof JewelItem jewelItem) {
          if (jewel != null) {
            return Optional.empty();
          }

          jewel = jewelItem.getJewel();
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
    Optional<Pair<ItemStack, Jewel>> inputs = this.inputs(craftingRecipeInput);

    if (inputs.isPresent()) {
      ItemStack result = inputs.get().first;
      result.set(TotemOverhaulDataComponentTypes.JEWEL, inputs.get().second);
      return result;
    }

    return ItemStack.EMPTY;
  }

  public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput craftingRecipeInput) {
    DefaultedList<ItemStack> defaultedList =
        DefaultedList.ofSize(craftingRecipeInput.getSize(), ItemStack.EMPTY);

    for (int i = 0; i < defaultedList.size(); ++i) {
      ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
      if (itemStack.getItem().hasRecipeRemainder()) {
        defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder()));
      } else if (itemStack.getItem() instanceof WrittenBookItem) {
        defaultedList.set(i, itemStack.copyWithCount(1));
        break;
      }
    }

    return defaultedList;
  }

  public RecipeSerializer<?> getSerializer() {
    return RecipeSerializer.BOOK_CLONING;
  }

  public boolean fits(int width, int height) {
    return width >= 3 && height >= 3;
  }
}
