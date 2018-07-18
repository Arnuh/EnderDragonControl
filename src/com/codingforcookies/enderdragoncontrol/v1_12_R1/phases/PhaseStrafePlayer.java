package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import javax.annotation.Nullable;

import com.codingforcookies.enderdragoncontrol.IPhase;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public class PhaseStrafePlayer extends NMSDragonController implements IPhase{

	private int c;
	private PathEntity d;
	private Vec3D e;
	private EntityLiving f;
	private boolean g;

	public PhaseStrafePlayer(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
	}

	public void c(){
		if(this.f == null){
			System.out.println("Skipping player strafe phase because no player was found");
			enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.HoldingPattern);
		}else{
			double d0;
			double d1;
			double d2;
			if(this.d != null && this.d.b()){
				d0 = this.f.locX;
				d1 = this.f.locZ;
				double d3 = d0 - enderDragon.locX;
				double d4 = d1 - enderDragon.locZ;
				d2 = (double) MathHelper.sqrt(d3 * d3 + d4 * d4);
				double d5 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
				this.e = new Vec3D(d0, this.f.locY + d5, d1);
			}
			d0 = this.e == null ? 0.0D : this.e.c(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
			if(d0 < 100.0D || d0 > 22500.0D){
				this.j();
			}
			d1 = 64.0D;
			if(this.f.h(enderDragon) < 4096.0D){
				if(enderDragon.hasLineOfSight(this.f)){
					++this.c;
					Vec3D vec3d = (new Vec3D(this.f.locX - enderDragon.locX, 0.0D, this.f.locZ - enderDragon.locZ)).a();
					Vec3D vec3d1 = (new Vec3D((double) MathHelper.sin(enderDragon.yaw * 0.017453292F), 0.0D, (double) (-MathHelper.cos(enderDragon.yaw * 0.017453292F)))).a();
					float f = (float) vec3d1.b(vec3d);
					float f1 = (float) (Math.acos((double) f) * 57.2957763671875D);
					f1 += 0.5F;
					if(this.c >= 5 && f1 >= 0.0F && f1 < 10.0F){
						d2 = 1.0D;
						Vec3D vec3d2 = enderDragon.e(1.0F);
						double d6 = enderDragon.bw.locX - vec3d2.x * 1.0D;
						double d7 = enderDragon.bw.locY + (double) (enderDragon.bw.length / 2.0F) + 0.5D;
						double d8 = enderDragon.bw.locZ - vec3d2.z * 1.0D;
						double d9 = this.f.locX - d6;
						double d10 = this.f.locY + (double) (this.f.length / 2.0F) - (d7 + (double) (enderDragon.bw.length / 2.0F));
						double d11 = this.f.locZ - d8;
						enderDragon.world.a((EntityHuman) null, 1017, new BlockPosition(enderDragon), 0);
						EntityDragonFireball entitydragonfireball = new EntityDragonFireball(enderDragon.world, enderDragon, d9, d10, d11);
						entitydragonfireball.setPositionRotation(d6, d7, d8, 0.0F, 0.0F);
						enderDragon.world.addEntity(entitydragonfireball);
						this.c = 0;
						if(this.d != null){
							while(!this.d.b()){
								this.d.a();
							}
						}
						enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.HoldingPattern);
					}
				}else if(this.c > 0){
					--this.c;
				}
			}else if(this.c > 0){
				--this.c;
			}
		}
	}

	private void j(){
		if(this.d == null || this.d.b()){
			int i = enderDragon.p();
			int j = i;
			if(enderDragon.getRandom().nextInt(8) == 0){
				this.g = !this.g;
				j = i + 6;
			}
			if(this.g){
				++j;
			}else{
				--j;
			}
			if(enderDragon.df() != null && enderDragon.df().c() > 0){
				j %= 12;
				if(j < 0){
					j += 12;
				}
			}else{
				j -= 12;
				j &= 7;
				j += 12;
			}
			this.d = enderDragon.a(i, j, (PathPoint) null);
			if(this.d != null){
				this.d.a();
			}
		}
		this.k();
	}

	private void k(){
		if(this.d != null && !this.d.b()){
			Vec3D vec3d = this.d.f();
			this.d.a();
			double d0 = vec3d.x;
			double d1 = vec3d.z;
			double d2;
			do{
				d2 = vec3d.y + (double) (enderDragon.getRandom().nextFloat() * 20.0F);
			}while(d2 < vec3d.y);
			this.e = new Vec3D(d0, d2, d1);
		}
	}

	public void d(){
		this.c = 0;
		this.e = null;
		this.d = null;
		this.f = null;
	}

	public void a(EntityLiving entityliving){
		this.f = entityliving;
		int i = enderDragon.p();
		int j = enderDragon.k(this.f.locX, this.f.locY, this.f.locZ);
		int k = MathHelper.floor(this.f.locX);
		int l = MathHelper.floor(this.f.locZ);
		double d0 = (double) k - enderDragon.locX;
		double d1 = (double) l - enderDragon.locZ;
		double d2 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1);
		double d3 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
		int i1 = MathHelper.floor(this.f.locY + d3);
		PathPoint pathpoint = new PathPoint(k, i1, l);
		this.d = enderDragon.a(i, j, pathpoint);
		if(this.d != null){
			this.d.a();
			this.k();
		}
	}

	@Nullable
	public Vec3D g(){
		return this.e;
	}

	public DragonControllerPhase<PhaseStrafePlayer> getControllerPhase(){
		return BasicPhaseList.StrafePlayer;
	}

	@Override
	public String getPhaseName(){
		return "StrafePlayer";
	}
}
