package me.nik.anticheatbase.utils.custom;

import org.bukkit.entity.Entity;

/*
https://technical-minecraft.fandom.com/wiki/Entity_Hitbox_Sizes
 */
public enum HitboxExpansion {
    PLAYER("PLAYER", 0.6F),
    CREEPER("CREEPER", 0.6F),
    SKELETON("SKELETON", 0.6F),
    DOLPHIN("DOLPHIN", 0.9F),
    PHANTOM("PHANTOM", 0.8F),
    SPIDER("SPIDER", 1.4F),
    GIANT("GIANT", 3.6F),
    ZOMBIE("ZOMBIE", 0.6F),
    HUSK("HUSK", 0.6F),
    EVOKER("EVOKER", 0.6F),
    VINDICATOR("VINDICATOR", 0.6F),
    PILLAGER("PILLAGER", 0.6F),
    VEX("VEX", 0.4F),
    SLIME("SLIME", 2.04F),
    GHAST("GHAST", 4F),
    PIGLIN("PIGLIN", 0.6F),
    ENDERMAN("ENDERMAN", 0.6F),
    CAVE_SPIDER("CAVE_SPIDER", 0.7F),
    SILVERFISH("SILVERFISH", 0.4F),
    ENDERMITE("ENDERMITE", 0.4F),
    BLAZE("BLAZE", 0.6F),
    MAGMA_CUBE("MAGMA_CUBE", 2.04F),
    ENDER_DRAGON("ENDERDRAGON", 16F),
    WITHER("WITHER", 0.9F),
    BAT("BAT", 0.5F),
    WITCH("WITCH", 0.6F),
    GUARDIAN("GUARDIAN", 0.85F),
    PIG("PIG", 0.9F),
    SHEEP("SHEEP", 0.9F),
    COW("COW", 0.9F),
    CHICKEN("CHICKEN", 0.4F),
    SQUID("SQUID", 0.8F),
    WOLF("WOLF", 0.6F),
    MUSHROOM_COW("MUSHROOM_COW", 0.9F),
    SNOWMAN("SNOWMAN", 0.7F),
    CAT("CAT", 0.6F),
    IRON_GOLEM("IRON_GOLEM", 1.4F),
    HORSE("HORSE", 1.3964F),
    DONKEY("DONKEY", 1.3964F),
    RABBIT("RABBIT", 0.4F),
    VILLAGER("VILLAGER", 0.6F);

    private final float expansion;
    private final String name;

    HitboxExpansion(String name, float expansion) {
        this.name = name;
        this.expansion = expansion;
    }

    public static float getExpansion(final Entity entity) {

        final String entityType = entity.getType().toString();

        for (HitboxExpansion expansion : values()) {

            if (!expansion.name.equals(entityType)) continue;

            return expansion.expansion;
        }

        //No expansion found
        return 5F;
    }
}