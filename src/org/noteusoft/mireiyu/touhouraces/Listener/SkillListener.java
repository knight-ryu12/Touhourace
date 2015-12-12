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
/*�`���b�g*/
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
					event.setFormat("��f[" + race + "��f]" + format);
			}
			else
			{
				String race = this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race");
				event.setFormat("��f[" + race + "��f]" + format);
			}
		}
	}
/*�ȉ��Z�ݒ�*/
/*�ޏo������*/
	@EventHandler
	public void on_quit(final PlayerQuitEvent event)
	{
		Player pl = event.getPlayer();
/*�o���v�J���t���[�W��*/
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
/*���^���Z�b�g*/
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

/*�Q��������*/
	@EventHandler
	public void on_join(final PlayerJoinEvent event)
	{
		Player pl = event.getPlayer();
/*���^�����t�^*/
		  MetadataValue casted = new FixedMetadataValue(this.plugin, false) ;
		  pl.setMetadata("casting", casted);
		  MetadataValue usingmagic = new FixedMetadataValue(this.plugin, false) ;
		  pl.setMetadata("using-magic", usingmagic);
		  MetadataValue spilituse = new FixedMetadataValue(this.plugin, 0) ;
		  pl.setMetadata("spilituse", spilituse);
/*�V�K�o�^*/
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
/*���X�|�[�������Ƃ�*/
 	@EventHandler
 	public void on_respawn(final PlayerRespawnEvent event)
 	{
			Player pl = event.getPlayer();
/*�d���̗̑͂�120�ɑ���*/
		if(pl.hasPermission("thr.skill") )
		{
			if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youma") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu"))
			{
				pl.setMaxHealth(120D);
			}
/*���d�̗̑͂�150�ɑ���*/
			else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kennyou"))
			{
				pl.setMaxHealth(150D);
			}
/*���̎푰�̗̑͂�100�ɑ���*/
			else
			{
				pl.setMaxHealth(100D);
			}
		}
	}
/*�v���C���[��������*/
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
/*�l���̉j��*/
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
/*�v���C���[�����Ⴊ�݃g�O��������*/
	@SuppressWarnings("deprecation")
	@EventHandler
	public void on_sneaktoggle(final PlayerToggleSneakEvent event)
	{
		Player pl = event.getPlayer() ;
		if(pl.hasMetadata("ignoreskill") == false && pl.hasPermission("thr.skill") )
			{ 
/*yousei�͗�͏���ŋ󒆃_�u���u�[�X�g*/
				if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito"))
				{
					if (pl.isOnGround() == false && pl.isSneaking() == true && this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20)
					{
						pl.setVelocity(pl.getLocation().getDirection().multiply(1.1D));
						pl.getWorld().playSound(pl.getLocation(), Sound.SHOOT_ARROW, 1, 1);
						pl.getWorld().playEffect(pl.getLocation(), Effect.TILE_DUST, 133, 1);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 5);
					    this.plugin.saveConfig();
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
				}
/*tenngu�͗�͏���ŋ󒆒����u�[�X�g*/
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
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
				}
/*��l�͂P�u���b�N�̕ǔ�����e�Ղɂł���*/
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
/*�v���C���[���N���b�N������*/
		@SuppressWarnings("deprecation")
		@EventHandler
		public void on_click(final PlayerInteractEvent event)
		{
			Player pl = event.getPlayer() ;
/*�푰P�V���b�v �_�[�N�v���Y�}�C��*/
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
/*�������A�C�e���X�^�b�N�łȂ���Γ��삵�Ȃ��B*/
					if (objective.getScore(pl.getPlayer()).getScore() >= cost_faith && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point") >= cost_racepoint && pl.getInventory().contains(cost_item))
					{
						objective.getScore(pl.getPlayer()).setScore(objective.getScore(pl.getPlayer()).getScore() - cost_faith);
						pl.getInventory().remove(cost_item);
						
						ItemStack buy_item = new ItemStack(Material.getMaterial(this.plugin.getConfig().getInt("rankshop." + useshopname + ".buyitem.typeid")) , this.plugin.getConfig().getInt("rankshop." + useshopname + ".buyitem.amount"));
						buy_item.setDurability((short) this.plugin.getConfig().getInt("rankshop." + useshopname + ".buyitem.meta"));
						pl.getInventory().addItem(buy_item);
						pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + cost_faith + "�̐M��" + ChatColor.GOLD + cost_racepoint + "�̎푰�|�C���g��" + cost_item + "�̃A�C�e��������������" + ChatColor.GREEN + buy_item.getAmount() + "��" + buy_item.getType().name() + "(���^:" + buy_item.getDurability() + ")����ɓ��ꂽ�I");
					}
					else if (objective.getScore(pl.getPlayer()).getScore() < cost_faith) pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + cost_faith + "���̐M�������Ă��܂���I");
					else if (this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point") < cost_racepoint) pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + cost_racepoint + "���̎푰�|�C���g�������Ă��܂���I");
					else if (pl.getInventory().contains(cost_item) == false) pl.sendMessage(cost_item.getAmount() + "��" + cost_item.getType().name() + "(���^:" + cost_item.getDurability() + ")������܂���I�I");
				}
			}
