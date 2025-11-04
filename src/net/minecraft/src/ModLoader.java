package net.minecraft.src;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ModLoader {
    private static List<BaseMod> mods = new ArrayList();

    static {
        try {
            File source = new File(ModLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (source.isFile() && source.getName().endsWith(".jar")) {
                JarInputStream jar = new JarInputStream(new FileInputStream(source));
                JarEntry entry = null;

                while(true) {
                    entry = jar.getNextJarEntry();
                    if (entry == null) {
                        break;
                    }

                    String name = entry.getName();
                    if (!entry.isDirectory() && name.startsWith("mod_") && name.endsWith(".class")) {
                        addMod(name.substring(0, name.length() - 6));
                    }
                }
            } else if (source.isDirectory()) {
                File[] files = source.listFiles();
                if (files != null) {
                    for(int i = 0; i < files.length; ++i) {
                        String name = files[i].getName();
                        if (files[i].isFile() && name.startsWith("mod_") && name.endsWith(".class")) {
                            addMod(name.substring(0, name.length() - 6));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addMod(String name) {
        try {
            Class instclass = ModLoader.class.getClassLoader().loadClass(name);
            if (instclass.getSuperclass() != BaseMod.class) {
                return;
            }

            if (mods.add((BaseMod)instclass.newInstance())) {
                System.out.println("Mod Loaded: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Init() {
        System.out.println("Mod Init");
    }

    public static void AddAllRecipes(CraftingManager recipes) {
        for(int i = 0; i < mods.size(); ++i) {
            ((BaseMod)mods.get(i)).AddRecipes(recipes);
        }

    }

    public static void RegisterAllBlocks(List<Block> registry) {
        for(int i = 0; i < mods.size(); ++i) {
            ((BaseMod)mods.get(i)).RegisterBlocks(registry);
        }

    }

    public static void AddAllRenderers(Map<Class, Render> renderers) {
        for(int i = 0; i < mods.size(); ++i) {
            ((BaseMod)mods.get(i)).AddRenderer(renderers);
        }

    }

    public static void AddAllEntityIDs() {
        for(int i = 0; i < mods.size(); ++i) {
            ((BaseMod)mods.get(i)).AddEntityID();
        }

    }

    public static int AddAllSmelting(int id) {
        int result = -1;

        for(int i = 0; i < mods.size() && result == -1; ++i) {
            result = ((BaseMod)mods.get(i)).AddSmelting(id);
        }

        return result;
    }

    public static int AddAllFuel(int id) {
        int result = 0;

        for(int i = 0; i < mods.size() && result == 0; ++i) {
            result = ((BaseMod)mods.get(i)).AddFuel(id);
        }

        return result;
    }
}
