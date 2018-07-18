package com.codingforcookies.enderdragoncontrol.phases;

import org.bukkit.Location;

import com.codingforcookies.enderdragoncontrol.IPhase;

/**
 * @author Arnah
 * @since Jul 17, 2018
*/
public interface IPhaseTakeoff extends IPhase{

	/**
	 * @return If the Ender Dragon > 100(of something) it will change to HoldingPattern
	 */
	public Location getTargetLocation();

	public void setTargetLocation(Location location);
}
