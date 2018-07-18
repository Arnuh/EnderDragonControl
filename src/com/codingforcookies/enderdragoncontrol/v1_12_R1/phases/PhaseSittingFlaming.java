package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import com.codingforcookies.enderdragoncontrol.IPhase;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseSittingFlaming extends NMSDragonController implements IPhase{

	private int b;
	private int c;
	private EntityAreaEffectCloud d;

	public PhaseSittingFlaming(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void b(){
		++this.b;
		if(this.b % 2 == 0 && this.b < 10){
			Vec3D vec3d = enderDragon.a(1.0F).a();
			vec3d.b(-0.7853982F);
			double d0 = enderDragon.bw.locX;
			double d1 = enderDragon.bw.locY + (double) (enderDragon.bw.length / 2.0F);
			double d2 = enderDragon.bw.locZ;
			for(int i = 0; i < 8; ++i){
				double d3 = d0 + enderDragon.getRandom().nextGaussian() / 2.0D;
				double d4 = d1 + enderDragon.getRandom().nextGaussian() / 2.0D;
				double d5 = d2 + enderDragon.getRandom().nextGaussian() / 2.0D;
				for(int j = 0; j < 6; ++j){
					enderDragon.world.addParticle(EnumParticle.DRAGON_BREATH, d3, d4, d5, -vec3d.x * 0.07999999821186066D * (double) j, -vec3d.y * 0.6000000238418579D, -vec3d.z * 0.07999999821186066D * (double) j, new int[0]);
				}
				vec3d.b(0.19634955F);
			}
		}
	}

	public void c(){
		++this.b;
		if(this.b >= 200){
			if(this.c >= 4){
				enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.Takeoff);
			}else{
				enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.SittingScanning);
			}
		}else if(this.b == 10){
			Vec3D vec3d = (new Vec3D(enderDragon.bw.locX - enderDragon.locX, 0.0D, enderDragon.bw.locZ - enderDragon.locZ)).a();
			double d0 = enderDragon.bw.locX + vec3d.x * 5.0D / 2.0D;
			double d1 = enderDragon.bw.locZ + vec3d.z * 5.0D / 2.0D;
			double d2 = enderDragon.bw.locY + (double) (enderDragon.bw.length / 2.0F);
			BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(MathHelper.floor(d0), MathHelper.floor(d2), MathHelper.floor(d1));
			while(enderDragon.world.isEmpty(blockposition_mutableblockposition)){
				--d2;
				blockposition_mutableblockposition.c(MathHelper.floor(d0), MathHelper.floor(d2), MathHelper.floor(d1));
			}
			d2 = (double) (MathHelper.floor(d2) + 1);
			this.d = new EntityAreaEffectCloud(enderDragon.world, d0, d2, d1);
			this.d.setSource(enderDragon);
			this.d.setRadius(5.0F);
			this.d.setDuration(200);
			this.d.setParticle(EnumParticle.DRAGON_BREATH);
			this.d.a(new MobEffect(MobEffects.HARM));
			enderDragon.world.addEntity(this.d);
		}
	}

	public void d(){
		this.b = 0;
		++this.c;
	}

	public void e(){
		if(this.d != null){
			this.d.die();
			this.d = null;
		}
	}

	public DragonControllerPhase<PhaseSittingFlaming> getControllerPhase(){
		return BasicPhaseList.SittingFlaming;
	}

	public void j(){
		this.c = 0;
	}

	@Override
	public String getPhaseName(){
		return "SittingFlaming";
	}
}
