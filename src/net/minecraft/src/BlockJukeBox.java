package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class BlockJukeBox extends Block {
    private Minecraft mc;
	protected BlockJukeBox(int var1, int var2) {
		super(var1, var2, Material.wood);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.blockIndexInTexture + (var1 == 1 ? 1 : 0);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		if(var6 > 0) {
			this.ejectRecord(var1, var2, var3, var4, var6);
			return true;
		} else {
			return false;
		}
	}

	public void ejectRecord(World var1, int var2, int var3, int var4, int var5) {
		var1.playRecord((String)null, var2, var3, var4, (String)null);
		var1.setBlockMetadataWithNotify(var2, var3, var4, 0);
		int var6 = Item.record13.shiftedIndex + var5 - 1;
		float var7 = 0.7F;
		double var8 = (double)(var1.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		double var10 = (double)(var1.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.2D + 0.6D;
		double var12 = (double)(var1.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		EntityItem var14 = new EntityItem(var1, (double)var2 + var8, (double)var3 + var10, (double)var4 + var12, new ItemStack(var6));
		var14.delayBeforeCanPickup = 10;
		var1.spawnEntityInWorld(var14);
	}

	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6) {
		if(!var1.multiplayerWorld) {
			if(var5 > 0) {
				this.ejectRecord(var1, var2, var3, var4, var5);
			}

			super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6);
		}
	}

    public void insertRecord(World world, int x, int y, int z, String recordName) {
        // Set the block metadata to indicate a record is inserted
        int metadata = recordName.equals("13") ? 1 : (recordName.equals("cat") ? 2 : 0);
        world.setBlockMetadataWithNotify(x, y, z, metadata);

        // Determine overlay text
        String overlayText = recordName;

        // Check all 6 adjacent blocks for a sign
        int[][] offsets = {
                {1, 0, 0}, {-1, 0, 0},
                {0, 1, 0}, {0, -1, 0},
                {0, 0, 1}, {0, 0, -1}
        };

        for (int[] off : offsets) {
            int sx = x + off[0];
            int sy = y + off[1];
            int sz = z + off[2];
            int blockID = world.getBlockId(sx, sy, sz);

            if (blockID == Block.signStanding.blockID || blockID == Block.signWall.blockID) {
                TileEntitySign sign = (TileEntitySign) world.getBlockTileEntity(sx, sy, sz);
                if (sign != null && sign.signText[1] != null &&
                        sign.signText[1].trim().equalsIgnoreCase("Now Playing:")) {
                    overlayText = sign.signText[2];
                    if (!sign.signText[3].isEmpty()) {
                        overlayText += " " + sign.signText[3];
                    }
                    break;
                }
            }
        }



        // Play the actual record sound (still 13.ogg / cat.ogg)
        world.playRecord(recordName, x, y, z, overlayText);


    // Show overlay (label comes from sign if present)
        if (!world.multiplayerWorld) {
            if (mc != null && mc.ingameGUI != null) {
                mc.ingameGUI.setRecordPlayingMessage("Now playing: " + overlayText);
            }
        }
    }
}
