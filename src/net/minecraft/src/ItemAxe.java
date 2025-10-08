package net.minecraft.src;

public class ItemAxe extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.planks, Block.bookshelf, Block.wood, Block.chest, Block.doorWood, Block.workbench, Block.stairCompactWood, Block.jukebox, Block.fence};

	public ItemAxe(int var1, int var2) {
		super(var1, 3, var2, blocksEffectiveAgainst);
	}
}
