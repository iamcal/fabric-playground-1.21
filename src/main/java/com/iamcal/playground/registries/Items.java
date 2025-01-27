package com.iamcal.playground.registries;

import com.iamcal.playground.items.QuickBow;
import com.iamcal.playground.items.SuspiciousSubstance;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import com.iamcal.playground.Playground;

public class Items {

    public static final FoodComponent SUSPICIOUS_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .snack()
            // The duration is in ticks, 20 ticks = 1 second
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 1), 1.0f)
            .build();

    public static final Item SUSPICIOUS_SUBSTANCE = register(
            new SuspiciousSubstance(new Item.Settings().food(SUSPICIOUS_FOOD_COMPONENT)), "suspicious_substance"
    );

    public static final Item QUICK_BOW = register(
            new QuickBow(new Item.Settings()), "quick_bow"
    );

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(Playground.MOD_ID, id);

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(Items.SUSPICIOUS_SUBSTANCE));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(Items.QUICK_BOW));

        // Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
        CompostingChanceRegistry.INSTANCE.add(Items.SUSPICIOUS_SUBSTANCE, 0.3f);

        // Add the suspicious substance to the flammable block registry with a burn time of 30 seconds.
        // Remember, Minecraft deals with logical based-time using ticks.
        // 20 ticks = 1 second.
        FuelRegistry.INSTANCE.add(Items.SUSPICIOUS_SUBSTANCE, 30 * 20);
    }
}
