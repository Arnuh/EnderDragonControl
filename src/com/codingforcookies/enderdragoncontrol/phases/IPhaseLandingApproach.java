package com.codingforcookies.enderdragoncontrol.phases;

import org.bukkit.Location;

import com.codingforcookies.enderdragoncontrol.IPhase;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public interface IPhaseLandingApproach extends IPhase{

	public Location getLandingLocation();

	public void setLandingLocation(Location location);

	/**
	 * @return If the ender dragon will target a player while landing.
	 */
	public boolean isTargetingPlayers();

	public void setTargetingPlayers(boolean targetPlayers);
}
