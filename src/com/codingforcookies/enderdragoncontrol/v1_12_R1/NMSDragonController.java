package com.codingforcookies.enderdragoncontrol.v1_12_R1;

import javax.annotation.Nullable;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
 */
public abstract class NMSDragonController implements IDragonController{

	protected EntityEnderDragon enderDragon;

	public NMSDragonController(EntityEnderDragon enderDragon){
		this.enderDragon = enderDragon;
	}

	@Override
	public boolean a(){
		return false;// getIsStationary
	}

	@Override
	public void a(EntityEnderCrystal arg0, BlockPosition arg1, DamageSource arg2, EntityHuman arg3){
		// onCrystalDestroyed
	}

	@Override
	public void b(){
		// doClientRenderEffects
	}

	@Override
	public void c(){
		// doLocalUpdate
	}

	@Override
	public void d(){
		// initPhase
	}

	@Override
	public void e(){
		// removeAreaEffect
	}

	@Override
	public float f(){
		return 0.6F;// getMaxRiseOrFall
	}

	@Override
	@Nullable
	public Vec3D g(){
		return null;// getTargetLocation
	}

	public float a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f){
		return f;// getAdjustedDamage
	}

	@Override
	public float h(){// getYawFactor
		float f = MathHelper.sqrt(enderDragon.motX * enderDragon.motX + enderDragon.motZ * enderDragon.motZ) + 1.0F;
		float f1 = Math.min(f, 40.0F);
		return 0.7F / f1 / f;
	}
}
