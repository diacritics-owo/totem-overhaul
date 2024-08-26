package diacritics.owo;

import diacritics.owo.component.EntityJewelComponent;
import diacritics.owo.jewel.Jewels;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MerchantEntity;

public class Helpers {
  public static void onEvokerKilledEntity(EntityJewelComponent component, LivingEntity entity) {
    if (entity instanceof MerchantEntity) {
      component.setJewelKey(Jewels.BLOOD);
    }
  }
}
