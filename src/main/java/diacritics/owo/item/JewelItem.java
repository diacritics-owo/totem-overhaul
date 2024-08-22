package diacritics.owo.item;

import diacritics.owo.jewel.Jewel;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;

public class JewelItem extends Item {
  private final RegistryKey<Jewel> jewelKey;

  public JewelItem(RegistryKey<Jewel> jewelKey, Settings settings) {
    super(settings.maxCount(1).fireproof().rarity(Rarity.UNCOMMON));
    this.jewelKey = jewelKey;
  }

  public RegistryKey<Jewel> getJewelKey() {
    return this.jewelKey;
  }
}
