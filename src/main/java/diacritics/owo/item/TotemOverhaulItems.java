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
  public static final RegistryKey<Item> BLOOD_JEWEL =
      registerJewelItem("blood_jewel", new JewelItem(Jewels.BLOOD, new Item.Settings()));
  public static final RegistryKey<Item> VOID_JEWEL =
      registerJewelItem("void_jewel", new JewelItem(Jewels.VOID, new Item.Settings()));
  public static final RegistryKey<Item> CHORUS_JEWEL =
      registerJewelItem("chorus_jewel", new JewelItem(Jewels.CHORUS, new Item.Settings()));

  public static RegistryKey<Item> registerJewelItem(String identifier, JewelItem item) {
    return TotemOverhaulHelpers.registerJewelItem(TotemOverhaul.identifier(identifier), item);
  }

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
      itemGroup.add(Registries.ITEM.get(BLOOD_JEWEL));
      itemGroup.add(Registries.ITEM.get(VOID_JEWEL));
      itemGroup.add(Registries.ITEM.get(CHORUS_JEWEL));
    });
  }
}
