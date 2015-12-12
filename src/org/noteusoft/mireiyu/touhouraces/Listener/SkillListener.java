package org.noteusoft.mireiyu.touhouraces.Listener;

import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.MathHelper;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.Vector;
import org.noteusoft.mireiyu.touhouraces.THRPlugin;

public class SkillListener implements Listener
{
	private THRPlugin plugin;
	private SimpleClans sc;
	@SuppressWarnings("unused")
	private CSDirector cs;
	
	public SkillListener(THRPlugin plugin)
	{
		this.plugin = plugin;
	}
/*チャット*/
	@EventHandler(priority = EventPriority.LOW)
	public void chatlegend(final AsyncPlayerChatEvent event)
	{
		Player pl = event.getPlayer();
		String format = event.getFormat();
		if (this.plugin.getConfig().contains("user." + pl.getUniqueId()) == true)
		{
			boolean existrace = false;
			String inforace = "";
			for (String race : this.plugin.getConfig().getConfigurationSection("race").getKeys(false))
			{
				if (race.toLowerCase().contains(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race")))
				{
					existrace = true;
					inforace = race;
					break;
				}
			}
			if (existrace)
			{
					String race = this.plugin.getConfig().getString("race." + inforace +  ".display.tag");
					event.setFormat("§f[" + race + "§f]" + format);
			}
			else
			{
				String race = this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race");
				event.setFormat("§f[" + race + "§f]" + format);
			}
		}
	}
/*以下技設定*/
/*退出した時*/
	@EventHandler
	public void on_quit(final PlayerQuitEvent event)
	{
		Player pl = event.getPlayer();
/*バンプカモフラージュ*/
		for (final LivingEntity bat :pl.getWorld().getEntitiesByClass(Bat.class))
		{
			if(bat.hasMetadata("invincible"))
			{
				if(pl.hasMetadata("batman"))
				{
					if (pl.getMetadata("batman").get(0).asString().toString().contains(bat.getMetadata("invincible").get(0).asString().toString()))
					{
					bat.removeMetadata("invincible" , this.plugin);
					bat.damage(2000D);
					}
				}
			}
		}
/*メタリセット*/
		if(pl.hasMetadata("batman"))
		  {
			pl.removeMetadata("batman", this.plugin);
		  }
		if(pl.hasMetadata("casting"))
		  {
			pl.removeMetadata("casting", this.plugin);
		  }
		if(pl.hasMetadata("using-magic"))
		  {
			pl.removeMetadata("using-magic", this.plugin);
		  }
		if(pl.hasMetadata("satorin0"))
		  {
			pl.removeMetadata("satorin0", this.plugin);
		  }
		if(pl.getGameMode() == GameMode.SPECTATOR)
		{
			pl.setGameMode(GameMode.SURVIVAL);
		}
	}

/*参加した時*/
	@EventHandler
	public void on_join(final PlayerJoinEvent event)
	{
		Player pl = event.getPlayer();
/*メタ初期付与*/
		  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
		  pl.setMetadata("casting", casted);
		  MetadataValue usingmagic = new FixedMetadataValue(this.plugin, false) ;
		  pl.setMetadata("using-magic", usingmagic);
		  MetadataValue spilituse = new FixedMetadataValue(this.plugin, 0) ;
		  pl.setMetadata("spilituse", spilituse);
/*新規登録*/
	      if (this.plugin.getConfig().contains("user." + pl.getUniqueId()) == false){
	    	  this.plugin.getConfig().set("user." + pl.getUniqueId() + ".name" , pl.getName());
	    	  this.plugin.getConfig().set("user." + pl.getUniqueId() + ".point" , 0);
	    	  this.plugin.getConfig().set("user." + pl.getUniqueId() + ".race" , "kedama");
	    	  this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", 0);
		      this.plugin.saveConfig();
	      }

    	 this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", 0);
	     this.plugin.saveConfig();
	}
/*リスポーンしたとき*/
 	@EventHandler
 	public void on_respawn(final PlayerRespawnEvent event)
 	{
			Player pl = event.getPlayer();
/*妖魔の体力は120に増加*/
		if(pl.hasPermission("thr.skill") )
		{
			if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youma") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu"))
			{
				pl.setMaxHealth(120D);
			}
/*賢妖の体力は150に増加*/
			else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kennyou"))
			{
				pl.setMaxHealth(150D);
			}
/*他の種族の体力は100に増加*/
			else
			{
				pl.setMaxHealth(100D);
			}
		}
	}
/*プレイヤーが動く時*/
	@EventHandler
	public void on_move(final PlayerMoveEvent event)
	{
		if (event.getPlayer().hasMetadata("batman"))
		{
			event.setCancelled(true);
		}
		Player pl = event.getPlayer();
		if(pl.hasPermission("thr.skill") )
		{
/*人魚の泳ぎ*/
			if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo"))
			{
				if (pl.getLocation().getBlock().getType() == Material.WATER || pl.getLocation().getBlock().getType() == Material.STATIONARY_WATER)
				{
					if (pl.isSneaking() == false && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 40D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
					pl.setVelocity(pl.getLocation().getDirection().multiply(0.7D));
					}
				}
			}
		}
	}
/*プレイヤーがしゃがみトグルした時*/
	@SuppressWarnings("deprecation")
	@EventHandler
	public void on_sneaktoggle(final PlayerToggleSneakEvent event)
	{
		Player pl = event.getPlayer() ;
		if(pl.hasMetadata("ignoreskill") == false && pl.hasPermission("thr.skill") )
			{ 
/*youseiは霊力消費で空中ダブルブースト*/
				if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito"))
				{
					if (pl.isOnGround() == false && pl.isSneaking() == true && this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20)
					{
						pl.setVelocity(pl.getLocation().getDirection().multiply(1.1D));
						pl.getWorld().playSound(pl.getLocation(), Sound.SHOOT_ARROW, 1, 1);
						pl.getWorld().playEffect(pl.getLocation(), Effect.TILE_DUST, 133, 1);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 5);
					    this.plugin.saveConfig();
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
				}
/*tennguは霊力消費で空中超速ブースト*/
				else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu"))
				{
					if (pl.isOnGround() == false && pl.isSneaking() == true && this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30)
					{
						pl.setVelocity(pl.getLocation().getDirection().multiply(15.0D));
						pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
						pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 0);
						pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1, -1);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 40);
					    this.plugin.saveConfig();
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
				}
/*仙人は１ブロックの壁抜けを容易にできる*/
				if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennnin"))
				{
					if (pl.isOnGround() == false && pl.isSneaking() && this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20)
					{
						float pitch = pl.getLocation().getPitch();
						float yaw = pl.getLocation().getYaw();
						Location warploc = new Location (pl.getWorld(),pl.getLocation().getX() + pl.getLocation().getDirection().getX() * 2,pl.getLocation().getY() + pl.getLocation().getDirection().getY() * 2,pl.getLocation().getZ() + pl.getLocation().getDirection().getZ() * 2);
						if (pl.getWorld().getBlockAt(warploc).getType() != Material.AIR)
						{
							pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_HIT, 2, 0);
						}
						else
						{
						pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
						pl.getWorld().playEffect(pl.getLocation(), Effect.COLOURED_DUST, 1, 5);
						warploc.setPitch(pitch);
						warploc.setYaw(yaw);
						pl.teleport(warploc);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
					    this.plugin.saveConfig();
						}
					}
				}
			}
		}
