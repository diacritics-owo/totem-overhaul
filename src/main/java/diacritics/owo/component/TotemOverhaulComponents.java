package diacritics.owo.component;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import diacritics.owo.TotemOverhaul;
import net.minecraft.entity.mob.EvokerEntity;

public class TotemOverhaulComponents implements EntityComponentInitializer {
  public static final ComponentKey<EvokerJewelComponent> JEWEL =
      ComponentRegistry.getOrCreate(TotemOverhaul.identifier("jewel"), EvokerJewelComponent.class);

  @Override
  public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
    registry.registerFor(EvokerEntity.class, JEWEL, EvokerJewelComponent::new);
  }
}
