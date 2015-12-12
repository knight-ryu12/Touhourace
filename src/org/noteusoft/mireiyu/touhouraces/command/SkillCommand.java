package org.noteusoft.mireiyu.touhouraces.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.noteusoft.mireiyu.touhouraces.THRPlugin;

public class SkillCommand implements CommandExecutor
{
	private THRPlugin plugin;

	public SkillCommand(THRPlugin plugin)
	{
		this.plugin = plugin;
	}
/*�R�}���h*/
/*��{*/
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command mcd, String commandLabel, String[] args)
	{
		if ((commandLabel.equalsIgnoreCase("touhouraces") && (sender instanceof Player) && args.length == 0))
		{
			Player pl = (Player)sender;
			pl.sendMessage(this.plugin + "Version " + this.plugin.pdfFile.getVersion() + "���:" + this.plugin.pdfFile.getAuthors().toString());
		}
		else if ((commandLabel.equalsIgnoreCase("thr"))  && (sender instanceof Player) && args.length == 0)
		{
			Player pl = (Player)sender;
			pl.sendMessage(this.plugin + "Version " + this.plugin.pdfFile.getVersion() + "���:" + this.plugin.pdfFile.getAuthors().toString());
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("reload")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.reload"))
				{
					this.plugin.reloadConfig();
					sender.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "�����[�h���܂���.");
				}
			}
			else
			{
				this.plugin.reloadConfig();
				sender.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "�����[�h���܂���.");
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("help")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.help") || pl.hasPermission("thr.user"))
				{
					sender.sendMessage(ChatColor.DARK_AQUA + this.plugin.touhouraces + "��6�\�ȃv���O�C���ꗗ");
					sender.sendMessage(ChatColor.AQUA + "touhouraces/thr : �o�[�W��������");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr reload : �����[�h");
					sender.sendMessage(ChatColor.AQUA + "thr mana : ���݃}�i�m�F");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr healmana [num] : �}�i��num���񕜂���");
					sender.sendMessage(ChatColor.AQUA + "thr setmana [num] : �}�i��num�ɂ���");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr toggleskill : �s���n�̃X�L���̔������g�O������");
					sender.sendMessage(ChatColor.AQUA + "thr setpoint [num] : �����̃|�C���g�i�g�����͔C�Ӂj��ݒ肷��");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr addpoint [num] : �����̃|�C���g�i�g�����͔C�Ӂj��ǉ�����");
					sender.sendMessage(ChatColor.AQUA + "thr setpoint [playername] [num] : playername�̃|�C���g�i�g�����͔C�Ӂj��ݒ肷��");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr addpoint [playername] [num] : playername�̃|�C���g�i�g�����͔C�Ӂj��ǉ�����");
					sender.sendMessage(ChatColor.AQUA + "thr steppoint [max] : �|�C���g�i�g�����͔C�Ӂj��max������Ƃ���1�㏸����");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr steppoint [playername] [max] : playername�̃|�C���g�i�g�����͔C�Ӂj��max������Ƃ���1�㏸����");
					sender.sendMessage(ChatColor.AQUA + "thr setrace  [�����푰��] : �����̎푰���푰���i�������j�ɕύX����");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr setrace [playername] [�����푰��] : playername�̎푰���푰���i�������j�ɕύX����");
					sender.sendMessage(ChatColor.AQUA + "thr setrace [playername] [�����푰��] : playername�̎푰���푰���i�������j�ɕύX����");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr setrace [playername] [�����푰��] : playername�̎푰���푰���i�������j�ɕύX����");
					sender.sendMessage(ChatColor.AQUA + "thr setrace [playername] [�����푰��] : playername�̎푰���푰���i�������j�ɕύX����");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr info : playername�̎푰�̏���\������");
					sender.sendMessage(ChatColor.AQUA + "thr evolinfo [�����푰��] : �푰�̏���\������");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr evollist : playername�̐i���ł���푰�̃��X�g��\������");
					sender.sendMessage(ChatColor.AQUA + "thr evolchange [�����푰��] : �푰�̐i�������݂�");
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̃R�}���h�̓v���C���[�̂ݍs�����Ƃ��o���܂��B");
			}
		}