/*プレイヤーがクリックした時*/
		@SuppressWarnings("deprecation")
		@EventHandler
		public void on_click(final PlayerInteractEvent event)
		{
			Player pl = event.getPlayer() ;
/*種族Pショップ ダークプリズマイン*/
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.PRISMARINE)
			{
				boolean existrace = false;
				String useshopname = "";
				for (String shopname : this.plugin.getConfig().getConfigurationSection("rankshop").getKeys(false))
				{
					if (this.plugin.getConfig().getString("rankshop." + shopname.toLowerCase() + ".world").contains(event.getClickedBlock().getLocation().getWorld().getName()))
					{
						if (this.plugin.getConfig().getInt("rankshop." + shopname.toLowerCase() + ".vector.x") == event.getClickedBlock().getLocation().getBlockX() && this.plugin.getConfig().getInt("rankshop." + shopname.toLowerCase() + ".vector.y") == event.getClickedBlock().getLocation().getBlockY() && this.plugin.getConfig().getInt("rankshop." + shopname.toLowerCase() + ".vector.z") == event.getClickedBlock().getLocation().getBlockZ())
						{
							existrace = true;
							useshopname = shopname;
							break;
						}
					}
				}
				if (existrace)
				{
					Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective("faith");
					int cost_faith = this.plugin.getConfig().getInt("rankshop." + useshopname + ".require.faith");
					int cost_racepoint = this.plugin.getConfig().getInt("rankshop." + useshopname + ".require.racepoint");
					ItemStack cost_item = new ItemStack(Material.getMaterial(this.plugin.getConfig().getInt("rankshop." + useshopname + ".require.item.typeid")) , this.plugin.getConfig().getInt("rankshop." + useshopname + ".require.item.amount"));
					cost_item.setDurability((short) this.plugin.getConfig().getInt("rankshop." + useshopname + ".require.item.meta"));
/*正しいアイテムスタックでなければ動作しない。*/
					if (objective.getScore(pl.getPlayer()).getScore() >= cost_faith && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point") >= cost_racepoint && pl.getInventory().contains(cost_item))
					{
						objective.getScore(pl.getPlayer()).setScore(objective.getScore(pl.getPlayer()).getScore() - cost_faith);
						pl.getInventory().remove(cost_item);
						
						ItemStack buy_item = new ItemStack(Material.getMaterial(this.plugin.getConfig().getInt("rankshop." + useshopname + ".buyitem.typeid")) , this.plugin.getConfig().getInt("rankshop." + useshopname + ".buyitem.amount"));
						buy_item.setDurability((short) this.plugin.getConfig().getInt("rankshop." + useshopname + ".buyitem.meta"));
						pl.getInventory().addItem(buy_item);
						pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + cost_faith + "の信仰と" + ChatColor.GOLD + cost_racepoint + "の種族ポイントと" + cost_item + "のアイテムを消費を消費して" + ChatColor.GREEN + buy_item.getAmount() + "個の" + buy_item.getType().name() + "(メタ:" + buy_item.getDurability() + ")を手に入れた！");
					}
					else if (objective.getScore(pl.getPlayer()).getScore() < cost_faith) pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + cost_faith + "分の信仰を持っていません！");
					else if (this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point") < cost_racepoint) pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + cost_racepoint + "分の種族ポイントを持っていません！");
					else if (pl.getInventory().contains(cost_item) == false) pl.sendMessage(cost_item.getAmount() + "個の" + cost_item.getType().name() + "(メタ:" + cost_item.getDurability() + ")がありません！！");
				}
			}
/*以下アイテムクリック動作集*/
			if (pl.hasPermission("thr.skill") && pl.hasMetadata("ignoreskill") == false)
			{
/*左クリック*/
				if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
				{
/*妖獣は霊力消費で弓、釣り竿を使い能力強化or狼や猫召喚*/
					if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youzyuu") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("siki") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("zyuuzin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo"))
					{
						if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() )
						{
							Material other_is_ok = pl.getItemInHand().getType() ; 
							if (other_is_ok == Material.FISHING_ROD || other_is_ok == Material.BOW || other_is_ok == Material.ARROW)
							{
								if (pl.getMetadata("casting").get(0).asBoolean() == true)
									{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
									}
								else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
								{
										pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
								}
								else
									{
									if (pl.getItemInHand().getType() == Material.FISHING_ROD)
									{
										if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("siki"))
										{
											MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
											pl.setMetadata("casting", casting);
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "愛くるしい猫を呼び出す、ニャア！！");
											pl.getWorld().playSound(pl.getLocation(), Sound.CAT_MEOW, 4, -1);
											this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
												private Plugin plugin;
												private String touhouraces;
												public void run() {
													  Player pl = event.getPlayer();
													  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
													  pl.setMetadata("casting", casted);
														  int n = 0;
														  while (n < 3)
														  {
															  Entity cat = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.OCELOT);
																MetadataValue tamedcat = new FixedMetadataValue(this.plugin, true) ;
																cat.setMetadata("tamedcat", tamedcat);
																MetadataValue catowner = new FixedMetadataValue(this.plugin, pl.getUniqueId() + "") ;
																cat.setMetadata("catowner", catowner);
																n ++ ;
														  }
														  pl.getWorld().playSound(pl.getLocation(), Sound.CAT_PURREOW , 1, 1);
														  pl.sendMessage(touhouraces + ChatColor.GOLD + "「ニャア」「ニャア」「ニャア」");
													}
												} , 40L);
											this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
										    this.plugin.saveConfig();
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
										}
										else
										{
										MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("casting", casting);
										pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "狼の群れを呼び出す ウオオオーン！！");
										pl.getWorld().playSound(pl.getLocation(), Sound.WOLF_WHINE, 4, -1);
										pl.getWorld().playEffect(pl.getLocation(), Effect.BLAZE_SHOOT, 1, 1);
										this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
											private Plugin plugin;
											private String touhouraces;
											public void run() {
												  Player pl = event.getPlayer();
												  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
												  pl.setMetadata("casting", casted);
													  int n = 0;
													  while (n < 3)
													  {
														  Entity wolf = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.WOLF);
															MetadataValue tamedwolf = new FixedMetadataValue(this.plugin, true) ;
															wolf.setMetadata("tamedwolf", tamedwolf);
															MetadataValue wolfowner = new FixedMetadataValue(this.plugin, pl.getUniqueId() + "") ;
															wolf.setMetadata("wolfowner", wolfowner);
															n ++ ;
													  }
													  pl.getWorld().playSound(pl.getLocation(), Sound.WOLF_BARK, 1, 1);
													  pl.sendMessage(touhouraces + ChatColor.GOLD + "「ウオン」「ウオン」「ウオン」");
												}
											} , 40L);
										this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
									    this.plugin.saveConfig();
										pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
										}
									}
									else
									{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力で自身の能力増強を図った！");
									pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 1);
									pl.getWorld().playEffect(pl.getLocation(), Effect.MOBSPAWNER_FLAMES ,1, 1);
									double ram = Math.random();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + ram);
									if (ram < 0.1D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "失敗！");
								
										}
									else if (ram < 0.2D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "移動速度がさらに上がった！");
											pl.removePotionEffect(PotionEffectType.SPEED);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,2));
							
										}
									else if (ram < 0.3D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "跳躍力が上がった！");
											pl.removePotionEffect(PotionEffectType.JUMP);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,300,2));
								
										}
									else if (ram < 0.4D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "振りの速さが上がった！");
											pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,250,2));
						
										}
									else if (ram < 0.5D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.LIGHT_PURPLE + "軽い再生能力を得た！");
											pl.removePotionEffect(PotionEffectType.REGENERATION);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,300,1));
		
										}
									else if (ram < 0.6D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "マゾい精神を得た！？");
											pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,250,2));
		
										}
									else if (ram < 0.7D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "サゾい精神を得た！？");
											pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,250,1));
			
										}
									else if (ram < 0.8D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "天空の力を得た！");
											if (pl.getWorld().isThundering())
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "天は雷光の如き力を授けて下さった！");
												pl.removePotionEffect(PotionEffectType.SPEED);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,2));
												pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,200,1));
												pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,200,2));
											}
											else if (pl.getWorld().hasStorm())
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_BLUE + "天は雨の尊い恵みを授けて下さった");
												pl.removePotionEffect(PotionEffectType.JUMP);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,200,2));
												pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,200,1));
												pl.removePotionEffect(PotionEffectType.REGENERATION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,1));
											}
											else
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "天は晴天の輝く強さを授けて下さった！");
												pl.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,400,0));
												pl.removePotionEffect(PotionEffectType.HEAL);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,0,30));
												pl.removePotionEffect(PotionEffectType.NIGHT_VISION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,400,0));
											}
		
										}
									else if (ram < 0.9D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.UNDERLINE + "時の力を得た！");
											if (pl.getWorld().getTime() < 14000)
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "太陽のエナジーは貴方にすざましき再生力を与える！");
												pl.removePotionEffect(PotionEffectType.REGENERATION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,4));
											}
											else
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "月の煌きはあなたの心を狂わすであろう！");
												pl.removePotionEffect(PotionEffectType.CONFUSION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,200,1));
											}
										}
									else if (ram < 1.0D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "失敗！");
		
										}
									else
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "失敗！");
		
										}
									MetadataValue casting = new FixedMetadataValue(this.plugin, false) ;
									pl.setMetadata("casting", casting);
									MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("using-magic", usingmagic);
									this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
									private Plugin plugin;
									private String touhouraces;
									public void run() {
										  Player pl = event.getPlayer();
										  MetadataValue usingmagic = new FixedMetadataValue(this.plugin, false) ;
										  pl.setMetadata("using-magic", usingmagic);
										  pl.sendMessage(touhouraces + ChatColor.RED + "詠唱クールダウンが解けました");
										}
									} , 300L);
									this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
								    this.plugin.saveConfig();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
									}
								}
							}
						}
					}
				}
