package diacritics.owo.jewel;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.mojang.serialization.Codec;

public record Jewel(Identifier identifier) {
  public static final Codec<Jewel> CODEC = Identifier.CODEC.xmap(Jewel::new, Jewel::identifier);
  public static final PacketCodec<ByteBuf, Jewel> PACKET_CODEC =
      Identifier.PACKET_CODEC.xmap(Jewel::new, Jewel::identifier);

  public String toString() {
    return this.identifier.toString();
  }

  public Identifier getFeatureTexture() {
    return Identifier.of(this.identifier.getNamespace(),
        "textures/entity/illager/evoker/jewel/" + this.identifier.getPath() + ".png");
  }

  public Text getTranslationKey() {
    return Text.translatable(this.identifier.toTranslationKey("jewels"));
  }
}
