package net.minecraft.isom;

import net.minecraft.src.CanvasIsomPreview;

import java.applet.Applet;
import java.awt.*;

public class IsomPreviewApplet extends Applet {
	private CanvasIsomPreview isomPreview = new CanvasIsomPreview();

	public IsomPreviewApplet() {
		this.setLayout(new BorderLayout());
		this.add(this.isomPreview, "Center");
	}

	public void start() {
		this.isomPreview.start();
	}

	public void stop() {
		this.isomPreview.stop();
	}
}