/*�ȉ��A�C�e���N���b�N����W*/
			if (pl.hasPermission("thr.skill") && pl.hasMetadata("ignoreskill") == false)
			{
/*���N���b�N*/
				if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
				{
/*�d�b�͗�͏���ŋ|�A�ނ�Ƃ��g���\�͋���or�T��L����*/
					if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youzyuu") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("siki") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("zyuuzin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo"))
					{
						if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() )
						{
							Material other_is_ok = pl.getItemInHand().getType() ; 
							if (other_is_ok == Material.FISHING_ROD || other_is_ok == Material.BOW || other_is_ok == Material.ARROW)
							{
								if (pl.getMetadata("casting").get(0).asBoolean() == true)
									{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
									}
								else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
								{
										pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
								}
								else
									{
									if (pl.getItemInHand().getType() == Material.FISHING_ROD)
									{
										if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("siki"))
										{
											MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
											pl.setMetadata("casting", casting);
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "�����邵���L���Ăяo���A�j���A�I�I");
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
														  pl.sendMessage(touhouraces + ChatColor.GOLD + "�u�j���A�v�u�j���A�v�u�j���A�v");
													}
												} , 40L);
											this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
										    this.plugin.saveConfig();
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
										}
										else
										{
										MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
										pl.setMetadata("casting", casting);
										pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "�T�̌Q����Ăяo�� �E�I�I�I�[���I�I");
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
													  pl.sendMessage(touhouraces + ChatColor.GOLD + "�u�E�I���v�u�E�I���v�u�E�I���v");
												}
											} , 40L);
										this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
									    this.plugin.saveConfig();
										pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
										}
									}
									else
									{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��͂Ŏ��g�̔\�͑�����}�����I");
									pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, 1);
									pl.getWorld().playEffect(pl.getLocation(), Effect.MOBSPAWNER_FLAMES ,1, 1);
									double ram = Math.random();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + ram);
									if (ram < 0.1D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "���s�I");
								
										}
									else if (ram < 0.2D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "�ړ����x������ɏオ�����I");
											pl.removePotionEffect(PotionEffectType.SPEED);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,2));
							
										}
									else if (ram < 0.3D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "�����͂��オ�����I");
											pl.removePotionEffect(PotionEffectType.JUMP);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,300,2));
								
										}
									else if (ram < 0.4D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "�U��̑������オ�����I");
											pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,250,2));
						
										}
									else if (ram < 0.5D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.LIGHT_PURPLE + "�y���Đ��\�͂𓾂��I");
											pl.removePotionEffect(PotionEffectType.REGENERATION);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,300,1));
		
										}
									else if (ram < 0.6D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "�}�]�����_�𓾂��I�H");
											pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,250,2));
		
										}
									else if (ram < 0.7D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "�T�]�����_�𓾂��I�H");
											pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
											pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,250,1));
			
										}
									else if (ram < 0.8D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "�V��̗͂𓾂��I");
											if (pl.getWorld().isThundering())
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "�V�͗����̔@���͂������ĉ��������I");
												pl.removePotionEffect(PotionEffectType.SPEED);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,2));
												pl.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,200,1));
												pl.removePotionEffect(PotionEffectType.FAST_DIGGING);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,200,2));
											}
											else if (pl.getWorld().hasStorm())
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_BLUE + "�V�͉J�̑����b�݂������ĉ�������");
												pl.removePotionEffect(PotionEffectType.JUMP);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,200,2));
												pl.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,200,1));
												pl.removePotionEffect(PotionEffectType.REGENERATION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,1));
											}
											else
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "�V�͐��V�̋P�������������ĉ��������I");
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
											pl.sendMessage(this.plugin.touhouraces + ChatColor.UNDERLINE + "���̗͂𓾂��I");
											if (pl.getWorld().getTime() < 14000)
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "���z�̃G�i�W�[�͋M���ɂ����܂����Đ��͂�^����I");
												pl.removePotionEffect(PotionEffectType.REGENERATION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,4));
											}
											else
											{
												pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "���������͂��Ȃ��̐S�����킷�ł��낤�I");
												pl.removePotionEffect(PotionEffectType.CONFUSION);
												pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,200,1));
											}
										}
									else if (ram < 1.0D) 
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "���s�I");
		
										}
									else
										{
											pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "���s�I");
		
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
										  pl.sendMessage(touhouraces + ChatColor.RED + "�r���N�[���_�E���������܂���");
										}
									} , 300L);
									this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
								    this.plugin.saveConfig();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
									}
								}
							}
						}
					}
				}
