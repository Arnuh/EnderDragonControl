package com.codingforcookies.enderdragoncontrol.phases;

import org.bukkit.Location;

import com.codingforcookies.enderdragoncontrol.IPhase;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public interface IPhaseHoldingPattern extends IPhase{

	public Location getPlayerArea();

	/**
	 * @param location X, Y for where the dragon checks for a valid player to "StrafePlayer" phase attack.
	 */
	public void setPlayerArea(Location location);

	public Location getHoldingLocation();

	public void setHoldingLocation(Location location);

	public int getHoldingLocationRadius();

	public void setHoldingLocationRadius(int holdingLocationRadius);
}
