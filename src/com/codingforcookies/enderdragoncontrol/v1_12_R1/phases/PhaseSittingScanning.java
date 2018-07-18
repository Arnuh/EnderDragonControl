package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import org.bukkit.entity.EnderDragon;

import com.codingforcookies.enderdragoncontrol.EnderDragonControl;
import com.codingforcookies.enderdragoncontrol.IPhase;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseSittingScanning extends NMSDragonController implements IPhase{

	private int b;

	public PhaseSittingScanning(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void c(){
		++this.b;
		EntityHuman entityhuman = enderDragon.world.a((Entity) enderDragon, 20.0D, 10.0D);
		if(entityhuman != null){
			if(this.b > 25){
				enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.SittingAttacking);
			}else{
				Vec3D vec3d = (new Vec3D(entityhuman.locX - enderDragon.locX, 0.0D, entityhuman.locZ - enderDragon.locZ)).a();
				Vec3D vec3d1 = (new Vec3D((double) MathHelper.sin(enderDragon.yaw * 0.017453292F), 0.0D, (double) (-MathHelper.cos(enderDragon.yaw * 0.017453292F)))).a();
				float f = (float) vec3d1.b(vec3d);
				float f1 = (float) (Math.acos((double) f) * 57.2957763671875D) + 0.5F;
				if(f1 < 0.0F || f1 > 10.0F){
					double d0 = entityhuman.locX - enderDragon.bw.locX;
					double d1 = entityhuman.locZ - enderDragon.bw.locZ;
					double d2 = MathHelper.a(MathHelper.g(180.0D - MathHelper.c(d0, d1) * 57.2957763671875D - (double) enderDragon.yaw), -100.0D, 100.0D);
					enderDragon.bh *= 0.8F;
					float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
					float f3 = f2;
					if(f2 > 40.0F){
						f2 = 40.0F;
					}
					enderDragon.bh = (float) ((double) enderDragon.bh + d2 * (double) (0.7F / f2 / f3));
					enderDragon.yaw += enderDragon.bh;
				}
			}
		}else if(this.b >= 100){
			entityhuman = enderDragon.world.a((Entity) enderDragon, 150.0D, 150.0D);
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.Takeoff);
			if(entityhuman != null){
				enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.ChargingPlayer);
				try{
					((PhaseChargingPlayer) EnderDragonControl.getPhaseManager((EnderDragon) enderDragon.getBukkitEntity()).getCurrentPhase()).a(new Vec3D(entityhuman.locX, entityhuman.locY, entityhuman.locZ));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				// ((DragonControllerCharge) enderDragon.getDragonControllerManager().b(DragonControllerPhase.i)).a(new Vec3D(entityhuman.locX, entityhuman.locY, entityhuman.locZ));
			}
		}
	}

	public void d(){
		this.b = 0;
	}

	public DragonControllerPhase<PhaseSittingScanning> getControllerPhase(){
		return BasicPhaseList.SittingScanning;
	}

	@Override
	public String getPhaseName(){
		return "SittingScanning";
	}
}