/*右クリック*/
			else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
/*霊力消費・充電装置*/
				Material dust_is_ok = pl.getItemInHand().getType() ; 
				if (pl.isSneaking() )
				{
					if (pl.getMetadata("spilituse").get(0).asDouble() != 0)
					{
						 MetadataValue spilituse = new FixedMetadataValue(this.plugin, 0) ;
						 pl.setMetadata("spilituse", spilituse);
						 pl.sendMessage(this.plugin.touhouraces + ChatColor.WHITE + "霊力ノーマル");
					}
					else
					{
						if (dust_is_ok == Material.SUGAR)
						{
							  MetadataValue spilituse = new FixedMetadataValue(this.plugin, 5) ;
							  pl.setMetadata("spilituse", spilituse);
							  pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "霊力消費小");
						}
						else if (dust_is_ok == Material.SULPHUR)
						{
							  MetadataValue spilituse = new FixedMetadataValue(this.plugin, 15) ;
							  pl.setMetadata("spilituse", spilituse);
							  pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_GRAY + "霊力消費大");
						}
						else if (dust_is_ok == Material.GLOWSTONE_DUST)
						{
							  MetadataValue spilituse = new FixedMetadataValue(this.plugin, -10) ;
							  pl.setMetadata("spilituse", spilituse);
							  pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "霊力回復中");
						}
					}
				}
