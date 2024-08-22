package diacritics.owo.item;

import diacritics.owo.jewel.Jewel;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

// TODO: separate registry for matching jewels to items?
public class JewelItem extends Item {
  private final Jewel jewel;

  public JewelItem(Jewel jewel, Settings settings) {
    super(settings.maxCount(1).fireproof().rarity(Rarity.UNCOMMON));
    this.jewel = jewel;
  }

  public Jewel getJewel() {
    return this.jewel;
  }
}
