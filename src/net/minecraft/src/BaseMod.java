package net.minecraft.src;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import java.util.Map;

public abstract class BaseMod {
    public void AddRecipes(CraftingManager recipes) {
    }

    public void RegisterBlocks(List<Block> registry) {
    }

    public void AddRenderer(Map<Class, Render> renderers) {
    }

    public void AddEntityID() {
    }

    public int AddSmelting(int id) {
        return -1;
    }

    public int AddFuel(int id) {
        return 0;
    }
}
