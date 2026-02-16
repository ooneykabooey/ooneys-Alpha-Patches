package net.minecraft.src;

public class EntityPig extends EntityAnimal {
	public boolean saddled = false;

	public EntityPig(World var1) {
		super(var1);
		this.texture = "/mob/pig.png";
		this.setSize(0.9F, 0.9F);
		this.saddled = false;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setBoolean("Saddle", this.saddled);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.saddled = var1.getBoolean("Saddle");
	}

	protected String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	public boolean interact(EntityPlayer var1) {
		if(this.saddled) {
			var1.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}

	protected int getDropItemId() {
		return Item.porkRaw.shiftedIndex;
	}

	public void onDeath(Entity var1) {
		if(this.scoreValue > 0 && var1 != null) {
			var1.addToPlayerScore(this, this.scoreValue);
		}

		this.dead = true;
		int var2 = this.getDropItemId();
		int saddle = Item.saddle.shiftedIndex;
		if(var2 > 0) {
			int var3 = this.rand.nextInt(3);

			for(int var4 = 0; var4 < var3; ++var4) {
				this.dropItem(var2, 1);
			}

			if (saddled) {
				this.dropItem(saddle, 1);
			}
		}

	}
}
