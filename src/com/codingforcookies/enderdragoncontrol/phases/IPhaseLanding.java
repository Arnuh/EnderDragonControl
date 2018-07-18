package com.codingforcookies.enderdragoncontrol.phases;

import org.bukkit.Location;

import com.codingforcookies.enderdragoncontrol.IPhase;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public interface IPhaseLanding extends IPhase{

	public Location getLandingLocation();

	public void setLandingLocation(Location location);
}
