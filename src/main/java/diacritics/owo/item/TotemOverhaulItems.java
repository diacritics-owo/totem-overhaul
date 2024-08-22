package diacritics.owo.item;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewels;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class TotemOverhaulItems {
    public static final RegistryKey<Item> RED_JEWEL =
            register("red_jewel", new JewelItem(Jewels.RED, new Item.Settings()));
    public static final RegistryKey<Item> GREEN_JEWEL =
            register("green_jewel", new JewelItem(Jewels.GREEN, new Item.Settings()));
    public static final RegistryKey<Item> BLUE_JEWEL =
            register("blue_jewel", new JewelItem(Jewels.BLUE, new Item.Settings()));

    public static RegistryKey<Item> register(String identifier, Item item) {
        return Registries.ITEM.getKey(
                Registry.register(Registries.ITEM, TotemOverhaul.identifier(identifier), item))
                .get();
    }

    public static void initialize() {}
}