/*�E�N���b�N*/
			else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
/*��͏���E�[�d���u*/
				Material dust_is_ok = pl.getItemInHand().getType() ; 
				if (pl.isSneaking() )
				{
					if (pl.getMetadata("spilituse").get(0).asDouble() != 0)
					{
						 MetadataValue spilituse = new FixedMetadataValue(this.plugin, 0) ;
						 pl.setMetadata("spilituse", spilituse);
						 pl.sendMessage(this.plugin.touhouraces + ChatColor.WHITE + "��̓m�[�}��");
					}
					else
					{
						if (dust_is_ok == Material.SUGAR)
						{
							  MetadataValue spilituse = new FixedMetadataValue(this.plugin, 5) ;
							  pl.setMetadata("spilituse", spilituse);
							  pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "��͏��");
						}
						else if (dust_is_ok == Material.SULPHUR)
						{
							  MetadataValue spilituse = new FixedMetadataValue(this.plugin, 15) ;
							  pl.setMetadata("spilituse", spilituse);
							  pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_GRAY + "��͏����");
						}
						else if (dust_is_ok == Material.GLOWSTONE_DUST)
						{
							  MetadataValue spilituse = new FixedMetadataValue(this.plugin, -10) ;
							  pl.setMetadata("spilituse", spilituse);
							  pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "��͉񕜒�");
						}
					}
				}
