package diacritics.owo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

// TODO: unique gem textures
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
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
