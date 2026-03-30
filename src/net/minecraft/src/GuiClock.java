package net.minecraft.src;

import org.mcphackers.launchwrapper.Launch;

// Menu to tweak clock settings.
public class GuiClock extends GuiScreen {
    private GuiScreen parentScreen;
    protected String screenTitle = "Clock";
    private GameSettings options;

    public GuiClock(GuiScreen var1, GameSettings var2) {
        this.parentScreen = var1;
        this.options = var2;
    }

    public void initGui() {
       // Draw Other Buttons
        for (int var1 = 0; var1 < this.options.numberOfClockOptions; ++var1) {
                this.controlList.add(new GuiSmallButton(this.options.numberOfOptions + var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), this.options.getOptionDisplayString(this.options.numberOfOptions + var1)));
        }
        this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
    }

    protected void actionPerformed(GuiButton var1) {

        if (var1.enabled) {
            if (var1.id < 100) {
                this.options.setOptionValue(var1.id, 1);
                var1.displayString = this.options.getOptionDisplayString(var1.id);
            }

            // Set variables for whichever button pressed.
            if (var1.id == 200) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(var1, var2, var3);
    }
}
