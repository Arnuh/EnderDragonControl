package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import javax.annotation.Nullable;

import com.codingforcookies.enderdragoncontrol.IPhase;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.DragonControllerPhase;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.Vec3D;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseChargingPlayer extends NMSDragonController implements IPhase{

	private Vec3D c;
	private int d;

	public PhaseChargingPlayer(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void c(){
		if(this.c == null){
			// PhaseChargingPlayer.b.warn("Aborting charge player as no target was set.");
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.HoldingPattern);
		}else if(this.d > 0 && this.d++ >= 10){
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.HoldingPattern);
		}else{
			double d0 = this.c.c(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
			if(d0 < 100.0D || d0 > 22500.0D || enderDragon.positionChanged || enderDragon.B){
				++this.d;
			}
		}
	}

	public void d(){
		this.c = null;
		this.d = 0;
	}

	public void a(Vec3D vec3d){
		this.c = vec3d;
	}

	public float f(){
		return 3.0F;
	}

	@Nullable
	public Vec3D g(){
		return this.c;
	}

	public DragonControllerPhase<PhaseChargingPlayer> getControllerPhase(){
		return BasicPhaseList.ChargingPlayer;
	}

	@Override
	public String getPhaseName(){
		return "ChargingPlayer";
	}
}
