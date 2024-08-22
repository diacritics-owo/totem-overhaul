package diacritics.owo.item;

import diacritics.owo.TotemOverhaul;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.util.TotemOverhaulHelpers;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

public class TotemOverhaulItems {
  public static final RegistryKey<Item> RED_JEWEL =
      registerJewelItem("red_jewel", new JewelItem(Jewels.RED, new Item.Settings()));
  public static final RegistryKey<Item> GREEN_JEWEL =
      registerJewelItem("green_jewel", new JewelItem(Jewels.GREEN, new Item.Settings()));
  public static final RegistryKey<Item> BLUE_JEWEL =
      registerJewelItem("blue_jewel", new JewelItem(Jewels.BLUE, new Item.Settings()));

  public static RegistryKey<Item> registerJewelItem(String identifier, JewelItem item) {
    return TotemOverhaulHelpers.registerJewelItem(TotemOverhaul.identifier(identifier), item);
  }

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
      itemGroup.add(Registries.ITEM.get(RED_JEWEL));
      itemGroup.add(Registries.ITEM.get(GREEN_JEWEL));
      itemGroup.add(Registries.ITEM.get(BLUE_JEWEL));
    });
  }
}
