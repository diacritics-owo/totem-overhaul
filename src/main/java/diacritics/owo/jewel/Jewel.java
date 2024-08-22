package diacritics.owo.jewel;

import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Jewel(Identifier identifier, RegistryKey<Item> jewelItem, int color) {

  public static final Codec<Jewel> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(Identifier.CODEC.fieldOf("identifier").forGetter(Jewel::identifier),
          RegistryKey.createCodec(Registries.ITEM.getKey()).fieldOf("jewelItem").forGetter(
              Jewel::jewelItem),
          Codecs.POSITIVE_INT.fieldOf("color").forGetter(Jewel::color))
      .apply(instance, Jewel::new));

  public static final PacketCodec<RegistryByteBuf, Jewel> PACKET_CODEC =
      PacketCodec.tuple(Identifier.PACKET_CODEC, Jewel::identifier,
          RegistryKey.createPacketCodec(Registries.ITEM.getKey()), Jewel::jewelItem,
          PacketCodecs.INTEGER, Jewel::color, Jewel::new);

  public String toString() {
    return this.identifier.toString();
  }

  public Identifier getFeatureTexture(String entity) {
    return Identifier.of(this.identifier.getNamespace(),
        "textures/entity/" + entity + "/jewel/" + this.identifier.getPath() + ".png");
  }

  public String getTranslationKey() {
    return this.identifier.toTranslationKey("jewel");
  }
}
