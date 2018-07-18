package com.codingforcookies.enderdragoncontrol.v1_12_R1.phases;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;

import com.codingforcookies.enderdragoncontrol.EnderDragonControl;
import com.codingforcookies.enderdragoncontrol.phases.IPhaseHoldingPattern;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.BasicPhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.NMSDragonController;

import net.minecraft.server.v1_12_R1.*;

/**
 * @author Arnah
 * @since Jul 8, 2018
 */
public class PhaseHoldingPattern extends NMSDragonController implements IPhaseHoldingPattern{

	private PathEntity b;
	private Vec3D c;
	private boolean d;
	private Location playerArea, holdingLocation;
	private int holdingLocationRadius = 20;
	private Field bOField, bPField;

	public PhaseHoldingPattern(EntityEnderDragon enderDragon){
		super(enderDragon);
		this.holdingLocation = new Location(null, 0, 0, 0);
	}

	public DragonControllerPhase<PhaseHoldingPattern> getControllerPhase(){
		return BasicPhaseList.HoldingPattern;
	}

	public void c(){
		double d1 = this.c == null ? 0.0D : this.c.c(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
		if((d1 < 100.0D) || (d1 > 22500.0D) || (enderDragon.positionChanged) || (enderDragon.B)){
			j();
		}
	}

	public void d(){
		this.b = null;
		this.c = null;
	}

	@Nullable
	public Vec3D g(){
		return this.c;
	}

	private void j(){
		int j;
		if((this.b != null) && (this.b.b())){

			j = enderDragon.df() == null ? 0 : enderDragon.df().c();
			if(enderDragon.getRandom().nextInt(j + 3) == 0){
				enderDragon.getDragonControllerManager().setControllerPhase(BasicPhaseList.LandingApproach);
				return;
			}
			if(playerArea != null){
				double d1 = 64.0D;
				BlockPosition localBlockPosition = enderDragon.world.q(new BlockPosition(playerArea.getX(), 0, playerArea.getZ()));
				EntityHuman localEntityHuman = enderDragon.world.a(localBlockPosition, d1, d1);
				if(localEntityHuman != null){
					d1 = localEntityHuman.d(localBlockPosition) / 512.0D;
				}
				if((localEntityHuman != null) && ((enderDragon.getRandom().nextInt(MathHelper.a((int) d1) + 2) == 0) || (enderDragon.getRandom().nextInt(j + 2) == 0))){
					a(localEntityHuman);
					return;
				}
			}
		}
		if((this.b == null) || (this.b.b())){
			// This method creates pathpoints for 24 positions in a circle around 0, 0
			int i = p();// enderDragon.p();
			j = i;
			if(enderDragon.getRandom().nextInt(8) == 0){
				this.d = (!this.d);
				j += 6;
			}
			if(this.d){
				j++;
			}else{
				j--;
			}
			if((enderDragon.df() == null) || (enderDragon.df().c() < 0)){
				j -= 12;
				j &= 0x7;
				j += 12;
			}else{
				j %= 12;
				if(j < 0){
					j += 12;
				}
			}
			this.b = enderDragon.a(i, j, null);
			if(this.b != null){
				this.b.a();
			}
		}
		k();
	}

	private void a(EntityHuman paramEntityHuman){
		// Sets a target of DragonControllerStrafe (Strafe Player)
		enderDragon.getDragonControllerManager().setControllerPhase(DragonControllerPhase.b);
		try{
			((PhaseStrafePlayer) EnderDragonControl.getPhaseManager((EnderDragon) enderDragon.getBukkitEntity()).getCurrentPhase()).a(paramEntityHuman);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void k(){
		if((this.b != null) && (!this.b.b())){
			Vec3D localVec3D = this.b.f();
			this.b.a();
			double d1 = localVec3D.x;
			double d2 = localVec3D.z;
			double d3;
			do{
				d3 = localVec3D.y + enderDragon.getRandom().nextFloat() * 20.0F;
			}while(d3 < localVec3D.y);
			this.c = new Vec3D(d1, d3, d2);
		}
	}

	public void a(EntityEnderCrystal paramEntityEnderCrystal, BlockPosition paramBlockPosition, DamageSource paramDamageSource, @Nullable EntityHuman paramEntityHuman){
		if((paramEntityHuman != null) && (!paramEntityHuman.abilities.isInvulnerable)){
			a(paramEntityHuman);
		}
	}

	public int p(){
		try{
			if(bOField == null){
				bOField = enderDragon.getClass().getDeclaredField("bO");
				bOField.setAccessible(true);
			}
			PathPoint[] bO = (PathPoint[]) bOField.get(enderDragon);
			if(bO[0] == null){
				for(int i = 0; i < 24; ++i){
					int j = 5;
					int k;
					int l;
					if(i < 12){// This sets where the dragon flies around.
						k = (int) (60.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.2617994F * (float) i)));
						l = (int) (60.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.2617994F * (float) i)));
					}else{
						int i1;
						if(i < 20){
							i1 = i - 12;
							k = (int) (40.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.3926991F * (float) i1)));
							l = (int) (40.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.3926991F * (float) i1)));
							j += 10;
						}else{
							i1 = i - 20;
							k = (int) (20.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.7853982F * (float) i1)));
							l = (int) (20.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.7853982F * (float) i1)));
						}
					}
					int j1 = Math.max(enderDragon.world.getSeaLevel() + 10, enderDragon.world.q(new BlockPosition(k, 0, l)).getY() + j);
					bO[i] = new PathPoint(k, j1, l);
				}
				if(bPField == null){
					bPField = enderDragon.getClass().getDeclaredField("bP");
					bPField.setAccessible(true);
				}
				int[] bP = (int[]) bPField.get(enderDragon);
				bP[0] = 6146;
				bP[1] = 8197;
				bP[2] = 8202;
				bP[3] = 16404;
				bP[4] = '\u8028';
				bP[5] = '\u8050';
				bP[6] = 65696;
				bP[7] = 131392;
				bP[8] = 131712;
				bP[9] = 263424;
				bP[10] = 526848;
				bP[11] = 525313;
				bP[12] = 1581057;
				bP[13] = 3166214;
				bP[14] = 2138120;
				bP[15] = 6373424;
				bP[16] = 4358208;
				bP[17] = 12910976;
				bP[18] = 9044480;
				bP[19] = 9706496;
				bP[20] = 15216640;
				bP[21] = 13688832;
				bP[22] = 11763712;
				bP[23] = 8257536;
				bPField.set(enderDragon, bP);
				bOField.set(enderDragon, bO);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return enderDragon.k(enderDragon.locX, enderDragon.locY, enderDragon.locZ);
	}

	@Override
	public String getPhaseName(){
		return "HoldingPattern";
	}

	@Override
	public Location getPlayerArea(){
		return playerArea;
	}

	@Override
	public void setPlayerArea(Location location){
		this.playerArea = location;
	}

	@Override
	public Location getHoldingLocation(){
		return holdingLocation;
	}

	@Override
	public void setHoldingLocation(Location location){
		this.holdingLocation = location;
	}

	@Override
	public int getHoldingLocationRadius(){
		return holdingLocationRadius;
	}

	@Override
	public void setHoldingLocationRadius(int holdingLocationRadius){
		this.holdingLocationRadius = holdingLocationRadius;
	}
}
