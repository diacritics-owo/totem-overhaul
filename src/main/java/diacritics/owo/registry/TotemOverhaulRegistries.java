package diacritics.owo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;

public class TotemOverhaulRegistries {
  public static final Registry<Jewel> JEWEL = new SimpleRegistry<>(
      RegistryKey.ofRegistry(TotemOverhaul.identifier("jewel")), Lifecycle.stable());
  public static final Codec<RegistryKey<Jewel>> JEWEL_CODEC =
      RegistryKey.createCodec(JEWEL.getKey());
  public static final PacketCodec<ByteBuf, RegistryKey<Jewel>> JEWEL_PACKET_CODEC =
      RegistryKey.createPacketCodec(JEWEL.getKey());

  public static void initialize() {}
}
