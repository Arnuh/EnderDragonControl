package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import javax.annotation.Nullable;

import org.bukkit.Location;

import com.codingforcookies.enderdragoncontrol.phases.IPhaseLandingApproach;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseLandingApproach extends NMSDragonController implements IPhaseLandingApproach{

	private PathEntity b;
	private Vec3D c;
	private Location landingLocation;
	private boolean targetPlayer;

	public PhaseLandingApproach(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
		this.landingLocation = new Location(null, 0, 0, 0);
	}

	public DragonControllerPhase<PhaseLandingApproach> getControllerPhase(){
		return BasicPhaseList.LandingApproach;
	}

	public void d(){
		this.b = null;
		this.c = null;
	}

	public void c(){
		double d0 = this.c == null ? 0.0D : this.c.c(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
		if(d0 < 100.0D || d0 > 22500.0D || enderDragon.positionChanged || enderDragon.B){
			this.j();
		}
	}

	@Nullable
	public Vec3D g(){
		return this.c;
	}

	private void j(){
		if(this.b == null || this.b.b()){
			int i = enderDragon.p();
			BlockPosition blockposition = enderDragon.world.q(new BlockPosition(landingLocation.getX(), landingLocation.getY(), landingLocation.getZ()));
			EntityHuman entityhuman = targetPlayer ? enderDragon.world.a(blockposition, 128.0D, 128.0D) : null;
			int j;
			if(entityhuman != null){
				Vec3D vec3d = (new Vec3D(entityhuman.locX, 0.0D, entityhuman.locZ)).a();
				j = enderDragon.k(-vec3d.x * 40.0D, 105.0D, -vec3d.z * 40.0D);
			}else{
				j = enderDragon.k(40.0D, (double) blockposition.getY(), 0.0D);
			}
			PathPoint pathpoint = new PathPoint(blockposition.getX(), blockposition.getY(), blockposition.getZ());
			this.b = enderDragon.a(i, j, pathpoint);
			if(this.b != null){
				this.b.a();
			}
		}
		this.k();
		if(this.b != null && this.b.b()){
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.Landing);
		}
	}

	private void k(){
		if(this.b != null && !this.b.b()){
			Vec3D vec3d = this.b.f();
			this.b.a();
			double d0 = vec3d.x;
			double d1 = vec3d.z;
			double d2;
			do{
				d2 = vec3d.y + (double) (enderDragon.getRandom().nextFloat() * 20.0F);
			}while(d2 < vec3d.y);
			this.c = new Vec3D(d0, d2, d1);
		}
	}

	@Override
	public String getPhaseName(){
		return "LandingApproach";
	}

	@Override
	public Location getLandingLocation(){
		return landingLocation;
	}

	@Override
	public void setLandingLocation(Location location){
		this.landingLocation = location;
	}

	@Override
	public boolean isTargetingPlayers(){
		return targetPlayer;
	}

	@Override
	public void setTargetingPlayers(boolean targetPlayers){
		this.targetPlayer = targetPlayers;
	}
}
