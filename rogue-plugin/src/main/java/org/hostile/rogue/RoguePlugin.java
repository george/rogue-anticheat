package org.hostile.rogue;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RoguePlugin extends JavaPlugin {

    @Getter private static RoguePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }
}