/*�����͗�͏���Ō����g�����e�햂�@*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("mazyo"))
				{
					Material sword_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() ){
						if (sword_is_ok == Material.WOOD_SWORD || sword_is_ok == Material.STONE_SWORD || sword_is_ok == Material.IRON_SWORD || sword_is_ok == Material.DIAMOND_SWORD || sword_is_ok == Material.GOLD_SWORD)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "�����\�����I");
							pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 1, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									if (pl.getItemInHand().getType() == Material.WOOD_SWORD)
									{
											pl.sendMessage(touhouraces + ChatColor.YELLOW + "�y�̖��@���������I");
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
												  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
												}
											}
											, 60L);
									}
									else if  (pl.getItemInHand().getType() == Material.STONE_SWORD)
									{
										pl.sendMessage(touhouraces + ChatColor.GREEN + "���̖��@���������I");
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
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
											}
										}
										, 60L);
									}
									else if  (pl.getItemInHand().getType() == Material.IRON_SWORD)
									{
										pl.sendMessage(touhouraces + ChatColor.RED + "�΂̖��@���������I");
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
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
											}
										}
										, 5L);
									}
									else if  (pl.getItemInHand().getType() == Material.DIAMOND_SWORD)
									{
										pl.sendMessage(touhouraces + ChatColor.GREEN + "���̖��@���������I");
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
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
											}
										}
										, 180L);
									}
									else
									{
										pl.sendMessage(touhouraces + ChatColor.DARK_PURPLE + "���̖��@���������I");
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
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
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
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*�l�ԋ��ʁE�_�Ŏ��ȉ�*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngen") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("mazyo")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houraizin")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("gennzinnsin")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sibito")|| this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennnin"))
				{
					Material sword_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20 && pl.isSneaking() ){
						if (sword_is_ok == Material.STICK)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "�_���\�����I");
							pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 1, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
							{
								private Plugin plugin;
								private String touhouraces;
								public void run() 
								{
									Player pl = event.getPlayer() ;
									pl.sendMessage(touhouraces + ChatColor.YELLOW + "���Ȏ������g�����I");
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
										  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
										}
									}, 20L);
									MetadataValue casted = new FixedMetadataValue(plugin, false) ;
									pl.setMetadata("casting", casted);
								}
							} , 80L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 15);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*�d���͗�͏���ŕ����g�����ł��グ�{������΂�*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youma") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kennyou"))
				{
					Material axe_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20 && pl.isSneaking() ){
						if (axe_is_ok == Material.WOOD_AXE || axe_is_ok == Material.STONE_AXE || axe_is_ok == Material.IRON_AXE || axe_is_ok == Material.DIAMOND_AXE || axe_is_ok == Material.GOLD_AXE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "�����\�����I");
							pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_IDLE, 1, 1);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 3, 3);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									if (pl.getItemInHand().getType() == Material.GOLD_AXE)
									{
											pl.sendMessage(touhouraces + ChatColor.DARK_GREEN + "���̕��őS�Ă𐁂���΂��I");
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
/*�͓��͗�͏���ŐΕ����g�������e����*/
									else if (plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa") && pl.getItemInHand().getType() == Material.STONE_AXE)
									{
										pl.sendMessage(touhouraces + ChatColor.GREEN + "�΂̕���TNT�𓊂����I");
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
											pl.sendMessage(touhouraces + ChatColor.GREEN + "���Œn�ʂ�@���グ���I");
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
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*kyuuketuki�͗�͏�����啃J���t���[�W��*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					Material pickel_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() ){
						if (pickel_is_ok == Material.STONE_PICKAXE)
						{
						if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
							}
						else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
						{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
						}
						else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "�o���v�J���t���[�W�����������I");
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
									  pl.sendMessage(touhouraces + ChatColor.RED + "���Ȃ����啂ɂȂ����I");
									  Entity bat = pl.getWorld().spawnEntity(pl.getEyeLocation(), EntityType.BAT);
									  MetadataValue invincible = new FixedMetadataValue(plugin, pl.getUniqueId()) ;
									  bat.setMetadata("invincible", invincible);
									  pl.setMetadata("using-magic", usingmagic);
								}
								} , 20L);
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
						    this.plugin.saveConfig();
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
					}
				}
