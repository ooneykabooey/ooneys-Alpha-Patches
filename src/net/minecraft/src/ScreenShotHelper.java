package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * @author https://www.youtube.com/channel/UCihhEiR0PEPeSXZGtyX-U_A
 * By SciFi Regular
 * Thank you so much for your source code!!
 * I will implement upon this ifever needed.
 */

public class ScreenShotHelper {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	private static ByteBuffer buffer;
	private static byte[] pixelData;
	private static int[] imageData;
	private Random fuckmd5;

	public static String saveScreenshot(File file0, int i1, int i2) {
		try {
			File file3 = new File(file0, "screenshots");
			file3.mkdir();
			if(buffer == null || buffer.capacity() < i1 * i2) {
				buffer = BufferUtils.createByteBuffer(i1 * i2 * 3);
			}

			if(imageData == null || imageData.length < i1 * i2 * 3) {
				pixelData = new byte[i1 * i2 * 3];
				imageData = new int[i1 * i2];
			}

			GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			buffer.clear();
			GL11.glReadPixels(0, 0, i1, i2, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
			buffer.clear();
			String string4 = "" + dateFormat.format(new Date());

			File file5;
			int i6;
			for(i6 = 1; (file5 = new File(file3, string4 + (i6 == 1 ? "" : "_" + i6) + ".png")).exists(); ++i6) {
			}

			buffer.get(pixelData);

			for(i6 = 0; i6 < i1; ++i6) {
				for(int i7 = 0; i7 < i2; ++i7) {
					int i8 = i6 + (i2 - i7 - 1) * i1;
					int i9 = pixelData[i8 * 3 + 0] & 255;
					int i10 = pixelData[i8 * 3 + 1] & 255;
					int i11 = pixelData[i8 * 3 + 2] & 255;
					int i12 = 0xFF000000 | i9 << 16 | i10 << 8 | i11;
					imageData[i6 + i7 * i1] = i12;
				}
			}

			BufferedImage bufferedImage14 = new BufferedImage(i1, i2, 1);
			bufferedImage14.setRGB(0, 0, i1, i2, imageData, 0, i1);
			ImageIO.write(bufferedImage14, "png", file5);
			return "Saved screenshot as " + file5.getName();
		} catch (Exception exception13) {
			exception13.printStackTrace();
			return "Failed to save: " + exception13;
		}
	}
}
