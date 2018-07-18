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
public class PhaseHover extends NMSDragonController implements IPhase{

	private Vec3D b;

	public PhaseHover(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void c(){
		if(this.b == null){
			this.b = new Vec3D(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
		}
	}

	public boolean a(){
		return true;
	}

	public void d(){
		this.b = null;
	}

	public float f(){
		return 1.0F;
	}

	@Nullable
	public Vec3D g(){
		return this.b;
	}

	public DragonControllerPhase<PhaseHover> getControllerPhase(){
		return BasicPhaseList.Hover;
	}

	@Override
	public String getPhaseName(){
		return "Hover";
	}
}