/*�d���͗�͏���ŃV���x�����g�����e���| + �����̌�*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito"))
				{
					Material spade_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20 && pl.isSneaking() ){
						if (spade_is_ok == Material.WOOD_SPADE || spade_is_ok == Material.STONE_SPADE || spade_is_ok == Material.IRON_SPADE || spade_is_ok == Material.DIAMOND_SPADE || spade_is_ok == Material.GOLD_SPADE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "�V���x�����\�����I");
							pl.getWorld().playSound(pl.getLocation(), Sound.CAT_MEOW, 1, 0);
							pl.getWorld().playEffect(pl.getLocation(), Effect.FOOTSTEP, 1, 0);
							this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
								private Plugin plugin;
								private String touhouraces;
								public void run() {
									Player pl = event.getPlayer() ;
									if (pl.getItemInHand().getType() == Material.GOLD_SPADE)
									{
											pl.sendMessage(touhouraces + ChatColor.LIGHT_PURPLE + "���̃V���x���̋P�����������f�킷�I");
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
										pl.sendMessage(touhouraces + ChatColor.DARK_GREEN + "���l�͓ł��΂�܂����I");
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
											  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
											}
										}
										, 100L);
									}
									else
										{
											pl.sendMessage(touhouraces + ChatColor.DARK_PURPLE + "�V���x���͌����������֓]�ڂ̖���J�����I");
											float pitch = pl.getLocation().getPitch();
											float yaw = pl.getLocation().getYaw();
											Location warploc = new Location (pl.getWorld(),pl.getLocation().getX() + pl.getLocation().getDirection().getX() * 6,pl.getLocation().getY() + pl.getLocation().getDirection().getY() * 6,pl.getLocation().getZ() + pl.getLocation().getDirection().getZ() * 6);
											if (pl.getWorld().getBlockAt(warploc).getType() != Material.AIR)
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_HIT, 2, 0);
												pl.sendMessage(touhouraces + ChatColor.RED + "�������\���ȏo����Ԃ������������ߓ���Ȃ������I");
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
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
/*����͗�͏���ŌL���g���r���A���e���� + ���쏢��*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("seirei")   || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("onnryou")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("hannrei")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sourei"))
				{
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 40 && pl.isSneaking() )
					{
						Material hoe_is_ok = pl.getItemInHand().getType() ; 
						if (hoe_is_ok == Material.WOOD_HOE || hoe_is_ok == Material.STONE_HOE || hoe_is_ok == Material.IRON_HOE || hoe_is_ok== Material.DIAMOND_HOE || hoe_is_ok == Material.GOLD_HOE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
								{
										pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
								}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
							}
							else
								{
								if (pl.getItemInHand().getType() == Material.GOLD_HOE)
								{
									MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("casting", casting);
									pl.sendMessage(this.plugin.touhouraces + ChatColor.LIGHT_PURPLE + "������Ăяo���A���g��ی삷��I");
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
												  pl.sendMessage(touhouraces + ChatColor.AQUA + "��̗삾�I");
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
												  pl.sendMessage(touhouraces + ChatColor.GOLD + "��̗삾�I");
											  }
											  this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
													public void run() {
														  Player pl = event.getPlayer();
														  MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
														  pl.setMetadata("using-magic", usingmagic);
														  pl.sendMessage(touhouraces + ChatColor.BLUE + "�r���̃N�[���_�E�����I���܂���");
														}
													} , 600L);
											}
										} , 20L);
									this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 40);
								    this.plugin.saveConfig();
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
									}
								else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("hannrei") && hoe_is_ok == Material.STONE_HOE )
								{
									MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("casting", casting);
									pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_PURPLE + "������r������I");
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
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
								}
								else if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sourei") && hoe_is_ok == Material.IRON_HOE )
								{
									MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
									pl.setMetadata("casting", casting);
									pl.sendMessage(this.plugin.touhouraces + ChatColor.UNDERLINE + ChatColor.BOLD + "���b�c�b�I�[�P�X�g���I�I");
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
														((Player) enemy).sendMessage(touhouraces + ChatColor.DARK_BLUE + "�T���E�E�E");
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
														((Player) enemy).sendMessage(touhouraces + ChatColor.DARK_RED + "�N����");
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
														((Player) enemy).sendMessage(touhouraces + ChatColor.GREEN + "�������I�I");
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
									pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
								}
								else
								{
								MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
								pl.setMetadata("casting", casting);
								pl.sendMessage(this.plugin.touhouraces + ChatColor.YELLOW + "���e���r�������I");
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
								pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
								}
							}
						}
					}
				}
			}
		}
	}
/*�v���C���[��MOB���N���b�N������*/
	@EventHandler(priority =EventPriority.LOWEST)
	public void on_click_MOB(final PlayerInteractEntityEvent event)
	{
		Player pl = event.getPlayer() ;
/*���l�͐l�Ԃɂ����b���Ȃ�*/
		if (event.getRightClicked().getType() == EntityType.VILLAGER)
		{
			if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngen") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("mazyo") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houraizin") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("gennzinnsin") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("misou") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sibito") == false && this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennninn") == false)
			{
				pl.sendMessage(this.plugin.touhouraces + ChatColor.GRAY + "���̃j���Q���͉���b���Ă���񂾂낤�E�E�E");
				pl.closeInventory();
				event.setCancelled(true);
			}
		}
		else
		{
			if (pl.hasMetadata("ignoreskill") == false && pl.hasPermission("thr.skill"))
			{
/*�z���S�͗�͏���ł�͂����g�����z��+������Ƃ��Ăǂ��[��*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					Material pickel_is_ok = pl.getItemInHand().getType() ; 
					if (this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 30 && pl.isSneaking() ){
						if (pickel_is_ok == Material.WOOD_PICKAXE || pickel_is_ok == Material.STONE_PICKAXE || pickel_is_ok == Material.IRON_PICKAXE || pickel_is_ok == Material.DIAMOND_PICKAXE || pickel_is_ok == Material.GOLD_PICKAXE)
						{
							if (pl.getMetadata("casting").get(0).asBoolean() == true)
							{
									pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���r�����ł��I");
							}
							else if (pl.getMetadata("using-magic").get(0).asBoolean() == true)
							{
								pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̖��@���g�p���ł��I");
							}
							else
							{
							MetadataValue casting = new FixedMetadataValue(this.plugin, true) ;
							pl.setMetadata("casting", casting);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "����\�����I");
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
											pl.sendMessage(touhouraces + ChatColor.DARK_GRAY + "������Ƃ���" + ChatColor.BOLD + ChatColor.YELLOW + "�ǂ��[��!�I�I");
											target.getWorld().playSound(target.getLocation(), Sound.NOTE_PIANO, 3, 3);
											target.sendMessage(touhouraces + ChatColor.DARK_GRAY + "������Ƃ���" + ChatColor.BOLD + ChatColor.YELLOW + "�ǂ��[��!�I�I");
											MetadataValue usingmagic = new FixedMetadataValue(this.plugin, true) ;
											pl.setMetadata("using-magic", usingmagic);
											this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
												public void run() {
														Player pl = event.getPlayer();
														Entity target = event.getRightClicked();
														if (pl.getLocation().distanceSquared(event.getRightClicked().getLocation() ) >= 150)
														{
															pl.sendMessage(touhouraces + ChatColor.BLUE + "�������������Ă��܂����I");
															target.sendMessage(touhouraces + ChatColor.BLUE + "�����؂����I");
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
										pl.sendMessage(touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
									}
									else
										{
											if (pl.getLocation().distanceSquared(target.getLocation() ) >= 80)
											{
												pl.getWorld().playSound(pl.getLocation(), Sound.SPIDER_DEATH, 2, 1);
												pl.sendMessage(touhouraces + ChatColor.BLUE + "�������������Ă��܂����I");
											}
											else if (target.getType() != EntityType.VILLAGER && target instanceof LivingEntity)
											{
												pl.sendMessage(touhouraces + ChatColor.DARK_RED + "���Ȃ��͋z�������I");
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
							pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
							}
						}
							
					}
				}
			}
		}
	}
/*�e�ł̍U���ɑ΂���hook*/
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
/*�v���C���[���_���[�W��^���鎞*/
    @EventHandler(priority=EventPriority.LOW)
	public void on_attack_entity(final EntityDamageByEntityEvent event)
	{
		if (event.getEntity() instanceof Bat && event.getEntity().hasMetadata("invincible"))
		{
			event.setDamage(0D);
			event.getDamager().sendMessage(this.plugin.touhouraces + ChatColor.RED + "�����Ă���͕̂�����������҂��܂��傤�I");
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
/*akuma�̈ł̐n(���_���[�W2�ȏ�Ō��ʂ���)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("akuma")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if (pl.getEyeLocation().getBlock().getLightLevel() <= 8 && event.getDamage() > 1D)
					{
						event.setDamage(event.getDamage() + 1D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, 152);
					}
				}
/*kyuuketuki�̍g����(���_���[�W1�ȏ�Ō��ʂ���)*/
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
/*oni�̗��_(���_���[�W1�ȏ�Ō��ʂ���)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if(pl.getLocation().distanceSquared(event.getEntity().getLocation()) <= 8D && event.getDamage() > 0D && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 25D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						event.setDamage(event.getDamage() + 2D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
					}
				}
/*youzyuu�̑_����(���_���[�W3�ȏ�Ō��ʂ���)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("youzyuu") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("siki") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("zyuuzin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo"))
				{
					if(pl.getLocation().distanceSquared(event.getEntity().getLocation()) >= 10D && event.getDamage() > 0D)
					{
						event.setDamage(event.getDamage() + 8D);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.POTION_BREAK, 2);
					}
				}
				
/*kami�̐_�ʗ�(���_���[�W10�ȉ��Ō��ʂ���)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kami") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("zyuuzin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yakusin"))
				{
					if (event.getDamage() <= 9D && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 10D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 10D);
						event.setDamage(event.getDamage() + 2D);
					}
				}
/*houzyousin�̋󕠐ӂ�*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin"))
				{
					if (Math.random() >= 0.8 && event.getEntity() instanceof Player && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 10D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						((Player) event.getEntity()).setFoodLevel(((Player) event.getEntity()).getFoodLevel() - 2);
						event.getEntity().sendMessage(this.plugin.touhouraces + ChatColor.GOLD + pl.getName() + "�͂������������������Ă����I");
					}
				}
/*sibito�̕m�����U��(���_���[�W�ɂ�����炸���ʂ���)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sibito"))
				{
					if (pl.getHealth() <= 20D)
					{
						event.setDamage(event.getDamage() + 3D);
						event.getDamager().getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_PIG_HURT, 1, 1);
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.TILE_BREAK, 49);
					}
				}
/*gennzinnsin�̊�ՃN���e�B�J��(���_���[�W�ɂ�����炸���ʂ���)*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("gennzinnsin"))
				{
					if (Math.random() > 0.7 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 5D)
					{
					    this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
						event.setDamage(event.getDamage() + 5D);
						pl.getWorld().playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
					}
				}
/*��͍Đ����̃f�o�t*/
				if (pl.getMetadata("spilituse").get(0).asDouble() < 0)
				{
					event.setDamage(event.getDamage() / 2D);
					if (pl.isSneaking())
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + pl.getName() + "�M���͗�͍Đ����[�h�̈ז{�C���o���܂���I");
					}
				}
			}
		}
		if (event.getEntity() instanceof Player)
		{
			Player pl = (Player) event.getEntity();
			if (pl.hasPermission("thr.skill"))
			{
/*�E���Ă�������̏��\��*/
				if (this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori"))
				{
					if (event.getDamage() >= pl.getHealth() && event.getDamager() instanceof Player && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 50)
					{
						    this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 50D);
							pl.sendMessage(event.getDamager().getName() + ":�̗�:" + ((Player) event.getDamager()).getHealth() + "");
							pl.sendMessage(event.getDamager().getName() + ":���W:" + event.getDamager().getLocation().getBlockX() + "," + event.getDamager().getLocation().getBlockY() + "," + event.getDamager().getLocation().getBlockZ());
							pl.sendMessage("�o��܂����E�E�E�o���ĂȂ����E�E�E");
						    String satorin0 = event.getDamager().getName();
							MetadataValue satorin00 = new FixedMetadataValue(this.plugin, satorin0) ;
							pl.setMetadata("satorin0", satorin00);
					}
				}
/*��_���M��*/
				if (this.plugin.getConfig().getString("user." + event.getEntity().getUniqueId() + ".race").toString().contains("yakusin"))
				{
					Entity killer = event.getDamager();
					if (killer instanceof Player && event.getDamage() >= pl.getHealth() && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 20)
					{
						this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 20D);
						Player killplayer = (Player) killer ;
						if (killplayer.isDead() == false)
						{
							killplayer.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���Ȃ��͖�_���M����󂯂��I�I");
							killplayer.damage(20D);
						}
					}
				}
/*����̏����ւ̋��|*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("onnryou"))
				{
					if (event.getDamage() >= pl.getHealth())
					{
						double rand = Math.random();
						if (rand > 0.7 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 40)
						{
							this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 40D);
							pl.setHealth(50D);
							pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "���������Ȃ��E�E�E��");
							if (event.getDamager() instanceof Player )
							{
								Player dpl = (Player) event.getDamager();
								dpl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "���������Ȃ��E�E�E��");
								dpl.addPotionEffect(new PotionEffect (PotionEffectType.WITHER,100,4));
							}
							pl.getWorld().playSound(pl.getLocation(), Sound.GHAST_CHARGE, 2, 2);
							event.setCancelled(true);
						}
					}
				}
/*akuma�̔���(�_���[�W5�ȏ�)�m���T���F�Œ�l�_���[�W�T*/
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
							dpl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_RED + "�M���͔������������I�I");
						}
					}
				}
