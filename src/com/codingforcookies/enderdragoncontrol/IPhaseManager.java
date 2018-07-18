package com.codingforcookies.enderdragoncontrol;


/**
 * Manager for the EnderDragon. Controls setting a new phase for the dragon.
 * 
 * @author Arnah
 * @since Jul 17, 2018
 */
public interface IPhaseManager{

	public IPhase getPhase(String phaseName);

	/**
	 * @param phase Object implementing IDragonController
	 */
	public void setPhase(Object dragonController);

	public IPhase getCurrentPhase();

	/**
	 * Creates a DragonController specific to the EnderDragon we are managing.
	 * 
	 * @param obj DragonControllerPhase
	 * @return Object implementing IDragonController.
	 */
	public IPhase createPhase(Object controllerPhase);
}
