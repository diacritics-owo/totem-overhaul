package diacritics.owo.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class JewelItem extends Item {
  public JewelItem(Settings settings) {
    super(settings.maxCount(1).fireproof().rarity(Rarity.UNCOMMON));
  }
}
