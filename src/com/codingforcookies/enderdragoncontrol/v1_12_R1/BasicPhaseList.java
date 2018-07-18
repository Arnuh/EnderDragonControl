package com.codingforcookies.enderdragoncontrol.v1_12_R1;


import java.lang.reflect.Constructor;

import com.codingforcookies.enderdragoncontrol.PhaseList;
import com.codingforcookies.enderdragoncontrol.v1_12_R1.phases.*;

import net.minecraft.server.v1_12_R1.DragonControllerPhase;
import net.minecraft.server.v1_12_R1.IDragonController;

/**
 * 
 * @author Arnah
 * @since Jul 8, 2018
 */
public class BasicPhaseList<T extends IDragonController>{

	public static final DragonControllerPhase<PhaseHoldingPattern> HoldingPattern = setup(PhaseHoldingPattern.class, "HoldingPattern");
	public static final DragonControllerPhase<PhaseStrafePlayer> StrafePlayer = setup(PhaseStrafePlayer.class, "StrafePlayer");
	public static final DragonControllerPhase<PhaseLandingApproach> LandingApproach = setup(PhaseLandingApproach.class, "LandingApproach");
	public static final DragonControllerPhase<PhaseLanding> Landing = setup(PhaseLanding.class, "Landing");
	public static final DragonControllerPhase<PhaseTakeoff> Takeoff = setup(PhaseTakeoff.class, "Takeoff");
	public static final DragonControllerPhase<PhaseSittingFlaming> SittingFlaming = setup(PhaseSittingFlaming.class, "SittingFlaming");
	public static final DragonControllerPhase<PhaseSittingScanning> SittingScanning = setup(PhaseSittingScanning.class, "SittingScanning");
	public static final DragonControllerPhase<PhaseSittingAttacking> SittingAttacking = setup(PhaseSittingAttacking.class, "SittingAttacking");
	public static final DragonControllerPhase<PhaseChargingPlayer> ChargingPlayer = setup(PhaseChargingPlayer.class, "ChargingPlayer");
	public static final DragonControllerPhase<PhaseDying> Dying = setup(PhaseDying.class, "Dying");
	public static final DragonControllerPhase<PhaseHover> Hover = setup(PhaseHover.class, "Hover");
	private static int index = 0;
	@SuppressWarnings("unchecked")
	public static <T extends IDragonController> DragonControllerPhase<T> setup(Class<T> clazz, String phaseName){
		/*try{
			// Allows the use to inject into mojangs DragonControllerPhase array.
			DragonControllerPhase<?>[] l = null;
			Field arrayField = null;
			for(Field field : DragonControllerPhase.class.getDeclaredFields()){
				if(field.getType().isArray()){
					field.setAccessible(true);
					arrayField = field;
					l = (DragonControllerPhase<?>[]) field.get(null);
					break;
				}
			}
			Constructor<?> con = DragonControllerPhase.class.getDeclaredConstructor(int.class, Class.class, String.class);
			con.setAccessible(true);
			DragonControllerPhase localDragonControllerPhase = (DragonControllerPhase) con.newInstance(l.length, paramClass, paramString);
			l = (DragonControllerPhase[]) Arrays.copyOf(l, l.length + 1);
			l[localDragonControllerPhase.b()] = localDragonControllerPhase;
			arrayField.set(null, l);
			return localDragonControllerPhase;
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;*/
		try{

			Constructor<?> con = DragonControllerPhase.class.getDeclaredConstructor(int.class, Class.class, String.class);
			con.setAccessible(true);
			DragonControllerPhase<T> dragonControllerPhase = (DragonControllerPhase<T>) con.newInstance(index++, clazz, phaseName);
			PhaseList.registerPhase(dragonControllerPhase);
			return dragonControllerPhase;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
