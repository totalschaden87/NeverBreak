package com.github.thebiologist13;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class BowFireListener implements Listener {
	
	private NeverBreak plugin;
	
	public BowFireListener(NeverBreak plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBowFire(EntityShootBowEvent ev) {
		//Player
		Player p = null;
		//Entity
		Entity e = ev.getEntity();
		
		if(e instanceof Player) {
			p = (Player) e;
		} else {
			return;
		}
		
		//Item player has in hand
		ItemStack stack = p.getItemInHand();
		//Items that NeverBreak can be used with
		List<?> items = plugin.getCustomConfig().getList("items");
		//Loop for all items from config
		for(Object o : items) {
			//Make sure that it is specifying data IDs 
			if(o instanceof Integer) {
				//If item in hand matches one from config
				if(stack.getTypeId() == (Integer) o) {
					//If a mode has been set for the player
					if(ToggleCommand.mode.containsKey(p)) {
						//If that mode is true
						if(ToggleCommand.mode.get(p) == true) {
							//Set the item to -128 durability
							stack.setDurability((short) -128);
						//If that mode is false, proceed as normal 
						} else {
							//Unless it was set to REALLY unused, then make the durability 0 again
							if(stack.getDurability() < 0 ) {
								stack.setDurability((short) 0);
							}
						}
					} 
				}
			//Continue if not a data ID
			} else {
				continue;
			}
		}
	}
}
