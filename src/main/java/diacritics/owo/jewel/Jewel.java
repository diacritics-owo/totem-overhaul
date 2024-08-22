package diacritics.owo.jewel;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// TODO: refactor to remove identifier from record
public record Jewel(Identifier identifier, int color) {
  public static final Codec<Jewel> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(Identifier.CODEC.fieldOf("identifier").forGetter(Jewel::identifier),
          Codecs.POSITIVE_INT.fieldOf("color").forGetter(Jewel::color))
      .apply(instance, Jewel::new));

  public static final PacketCodec<RegistryByteBuf, Jewel> PACKET_CODEC = PacketCodec.tuple(
      Identifier.PACKET_CODEC, Jewel::identifier, PacketCodecs.INTEGER, Jewel::color, Jewel::new);

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