/*yousei�̃O���C�Y*/
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
/*kobito�̒��O���C�Y(�������Ȃ��ƃ_���[�W��(�Q�_���[�W�ȏ�E��͂Q�O�ȉ��ł���Ƀ_���[�WUP)*/
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
/*���e�V���[�Y*/
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
							((Player)event.getEntity()).sendMessage(this.plugin.touhouraces + ChatColor.DARK_PURPLE + "��͂��z�����ꂽ�I");
						}
					}
				}
			}
		}
	}
    
/*�v���C���[���Ƃɂ����_���[�W���󂯂���*/
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
/*�C���r�W�u���̃f�o�t*/
				if(pl.hasPotionEffect(PotionEffectType.INVISIBILITY))
				{
					pl.removePotionEffect(PotionEffectType.INVISIBILITY);
					pl.playSound(pl.getLocation(), Sound.WOLF_HURT, 1, -1);
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "1.8�̃o�O�Ŗ��O�ی����Ȃ̂�ˁE�E�E");
				}
/*yousei�͗����ϐ�����*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yousei") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("satori") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kobito") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kibito"))
				{
					if (event.getCause() == DamageCause.FALL)
					{
						event.setDamage(event.getDamage() / 2);
					}
				}
/*tenngu�͗�������*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("tenngu"))
				{
					if (event.getCause() == DamageCause.FALL)
					{
						event.setCancelled(true);
					}
				}
/*akuma�͉��Ă𖳌���*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("akuma")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("oni"))
				{
					if (event.getCause() == DamageCause.FIRE_TICK)
					{
						event.setCancelled(true);
					}
				}
/*kyuuktuki�̓}�O�}��΂���������*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kyuuketuki"))
				{
					if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.LAVA)
					{
						event.setCancelled(true);
					}
				}
/*�l���A�͓��͓M���𖳌���*/
				if(pl.hasPermission("thr.skill")&& this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("ninngyo") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kappa"))
				{
					if (event.getCause() == DamageCause.DROWNING)
					{
						event.setCancelled(true);
					}
				}
/*houzyousin�͉쎀�𖳌���*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin"))
				{
					if (event.getCause() == DamageCause.STARVATION)
					{
						event.setCancelled(true);
					}
				}
/*serirei�̓V�t�g�Ŗh�䑝*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("seirei")   || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("onnryou")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("hannrei")  || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sourei"))
				{
					if (pl.isSneaking() && event.getDamage() > 3 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 5D)
					{
						this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
						event.setDamage(event.getDamage() - 3 );
					}
				}
/*kami�͂Q�_���[�W�ȏ�̍U�����P�_���[�W���炷*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("kami") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houzyousin") || this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("yakusin"))
				{
					if (event.getDamage() > 1 && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 10D && pl.getMetadata("spilituse").get(0).asDouble() > 0)
					{
						event.setDamage(event.getDamage() - 1 );
					}
				}
/*houraizin�͕�������\��������*/
				if(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("houraizin"))
				{
					if (event.getDamage() >= pl.getHealth() && this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") > 30D)
					{
						double reverse = Math.random();
								if (reverse > 0.6)
								{
									this.plugin.getConfig().set(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
									pl.setHealth(pl.getMaxHealth());
									pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "�M���͕s���̗͂��g���h�����I�I");
									pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, -1);
									event.setDamage(0D);
								}
					}
				}
/*��͍Đ����̃f�o�t*/
				if (pl.getMetadata("spilituse").get(0).asDouble() < 0)
				{
					event.setDamage(event.getDamage() * 2D);
					if (pl.isSneaking())
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + pl.getName() + "�M���͗�͍Đ����[�h�̈ה��ɏ_���ł��I");
					}
				}
			}
		}
	}
}  