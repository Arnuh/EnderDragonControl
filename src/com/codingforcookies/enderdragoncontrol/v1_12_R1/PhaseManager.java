package com.codingforcookies.enderdragoncontrol.v1_12_R1;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.codingforcookies.enderdragoncontrol.EnderDragonControl;
import com.codingforcookies.enderdragoncontrol.IPhase;
import com.codingforcookies.enderdragoncontrol.IPhaseManager;
import com.codingforcookies.enderdragoncontrol.PhaseList;

import net.minecraft.server.v1_12_R1.DragonControllerManager;
import net.minecraft.server.v1_12_R1.DragonControllerPhase;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.IDragonController;

/**
 * Handles setting the phase of the EnderDragon.
 * 
 * @author Arnah
 * @since Jul 8, 2018
 */
public class PhaseManager extends DragonControllerManager implements IPhaseManager{

	// private static final Logger a = LogManager.getLogger();
	private final EntityEnderDragon enderDragon;
	// Have to make this a map since mojang set phases by index and it could change when restarting if multiple plugins registered custom phases.
	private Map<String, IDragonController> dragonPhases = null;
	private IDragonController currentDragonController;

	public PhaseManager(EntityEnderDragon entityenderdragon){
		super(entityenderdragon);
		this.enderDragon = entityenderdragon;// Mojangs makes it private
		this.dragonPhases = new HashMap<>();
	}

	public IPhase getPhase(String phaseName){
		IPhase controller = (IPhase) dragonPhases.get(phaseName);
		if(controller == null){
			Object controllerPhase = PhaseList.getControllerPhase(phaseName);
			if(controllerPhase != null){
				controller = createPhase(controllerPhase);
				dragonPhases.put(phaseName, (IDragonController) controller);
			}else return null;
		}
		return controller;
	}

	public IPhase getCurrentPhase(){
		return (IPhase) currentDragonController;
	}

	/**
	 * @param phase Object implementing IDragonController
	 */
	@Override
	public void setPhase(Object phase){
		if(dragonPhases == null){
			return;
		}
		IDragonController newPhase = (IDragonController) phase;
		if(this.currentDragonController == null || newPhase != this.currentDragonController.getControllerPhase()){
			if(this.currentDragonController != null){
				this.currentDragonController.e();
			}

			String key = ((IPhase) phase).getPhaseName();
			// System.out.println("setPhase: " + key);
			dragonPhases.put(key, newPhase);
			this.currentDragonController = newPhase;
			this.currentDragonController.d();
		}
	}

	public void setControllerPhase(DragonControllerPhase<?> dragoncontrollerphase){
		if(dragonPhases == null){
			// Since we extends mojangs DragonControllerManager the constructor does setControllerPhase which we override.
			// But since we haven't fully finished our constructor it creates NPEs.
			return;
		}
		if(this.currentDragonController == null || dragoncontrollerphase != this.currentDragonController.getControllerPhase()){
			if(this.currentDragonController != null){
				this.currentDragonController.e();// Just a method mojang calls on phase change.
			}
			/*
			// CraftBukkit start - Call EnderDragonChangePhaseEvent
			EnderDragonChangePhaseEvent event = new EnderDragonChangePhaseEvent((CraftEnderDragon) this.enderDragon.getBukkitEntity(), (this.currentDragonController == null) ? null : CraftEnderDragon.getBukkitPhase(this.currentDragonController.getControllerPhase()), CraftEnderDragon.getBukkitPhase(dragoncontrollerphase));
			this.enderDragon.world.getServer().getPluginManager().callEvent(event);
			if(event.isCancelled()){
				return;
			}
			dragoncontrollerphase = CraftEnderDragon.getMinecraftPhase(event.getNewPhase());
			// CraftBukkit end
			*/
			// System.out.println("setControllerPhase: " + dragoncontrollerphase.toString());
			this.currentDragonController = this.b(dragoncontrollerphase);
			if(!this.enderDragon.world.isClientSide){
				this.enderDragon.getDataWatcher().set(EntityEnderDragon.PHASE, Integer.valueOf(dragoncontrollerphase.b()));
			}

			// a.debug("Dragon is now in phase {} on the {}", dragoncontrollerphase, this.enderDragon.world.isClientSide ? "client" : "server");
			this.currentDragonController.d();
		}
	}

	public IDragonController a(){
		return this.currentDragonController;
	}

	@SuppressWarnings("unchecked")
	public <T extends IDragonController> T b(DragonControllerPhase<T> dragoncontrollerphase){
		String key = EnderDragonControl.cleanupNMSPhaseString(dragoncontrollerphase.toString());
		T ret = (T) dragonPhases.get(key);// CraftBukkit - decompile error
		if(ret == null){
			// Creates a instance of the phase for this specific manager/dragon if missing.
			// return (IDragonController) a().newInstance(new Object[] { entityenderdragon});
			ret = (T) dragoncontrollerphase.a(this.enderDragon);
			if(ret.getClass().getName().startsWith("net.minecraft")){
				Object phaseObj = PhaseList.getControllerPhase(key);
				if(phaseObj == null){
					EnderDragonControl.getInstance().getLogger().log(Level.WARNING, "Failed to find a replacement object for phase: " + key);
					phaseObj = BasicPhaseList.HoldingPattern;
				}
				ret = (T) ((DragonControllerPhase<T>) phaseObj).a(enderDragon);
			}else{
				dragonPhases.put(key, ret);
			}
		}

		return ret;
	}

	public IPhase createPhase(Object controllerPhase){
		// This method is for when the user doesn't need any special data set inside the phase.
		String key = EnderDragonControl.cleanupNMSPhaseString(controllerPhase.toString());
		IPhase ret = (IPhase) dragonPhases.get(key);
		if(ret == null){
			try{
				ret = (IPhase) controllerPhase.getClass().getMethod("a", EntityEnderDragon.class).invoke(controllerPhase, enderDragon);
				dragonPhases.put(key, (IDragonController) ret);
			}catch(Exception ex){
				EnderDragonControl.getInstance().getLogger().log(Level.SEVERE, "Failed to create Phase object", ex);
			}
		}
		return ret;
	}
}