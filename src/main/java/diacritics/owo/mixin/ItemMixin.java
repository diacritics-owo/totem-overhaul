package diacritics.owo.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import java.util.List;

@Mixin(Item.class)
abstract public class ItemMixin {
  @Inject(at = @At("HEAD"), method = "appendTooltip")
  public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
      TooltipType type, CallbackInfo info) {
    if (stack.contains(TotemOverhaulDataComponentTypes.JEWEL)) {
      tooltip.add(Text.translatable(
          stack.get(TotemOverhaulDataComponentTypes.JEWEL).getValue().toTranslationKey("jewel")));
    }
  }
}
