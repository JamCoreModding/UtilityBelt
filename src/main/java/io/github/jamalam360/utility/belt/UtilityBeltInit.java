/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Jamalam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.jamalam360.utility.belt;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import io.github.jamalam360.jamlib.config.JamLibConfig;
import io.github.jamalam360.jamlib.log.JamLibLogger;
import io.github.jamalam360.jamlib.network.JamLibServerNetworking;
import io.github.jamalam360.jamlib.registry.JamLibRegistry;
import io.github.jamalam360.utility.belt.config.UtilityBeltConfig;
import io.github.jamalam360.utility.belt.item.InventoryComponent;
import io.github.jamalam360.utility.belt.item.ItemInventoryComponent;
import io.github.jamalam360.utility.belt.registry.ItemRegistry;
import io.github.jamalam360.utility.belt.registry.Networking;
import io.github.jamalam360.utility.belt.registry.ScreenHandlerRegistry;
import io.github.jamalam360.utility.belt.registry.TrinketsBehaviours;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class UtilityBeltInit implements ModInitializer, ItemComponentInitializer {

    public static final String MOD_ID = "utilitybelt";
    public static final JamLibLogger LOGGER = JamLibLogger.getLogger(MOD_ID);

    public static final Map<PlayerEntity, Boolean> UTILITY_BELT_SELECTED = new Object2BooleanArrayMap<>();
    public static final Map<PlayerEntity, Integer> UTILITY_BELT_SELECTED_SLOTS = new Object2IntArrayMap<>();
    public static final TagKey<Item> ALLOWED_IN_UTILITY_BELT = TagKey.of(Registries.ITEM.getKey(), idOf("allowed_in_utility_belt"));
    @SuppressWarnings("rawtypes")
    public static final ComponentKey<InventoryComponent> INVENTORY =
          ComponentRegistry.getOrCreate(idOf("belt_inventory"), InventoryComponent.class);
    public static final int UTILITY_BELT_SIZE = 4;

    public static Identifier idOf(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        JamLibRegistry.register(ItemRegistry.class, ScreenHandlerRegistry.class);
        JamLibConfig.init(MOD_ID, UtilityBeltConfig.class);
        Networking.setHandlers();
        TrinketsBehaviours.registerEvents();
        JamLibServerNetworking.registerHandlers(MOD_ID);
        LOGGER.logInitialize();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(ItemRegistry.UTILITY_BELT, INVENTORY, ItemInventoryComponent::new);
    }
}
