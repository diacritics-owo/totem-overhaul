package diacritics.owo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import diacritics.owo.jewel.Jewel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import java.util.function.Function;

public class TotemOverhaulRegistries {
    public static final Registry<Jewel> JEWEL =
            new SimpleRegistry<>(TotemOverhaulRegistryKeys.JEWEL, Lifecycle.stable());
    public static final Registry<RegistryKey<Item>> JEWEL_ITEM =
            new SimpleRegistry<>(TotemOverhaulRegistryKeys.JEWEL_ITEM, Lifecycle.stable());
    public static final Registry<Function<LivingEntity, Boolean>> JEWEL_EFFECT =
            new SimpleRegistry<>(TotemOverhaulRegistryKeys.JEWEL_EFFECT, Lifecycle.stable());

    public static final Codec<RegistryKey<Jewel>> JEWEL_CODEC =
            RegistryKey.createCodec(JEWEL.getKey());

    public static void initialize() {}
}