/*魔女は霊力消費で剣を使った各種魔法*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("mazyo"))
				{
					Material sword_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() ){
						if (sword_is_ok == Material.WOOD_SWORD || sword_is_ok == Material.STONE_SWORD || sword_is_ok == Material.IRON_SWORD || sword_is_ok == Material.DIAMOND_SWORD || sword_is_ok == Material.GOLD_SWORD)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "剣を構えた！");
							pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 1, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									if (pl.getItemInHand().getType() == Material.WOOD_SWORD)
									{
											pl.sendMessage(touhouraces + ChatColor.YELLOW + "土の魔法を唱えた！");
											pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.PISTON_EXTEND, 1, -1);
											List<Entity> enemys=pl.getNearbyEntities(12D, 12D, 12D);
											for (Entity enemy : enemys)
											{
												if (enemy instanceof LivingEntity && enemy.isOnGround())
												{
													((LivingEntity) enemy).damage(25D);
													enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HORSE_HIT, 1, 0);
												}
											}
											MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
											pl.setMetadata("using-magic", usingmagic);
											plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
											{
													public void run() 
												{
												  Player pl = event.getPlayer();
												  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
												  pl.setMetadata("using-magic", usingmagic);
												  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
												}
											}
											, 60L);
									}
									else if  (pl.getItemInHand().getType() == Material.STONE_SWORD)
									{
										pl.sendMessage(touhouraces + ChatColor.GREEN + "風の魔法を唱えた！");
										pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
										pl.setVelocity(pl.getVelocity().add(new Vector(0.5D,3D,0.5D)));
										pl.setFallDistance(-40F);
										MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("using-magic", usingmagic);
										this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
										{
												public void run() 
											{
											  Player pl = event.getPlayer();
											  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
											  pl.setMetadata("using-magic", usingmagic);
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
											}
										}
										, 60L);
									}
									else if  (pl.getItemInHand().getType() == Material.IRON_SWORD)
									{
										pl.sendMessage(touhouraces + ChatColor.RED + "火の魔法を唱えた！");
										pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.FIRE, 1, 0);
										 Location location=pl.getEyeLocation();
										  float pitch=location.getPitch() / 180.0F * 3.1415927F;
										  float yaw=location.getYaw() / 180.0F * 3.1415927F ;
										  double motX=-MathHelper.sin(yaw) * MathHelper.cos(pitch);
										  double motZ=MathHelper.cos(yaw) * MathHelper.cos(pitch);
										  double motY=-MathHelper.sin(pitch);
										  Vector velocity=new Vector(motX,motY,motZ).multiply(2D);
										  Snowball snowball=pl.throwSnowball();
										  MetadataValue shooter = new FixedMetadataValue(this.plugin, pl.getUniqueId().toString()) ;
										  snowball.setMetadata("mazyo-fireball", shooter);
										  snowball.setVelocity(velocity);
										  snowball.setFireTicks(300);
										MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("using-magic", usingmagic);
										this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
										{
												public void run() 
											{
											  Player pl = event.getPlayer();
											  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
											  pl.setMetadata("using-magic", usingmagic);
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
											}
										}
										, 5L);
									}
									else if  (pl.getItemInHand().getType() == Material.DIAMOND_SWORD)
									{
										pl.sendMessage(touhouraces + ChatColor.GREEN + "水の魔法を唱えた！");
										pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.MAGMACUBE_JUMP, 1, 0);
										List<Entity> enemys=pl.getNearbyEntities(8D, 8D, 8D);
										enemys.add(pl);
										for (Entity enemy : enemys)
										{
											if (enemy instanceof LivingEntity && enemy.isDead() == false)
											{
												if (((LivingEntity) enemy).getHealth() + 20D > ((LivingEntity) enemy).getMaxHealth()) 
												{
													((LivingEntity) enemy).setHealth(((LivingEntity) enemy).getMaxHealth());
													enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.LEVEL_UP, 1, 2);
												}
												else
												{
												((LivingEntity) enemy).setHealth(((LivingEntity) enemy).getHealth() + 20D);
												enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.LEVEL_UP, 1, 2);
												}
											}
										}
										MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("using-magic", usingmagic);
										this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
										{
												public void run() 
											{
											  Player pl = event.getPlayer();
											  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
											  pl.setMetadata("using-magic", usingmagic);
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
											}
										}
										, 180L);
									}
									else
									{
										pl.sendMessage(touhouraces + ChatColor.DARK_PURPLE + "雷の魔法を唱えた！");
										Entity lightning1 = pl.getWorld().spawnEntity(pl.getLocation().add(7D,0,0), EntityType.LIGHTNING);
										Entity lightning2 = pl.getWorld().spawnEntity(pl.getLocation().add(-7D,0,0), EntityType.LIGHTNING);
										Entity lightning3 = pl.getWorld().spawnEntity(pl.getLocation().add(0,0,7D), EntityType.LIGHTNING);
										Entity lightning4 = pl.getWorld().spawnEntity(pl.getLocation().add(0,0,-7D), EntityType.LIGHTNING);
										MetadataValue lightningeffect = new FixedMetadataValue(this.plugin, 15D) ;
										lightning1.setMetadata("lightningeffect", lightningeffect);
										lightning2.setMetadata("lightningeffect", lightningeffect);
										lightning3.setMetadata("lightningeffect", lightningeffect);
										lightning4.setMetadata("lightningeffect", lightningeffect);
										
										MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("using-magic", usingmagic);
										this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
										{
												public void run() 
											{
											  Player pl = event.getPlayer();
											  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
											  pl.setMetadata("using-magic", usingmagic);
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
											}
										}
										, 180L);
									}
									  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
									  pl.setMetadata("casting", casted);
									}
								} , 40L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*人間共通・棒で自己回復*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngen") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("mazyo")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houraizin")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("gennzinnsin")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sibito")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennnin"))
				{
					Material sword_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20 && pl.isSneaking() ){
						if (sword_is_ok == Material.STICK)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "棒を構えた！");
							pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 1, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
							{
								private Plugin plugin;
								private String touhouraces;
								public void run() 
								{
									Player pl = event.getPlayer() ;
									pl.sendMessage(touhouraces + ChatColor.YELLOW + "自己治癒を使った！");
									pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ORB_PICKUP, 1, 1);
									if (pl.getHealth() + 10D > pl.getMaxHealth()) 
									{
										pl.setHealth(pl.getMaxHealth());
										pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP, 1, 2);
									}
									else
									{
										pl.setHealth(pl.getHealth() + 1D);
										pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP, 1, 2);
									}
									MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
									pl.setMetadata("using-magic", usingmagic);
									plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
									{
											public void run() 
										{
										  Player pl = event.getPlayer();
										  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
										  pl.setMetadata("using-magic", usingmagic);
										  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
										}
									}, 20L);
									MetadataValue casted = new FixedMetadataValue(plugin, false) ;
									pl.setMetadata("casting", casted);
								}
							} , 80L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 15);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*妖魔は霊力消費で斧を使った打ち上げ＋吹き飛ばし*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youma") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kennyou"))
				{
					Material axe_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20 && pl.isSneaking() ){
						if (axe_is_ok == Material.WOOD_AXE || axe_is_ok == Material.STONE_AXE || axe_is_ok == Material.IRON_AXE || axe_is_ok == Material.DIAMOND_AXE || axe_is_ok == Material.GOLD_AXE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "斧を構えた！");
							pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_IDLE, 1, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									if (pl.getItemInHand().getType() == Material.GOLD_AXE)
									{
											pl.sendMessage(touhouraces + ChatColor.DARK_GREEN + "金の斧で全てを吹き飛ばす！");
											pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 2, 0);
											pl.getWorld().playSound(pl.getLocation(), Sound.EXPLODE, 2, 0);
											pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1, 1);
											List<Entity> enemys=pl.getNearbyEntities(7D, 7D, 7D);
											for (Entity enemy : enemys)
											{
												if (enemy instanceof LivingEntity)
												{
													enemy.setVelocity(enemy.getVelocity().add(new Vector(new Double((enemy.getLocation().getX() - pl.getLocation().getX())),0,new Double((enemy.getLocation().getZ() - pl.getLocation().getZ())))));
													enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1, 1);
												}
											}
									}
/*河童は霊力消費で石斧を使った爆弾投げ*/
									else if (plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa") && pl.getItemInHand().getType() == Material.STONE_AXE)
									{
										pl.sendMessage(touhouraces + ChatColor.GREEN + "石の斧でTNTを投げた！");
										pl.getWorld().playSound(pl.getLocation(), Sound.FIRE_IGNITE, 1, 0);
										 Location location=pl.getEyeLocation();
										  float pitch=location.getPitch() / 180.0F * 3.1415927F;
										  float yaw=location.getYaw() / 180.0F * 3.1415927F ;
										  double motX=-MathHelper.sin(yaw) * MathHelper.cos(pitch);
										  double motZ=MathHelper.cos(yaw) * MathHelper.cos(pitch);
										  double motY=-MathHelper.sin(pitch);
										  Vector velocity=new Vector(motX,motY,motZ).multiply(1D);
										  TNTPrimed tnt = (TNTPrimed) pl.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
										  MetadataValue shooter = new FixedMetadataValue(this.plugin, pl.getUniqueId().toString()) ;
										  tnt.setMetadata("tnt", shooter);
										  tnt.setVelocity(velocity);
										  tnt.setIsIncendiary(true);
										  tnt.setFireTicks(20);							  
									}
									else
										{
											pl.sendMessage(touhouraces + ChatColor.GREEN + "斧で地面を叩き上げた！");
											pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 2, 0);
											pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
											List<Entity> enemys=pl.getNearbyEntities(7D, 7D, 7D);
											for (Entity enemy : enemys)
											{
												if (enemy instanceof LivingEntity)
												{
													enemy.setVelocity(enemy.getVelocity().add(new Vector(0,1.5D,0)));
													enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HURT_FLESH, 1, 0);
												}
											}
										}
									  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
									  pl.setMetadata("casting", casted);
									}
								} , 20L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 15);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*kyuuketukiは霊力消費で蝙蝠カモフラージュ*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					Material pickel_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() ){
						if (pickel_is_ok == Material.STONE_PICKAXE)
						{
						if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
							}
						else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
						{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
						}
						else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "バンプカモフラージュを唱えた！");
							pl.getWorld().playSound(pl.getLocation(), Sound.BAT_IDLE, 1, 0);
							pl.getWorld().playEffect(pl.getLocation(), Effect.SMOKE, 1, 1);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									  Player pl = event.getPlayer();
									  MetadataValue casted = new FixedMetadataValue(plugin, false) ;
									  MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
									  pl.setMetadata("casting", casted);
									  MetadataValue batman = new FixedMetadataValue(plugin, pl.getUniqueId()) ;
									  pl.setMetadata("batman", batman);
									  pl.setGameMode(GameMode.SPECTATOR);
										pl.getWorld().playSound(pl.getLocation(), Sound.BAT_TAKEOFF, 1, 0);
									  pl.sendMessage(touhouraces + ChatColor.RED + "あなたは蝙蝠になった！");
									  Entity bat = pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.BAT);
									  MetadataValue invincible = new FixedMetadataValue(plugin, pl.getUniqueId()) ;
									  bat.setMetadata("invincible", invincible);
									  pl.setMetadata("using-magic", usingmagic);
								}
								} , 20L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
					}
				}
