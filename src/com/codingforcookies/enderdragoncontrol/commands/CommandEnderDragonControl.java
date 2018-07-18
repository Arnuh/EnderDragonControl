package com.codingforcookies.enderdragoncontrol.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.codingforcookies.enderdragoncontrol.EnderDragonControl;
import com.codingforcookies.enderdragoncontrol.IPhaseManager;
import com.codingforcookies.enderdragoncontrol.phases.IPhaseHoldingPattern;
import com.codingforcookies.enderdragoncontrol.phases.IPhaseLanding;
import com.codingforcookies.enderdragoncontrol.phases.IPhaseLandingApproach;
import com.codingforcookies.enderdragoncontrol.phases.IPhaseTakeoff;

/**
 * @author Arnah
 * @since Jul 8, 2018
*/
public class CommandEnderDragonControl implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			return false;
		}
		Player p = (Player) sender;
		for(Entity e : p.getWorld().getEntities()){
			if(e.getType().equals(EntityType.ENDER_DRAGON)){
				e.remove();
			}
		}
		EnderDragon dragon = (EnderDragon) p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_DRAGON);
		try{
			EnderDragonControl.setCustomMananger(dragon);
			Location loc = new Location(p.getWorld(), 100, 60, 100);
			IPhaseManager manager = EnderDragonControl.getPhaseManager(dragon);
			((IPhaseHoldingPattern) manager.getPhase("HoldingPattern")).setPlayerArea(loc);
			Location high = loc.clone();
			high.setY(120);
			((IPhaseHoldingPattern) manager.getPhase("HoldingPattern")).setHoldingLocation(high);// shit no work
			((IPhaseLanding) manager.getPhase("Landing")).setLandingLocation(high);
			((IPhaseLandingApproach) manager.getPhase("LandingApproach")).setLandingLocation(high);
			((IPhaseLandingApproach) manager.getPhase("LandingApproach")).setTargetingPlayers(true);
			((IPhaseTakeoff) manager.getPhase("Takeoff")).setTargetLocation(high);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return true;
	}

}
