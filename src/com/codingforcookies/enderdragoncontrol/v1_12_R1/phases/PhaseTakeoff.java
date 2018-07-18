package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import javax.annotation.Nullable;

import org.bukkit.Location;

import com.codingforcookies.enderdragoncontrol.phases.IPhaseTakeoff;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseTakeoff extends NMSDragonController implements IPhaseTakeoff{

	private boolean b;
	private PathEntity c;
	private Vec3D d;
	private Location targetLocation;

	public PhaseTakeoff(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
		this.targetLocation = new Location(null, 0, 0, 0);
	}

	public void c(){
		if(!this.b && this.c != null){
			BlockPosition blockposition = this.enderDragon.world.q(new BlockPosition(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ()));
			double d0 = this.enderDragon.d(blockposition);
			if(d0 > 100.0D){
				this.enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.HoldingPattern);
			}
		}else{
			this.b = false;
			this.j();
		}
	}

	public void d(){
		this.b = true;
		this.c = null;
		this.d = null;
	}

	private void j(){
		int i = this.enderDragon.p();
		Vec3D vec3d = this.enderDragon.a(1.0F);
		int j = this.enderDragon.k(-vec3d.x * 40.0D, 105.0D, -vec3d.z * 40.0D);
		if(this.enderDragon.df() != null && this.enderDragon.df().c() > 0){
			j %= 12;
			if(j < 0){
				j += 12;
			}
		}else{
			j -= 12;
			j &= 7;
			j += 12;
		}
		this.c = this.enderDragon.a(i, j, (PathPoint) null);
		if(this.c != null){
			this.c.a();
			this.k();
		}
	}

	private void k(){
		Vec3D vec3d = this.c.f();
		this.c.a();
		double d0;
		do{
			d0 = vec3d.y + (double) (this.enderDragon.getRandom().nextFloat() * 20.0F);
		}while(d0 < vec3d.y);
		this.d = new Vec3D(vec3d.x, d0, vec3d.z);
	}

	@Nullable
	public Vec3D g(){
		return this.d;
	}

	public DragonControllerPhase<PhaseTakeoff> getControllerPhase(){
		return BasicPhaseList.Takeoff;
	}

	@Override
	public String getPhaseName(){
		return "Takeoff";
	}

	@Override
	public Location getTargetLocation(){
		return targetLocation;
	}

	@Override
	public void setTargetLocation(Location location){
		this.targetLocation = location;
	}
}
