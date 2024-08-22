package diacritics.owo.jewel;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Jewel(int color) {
  public static final Codec<Jewel> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codecs.POSITIVE_INT.fieldOf("color").forGetter(Jewel::color))
          .apply(instance, Jewel::new));

  public static final PacketCodec<RegistryByteBuf, Jewel> PACKET_CODEC =
      PacketCodec.tuple(PacketCodecs.INTEGER, Jewel::color, Jewel::new);
}
