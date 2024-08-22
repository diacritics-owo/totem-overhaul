package diacritics.owo.jewel;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.registry.Registry;

public class Jewels {
  // the item for the gray jewel won't (shouldn't) be used (shouldn't matter anyway? registry.get
  // will return null for a null key - see jewelcomponent::tryusejewel)
  public static final Jewel GRAY = register(new Jewel(TotemOverhaul.identifier("gray"), 0xFFFFFF));

  public static final Jewel RED = register(new Jewel(TotemOverhaul.identifier("red"), 0xFF9C9B));
  public static final Jewel GREEN =
      register(new Jewel(TotemOverhaul.identifier("green"), 0xA7FFC0));
  public static final Jewel BLUE = register(new Jewel(TotemOverhaul.identifier("blue"), 0xA6B7FE));

  public static final Jewel DEFAULT = GRAY;

  public static Jewel register(Jewel jewel) {
    return Registry.register(TotemOverhaulRegistries.JEWEL, jewel.identifier(), jewel);
  }

  public static void initialize() {}
}