/*妖精は霊力消費でシャベルを使ったテレポ + 混乱の光*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito"))
				{
					Material spade_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20 && pl.isSneaking() ){
						if (spade_is_ok == Material.WOOD_SPADE || spade_is_ok == Material.STONE_SPADE || spade_is_ok == Material.IRON_SPADE || spade_is_ok == Material.DIAMOND_SPADE || spade_is_ok == Material.GOLD_SPADE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "シャベルを構えた！");
							pl.getWorld().playSound(pl.getLocation(), Sound.CAT_MEOW, 1, 0);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 1, 0);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									if (pl.getItemInHand().getType() == Material.GOLD_SPADE)
									{
											pl.sendMessage(touhouraces + ChatColor.LIGHT_PURPLE + "金のシャベルの輝きがあたりを惑わす！");
											pl.getWorld().playSound(pl.getLocation(), Sound.CAT_PURR, 3, -1);
											pl.getWorld().playEffect(pl.getLocation(), Effect.HAPPY_VILLAGER, 1, 1);
											List<Entity> enemys=pl.getNearbyEntities(14D, 14D, 14D);
											for (Entity enemy : enemys)
											{
												if (enemy instanceof Player)
												{
													((Player) enemy).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,300,3));
												}
											}
									}
									else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito") && pl.getItemInHand().getType() == Material.STONE_SPADE)
									{
										pl.sendMessage(touhouraces + ChatColor.DARK_GREEN + "樹人は毒をばらまいた！");
										pl.getWorld().playSound(pl.getLocation(), Sound.PIG_DEATH, 3, -1);
										pl.getWorld().playEffect(pl.getLocation(), Effect.VOID_FOG, 1, 1);
										List<Entity> enemys=pl.getNearbyEntities(14D, 14D, 14D);
										for (Entity enemy : enemys)
										{
											if (enemy instanceof LivingEntity)
											{
												((LivingEntity) enemy).addPotionEffect(new PotionEffect(PotionEffectType.POISON,200,1));
											}
										}
										MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("using-magic", usingmagic);
										this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
										{
											private Plugin plugin;
											private String touhouraces;
												public void run() 
											{
											  Player pl = event.getPlayer();
											  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
											  pl.setMetadata("using-magic", usingmagic);
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
											}
										}
										, 100L);
									}
									else
										{
											pl.sendMessage(touhouraces + ChatColor.DARK_PURPLE + "シャベルは向いた方向へ転移の門を開いた！");
											float pitch = pl.getLocation().getPitch();
											float yaw = pl.getLocation().getYaw();
											Location warploc = new Location (pl.getWorld(),pl.getLocation().getX() + pl.getLocation().getDirection().getX() * 6,pl.getLocation().getY() + pl.getLocation().getDirection().getY() * 6,pl.getLocation().getZ() + pl.getLocation().getDirection().getZ() * 6);
											if (pl.getWorld().getBlockAt(warploc).getType() != Material.AIR)
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_HIT, 2, 0);
												pl.sendMessage(touhouraces + ChatColor.RED + "しかし十分な出口空間が無かったため入れなかった！");
											}
											else
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 0);
												pl.getWorld().playEffect(pl.getLocation(), Effect.COLOURED_DUST, 1, 5);
												warploc.setPitch(pitch);
												warploc.setYaw(yaw);
												pl.teleport(warploc);
											}
										}
									  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
									  pl.setMetadata("casting", casted);
									}
								} , 20L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 25);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*精霊は霊力消費で鍬を使い詠唱、光弾発射 + 守護霊召喚*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("seirei")   || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("onnryou")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("hannrei")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sourei"))
				{
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 40 && pl.isSneaking() )
					{
						Material hoe_is_ok = pl.getItemInHand().getType() ; 
						if (hoe_is_ok == Material.WOOD_HOE || hoe_is_ok == Material.STONE_HOE || hoe_is_ok == Material.IRON_HOE || hoe_is_ok== Material.DIAMOND_HOE || hoe_is_ok == Material.GOLD_HOE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
								{
										pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
								}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
							}
							else
								{
								if (pl.getItemInHand().getType() == Material.GOLD_HOE)
								{
									MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("casting", casting);
									pl.sendMessage(this.plugin.touhouraces + ChatColor.LIGHT_PURPLE + "守護霊を呼び出し、自身を保護する！");
									pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_USE, 2, 0);
									pl.getWorld().playEffect(pl.getLocation(), Effect.RECORD_PLAY, 1, 1);
									this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
										private Plugin plugin;
										private String touhouraces;
										public void run() {
											  Player pl = event.getPlayer();
											  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
											  pl.setMetadata("casting", casted);
											  MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
											  pl.setMetadata("using-magic", usingmagic);
											  double type = Math.random();
											  if (type <= 8)
											  {
												  int n = 0;
												  while (n < 3)
												  {
													  Entity snowman = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.SNOWMAN);
														MetadataValue syugoreisnow = new FixedMetadataValue(this.plugin, true) ;
														snowman.setMetadata("syugoreisnow", syugoreisnow);
														MetadataValue syugoreitarget = new FixedMetadataValue(this.plugin, pl.getName()) ;
														snowman.setMetadata("syugoreitarget", syugoreitarget);
														n ++ ;
												  }
												  pl.getWorld().playSound(pl.getLocation(), Sound.IRONGOLEM_HIT, 2, 1);
												  pl.sendMessage(touhouraces + ChatColor.AQUA + "雪の霊だ！");
											  }
											  else
											  {
												  int n = 0;
												  while (n < 1)
												  {
													  Entity snowman = pl.getWorld().spawnEntity(pl.getLocation(), EntityType.IRON_GOLEM);
														MetadataValue syugoreiiron = new FixedMetadataValue(this.plugin, true) ;
														snowman.setMetadata("syugoreiiron", syugoreiiron);
														MetadataValue syugoreitarget = new FixedMetadataValue(this.plugin, pl.getName()) ;
														snowman.setMetadata("syugoreitarget", syugoreitarget);
														n ++ ;
												  }
												  pl.getWorld().playSound(pl.getLocation(), Sound.IRONGOLEM_HIT, 2, -1);
												  pl.sendMessage(touhouraces + ChatColor.GOLD + "岩の霊だ！");
											  }
											  this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
													public void run() {
														  Player pl = event.getPlayer();
														  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
														  pl.setMetadata("using-magic", usingmagic);
														  pl.sendMessage(touhouraces + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
														}
													} , 600L);
											}
										} , 20L);
									this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 40);
								    this.plugin.saveConfig();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
									}
								else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("hannrei") && hoe_is_ok == Material.STONE_HOE )
								{
									MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("casting", casting);
									pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_PURPLE + "半霊を詠唱する！");
									this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
										public void run() {
											Player pl = event.getPlayer() ;
											pl.getWorld().playSound(pl.getLocation(), Sound.GHAST_SCREAM, 1, 1);
											pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
											  Location location=pl.getEyeLocation();
											  float pitch=location.getPitch() / 180.0F * 3.1415927F;
											  float yaw=location.getYaw() / 180.0F * 3.1415927F;
											  double motX=-MathHelper.sin(yaw) * MathHelper.cos(pitch);
											  double motZ=MathHelper.cos(yaw) * MathHelper.cos(pitch);
											  double motY=-MathHelper.sin(pitch);
											  Vector velocity=new Vector(motX,motY,motZ).multiply(2D);
											  Snowball snowball=pl.throwSnowball();
											  MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString()) ;
											  snowball.setMetadata("hannrei-curseball", shooter);
											  snowball.setVelocity(velocity);
											  pl.removeMetadata("casting", plugin);
											  MetadataValue casted = new FixedMetadataValue(plugin, false) ;
											  pl.setMetadata("casting", casted);
											}
										} , 30L);
									this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
								    this.plugin.saveConfig();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
								}
								else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sourei") && hoe_is_ok == Material.IRON_HOE )
								{
									MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("casting", casting);
									pl.sendMessage(this.plugin.touhouraces + ChatColor.UNDERLINE + ChatColor.BOLD + "レッツッオーケストラ！！");
									pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_BASS_DRUM, 1, 0);
									this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
										private Plugin plugin;
										private String touhouraces;
										public void run() {
											Player pl = event.getPlayer() ;
											List<Entity> enemys=pl.getNearbyEntities(20D, 20D, 20D);
											double rand = Math.random();
											if (rand >= 0.8)
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_BASS_GUITAR, 10, -2);
												for (Entity enemy : enemys)
												{
													if (enemy instanceof Player)
													{

														((Player) enemy).addPotionEffect(new PotionEffect (PotionEffectType.SLOW , 200 , 5));
														((Player) enemy).sendMessage(touhouraces + ChatColor.DARK_BLUE + "鬱だ・・・");
													}
												}
											}
											else if (rand >= 0.4)
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_SNARE_DRUM, 10, 1);
												for (Entity enemy : enemys)
												{
													if (enemy instanceof Player)
													{

														((Player) enemy).addPotionEffect(new PotionEffect (PotionEffectType.CONFUSION , 400 , 2));
														((Player) enemy).sendMessage(touhouraces + ChatColor.DARK_RED + "躁だ★");
													}
												}
											}
											else
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.NOTE_PIANO, 10, 0);
												for (Entity enemy : enemys)
												{
													if (enemy instanceof Player)
													{
														((Player) enemy).sendMessage(touhouraces + ChatColor.GREEN + "騒音だ！！");
														if (((Player) enemy).getHealth() - 15D >= 0)
														{
														((Player) enemy).setHealth(((Player) enemy).getHealth() - 15D);
														}
														else
														{
															((Player) enemy).setHealth(0D);
														}
													}
												}
											}
											  pl.removeMetadata("casting", this.plugin);
											  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
											  pl.setMetadata("casting", casted);
											}
										} , 60L);
									this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 40);
								    this.plugin.saveConfig();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
								}
								else
								{
								MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
								pl.setMetadata("casting", casting);
								pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "光弾を詠唱した！");
								pl.getWorld().playSound(pl.getLocation(), Sound.GHAST_SCREAM2, 1, 1);
								pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
								this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
									public void run() {
										Player pl = event.getPlayer() ;
										pl.getWorld().playSound(pl.getLocation(), Sound.DIG_SNOW, 2, 2);
										pl.getWorld().playEffect(pl.getLocation(), Effect.SNOW_SHOVEL, 1, 1);
										  Location location=pl.getEyeLocation();
										  int n = 0;
										  while (n < 8)
										  {
											  float pitch=location.getPitch() / 180.0F * 3.1415927F;
											  float yaw=location.getYaw() / 180.0F * 3.1415927F + n * 45;
											  double motX=-MathHelper.sin(yaw) * MathHelper.cos(pitch);
											  double motZ=MathHelper.cos(yaw) * MathHelper.cos(pitch);
											  double motY=-MathHelper.sin(pitch);
											  Vector velocity=new Vector(motX,motY,motZ).multiply(2D);
											  Snowball snowball=pl.throwSnowball();
											  MetadataValue shooter = new FixedMetadataValue(plugin, pl.getUniqueId().toString()) ;
											  snowball.setMetadata("seirei-lightball", shooter);
											  snowball.setVelocity(velocity);
											  n ++ ;
										  }
										  pl.removeMetadata("casting", plugin);
										  MetadataValue casted = new FixedMetadataValue(plugin, false) ;
										  pl.setMetadata("casting", casted);
										}
									} , 15L);
								this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 4);
							    this.plugin.saveConfig();
								pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
								}
							}
						}
					}
				}
			}
		}
	}
/*プレイヤーがMOBをクリックした時*/
	@EventHandler(priority =EventPriority.LOWEST)
	public void on_click_MOB(final PlayerInteractEntityEvent event)
	{
		Player pl = event.getPlayer() ;
/*村人は人間にしか話せない*/
		if (event.getRightClicked().getType() == EntityType.VILLAGER)
		{
			if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngen") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("mazyo") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houraizin") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("gennzinnsin") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("misou") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sibito") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennninn") == false)
			{
				pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "このニンゲンは何を話しているんだろう・・・");
				pl.closeInventory();
				event.setCancelled(true);
			}
		}
		else
		{
			if (pl.hasMetadata("ignoreskill") == false && pl.hasPermission("thr.skill"))
			{
/*吸血鬼は霊力消費でつるはしを使った吸血+きゅっとしてどかーん*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					Material pickel_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() ){
						if (pickel_is_ok == Material.WOOD_PICKAXE || pickel_is_ok == Material.STONE_PICKAXE || pickel_is_ok == Material.IRON_PICKAXE || pickel_is_ok == Material.DIAMOND_PICKAXE || pickel_is_ok == Material.GOLD_PICKAXE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を詠唱中です！");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "他の魔法を使用中です！");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "牙を構えた！");
							pl.getWorld().playSound(pl.getLocation(), Sound.SPIDER_IDLE, 2, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.LAVADRIP, 2, 1);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									Entity target = event.getRightClicked();
									if (pl.getItemInHand().getType() == Material.GOLD_PICKAXE)
									{
											pl.sendMessage(touhouraces + ChatColor.DARK_GRAY + "きゅっとして" + ChatColor.BOLD + ChatColor.YELLOW + "どかーん!！！");
											target.getWorld().playSound(target.getLocation(), Sound.NOTE_PIANO, 3, 3);
											target.sendMessage(touhouraces + ChatColor.DARK_GRAY + "きゅっとして" + ChatColor.BOLD + ChatColor.YELLOW + "どかーん!！！");
											MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
											pl.setMetadata("using-magic", usingmagic);
											this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
												public void run() {
														Player pl = event.getPlayer();
														Entity target = event.getRightClicked();
														if (pl.getLocation().distanceSquared(event.getRightClicked().getLocation() ) >= 150)
														{
															pl.sendMessage(touhouraces + ChatColor.BLUE + "しかし逃げられてしまった！");
															target.sendMessage(touhouraces + ChatColor.BLUE + "逃げ切った！");
														}
														else
														{
														target.getWorld().playSound(target.getLocation(), Sound.EXPLODE, 2, 1);
														target.getWorld().playEffect(target.getLocation(), Effect.EXPLOSION_LARGE, 1, 1);
														if (target instanceof LivingEntity)
														{
															if (((LivingEntity) target).getHealth() / 4 >= 0)
															{
																((LivingEntity) target).setHealth(((LivingEntity) target).getHealth() / 4);
															}
															else
															{
																((LivingEntity) target).setHealth(0D);
															}
														}
													}
												  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
												  pl.setMetadata("using-magic", usingmagic);
												}
												} , 120L);
										this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
									    this.plugin.saveConfig();
										pl.sendMessage(touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
									}
									else
										{
											if (pl.getLocation().distanceSquared(target.getLocation() ) >= 80)
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.SPIDER_DEATH, 2, 1);
												pl.sendMessage(touhouraces + ChatColor.BLUE + "しかし逃げられてしまった！");
											}
											else if (target.getType() != EntityType.VILLAGER && target instanceof LivingEntity)
											{
												pl.sendMessage(touhouraces + ChatColor.DARK_RED + "あなたは吸血した！");
												target.getWorld().playSound(pl.getLocation(), Sound.SPIDER_DEATH, 2, 1);
												target.getWorld().playEffect(pl.getLocation(), Effect.TILE_BREAK, 1, 152);
												{
													if (((LivingEntity) target).getHealth() - 30D >= 0)
													{
														((LivingEntity) target).setHealth(((LivingEntity) target).getHealth() - 30D);
													}
													else
													{
														((LivingEntity) target).setHealth(0D);
													}
												}
												if (pl.getHealth() > pl.getMaxHealth() - 30)
												{
													 pl.setHealth(pl.getMaxHealth());		
												}
												else
												{
												 pl.setHealth(15D + pl.getHealth());									
												}
											}
										}
									  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
									  pl.setMetadata("casting", casted);
									}
								} , 40L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
			}
		}
	}
/*銃での攻撃に対するhook*/
    @EventHandler(priority=EventPriority.HIGH)
	public void on_simpleclans_crackshot_hook(final WeaponDamageEntityEvent event)
	{
        if (event.getPlayer() instanceof Player && event.getDamager() != null )
        {
        	if (event.getVictim() instanceof Player == true)
        	{
			UUID shooter_owner_id = ((Player) event.getPlayer()).getUniqueId();
			UUID victim_id = ((Player) event.getVictim()).getUniqueId();
				if (sc.getClanManager().getClanByPlayerUniqueId(shooter_owner_id) != null && sc.getClanManager().getClanByPlayerUniqueId(victim_id) != null)
				{
					if (sc.getClanManager().getClanByPlayerUniqueId(shooter_owner_id) == sc.getClanManager().getClanByPlayerUniqueId(victim_id))
					{
						event.setCancelled(true);
					}
					else if (sc.getClanManager().getClanPlayer(shooter_owner_id).isAlly(sc.getClanManager().getClanPlayer(victim_id).toPlayer()))
					{
						event.setCancelled(true);
					}
				}
        	}
        }
			@SuppressWarnings("deprecation")
			EntityDamageByEntityEvent weaponattack = new EntityDamageByEntityEvent(event.getPlayer(), event.getVictim(), DamageCause.ENTITY_ATTACK, event.getDamage());
			on_attack_entity(weaponattack);
	}
/*プレイヤーがダメージを与える時*/
    @EventHandler(priority=EventPriority.LOW)
	public void on_attack_entity(final EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Bat && event.getEntity().hasMetadata("invincible"))
		{
			event.setDamage(0D);
			event.getDamager().sendMessage(this.plugin.touhouraces + ChatColor.RED + "化けているのは分かったから待ちましょう！");
			event.setCancelled(true);
		}
		if (event.getEntity() instanceof Snowman && event.getEntity().hasMetadata("syugoreisnow"))
		{
			event.setDamage(event.getDamage() / 20);
		}
		if (event.getEntity() instanceof IronGolem && event.getEntity().hasMetadata("syugoreiiron"))
		{
			event.setDamage(event.getDamage() / 20);
		}
		if (event.getEntity() instanceof Wolf && event.getEntity().hasMetadata("tamedwolf"))
		{
			event.setDamage(event.getDamage() / 10);
		}
		if (event.getEntity() instanceof Ocelot && event.getEntity().hasMetadata("tamedcat"))
		{
			event.setDamage(event.getDamage() / 10);
		}
		if (event.getDamager() instanceof Player)
		{
			Player pl = (Player) event.getDamager() ;
			if (pl.hasPermission("thr.skill"))
			{
/*akumaの闇の刃(元ダメージ2以上で効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("akuma")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if (pl.getEyeLocation().getBlock().getLightLevel() <= 8 && event.getDamage() > 1D)
					{
						event.setDamage(event.getDamage() + 1D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, 152);
					}
				}
/*kyuuketukiの紅い月(元ダメージ1以上で効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					if (pl.getEyeLocation().getBlock().getLightLevel() <= 4 && event.getDamage() > 0D)
					{
						event.setDamage(event.getDamage() + 2D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, 152);
					}
					else if (pl.getEyeLocation().getBlock().getLightLevel() >= 14 && event.getDamage() > 4)
					{
						event.setDamage(event.getDamage() - 2D);
					}
				}
/*oniの乱神(元ダメージ1以上で効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if(pl.getLocation().distanceSquared(event.getEntity().getLocation()) <= 8D && event.getDamage() > 0D && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 25D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						event.setDamage(event.getDamage() + 2D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
					}
				}
/*youzyuuの狙撃手(元ダメージ3以上で効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youzyuu") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("siki") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("zyuuzin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo"))
				{
					if(pl.getLocation().distanceSquared(event.getEntity().getLocation()) >= 10D && event.getDamage() > 0D)
					{
						event.setDamage(event.getDamage() + 8D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.POTION_BREAK, 2);
					}
				}
				
/*kamiの神通力(元ダメージ10以下で効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kami") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("zyuuzin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yakusin"))
				{
					if (event.getDamage() <= 9D && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 10D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 10D);
						event.setDamage(event.getDamage() + 2D);
					}
				}
/*houzyousinの空腹責め*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin"))
				{
					if (Math.random() >= 0.8 && event.getEntity() instanceof Player && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 10D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						((Player) event.getEntity()).setFoodLevel(((Player) event.getEntity()).getFoodLevel() - 2);
						event.getEntity().sendMessage(this.plugin.touhouraces + ChatColor.GOLD + pl.getName() + "はおいしい芋を見せつけてきた！");
					}
				}
/*sibitoの瀕死時攻撃(元ダメージにかかわらず効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sibito"))
				{
					if (pl.getHealth() <= 20D)
					{
						event.setDamage(event.getDamage() + 3D);
						event.getDamager().getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_PIG_HURT, 1, 1);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.TILE_BREAK, 49);
					}
				}
/*gennzinnsinの奇跡クリティカル(元ダメージにかかわらず効果あり)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("gennzinnsin"))
				{
					if (Math.random() > 0.7 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 5D)
					{
					    this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
						event.setDamage(event.getDamage() + 5D);
						pl.getWorld().playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
					}
				}
/*霊力再生中のデバフ*/
				if (pl.getMetadata("spilituse").get(0).asDouble() < 0)
				{
					event.setDamage(event.getDamage() / 2D);
					if (pl.isSneaking())
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + pl.getName() + "貴方は霊力再生モードの為本気を出せません！");
					}
				}
			}
		}
		if (event.getEntity() instanceof Player)
		{
			Player pl = (Player) event.getEntity();
			if (pl.hasPermission("thr.skill"))
			{
/*殺してきた相手の情報表示*/
				if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori"))
				{
					if (event.getDamage() >= pl.getHealth() && event.getDamager() instanceof Player && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 50)
					{
						    this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 50D);
							pl.sendMessage(event.getDamager().getName() + ":体力:" + ((Player) event.getDamager()).getHealth() + "");
							pl.sendMessage(event.getDamager().getName() + ":座標:" + event.getDamager().getLocation().getBlockX() + "," + event.getDamager().getLocation().getBlockY() + "," + event.getDamager().getLocation().getBlockZ());
							pl.sendMessage("覚りました・・・覚えてなさい・・・");
						    String satorin0 = event.getDamager().getName();
							MetadataValue satorin00 = new FixedMetadataValue(this.plugin, satorin0) ;
							pl.setMetadata("satorin0", satorin00);
					}
				}
/*厄神の祟り*/
				if (this.plugin.getConfig().getString("user." + event.getEntity().getUniqueId() + ".race").toString().contains("yakusin"))
				{
					Entity killer = event.getDamager();
					if (killer instanceof Player && event.getDamage() >= pl.getHealth() && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 20)
					{
						this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 20D);
						Player killplayer = (Player) killer ;
						if (killplayer.isDead() == false)
						{
							killplayer.sendMessage(this.plugin.touhouraces + ChatColor.RED + "あなたは厄神の祟りを受けた！！");
							killplayer.damage(20D);
						}
					}
				}
