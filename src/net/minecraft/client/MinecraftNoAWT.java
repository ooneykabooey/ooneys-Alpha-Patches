package net.minecraft.client;

import net.minecraft.src.PanelCrashReport;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.UnexpectedThrowable;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MinecraftNoAWT extends Minecraft {

    private final Frame mcFrame;
    private int previousWidth, previousHeight;

    public MinecraftNoAWT(int width, int height, boolean fullscreen) {
        super(null, null, null, width, height, fullscreen);
        this.previousWidth = width;
        this.previousHeight = height;
        this.mcFrame = new Frame("Minecraft");
    }

    public void displayUnexpectedThrowable(UnexpectedThrowable var1) {
        this.mcFrame.removeAll();
        this.mcFrame.add(new PanelCrashReport(var1), "Center");
        this.mcFrame.validate();
        this.mcFrame.setSize(this.displayWidth, this.displayHeight);
        this.mcFrame.setLocationRelativeTo(null);
        this.mcFrame.setAutoRequestFocus(true);
        this.mcFrame.addWindowListener(new WindowAdapter() {
                                           public void windowClosing(WindowEvent we) {
                                               mcFrame.dispose();
                                               System.exit(1);
                                           }
                                       }
        );
        this.mcFrame.setVisible(true);
    }

    @Override
    public void startGame() throws LWJGLException {
        Display.setResizable(true);
        super.startGame();
        Display.setTitle("Minecraft Alpha 1.1.2_01");
    }

    public void runTick() {
        if (GL11.glGetString(GL11.GL_RENDERER).contains("Apple M")) {
            GL11.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
        }
        if (Display.getWidth() != this.displayWidth || Display.getHeight() != this.displayHeight) {
            this.resize(Display.getWidth(), Display.getHeight());
        }

        super.runTick();
    }

    public void toggleFullscreen() {
        final Object fullscreen_b;
        boolean isFullscreen;
        try {
            fullscreen_b = this.getClass().getDeclaredField("fullscreen").get(this);
            isFullscreen = (boolean) fullscreen_b;
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
            isFullscreen = Display.isFullscreen();
        }
        try {
            isFullscreen = !isFullscreen;
            if (isFullscreen) {
                this.previousWidth = Display.getWidth();
                this.previousHeight = Display.getHeight();

                Display.setDisplayMode(Display.getDesktopDisplayMode());
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
            } else {
                this.displayWidth = this.previousWidth;
                this.displayHeight = this.previousHeight;
                Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
            }
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }

            if (this.currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
            }

            Display.setFullscreen(isFullscreen);
            Display.update();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        try {
            this.getClass().getDeclaredField("fullscreen").set(this, isFullscreen);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }

    private void resize(int var1, int var2) {
        if (var1 <= 0) {
            var1 = 1;
        }
        if (var2 <= 0) {
            var2 = 1;
        }
        this.displayWidth = var1;
        this.displayHeight = var2;
        if (this.currentScreen != null) {
            ScaledResolution var3 = new ScaledResolution(var1, var2);
            int var4 = var3.getScaledWidth();
            int var5 = var3.getScaledHeight();
            this.currentScreen.setWorldAndResolution(this, var4, var5);
        }
    }
}
