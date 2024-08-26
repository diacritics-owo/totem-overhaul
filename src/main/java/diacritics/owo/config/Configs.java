package diacritics.owo.config;

import java.util.function.Supplier;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;

public class Configs {
  public static TotemOverhaulConfig config =
      ConfigApi.registerAndLoadConfig((Supplier<TotemOverhaulConfig>) TotemOverhaulConfig::new);

  public static void initialize() {}
}
