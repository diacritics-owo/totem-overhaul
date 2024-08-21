package diacritics.owo.jewel;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Jewel(Identifier identifier, RegistryKey<Item> jewelItem) {
  public static final Codec<Jewel> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(Identifier.CODEC.fieldOf("identifier").forGetter(Jewel::identifier), RegistryKey
          .createCodec(Registries.ITEM.getKey()).fieldOf("jewelItem").forGetter(Jewel::jewelItem))
      .apply(instance, Jewel::new));

  public String toString() {
    return this.identifier.toString();
  }

  public Identifier getFeatureTexture() {
    return Identifier.of(this.identifier.getNamespace(),
        "textures/entity/illager/evoker/jewel/" + this.identifier.getPath() + ".png");
  }

  public Text getTranslationKey() {
    return Text.translatable(this.identifier.toTranslationKey("jewel"));
  }
}
