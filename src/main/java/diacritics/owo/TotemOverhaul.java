package diacritics.owo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import diacritics.owo.component.TotemOverhaulDataComponentTypes;
import diacritics.owo.item.TotemOverhaulItems;
import diacritics.owo.jewel.Jewels;
import diacritics.owo.jewel.effect.JewelEffects;
import diacritics.owo.recipe.TotemOverhaulRecipeSerializers;

// TODO: config
public class TotemOverhaul implements ModInitializer {
	public static final String MOD_ID = "totem-overhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("hello from totem overhaul!");

		Jewels.initialize();
		TotemOverhaulItems.initialize();
		TotemOverhaulDataComponentTypes.initialize();
		TotemOverhaulRecipeSerializers.initialize();
		JewelEffects.initialize();

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4, factory -> {
			factory.add(new TradeOffers.Factory() {
				@Override
				public TradeOffer create(Entity entity, Random random) {
					return new TradeOffer(new TradedItem(Items.EMERALD, random.nextBetween(8, 11)),
							new ItemStack(Items.TOTEM_OF_UNDYING, 1), random.nextBetween(2, 5), 15, 1);
				}
			});
		});

		LootTableEvents.MODIFY.register((key, builder, source, registries) -> {
			if (key.equals(LootTables.END_CITY_TREASURE_CHEST)) {
				builder.pool(LootPool.builder().bonusRolls(ConstantLootNumberProvider.create(0))
						.with(EmptyEntry.builder().weight(20))
						.with(ItemEntry.builder(Registries.ITEM.get(TotemOverhaulItems.VOID_JEWEL)).weight(4))
						.rolls(ConstantLootNumberProvider.create(1)));

				builder.pool(LootPool.builder().bonusRolls(ConstantLootNumberProvider.create(0))
						.with(EmptyEntry.builder().weight(20))
						.with(ItemEntry.builder(Registries.ITEM.get(TotemOverhaulItems.CHORUS_JEWEL)).weight(5))
						.rolls(ConstantLootNumberProvider.create(1)));
			}
		});
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
