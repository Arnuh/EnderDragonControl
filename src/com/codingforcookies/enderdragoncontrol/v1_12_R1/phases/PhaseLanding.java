package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;

import com.codingforcookies.enderdragoncontrol.EnderDragonControl;
import com.codingforcookies.enderdragoncontrol.phases.IPhaseLanding;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseLanding extends NMSDragonController implements IPhaseLanding{

	private Vec3D b;
	private Location landingLocation;

	public PhaseLanding(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
		this.landingLocation = new Location(null, 0, 0, 0);
	}

	public void b(){
		Vec3D vec3d = enderDragon.a(1.0F).a();
		vec3d.b(-0.7853982F);
		double d0 = enderDragon.bw.locX;
		double d1 = enderDragon.bw.locY + (double) (enderDragon.bw.length / 2.0F);
		double d2 = enderDragon.bw.locZ;
		for(int i = 0; i < 8; ++i){
			double d3 = d0 + enderDragon.getRandom().nextGaussian() / 2.0D;
			double d4 = d1 + enderDragon.getRandom().nextGaussian() / 2.0D;
			double d5 = d2 + enderDragon.getRandom().nextGaussian() / 2.0D;
			enderDragon.world.addParticle(EnumParticle.DRAGON_BREATH, d3, d4, d5, -vec3d.x * 0.07999999821186066D + enderDragon.motX, -vec3d.y * 0.30000001192092896D + enderDragon.motY, -vec3d.z * 0.07999999821186066D + enderDragon.motZ, new int[0]);
			vec3d.b(0.19634955F);
		}
	}

	public void c(){
		if(this.b == null){
			this.b = new Vec3D(enderDragon.world.q(new BlockPosition(landingLocation.getX(), landingLocation.getY(), landingLocation.getZ())));
		}
		if(this.b.c(enderDragon.locX, enderDragon.locY, enderDragon.locZ) < 1.0D){
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.SittingFlaming);// Mojang does this after called j
			try{
				((PhaseSittingFlaming) EnderDragonControl.getPhaseManager((EnderDragon) enderDragon.getBukkitEntity()).getCurrentPhase()).j();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public float f(){
		return 1.5F;
	}

	public float h(){
		float f = MathHelper.sqrt(enderDragon.motX * enderDragon.motX + enderDragon.motZ * enderDragon.motZ) + 1.0F;
		float f1 = Math.min(f, 40.0F);
		return f1 / f;
	}

	public void d(){
		this.b = null;
	}

	@Nullable
	public Vec3D g(){
		return this.b;
	}

	public DragonControllerPhase<PhaseLanding> getControllerPhase(){
		return BasicPhaseList.Landing;
	}

	@Override
	public String getPhaseName(){
		return "Landing";
	}

	@Override
	public Location getLandingLocation(){
		return landingLocation;
	}

	@Override
	public void setLandingLocation(Location location){
		this.landingLocation = location;
	}
}