/*�g�O��*/
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("toggleskill")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if (pl.hasPermission("thr.toggleskill") || pl.hasPermission("thr.user"))
				{
					if (pl.hasMetadata("ignoreskill"))
					{
						pl.removeMetadata("ignoreskill", this.plugin);
						pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_AQUA + "�s���X�L���͍Ăє������܂�"  ); 
					}
					else
					{
						MetadataValue ignoreskill = new FixedMetadataValue(this.plugin, true) ;
						pl.setMetadata("ignoreskill", ignoreskill);
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "�s���X�L���𕕈󂵂܂���"  ); 
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
/*�}�i�֘A*/
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("mana")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if (pl.hasPermission("thr.mana") || pl.hasPermission("thr.user"))
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̃R�}���h�̓v���C���[�̂ݍs�����Ƃ��o���܂��B");
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("healmana")) && (args.length == 2))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if (pl.hasPermission("thr.healmana"))
				{
					
					this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") + Integer.parseInt(args[1]));
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̃R�}���h�̓v���C���[�̂ݍs�����Ƃ��o���܂��B");
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setmana")) && (args.length == 2))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if (pl.hasPermission("thr.setmana"))
				{
					
					this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", Integer.parseInt(args[1]));
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̃R�}���h�̓v���C���[�̂ݍs�����Ƃ��o���܂��B");
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("healmana")) && (args.length == 3))
		{
			if ((sender instanceof Player))
			{
				Player pl = Bukkit.getPlayer(args[2]);
				if (pl != null)
				{
					if (pl.hasPermission("thr.setmana"))
					{
						
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit" , this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") + Integer.parseInt(args[1]));
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
					}
				}
			}
			else
			{
				Player pl = Bukkit.getPlayer(args[2]);
				if (pl != null)
				{
					this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit" , this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") + Integer.parseInt(args[1]));
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setmana")) && (args.length == 3))
		{
			if ((sender instanceof Player))
			{
				Player pl = Bukkit.getPlayer(args[2]);
				if (pl != null)
				{
					if (pl.hasPermission("thr.setmana"))
					{
						
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", Integer.parseInt(args[1]));
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
					}
				}
			}
			else
			{
				Player pl = Bukkit.getPlayer(args[2]);
				if (pl != null)
				{
					this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit" , Integer.parseInt(args[1]));
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "��́F" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
			}
		}
/*�|�C���g�֘A*/
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setpoint")) && (args.length == 2))
		{
			if (sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setpoint"))
				{
					if (Bukkit.getPlayer(args[1]) != null)
					{
						int point = Integer.parseInt(args[1]);
						this.plugin.getConfig().set("user." + commander.getUniqueId() + ".point" ,point);
						this.plugin.saveConfig();
				    	commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��̓|�C���g��" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".point") + "�ɂȂ�܂����B");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setpoint")) && (args.length == 3))
		{
			if(sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setpoint"))
				{
					if (Bukkit.getPlayer(args[1]) != null)
					{
						Player pl = Bukkit.getPlayer(args[1]);
						int point = Integer.parseInt(args[2]);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".point" ,point);
				    	this.plugin.saveConfig();
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "�̃|�C���g��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "�ɂ��܂����B");
						pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��̓|�C���g��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "�ɂȂ�܂����B");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("addpoint")) && (args.length == 2))
		{
			if (sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setpoint"))
				{
					int point = Integer.parseInt(args[1]);
					this.plugin.getConfig().set("user." + commander.getUniqueId() + ".point" ,this.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point")  + point);
					this.plugin.saveConfig();
			    	commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��̓|�C���g��" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".point") + "�ɂȂ�܂����B");
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("addpoint")) && (args.length == 3))
		{
			if(sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setpoint"))
				{
					if (Bukkit.getPlayer(args[1]) != null)
					{
						Player pl = Bukkit.getPlayer(args[1]);
						int point = Integer.parseInt(args[2]);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".point" ,this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point")  + point);
				    	this.plugin.saveConfig();
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "�̃|�C���g��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "�ɂ��܂����B");
						pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��̓|�C���g��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "�ɂȂ�܂����B");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("steppoint")) && (args.length == 2))
		{
			if (sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.steppoint"))
				{
					if (this.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point") < Integer.parseInt(args[1]))
					{
						this.plugin.getConfig().set("user." + commander.getUniqueId() + ".point" ,this.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point")  + 1);
						this.plugin.saveConfig();
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��̓|�C���g��" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".point") + "�ɂȂ�܂����B");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("steppoint")) && (args.length == 3))
		{
			if(sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.steppoint"))
				{
					if (Bukkit.getPlayer(args[1]) != null)
					{
						Player pl = Bukkit.getPlayer(args[1]);
						if (this.plugin.getConfig().getInt("user." + commander.getUniqueId() + ".point") < Integer.parseInt(args[2]))
						{
							this.plugin.getConfig().set("user." + pl.getUniqueId() + ".point" ,this.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".point")  + 1);
							this.plugin.saveConfig();
							commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "�̃|�C���g��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "�ɂ��܂����B");
							pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��̓|�C���g��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "�ɂȂ�܂����B");
						}
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
/*�푰�֘A*/
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setrace")) && (args.length == 2))
		{
			if (sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setrace"))
				{
					this.plugin.getConfig().set("user." + commander.getUniqueId() + ".race" , args[1].toString());
					this.plugin.saveConfig();
			    	commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��͎푰��" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".race") + "�ɂȂ�܂����B");
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setrace")) && (args.length == 3))
		{
			if(sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setrace"))
				{
					if (Bukkit.getPlayer(args[1]) != null)
					{
						Player pl = Bukkit.getPlayer(args[1]);
						this.plugin.getConfig().set("user." + pl.getUniqueId() + ".race" , args[2].toString());
						this.plugin.saveConfig();
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "�̎푰��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race") + "�ɂ��܂����B");
						pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "���Ȃ��͎푰��" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race") + "�ɂȂ�܂����B");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���I");
				}
			}
			
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("evollist")) && (args.length == 1))
		{
			if(sender instanceof Player)
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.evol.user.list") || pl.hasPermission("thr.user"))
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "�̐i���ł���惊�X�g");
					List<String> evolraces = new ArrayList<String>();
					for (String race :this.plugin. getConfig().getConfigurationSection("race").getKeys(false))
					{
						if (this.plugin.getConfig().getString("race." + race + ".racetype.root").contains(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race"))) evolraces.add(race);
					}
					for (String evolrace : evolraces)
					{
						pl.sendMessage(this.plugin.getConfig().getString("race." + evolrace + ".display.real") + "�F����name��" + evolrace);
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���B");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("evolinfo")) && (args.length == 2))
		{
			if(sender instanceof Player)
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.evol.user.info") || pl.hasPermission("thr.user"))
				{
					boolean existrace = false;
					String inforace = "";
					for (String race : this.plugin.getConfig().getConfigurationSection("race").getKeys(false))
					{
						if (race.toLowerCase().contains(args[1].toLowerCase()))
						{
							existrace = true;
							inforace = race;
							break;
						}
					}
					if (existrace)
					{
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".display.real") + "�F����name��" + inforace + "�i" + this.plugin.getConfig().getString("race." + inforace + ".display.tag") + "�j�̏��");
						pl.sendMessage("���푰�F" + this.plugin.getConfig().getString("race." + inforace + ".racetype.root") );
						pl.sendMessage("�����N�F" + this.plugin.getConfig().getString("race." + inforace + ".racetype.rank") );
						pl.sendMessage("�i���ɕK�v�Ȑi���̌��ЁF" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.shard") );
						pl.sendMessage("�i���ɕK�v�Ȑi���̕�΁F" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.crystal") );
						pl.sendMessage("�i���ɕK�v�Ȏ푰�f�ށF" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount") + "��" + Material.getMaterial(this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.typeid")) + "(���^" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.meta") + "�j");	
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.story") );
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.skills"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̎푰����name�͑��݂��܂���B�i����̂��̂�m�肽����� /thr evollist �����܂��傤�B");
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���B");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("info")) && (args.length == 1))
		{
			if(sender instanceof Player)
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.info") || pl.hasPermission("thr.user"))
				{
					boolean existrace = false;
					String inforace = "";
					for (String race : this.plugin.getConfig().getConfigurationSection("race").getKeys(false))
					{
						if (race.toLowerCase().contains(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toLowerCase()))
						{
							existrace = true;
							inforace = race;
							break;
						}
					}
					if (existrace)
					{
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".display.real") + "�F����name��" + inforace + "�i" + this.plugin.getConfig().getString("race." + inforace + ".display.tag") + "�j�̏��");
						pl.sendMessage("���푰�F" + this.plugin.getConfig().getString("race." + inforace + ".racetype.root") );
						pl.sendMessage("�����N�F" + this.plugin.getConfig().getString("race." + inforace + ".racetype.rank") );
						pl.sendMessage("�i���ɕK�v�Ȑi���̌��ЁF" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.shard") );
						pl.sendMessage("�i���ɕK�v�Ȑi���̕�΁F" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.crystal") );
						pl.sendMessage("�i���ɕK�v�Ȏ푰�f�ށF" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount") + "��" + Material.getMaterial(this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.typeid")) + "(���^" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.meta") + "�j");	
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.story") );
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.skills"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̎푰����name�͑��݂��܂���B�Ȃ�łł��傤�H");
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���B");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("evolchange")) && (args.length == 2))
		{
			if(sender instanceof Player)
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.evol.user.change") || pl.hasPermission("thr.user"))
				{
					boolean existrace = false;
					String inforace = "";
					for (String race : this.plugin.getConfig().getConfigurationSection("race").getKeys(false))
					{
						if (race.toLowerCase().contains(args[1].toLowerCase()))
						{
							existrace = true;
							inforace = race;
							break;
						}
					}
					if (existrace)
					{
						if (this.plugin.getConfig().getString("race." + inforace + ".racetype.root").contains(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race")))
						{
							PlayerInventory inventory = pl.getInventory();
							int ok_shard = 0;
							int ok_crystal = 0;
							int ok_raceitem = 0;
							ItemStack shard = null;
							ItemStack crystal = null;
							ItemStack raceitem = null;
							if (this.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.shard") != 0) 
							{
								shard = new ItemStack(Material.PRISMARINE_SHARD,this.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.shard"));
								if (inventory.contains(shard)) ok_shard = 1;
								else ok_shard = 2;
							}
							if (this.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.crystal") != 0)
							{
								crystal = new ItemStack(Material.PRISMARINE_CRYSTALS,this.plugin.getConfig().getInt("race." + inforace + ".evol.evolpoint.crystal"));
								if (inventory.contains(crystal)) ok_crystal = 1;
								else ok_shard = 2;
							}
							if (this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount") != 0)
							{
								raceitem = new ItemStack(Material.getMaterial(this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.typeid")),this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount"));
								int raceitemmeta = this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.meta");
								raceitem.setDurability((short) raceitemmeta);
								if (inventory.contains(raceitem)) ok_raceitem = 1;
								else ok_raceitem = 2;
							}
							if (ok_shard == 2 || ok_crystal == 2 || ok_raceitem == 2) pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̎푰�ɐi������ׂ̃A�C�e��������܂���I");
							else
							{
								pl.playSound(pl.getLocation(),Sound.LEVEL_UP, 2, 0);
								if (ok_shard == 1)
								{
									for (ItemStack item : inventory.getContents())
									{
										if (item.getType() == shard.getType())
										{
											if (item.getAmount() > shard.getAmount())
											{
												item.setAmount(item.getAmount() - shard.getAmount());
											}
											else
											{
												inventory.remove(item);
											}
										}
										break;
									}
								}
								if (ok_crystal == 1)
								{
									for (ItemStack item : inventory.getContents())
									{
										if (item.getType() == crystal.getType())
										{
											if (item.getAmount() > crystal.getAmount())
											{
												item.setAmount(item.getAmount() - crystal.getAmount());
											}
											else
											{
												inventory.remove(item);
											}
										}
										break;
									}
								}
								if (ok_raceitem == 1) 
								{
									for (ItemStack item : inventory.getContents())
									{
										if (item.getType() == raceitem.getType())
										{
											if (item.getAmount() > raceitem.getAmount())
											{
												item.setAmount(item.getAmount() - raceitem.getAmount());
											}
											else
											{
												inventory.remove(item);
											}
										}
										break;
									}
								}
								this.plugin.getConfig().set("user." + pl.getUniqueId() + ".race", inforace);
								this.plugin.saveConfig();
								Bukkit.broadcastMessage(this.plugin.touhouraces + ChatColor.BLUE + pl.getName() + "��" + this.plugin.getConfig().getString("race." + inforace + ".racetype.root") + "����" + this.plugin.getConfig().getString("race." + inforace + ".display.real") + "�ɐi�������I�I");
								ItemStack rewarditem = null;
								if (this.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.amount") != 0)
								{
									rewarditem = new ItemStack(Material.getMaterial(this.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.typeid")),this.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.amount"));
									int rewarditemmeta = this.plugin.getConfig().getInt("race." + inforace + ".evol.rewarditem.meta");
									rewarditem.setDurability((short) rewarditemmeta);
									pl.getInventory().addItem(rewarditem);
								}
								
							}
						}
						else
						{
							pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "�i���ł���푰�ł͂���܂���I /thr evollist �����܂��傤�B");
						}
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "���̎푰����name�͑��݂��܂���B /thr evollist �����܂��傤�B");
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "����������܂���B");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("touhouraces")) && (args.length >= 1))
		{
			sender.sendMessage(this.plugin.touhouraces + "��c thr�����g���������B");
		}
		return true;
	}
}