package diacritics.owo.item;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewel;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.registry.TotemOverhaulRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class TotemOverhaulItems {
  public static final RegistryKey<Item> RED_JEWEL =
      registerJewelItem(Jewels.RED, "red_jewel", new JewelItem(Jewels.RED, new Item.Settings()));
  public static final RegistryKey<Item> GREEN_JEWEL = registerJewelItem(Jewels.GREEN, "green_jewel",
      new JewelItem(Jewels.GREEN, new Item.Settings()));
  public static final RegistryKey<Item> BLUE_JEWEL =
      registerJewelItem(Jewels.BLUE, "blue_jewel", new JewelItem(Jewels.BLUE, new Item.Settings()));

  public static RegistryKey<Item> registerJewelItem(Jewel jewel, String identifier,
      JewelItem item) {
    return Registry.register(TotemOverhaulRegistries.JEWEL_ITEMS, jewel.identifier(),
        register(identifier, item));
  }

  public static RegistryKey<Item> register(String identifier, Item item) {
    return Registries.ITEM
        .getKey(Registry.register(Registries.ITEM, TotemOverhaul.identifier(identifier), item))
        .get();
  }

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
      itemGroup.add(Registries.ITEM.get(RED_JEWEL));
      itemGroup.add(Registries.ITEM.get(GREEN_JEWEL));
      itemGroup.add(Registries.ITEM.get(BLUE_JEWEL));
    });
  }
}
