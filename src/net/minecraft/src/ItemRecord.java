package net.minecraft.src;

public class ItemRecord extends Item {
	private String recordName;

	protected ItemRecord(int var1, String var2) {
		super(var1);
		this.recordName = var2;
		this.maxStackSize = 1;
	}

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (world.getBlockId(x, y, z) == Block.jukebox.blockID && world.getBlockMetadata(x, y, z) == 0) {
            // Insert the record into the jukebox
            BlockJukeBox jukebox = (BlockJukeBox) Block.blocksList[Block.jukebox.blockID];
            jukebox.insertRecord(world, x, y, z, this.recordName);

            // Consume the record
            --stack.stackSize;
            return true;
        }
        return false;
    }

}
