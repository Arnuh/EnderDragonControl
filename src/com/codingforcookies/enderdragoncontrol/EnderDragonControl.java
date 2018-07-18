package com.codingforcookies.enderdragoncontrol;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.entity.EnderDragon;
import org.bukkit.plugin.java.JavaPlugin;

import com.codingforcookies.enderdragoncontrol.commands.CommandEnderDragonControl;

/**
 * @author Arnah
 * @since Jul 8, 2018
*/
public class EnderDragonControl extends JavaPlugin{

	public static String basePackage = "com.codingforcookies.enderdragoncontrol.";
	public static String baseNMSPackage = "net.minecraft.server.";
	private static EnderDragonControl instance;
	private String nmsVersion = null;

	public void onEnable(){
		instance = this;
		try{
			nmsVersion = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			basePackage += nmsVersion;
			baseNMSPackage += nmsVersion;
			Class.forName(basePackage + ".PhaseManager");
			Class.forName(basePackage + ".BasicPhaseList");
		}catch(Exception ex){
			getLogger().log(Level.SEVERE, "Failed to get NMS version and PhaseManager. Unsupported version?", ex);
		}
		getLogger().log(Level.INFO, "Loaded " + PhaseList.getPhaseCount() + " ender dragon phases.");
		getCommand("controltest").setExecutor(new CommandEnderDragonControl());
	}

	public static EnderDragonControl getInstance(){
		return instance;
	}

	/**
	 * Sets the DragonControllerManager to AIDragonControllerManager allowing the API to modify the phases.
	 */
	public static void setCustomMananger(EnderDragon dragon) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException, InstantiationException, ClassNotFoundException{
		Method method = dragon.getClass().getMethod("getHandle");
		method.setAccessible(true);
		Object ent = method.invoke(dragon);

		for(Field field : ent.getClass().getDeclaredFields()){// Overrides mojangs default controller manager.
			if(field.getType().getSimpleName().equals("DragonControllerManager")){
				field.setAccessible(true);
				IPhaseManager manager = (IPhaseManager) Class.forName(basePackage + ".PhaseManager").getConstructors()[0].newInstance(ent);
				field.set(ent, manager);
				// getLogger().log(Level.INFO, "Overrided DragonControllerManager field: " + field.getName() + ".");
				// Sets the phase to the HoldingPattern.
				manager.setPhase(manager.createPhase(PhaseList.getControllerPhase("HoldingPattern")));
				break;
			}
		}
	}

	public static IPhaseManager getPhaseManager(EnderDragon dragon) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException{
		Method method = dragon.getClass().getMethod("getHandle");
		method.setAccessible(true);
		Object ent = method.invoke(dragon);
		for(Field field : ent.getClass().getDeclaredFields()){// Overrides mojangs default controller manager.
			if(field.getType().getSimpleName().equals("DragonControllerManager")){
				field.setAccessible(true);
				IPhaseManager manager = (IPhaseManager) field.get(ent);
				return manager;
			}
		}
		return null;
	}

	public static String cleanupNMSPhaseString(String toStringValue){
		if(toStringValue.contains("(")){
			return toStringValue.substring(0, toStringValue.indexOf("(")).trim();
		}else return toStringValue;
	}
}
