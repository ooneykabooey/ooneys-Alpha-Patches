package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.nio.IntBuffer;

public class MouseHelper {
    private float smoothX = 0.0F;
    private float smoothY = 0.0F;
	private Component windowComponent;
	private Cursor cursor;
	public int deltaX;
	public int deltaY;
	private int mouseInt = 10;
    private Minecraft minecraft;

	public MouseHelper(Component var1, Minecraft mc) {
		this.windowComponent = var1;
        this.minecraft = mc;
		IntBuffer var2 = GLAllocation.createDirectIntBuffer(1);
		var2.put(0);
		var2.flip();
		IntBuffer var3 = GLAllocation.createDirectIntBuffer(1024);

		try {
			this.cursor = new Cursor(32, 32, 16, 16, 1, var3, var2);
		} catch (LWJGLException var5) {
			var5.printStackTrace();
		}

	}

	public void grabMouseCursor() {
		Mouse.setGrabbed(true);
		this.deltaX = 0;
		this.deltaY = 0;
	}

	public void ungrabMouseCursor() {
//        if (this.windowComponent != null) {
//            Mouse.setCursorPosition(this.windowComponent.getWidth() / 2, this.windowComponent.getHeight() / 2);
//        }
		Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		Mouse.setGrabbed(false);
	}

	public void mouseXYChange() {
        int dx = Mouse.getDX();
        int dy = Mouse.getDY();
//		this.deltaX = Mouse.getDX();
//		this.deltaY = Mouse.getDY();

        if (Keyboard.isKeyDown(this.minecraft.options.keyBindZoom.keyCode)) {

            smoothX += (dx - smoothX) * 0.15F;
            smoothY += (dy - smoothY) * 0.15F;

            float zoomFactor = this.minecraft.entityRenderer.zoomFOV;
            float sensitivityScale = (0.3F + zoomFactor * 0.9F);

            this.deltaX = (int) (smoothX * sensitivityScale);
            this.deltaY = (int) (smoothY * sensitivityScale);
        } else {
            smoothX = dx;
            smoothY = dy;

            this.deltaX = dx;
            this.deltaY = dy;
        }
	}
}
