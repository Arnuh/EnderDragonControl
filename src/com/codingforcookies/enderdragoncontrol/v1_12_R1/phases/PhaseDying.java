package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import javax.annotation.Nullable;

import com.codingforcookies.enderdragoncontrol.phases.IPhaseDying;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseDying extends NMSDragonController implements IPhaseDying{

	private Vec3D b;
	private int c;

	public PhaseDying(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void b(){
		if(this.c++ % 10 == 0){
			float f = (enderDragon.getRandom().nextFloat() - 0.5F) * 8.0F;
			float f1 = (enderDragon.getRandom().nextFloat() - 0.5F) * 4.0F;
			float f2 = (enderDragon.getRandom().nextFloat() - 0.5F) * 8.0F;
			enderDragon.world.addParticle(EnumParticle.EXPLOSION_HUGE, enderDragon.locX + (double) f, enderDragon.locY + 2.0D + (double) f1, enderDragon.locZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	public void c(){
		++this.c;
		if(this.b == null){// add setter/getter in IPhaseDying
			BlockPosition blockposition = enderDragon.world.getHighestBlockYAt(WorldGenEndTrophy.a);
			this.b = new Vec3D((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ());
		}
		double d0 = this.b.c(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
		if(d0 >= 100.0D && d0 <= 22500.0D && !enderDragon.positionChanged && !enderDragon.B){
			enderDragon.setHealth(1.0F);
		}else{
			enderDragon.setHealth(0.0F);
		}
	}

	public void d(){
		this.b = null;
		this.c = 0;
	}

	public float f(){
		return 3.0F;
	}

	@Nullable
	public Vec3D g(){
		return this.b;
	}

	public DragonControllerPhase<PhaseDying> getControllerPhase(){
		return BasicPhaseList.Dying;
	}

	@Override
	public String getPhaseName(){
		return "Dying";
	}
}
