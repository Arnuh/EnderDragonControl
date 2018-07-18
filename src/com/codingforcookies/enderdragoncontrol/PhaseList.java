package com.codingforcookies.enderdragoncontrol;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


/**
 * Handles storing custom DragonControllerPhases for EnderDragons.
 * 
 * @author Arnah
 * @since Jul 17, 2018
 */
public class PhaseList{

	private static List<Object> phases = new ArrayList<>();

	public static void registerPhase(Object phase) {
		if(!phase.getClass().getName().startsWith("net.minecraft")) {
			// Only want DragonControllerPhase instances
			EnderDragonControl.getInstance().getLogger().log(Level.WARNING, "Called registerPhase on a class that isn't in NMS.");
			return;
		}
		phases.add(phase);
	}

	/**
	 * @return instance of DragonControllerPhase(nms)
	 */
	public static Object getControllerPhase(String phaseName){
		for(Object ob : phases){
			if(getPhaseName(ob).equalsIgnoreCase(phaseName)) return ob;
		}
		return null;
	}

	public static String getPhaseName(Object phase){
		try{
			for(Field f : phase.getClass().getDeclaredFields()){
				if(f.getType().isAssignableFrom(String.class)){
					f.setAccessible(true);
					return (String) f.get(phase);
				}
			}
		}catch(Exception ex){
			EnderDragonControl.getInstance().getLogger().log(Level.SEVERE, "Failed to grab phase name for " + phase.getClass().getName(), ex);
		}
		return null;
	}

	public static int getPhaseCount(){
		return phases.size();
	}
}