/*怨霊の消失への恐怖*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("onnryou"))
				{
					if (event.getDamage() >= pl.getHealth())
					{
						double rand = Math.random();
						if (rand > 0.7 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 40)
						{
							this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 40D);
							pl.setHealth(50D);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "消えたくない・・・っ");
							if (event.getDamager() instanceof Player )
							{
								Player dpl = (Player) event.getDamager();
								dpl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "消えたくない・・・っ");
								dpl.addPotionEffect(new PotionEffect (PotionEffectType.WITHER,100,4));
							}
							pl.getWorld().playSound(pl.getLocation(), Sound.GHAST_CHARGE, 2, 2);
							event.setCancelled(true);
						}
					}
				}
/*akumaの反撃(ダメージ5以上)確立５割：固定値ダメージ５*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("akuma")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if(event.getDamage() >= 10D && Math.random() >= 0.6 && event.getDamager() instanceof LivingEntity && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 10)
					{
						((LivingEntity)event.getDamager()).damage(5D);
						if (event.getDamager() instanceof Player )
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 10D);
						{
							Player dpl = (Player) event.getDamager();
							dpl.playSound(dpl.getLocation(), Sound.HURT_FLESH, 1, 0);
							dpl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "貴方は反撃を喰らった！！");
						}
					}
				}
/*youseiのグレイズ*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori"))
					{
						double ran = Math.random();
						if (ran >= 0.9)
							{
							pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HISS, 1, 2);
							event.setCancelled(true);
							}
						else if (event.getDamage() > 3D)
						{
							event.setDamage(event.getDamage() + 1D);
						}
					}
/*kobitoの超グレイズ(避けられないとダメージ増(２ダメージ以上・霊力２０以下でさらにダメージUP)*/
				else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito"))
				{
					double ran = Math.random();
					if (ran >= 0.7)
						{
						pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HISS, 1, 2);
						event.setCancelled(true);
						}
					else if (event.getDamage() > 1D  && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 20)
					{
						event.setDamage(event.getDamage() + 2D);
					}
					else if (event.getDamage() > 1D)
					{
						event.setDamage(event.getDamage() + 4D);
					}
				}
			}
			else
			{
/*光弾シリーズ*/
				if (event.getDamager().hasMetadata("seirei-lightball"))
				{
					if (event.getDamager().getMetadata("seirei-lightball").get(0).asString() != pl.getUniqueId().toString())
					{
						event.setDamage(6D);
					}
				}
				if (event.getDamager().hasMetadata("lightningeffect"))
				{
					event.setDamage(event.getDamager().getMetadata("lightningeffect").get(0).asDouble());
				}
				else if (event.getDamager().hasMetadata("mazyo-fireball"))
				{
					if (event.getDamager().getMetadata("mazyo-fireball").get(0).asString() != pl.getUniqueId().toString())
					{
					event.setDamage(10D);
					event.getEntity().setFireTicks(200);
					}
				}
				else if (event.getDamager().hasMetadata("hannrei-curseball"))
				{
					if (event.getDamager().getMetadata("hannrei-curseball").get(0).asString() != pl.getUniqueId().toString())
					{
						pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,150,3));
						pl.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,150,3));
						if (event.getEntity() instanceof Player && Bukkit.getPlayer(UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString())) != null)
						{
							if (this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 30)
							{
								this.plugin.getConfig().set("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit",this.plugin.getConfig().getInt("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit") + 30D);
								this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
								if (this.plugin.getConfig().getInt("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit") > 100)this.plugin.getConfig().set("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit",100D);
							}
							else
							{
								this.plugin.getConfig().set("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit",this.plugin.getConfig().getInt("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit") + this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit"));
								this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",0);
								if (this.plugin.getConfig().getInt("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit") > 100)this.plugin.getConfig().set("user." + UUID.fromString(event.getDamager().getMetadata("hannrei-curseball").get(0).asString()) + ".spilit",100D);
								
							}
							((Player)event.getEntity()).sendMessage(this.plugin.touhouraces + ChatColor.DARK_PURPLE + "霊力を吸い取られた！");
						}
					}
				}
			}
		}
	}
    
