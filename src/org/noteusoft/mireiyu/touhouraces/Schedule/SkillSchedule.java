package org.noteusoft.mireiyu.touhouraces.Schedule;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.noteusoft.mireiyu.touhouraces.THRPlugin;

public class SkillSchedule implements Listener
{
	private THRPlugin plugin;
	public SkillSchedule(THRPlugin plugin)
	{
		this.plugin = plugin;
	}
/*常時タスク*/
	public void onEnable()
	{
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
		{
			private Plugin plugin;
			private String touhouraces;
			public void run() 
			{
/*全て20tick毎*/
				for (World world : Bukkit.getWorlds())
				{
					for (final Player player : Bukkit.getOnlinePlayers())
					{
						if (player.hasMetadata("batman"))
						{
							for (final LivingEntity bat : world.getEntitiesByClass(Bat.class))
							{
								if(bat.hasMetadata("invincible"))
								{
									if (player.getMetadata("batman").get(0).asString().toString().contains(bat.getMetadata("invincible").get(0).asString().toString()))
									{
										plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
										{
											public void run() 
											{
												player.teleport(bat);
												player.setGameMode(GameMode.SURVIVAL);
												MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
												player.setMetadata("using-magic", usingmagic);
												player.removeMetadata("batman", plugin);
												player.sendMessage(touhouraces + ChatColor.RED + "バンプカモフラージュの効果が切れました");
												bat.removeMetadata("invincible", plugin);
												bat.damage(1000);
											}
										}, 100L);
									}
								}
							}
						}
						if (plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") < 100 && player.getMetadata("spilituse").get(0).asDouble() == 0)
						{
							plugin.getConfig().set("user." + player.getUniqueId() + ".spilit", plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") + 5);
							if (player.isSneaking())
							{
								player.sendMessage(touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit"));
							}
						}
						else if (plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") <  100 && player.getMetadata("spilituse").get(0).asDouble() < 0)
						{
							plugin.getConfig().set("user." + player.getUniqueId() + ".spilit", plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") - player.getMetadata("spilituse").get(0).asDouble());
							player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, -1);
							if (player.isSneaking())
							{
								player.sendMessage(touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit"));
							}
						}
						else if (plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") >  0 && player.getMetadata("spilituse").get(0).asDouble() > 0)
						{
							plugin.getConfig().set("user." + player.getUniqueId() + ".spilit", plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") - player.getMetadata("spilituse").get(0).asDouble());
							if (player.isSneaking())
							{
								player.sendMessage(touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit"));
							}
						}
						if (player.hasPermission("thr.skill") )
						{
							if (player.hasMetadata("ignoreskill") == false)
							{
								if(player.hasMetadata("satorin0") && player.isSneaking())
								{
									Player dpl = Bukkit.getPlayer(player.getMetadata("satorin0").get(0).asString());
									if (dpl != null)
									{
										player.sendMessage("名前:" + player.getMetadata("satorin0").get(0).asString());
										player.sendMessage("体力:" + dpl.getHealth());
										player.sendMessage("座標:" + dpl.getLocation().getBlockX() + "," + dpl.getLocation().getBlockY() + "," + dpl.getLocation().getBlockZ());
									}
								}
							}
							/*妖魔の再生ボーナス*/
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("youma") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kappa") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("tenngu"))
							{
								if (player.isDead() == false)
								{
									if (player.getHealth() > player.getMaxHealth() - 2D)
									{
										player.setHealth(player.getMaxHealth());		
									}
									else
									{
										player.setHealth(2D + player.getHealth());									
									}
								}
							}
							/*賢妖のさらなる再生ボーナス*/
							else if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kennyou"))
							{
								if (player.isDead() == false && plugin.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") >=  10 && player.getMetadata("spilituse").get(0).asDouble() > 0)
								{
									if (player.getHealth() > player.getMaxHealth() - 5D)
									{
										player.setHealth(player.getMaxHealth());		
									}
									else
									{
										player.setHealth(5D + player.getHealth());									
									}
								}
								else if (player.isDead() == false)
								{
									player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
								}
							}
							/*全員に再生ボーナス*/
							else
							{
								if (player.isDead() == false)
								{
									if (player.getHealth() > player.getMaxHealth() - 1D)
									{
										player.setHealth(player.getMaxHealth());		
									}
									else
									{
										player.setHealth(1D + player.getHealth());									
									}
								}
							}
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("youma") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kappa") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("tenngu") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kennyou"))
							{
								if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) == false)  player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,2000,0));
							}
				
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("akuma")  || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kyuuketuki")  || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("oni"))
							{

								if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION) == false)  player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,2000,1));
							}
				
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("yousei") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("satori") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kobito") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kibito"))
							{
								if (player.hasPotionEffect(PotionEffectType.JUMP) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,2000,1));
							}
				
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("youzyuu") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("siki") || plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("ninngyo"))
							{
								if (player.hasPotionEffect(PotionEffectType.SPEED) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,2000,0));
							}
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin") && player.getWorld().getTime() < 15000)
							{
								if (player.hasPotionEffect(PotionEffectType.SPEED) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,2000,0));
							}
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin") && player.getWorld().getTime() >= 15000 && player.getWorld().getTime() < 15020)
							{
								player.sendMessage(touhouraces + ChatColor.RED + "あなたは獣の血を呼び覚ました！！");
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.NIGHT_VISION);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);	
								player.playSound(player.getLocation(), Sound.WOLF_DEATH, 1, 0);
							}
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin") && player.getWorld().getTime() >= 15000)
							{
								if (player.hasPotionEffect(PotionEffectType.SPEED) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,2000,1));			
								if (player.hasPotionEffect(PotionEffectType.JUMP) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,2000,2));
								if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,2000,0));
								if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,2000,0));
							}

							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin") && player.getWorld().getTime() >= 15000 && player.getWorld().getTime() < 15020)
							{
								player.removePotionEffect(PotionEffectType.SLOW);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);	
								player.sendMessage(touhouraces + ChatColor.DARK_RED + "あなたは紅い月の力で覚醒する！！");
								player.playSound(player.getLocation(), Sound.BAT_HURT, 1, 0);
							}
							if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kyuuketuki") && player.getWorld().getTime() >= 15000)
							{
								if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,2000,1));
								if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,2000,1));
								if (player.hasPotionEffect(PotionEffectType.REGENERATION) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,2000,1));
								if (player.hasPotionEffect(PotionEffectType.SPEED) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,2000,0));
							}
							else if (plugin.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kyuuketuki"))
							{
								if (player.hasPotionEffect(PotionEffectType.SLOW) == false) player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,2000,0));
							}

						}
					}
					for (final Bat bat : world.getEntitiesByClass(Bat.class))
					{
						if(bat.hasMetadata("invincible"))
						{
							List<Entity> entityforsyugorei = bat.getNearbyEntities(20, 20, 20);
							for (Entity entity : entityforsyugorei)		
							{
								if (entity instanceof Player )
								{
									bat.setVelocity(bat.getVelocity().add(new Vector(new Double(20 - (bat.getLocation().getX() - entity.getLocation().getX())) / 16,0,new Double(20 - (bat.getLocation().getZ() - entity.getLocation().getZ())) / 16)));
									break;
								}
							}
						}
					}
					for (final Snowman snowman : world.getEntitiesByClass(Snowman.class))
					{
						if(snowman.hasMetadata("syugoreisnow"))
						{
							if(snowman.hasMetadata("syugoreitarget"))
							{
								List<Entity> entityforsyugorei = snowman.getNearbyEntities(30D, 30D, 30D);
								for (Entity entity : entityforsyugorei)		
								{
									if (entity instanceof Player )
									{
										if (((Player)entity).getName().toString().contains(snowman.getMetadata("syugoreitarget").get(0).asString()) == false)
										{
											snowman.setTarget((LivingEntity) entity);
											break;
										}
									}
								}
							}
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
							{
								public void run() 
								{
									snowman.damage(1000);
								}
							}, 300L);
						}
					}
					for (final IronGolem irongolem : world.getEntitiesByClass(IronGolem.class))
					{
						if(irongolem.hasMetadata("syugoreiiron"))
						{
							if(irongolem.hasMetadata("syugoreitarget"))
							{
								if(irongolem.getMetadata("syugoreitarget").get(0) != null)
								{
									List<Entity> entityforsyugorei = irongolem.getNearbyEntities(30D, 30D, 30D);
									for (Entity entity : entityforsyugorei)		
									{
										if (entity instanceof Player )
										{
											if (((Player)entity).getName().toString().contains(irongolem.getMetadata("syugoreitarget").get(0).asString()) == false)
											{
												irongolem.setTarget((LivingEntity) entity);
												break;
											}
										}
									}
								}
							}
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
							{
								public void run() 
								{
									irongolem.damage(1000);
								}
							}, 300L);
						}
					}
					for (final Wolf wolf : world.getEntitiesByClass(Wolf.class))
					{
						if(wolf.hasMetadata("tamedwolf"))
						{
							if(wolf.hasMetadata("wolfowner"))
							{
								String owner = wolf.getMetadata("wolfowner").get(0).asString();
								for (Entity enemy :  wolf.getNearbyEntities(20D,20D,20D))
								{
									if (enemy instanceof LivingEntity)
									{
										if (enemy instanceof Player)
										{
											if ((((Player)enemy).getUniqueId() + "").toString().contains(owner) == false)
											{
												wolf.setTarget((LivingEntity) enemy);
												break;
											}
										}
										else if (enemy instanceof Wolf == false)
										{
											wolf.setTarget((LivingEntity) enemy);
											break;
										}
									}
								}
							}
						}
					}
					for (final Ocelot cat : world.getEntitiesByClass(Ocelot.class))
					{
						if(cat.hasMetadata("tamedcat"))
						{
							if(cat.hasMetadata("catowner"))
							{
								String owner = cat.getMetadata("catowner").get(0).asString();
								for (Entity enemy :  cat.getNearbyEntities(25D,25D, 25D))
								{
									if (enemy instanceof LivingEntity)
									{
										if (enemy instanceof Player)
										{
											if ((((Player)enemy).getUniqueId() + "").toString().contains(owner) == false)
											{
												cat.setTarget((LivingEntity) enemy);
												cat.teleport((LivingEntity) enemy);
												break;
											}
										}
										else if (enemy instanceof Ocelot == false)
										{
											cat.setTarget((LivingEntity) enemy);
											cat.teleport((LivingEntity) enemy);
											break;
										}
									}
								}
							}
						}
					}
				}
			plugin.saveConfig();
			}
		}, 0L, 20L);
	}
}  