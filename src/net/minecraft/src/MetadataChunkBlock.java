package net.minecraft.src;

import org.lwjgl.Sys;

public class MetadataChunkBlock {
	public final EnumSkyBlock skyBlock;
	public int minX;
	public int minY;
	public int minZ;
	public int maxX;
	public int maxY;
	public int maxZ;

	public MetadataChunkBlock(EnumSkyBlock var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this.skyBlock = var1;
		this.minX = var2;
		this.minY = var3;
		this.minZ = var4;
		this.maxX = var5;
		this.maxY = var6;
		this.maxZ = var7;
	}

	public void updateLight(World var1) {
		int var2 = this.maxX - this.minX + 1;
		int var3 = this.maxY - this.minY + 1;
		int var4 = this.maxZ - this.minZ + 1;
		int var5 = var2 * var3 * var4;
		if(var5 <= 74273) {
			for(int var6 = this.minX; var6 <= this.maxX; ++var6) {
				for(int var7 = this.minZ; var7 <= this.maxZ; ++var7) {
                    boolean var40 = var1.doChunksNearChunkExist(var6, 0, var7, 1);
                    if (var40) {
                        Chunk var41 = var1.getChunkFromChunkCoords(var6 >> 4, var7 >> 4);
                        if (var41.isChunkRendered || this.minY >= 128 && var41.blocks2 == null) {
                            var40 = false;
                        }
                    }

                    if (var40) {
                        if (this.minY < 0) {
                            this.minY = 0;
                        }
                        if (this.maxY >= 256) {
                            this.maxY = 255;
                        }

                        for (int var22 = this.minY; var22 <= this.maxY; ++var22) {
                            int var10 = var1.getSavedLightValue(this.skyBlock, var6, var22, var7);
                            boolean var11 = false;
                            int var12 = var1.getBlockId(var6, var22, var7);
                            int var13 = Block.lightOpacity[var12];
                            if (var13 == 0) {
                                var13 = 1;
                            }

                            int var14 = 0;
                            if (this.skyBlock == EnumSkyBlock.Sky) {
                                if (var1.canExistingBlockSeeTheSky(var6, var22, var7)) {
                                    var14 = 15;
                                }
                            } else if (this.skyBlock == EnumSkyBlock.Block) {
                                var14 = Block.lightValue[var12];
                            }

                            int var24;
                            if (var13 >= 15 && var14 == 0) {
                                var24 = 0;
                            } else {
                                int var15 = var1.getSavedLightValue(this.skyBlock, var6 - 1, var22, var7);
                                int var17 = var1.getSavedLightValue(this.skyBlock, var6 + 1, var22, var7);
                                int var18 = var1.getSavedLightValue(this.skyBlock, var6, var22 - 1, var7);
                                int var19 = var1.getSavedLightValue(this.skyBlock, var6, var22 + 1, var7);
                                int var20 = var1.getSavedLightValue(this.skyBlock, var6, var22, var7 - 1);
                                int var21 = var1.getSavedLightValue(this.skyBlock, var6, var22, var7 + 1);
                                var24 = var15;
                                if (var17 > var15) {
                                    var24 = var17;
                                }

                                if (var18 > var24) {
                                    var24 = var18;
                                }

                                if (var19 > var24) {
                                    var24 = var19;
                                }

                                if (var20 > var24) {
                                    var24 = var20;
                                }

                                if (var21 > var24) {
                                    var24 = var21;
                                }

                                var24 -= var13;
                                if (var24 < 0) {
                                    var24 = 0;
                                }

                                if (var14 > var24) {
                                    var24 = var14;
                                }
                            }


                            if (var10 != var24) {
                                var1.setLightValue(this.skyBlock, var6, var22, var7, var24);
                                int var23 = var24 - 1;
                                if (var23 < 0) {
                                    var23 = 0;
                                }

                                var1.neighborLightPropagationChanged(this.skyBlock, var6 - 1, var22, var7, var23);
                                var1.neighborLightPropagationChanged(this.skyBlock, var6, var22 - 1, var7, var23);
                                var1.neighborLightPropagationChanged(this.skyBlock, var6, var22, var7 - 1, var23);

                                if (var6 + 1 >= this.maxX) {
                                    var1.neighborLightPropagationChanged(this.skyBlock, var6 + 1, var22, var7, var23);
                                }

                                if (var22 + 1 >= this.maxY) {
                                    var1.neighborLightPropagationChanged(this.skyBlock, var6, var22 + 1, var7, var23);
                                }

                                if (var7 + 1 >= this.maxZ) {
                                    var1.neighborLightPropagationChanged(this.skyBlock, var6, var22, var7 + 1, var23);
                                }
                            }
                        }
                    }
				}
			}
		} else {
            System.out.println("Light too large, skipping!");
        }
	}

	public boolean getLightUpdated(int var1, int var2, int var3, int var4, int var5, int var6) {
		if(var1 >= this.minX && var2 >= this.minY && var3 >= this.minZ && var4 <= this.maxX && var5 <= this.maxY && var6 <= this.maxZ) {
			return true;
		} else {
			byte var7 = 1;
			if(var1 >= this.minX - var7 && var2 >= this.minY - var7 && var3 >= this.minZ - var7 && var4 <= this.maxX + var7 && var5 <= this.maxY + var7 && var6 <= this.maxZ + var7) {
				int var8 = this.maxX - this.minX;
				int var9 = this.maxY - this.minY;
				int var10 = this.maxZ - this.minZ;
				if(var1 > this.minX) {
					var1 = this.minX;
				}

				if(var2 > this.minY) {
					var2 = this.minY;
				}

				if(var3 > this.minZ) {
					var3 = this.minZ;
				}

				if(var4 < this.maxX) {
					var4 = this.maxX;
				}

				if(var5 < this.maxY) {
					var5 = this.maxY;
				}

				if(var6 < this.maxZ) {
					var6 = this.maxZ;
				}

				int var11 = var4 - var1;
				int var12 = var5 - var2;
				int var13 = var6 - var3;
				int var14 = var8 * var9 * var10;
				int var15 = var11 * var12 * var13;
				if(var15 - var14 <= 2) {
					this.minX = var1;
					this.minY = var2;
					this.minZ = var3;
					this.maxX = var4;
					this.maxY = var5;
					this.maxZ = var6;
					return true;
				}
			}

			return false;
		}
	}
}