/*プレイヤーがとにかくダメージを受けた時*/
	@EventHandler public void on_every_damaged(final EntityDamageEvent event)
	{
		if (event.getEntityType() == EntityType.SNOWMAN && event.getEntity().hasMetadata("syugoreisnow"))
		{
			event.setDamage(0D);
		}
		else if (event.getEntityType() == EntityType.IRON_GOLEM && event.getEntity().hasMetadata("syugoreiiron"))
		{
			event.setDamage(0D);
		}
		if (event.getEntity() instanceof Player)
		{
			Player pl = (Player) event.getEntity();
			if (pl.hasPermission("thr.skill"))
			{
/*インビジブルのデバフ*/
				if(pl.hasPotionEffect(PotionEffectType.INVISIBILITY))
				{
					pl.removePotionEffect(PotionEffectType.INVISIBILITY);
					pl.playSound(pl.getLocation(), Sound.WOLF_HURT, 1, -1);
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "1.8のバグで名前丸見えなのよね・・・");
				}
/*youseiは落下耐性あり*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito"))
				{
					if (event.getCause() == DamageCause.FALL)
					{
						event.setDamage(event.getDamage() / 2);
					}
				}
/*tennguは落下無効*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu"))
				{
					if (event.getCause() == DamageCause.FALL)
					{
						event.setCancelled(true);
					}
				}
/*akumaは延焼を無効化*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("akuma")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if (event.getCause() == DamageCause.FIRE_TICK)
					{
						event.setCancelled(true);
					}
				}
/*kyuuktukiはマグマや火をも無効化*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.LAVA)
					{
						event.setCancelled(true);
					}
				}
/*人魚、河童は溺死を無効化*/
				if(pl.hasPermission("thr.skill")&& this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa"))
				{
					if (event.getCause() == DamageCause.DROWNING)
					{
						event.setCancelled(true);
					}
				}
/*houzyousinは餓死を無効化*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin"))
				{
					if (event.getCause() == DamageCause.STARVATION)
					{
						event.setCancelled(true);
					}
				}
/*serireiはシフトで防御増*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("seirei")   || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("onnryou")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("hannrei")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sourei"))
				{
					if (pl.isSneaking() && event.getDamage() > 3 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 5D)
					{
						this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
						event.setDamage(event.getDamage() - 3 );
					}
				}
/*kamiは２ダメージ以上の攻撃を１ダメージ減らす*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kami") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yakusin"))
				{
					if (event.getDamage() > 1 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 10D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						event.setDamage(event.getDamage() - 1 );
					}
				}
/*houraizinは復活する可能性がある*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houraizin"))
				{
					if (event.getDamage() >= pl.getHealth() && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 30D)
					{
						double reverse = Math.random();
								if (reverse > 0.6)
								{
									this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
									pl.setHealth(pl.getMaxHealth());
									pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "貴方は不死の力を使い蘇った！！");
									pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, -1);
									event.setDamage(0D);
								}
					}
				}
/*霊力再生中のデバフ*/
				if (pl.getMetadata("spilituse").get(0).asDouble() < 0)
				{
					event.setDamage(event.getDamage() * 2D);
					if (pl.isSneaking())
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + pl.getName() + "貴方は霊力再生モードの為非常に柔いです！");
					}
				}
			}
		}
	}
}  