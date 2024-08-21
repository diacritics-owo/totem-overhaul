package diacritics.owo.component;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewels;
import net.minecraft.entity.mob.EvokerEntity;

public class TotemOverhaulComponents implements EntityComponentInitializer {
  public static final ComponentKey<JewelComponent> JEWEL =
      ComponentRegistry.getOrCreate(TotemOverhaul.identifier("jewel"), JewelComponent.class);

  @Override
  public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
    registry.registerFor(EvokerEntity.class, JEWEL,
        evoker -> new JewelComponent(evoker, Jewels.BLUE));
  }
}
