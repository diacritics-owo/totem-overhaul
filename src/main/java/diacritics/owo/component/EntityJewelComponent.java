package diacritics.owo.component;

import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;
import java.util.function.BiFunction;

public class EntityJewelComponent implements AutoSyncedComponent {
  public static final String JEWEL_KEY = "jewel";
  public static final String CAN_HAVE_JEWEL_KEY = "canHaveJewel";

  private final LivingEntity provider;
  private RegistryKey<Jewel> jewelKey;
  private boolean canHaveJewel = true;

  public EntityJewelComponent(LivingEntity provider) {
    this.provider = provider;
  }

  public EntityJewelComponent(LivingEntity provider, RegistryKey<Jewel> jewelKey) {
    this.provider = provider;
    this.setJewelKey(jewelKey);
  }

  public Jewel getJewel() {
    return TotemOverhaulRegistries.JEWEL.get(this.getJewelKey());
  }

  public RegistryKey<Jewel> getJewelKey() {
    if (!this.canHaveJewel) {
      return null;
    }

    return this.jewelKey == null ? Jewels.NONE : this.jewelKey;
  }

  public void setJewelKey(RegistryKey<Jewel> jewelKey) {
    this.jewelKey = jewelKey;
    TotemOverhaulComponents.JEWEL.sync(this.provider);
  }

  public boolean tryUseJewel(DamageSource source) {
    if (this.jewelKey == null) {
      return false;
    }

    BiFunction<LivingEntity, DamageSource, Boolean> effect =
        TotemOverhaulRegistries.JEWEL_EFFECT.get(this.jewelKey.getValue());
    this.provider.dropItem(
        Registries.ITEM.get(TotemOverhaulRegistries.JEWEL_ITEM.get(this.jewelKey.getValue())));

    this.canHaveJewel = false;
    this.setJewelKey(null);

    return effect != null && effect.apply(this.provider, source);
  }

  public Identifier getFeatureTexture(String entity) {
    Identifier identifier = this.getJewelKey().getValue();
    return Identifier.of(identifier.getNamespace(),
        "textures/entity/" + entity + "/jewel/" + identifier.getPath() + ".png");
  }

  @Override
  public void readFromNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (tag.contains(JEWEL_KEY)) {
      TotemOverhaulRegistries.JEWEL_CODEC
          .parse(registryLookup.getOps(NbtOps.INSTANCE), tag.get(JEWEL_KEY))
          .resultOrPartial(jewelKey -> {
            TotemOverhaul.LOGGER.error("failed to parse registrykey<jewel>: '{}'", jewelKey);
          }).ifPresent(jewelKey -> {
            this.setJewelKey(jewelKey);
          });
    } else {
      this.setJewelKey(null);
    }

    if (tag.contains(CAN_HAVE_JEWEL_KEY)) {
      this.canHaveJewel = tag.getBoolean(CAN_HAVE_JEWEL_KEY);
    } else {
      this.canHaveJewel = true;
    }
  }

  @Override
  public void writeToNbt(NbtCompound tag, WrapperLookup registryLookup) {
    if (this.jewelKey == null) {
      tag.remove(JEWEL_KEY);
    } else {
      tag.put(JEWEL_KEY, TotemOverhaulRegistries.JEWEL_CODEC
          .encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.jewelKey).getOrThrow());
    }

    tag.putBoolean(CAN_HAVE_JEWEL_KEY, canHaveJewel);
  }
}
