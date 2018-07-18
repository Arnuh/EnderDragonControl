package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import com.codingforcookies.enderdragoncontrol.IPhase;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.DragonControllerPhase;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.SoundEffects;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseSittingAttacking extends NMSDragonController implements IPhase{

	private int b;

	public PhaseSittingAttacking(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void b(){
		enderDragon.world.a(enderDragon.locX, enderDragon.locY, enderDragon.locZ, SoundEffects.aY, enderDragon.bK(), 2.5F, 0.8F + enderDragon.getRandom().nextFloat() * 0.3F, false);
	}

	public void c(){
		if(this.b++ >= 40){
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.SittingFlaming);
		}
	}

	public void d(){
		this.b = 0;
	}

	public DragonControllerPhase<PhaseSittingAttacking> getControllerPhase() {
		return BasicPhaseList.SittingAttacking;
    }

	@Override
	public String getPhaseName(){
		return "SittingAttacking";
	}
}