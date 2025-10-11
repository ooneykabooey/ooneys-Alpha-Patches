package net.minecraft.src;

import paulscode.sound.codecs.CodecJOrbis;

import java.io.IOException;
import java.io.InputStream;

public class CodecMus extends CodecJOrbis {
	protected InputStream openInputStream() throws IOException {
		return new MusInputStream(this, this.url, this.urlConnection.getInputStream());
	}
}
