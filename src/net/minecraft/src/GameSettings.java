package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class GameSettings {
	private static final String[] RENDER_DISTANCES = new String[]{"FAR", "NORMAL", "SHORT", "TINY"};
	private static final String[] DIFFICULTY_LEVELS = new String[]{"Peaceful", "Easy", "Normal", "Hard"};
	public float musicVolume = 1.0F;
    public float fovOption = 0.583F;
	public float soundVolume = 1.0F;
	public float mouseSensitivity = 0.5F;
	public boolean invertMouse = false;
	public int renderDistance = 0;
	public boolean viewBobbing = true;
	public boolean anaglyph = false;
	public boolean limitFramerate = false;
	public boolean fancyGraphics = true;
	public KeyBinding keyBindForward = new KeyBinding("Forward", 17);
	public KeyBinding keyBindLeft = new KeyBinding("Left", 30);
	public KeyBinding keyBindBack = new KeyBinding("Back", 31);
	public KeyBinding keyBindRight = new KeyBinding("Right", 32);
	public KeyBinding keyBindJump = new KeyBinding("Jump", 57);
	public KeyBinding keyBindInventory = new KeyBinding("Inventory", 23);
	public KeyBinding keyBindDrop = new KeyBinding("Drop", 16);
	public KeyBinding keyBindChat = new KeyBinding("Chat", 20);
	public KeyBinding keyBindToggleFog = new KeyBinding("Toggle fog", 33);
	public KeyBinding keyBindSneak = new KeyBinding("Sneak", 42);
    public KeyBinding keyBindZoom = new KeyBinding("Zoom", 34);
    public KeyBinding keyBindPerspective = new KeyBinding("Perspective", 63);
	public KeyBinding[] keyBindings = new KeyBinding[]{this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindToggleFog, this.keyBindZoom, this.keyBindPerspective};
	protected Minecraft mc;
	private File optionsFile;
	public int numberOfOptions = 12;
	public int difficulty = 2;
	public boolean thirdPersonView = false;
    public boolean thirdPersonFront = false; // false = facing back, true = facing front

	public int numberOfClockOptions = 11;
	public boolean shownMCTime = false;

	public boolean clockTruncated = true;
	public boolean clock24Hrtime = false;
	public static final String[] clockDateFormats = {"MDY", "DMY", "YMD", "YDM", "---"};
	public int clockDateFormat = 0;
	public boolean clockShowDayOfWeek = false;
	public boolean clockShowMonth = true;
	public boolean clockShowYear = false;
	public boolean clockShowDay = true;
	public boolean clockShowTime = true;
	public boolean showMcDay = false;
	public boolean showMcDayTimeTicks = false;
	public boolean showDateTime = true;



	public GameSettings(Minecraft var1, File var2) {
		this.mc = var1;
		this.optionsFile = new File(var2, "options.txt");
		this.loadOptions();
	}

	public GameSettings() {
	}

	public String getKeyBindingDescription(int var1) {
		return this.keyBindings[var1].keyDescription + ": " + Keyboard.getKeyName(this.keyBindings[var1].keyCode);
	}

	public void setKeyBinding(int var1, int var2) {
		this.keyBindings[var1].keyCode = var2;
		this.saveOptions();
	}

	public void setOptionFloatValue(int var1, float var2) {
        if (var1 == 0) {
            this.fovOption = 15.0F + var2 * 90.0F;
        }

		if(var1 == 1) {
			this.musicVolume = var2;
			this.mc.sndManager.onSoundOptionsChanged();
		}

		if(var1 == 2) {
			this.soundVolume = var2;
			this.mc.sndManager.onSoundOptionsChanged();
		}

		if(var1 == 4) {
			this.mouseSensitivity = var2;
		}

	}

	public void setOptionValue(int var1, int var2) {
		if(var1 == 3) {
			this.invertMouse = !this.invertMouse;
		}

		if(var1 == 5) {
			this.renderDistance = this.renderDistance + var2 & 3;
		}

		if(var1 == 6) {
			this.viewBobbing = !this.viewBobbing;
		}

		if(var1 == 7) {
			this.anaglyph = !this.anaglyph;
			this.mc.renderEngine.refreshTextures();
		}

		if(var1 == 8) {
			this.limitFramerate = !this.limitFramerate;
		}

		if(var1 == 9) {
			this.difficulty = this.difficulty + var2 & 3;
		}

		if(var1 == 10) {
			this.fancyGraphics = !this.fancyGraphics;
			this.mc.renderGlobal.loadRenderers();
		}



		// Clock Options

		// Truncate
		if (var1 == 12) {
			this.clockTruncated = !this.clockTruncated;
		}

		// 24 Hr Time
		if (var1 == 13) {
			this.clock24Hrtime = !this.clock24Hrtime;
		}

		// Date Format
		if (var1 == 14) {
			if (!this.shownMCTime) {
				this.clockDateFormat = (this.clockDateFormat == 4) ? 0 : (this.clockDateFormat + 1) & 3;
			} else {
				this.clockDateFormat = 4;
			}
		}

		// Show Time
		if (var1 == 15) {
			if (!this.shownMCTime) {
				this.clockShowTime = !this.clockShowTime;
			} else {
				this.clockShowTime = false;
			}
		}

		// Show Day
		if (var1 == 16) {
			if (!this.shownMCTime) {
				this.clockShowDay = !this.clockShowDay;
			} else {
				this.clockShowDay = false;
			}
		}


		// Show Day Of Week
		if (var1 == 17) {
			if (!this.shownMCTime) {
				this.clockShowDayOfWeek = !this.clockShowDayOfWeek;
			} else {
				this.clockShowDayOfWeek = false;
			}
		}

		// Show Month
		if (var1 == 18) {
			if (!this.shownMCTime) {
				this.clockShowMonth = !this.clockShowMonth;
			} else {
				this.clockShowMonth = false;
			}
		}

		// Show Year
		if (var1 == 19) {
			if (!this.shownMCTime) {
				this.clockShowYear = !this.clockShowYear;
			} else {
				this.clockShowYear = false;
			}
		}

		// Show MC Day (falsifies every other metric but Day Time)
		if (var1 == 20) {
			if (!this.showMcDayTimeTicks) {
				this.shownMCTime = !this.shownMCTime;
			}
			this.showMcDay = !this.showMcDay;
		}

		// Show Time in Day (falsifies every other metric but MC Day)
		if (var1 == 21) {
			if (!this.showMcDay) {
				this.shownMCTime = !this.shownMCTime;
			}
			this.showMcDayTimeTicks = !this.showMcDayTimeTicks;
		}

		if (var1 == 22) {
			this.showDateTime = !this.showDateTime;
		}

		this.saveOptions();
	}

	public int isSlider(int var1) {
		return var1 == 0 ? 1 : (var1 == 1 ? 1 : (var1 == 2 ? 1 : (var1 == 4 ? 1 : 0)));
	}

	public float getOptionFloatValue(int var1) {
		return var1 == 0 ? this.fovOption : (var1 == 1 ? this.musicVolume : (var1 == 2 ? this.soundVolume : (var1 == 4 ? this.mouseSensitivity : 0.0F)));
	}

	public String getDateFormat(int var1) {
		return clockDateFormats[var1];
	}

	public String getOptionDisplayString(int var1) {
		return var1 == 0 ? "FOV: " + (this.fovOption > 15.0F ? (int)(this.fovOption) + "°" : "ZOOOOM") :
				(var1 == 1 ? "Music: " + (this.musicVolume > 0.0F ? (int)(this.musicVolume * 100.0F) + "%" : "OFF") :
						(var1 == 2 ? "Sound: " + (this.soundVolume > 0.0F ? (int)(this.soundVolume * 100.0F) + "%" : "OFF") :
								(var1 == 3 ? "Invert mouse: " + (this.invertMouse ? "ON" : "OFF") :
										(var1 == 4 ? (this.mouseSensitivity == 0.0F ? "Sensitivity: *yawn*" : (this.mouseSensitivity == 1.0F ? "Sensitivity: HYPERSPEED!!!" : "Sensitivity: " + (int)(this.mouseSensitivity * 200.0F) + "%")) :
												(var1 == 5 ? "Render distance: " + RENDER_DISTANCES[this.renderDistance] :
														(var1 == 6 ? "View bobbing: " + (this.viewBobbing ? "ON" : "OFF") :
																(var1 == 7 ? "3d anaglyph: " + (this.anaglyph ? "ON" : "OFF") :
																		(var1 == 8 ? "Limit framerate: " + (this.limitFramerate ? "ON" : "OFF") :
																				(var1 == 9 ? "Difficulty: " + DIFFICULTY_LEVELS[this.difficulty] :
																						(var1 == 10 ? "Graphics: " + (this.fancyGraphics ? "FANCY" : "FAST") :
																								(var1 == 11 ? "Clock Settings..." :
																										(var1 == 12 ? "Truncate Date: " + (this.clockTruncated ? "YES" : "NO") :
		(var1 == 13 ? "24 Hour Time: " + (this.clock24Hrtime ? "ON" : "OFF") :
		(var1 == 14 ? "Date Format: " + getDateFormat(this.clockDateFormat) :
		(var1 == 15 ? "Show Time: " + (this.clockShowTime ? "YES" : "NO") :
		(var1 == 16 ? "Show Day: " + (this.clockShowDay ? "YES" : "NO") :
		(var1 == 17 ? "Show Day Of Week: " + (this.clockShowDayOfWeek ? "YES" : "NO") :
		(var1 == 18 ? "Show Month: " + (this.clockShowMonth ? "YES" : "NO") :
		(var1 == 19 ? "Show Year: " + (this.clockShowYear ? "YES" : "NO") :
		(var1 == 20 ? "Show MC Day: " + (this.showMcDay ? "YES" : "NO") :
		(var1 == 21 ? "Show MC Time: " + (this.showMcDayTimeTicks ? "YES" : "NO") :
				(var1 == 22 ? "Show Clock: " + (this.showDateTime ? "ON" : "OFF") : ""
		))))))))))))))))))))));
	}

	public void loadOptions() {
		try {
			if(!this.optionsFile.exists()) {
				return;
			}

			BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
			String var2 = "";

			while(true) {
				var2 = var1.readLine();
				if(var2 == null) {
					var1.close();
					break;
				}

				String[] var3 = var2.split(":");

                if (var3[0].equals("fov")) {
                    this.fovOption = Float.parseFloat(var3[1]);
                }

				if(var3[0].equals("music")) {
					this.musicVolume = this.parseFloat(var3[1]);
				}

				if(var3[0].equals("sound")) {
					this.soundVolume = this.parseFloat(var3[1]);
				}

				if(var3[0].equals("mouseSensitivity")) {
					this.mouseSensitivity = this.parseFloat(var3[1]);
				}

				if(var3[0].equals("invertYMouse")) {
					this.invertMouse = var3[1].equals("true");
				}

				if(var3[0].equals("viewDistance")) {
					this.renderDistance = Integer.parseInt(var3[1]);
				}

				if(var3[0].equals("bobView")) {
					this.viewBobbing = var3[1].equals("true");
				}

				if(var3[0].equals("anaglyph3d")) {
					this.anaglyph = var3[1].equals("true");
				}

				if(var3[0].equals("limitFramerate")) {
					this.limitFramerate = var3[1].equals("true");
				}

				if(var3[0].equals("difficulty")) {
					this.difficulty = Integer.parseInt(var3[1]);
				}

				if(var3[0].equals("fancyGraphics")) {
					this.fancyGraphics = var3[1].equals("true");
				}

				if (var3[0].equals("clock-show")) {
					this.showDateTime = var3[1].equals("true");
				}

				if (var3[0].equals("clock-truncated")) {
					this.clockTruncated = var3[1].equals("true");
				}

				if (var3[0].equals("clock-military")) {
					this.clock24Hrtime =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-format")) {
					this.clockDateFormat = Integer.parseInt(var3[1]);
				}

				if (var3[0].equals("clock-time")) {
					this.clockShowTime =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-day")) {
					this.clockShowDay =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-dayofweek")) {
					this.clockShowDayOfWeek =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-month")) {
					this.clockShowMonth =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-year")) {
					this.clockShowYear =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-mcdata-visible")) {
					this.shownMCTime =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-mc-days")) {
					this.showMcDay =  var3[1].equals("true");
				}

				if (var3[0].equals("clock-mc-time")) {
					this.showMcDayTimeTicks = var3[1].equals("true");
				}



				for(int var4 = 0; var4 < this.keyBindings.length; ++var4) {
					if(var3[0].equals("key_" + this.keyBindings[var4].keyDescription)) {
						this.keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
					}
				}
			}
		} catch (Exception var5) {
			System.out.println("Failed to load options");
			var5.printStackTrace();
		}

	}

	private float parseFloat(String var1) {
		return var1.equals("true") ? 1.0F : (var1.equals("false") ? 0.0F : Float.parseFloat(var1));
	}

	public void saveOptions() {
		try {
			PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFile));

            var1.println("fov: " + this.fovOption);
			var1.println("music:" + this.musicVolume);
			var1.println("sound:" + this.soundVolume);
			var1.println("invertYMouse:" + this.invertMouse);
			var1.println("mouseSensitivity:" + this.mouseSensitivity);
			var1.println("viewDistance:" + this.renderDistance);
			var1.println("bobView:" + this.viewBobbing);
			var1.println("anaglyph3d:" + this.anaglyph);
			var1.println("limitFramerate:" + this.limitFramerate);
			var1.println("difficulty:" + this.difficulty);
			var1.println("fancyGraphics:" + this.fancyGraphics);
			var1.println("clock-show:" + this.showDateTime);
			var1.println("clock-truncated:"+this.clockTruncated);
			var1.println("clock-military:"+this.clock24Hrtime);
			var1.println("clock-format:"+this.clockDateFormat);
			var1.println("clock-time:"+this.clockShowTime);
			var1.println("clock-day:"+this.clockShowDay);
			var1.println("clock-dayofweek:"+this.clockShowDayOfWeek);
			var1.println("clock-month:"+this.clockShowMonth);
			var1.println("clock-year:"+this.clockShowYear);
			var1.println("clock-mcdata-visible:"+this.shownMCTime);
			var1.println("clock-mc-days:"+this.showMcDay);
			var1.println("clock-mc-time:"+this.showMcDayTimeTicks);

			for(int var2 = 0; var2 < this.keyBindings.length; ++var2) {
				var1.println("key_" + this.keyBindings[var2].keyDescription + ":" + this.keyBindings[var2].keyCode);
			}

			var1.close();
		} catch (Exception var3) {
			System.out.println("Failed to save options");
			var3.printStackTrace();
		}

	}
}
