package diacritics.owo.config;

import diacritics.owo.TotemOverhaul;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

public class TotemOverhaulConfig extends Config {
  public TotemOverhaulConfig() {
    super(TotemOverhaul.identifier("config"));
  }

  public VoidJewelSection voidJewel = new VoidJewelSection();
  public ChorusJewelSection chorusJewel = new ChorusJewelSection();

  public static class VoidJewelSection extends ConfigSection {
    public ValidatedInt height = new ValidatedInt(300, 1024, -64);
  }

  public static class ChorusJewelSection extends ConfigSection {
    public ValidatedInt radius = new ValidatedInt(8, 64, 0);
    public ValidatedInt rolls = new ValidatedInt(16, 64, 0);
  }
}
