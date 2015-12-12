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
/*コマンド*/
/*基本*/
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command mcd, String commandLabel, String[] args)
	{
		if ((commandLabel.equalsIgnoreCase("touhouraces") && (sender instanceof Player) && args.length == 0))
		{
			Player pl = (Player)sender;
			pl.sendMessage(this.plugin + "Version " + this.plugin.pdfFile.getVersion() + "作者:" + this.plugin.pdfFile.getAuthors().toString());
		}
		else if ((commandLabel.equalsIgnoreCase("thr"))  && (sender instanceof Player) && args.length == 0)
		{
			Player pl = (Player)sender;
			pl.sendMessage(this.plugin + "Version " + this.plugin.pdfFile.getVersion() + "作者:" + this.plugin.pdfFile.getAuthors().toString());
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("reload")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.reload"))
				{
					this.plugin.reloadConfig();
					sender.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "リロードしました.");
				}
			}
			else
			{
				this.plugin.reloadConfig();
				sender.sendMessage(this.plugin.touhouraces + ChatColor.GOLD + "リロードしました.");
			}
		}
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("help")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if(pl.hasPermission("thr.help") || pl.hasPermission("thr.user"))
				{
					sender.sendMessage(ChatColor.DARK_AQUA + this.plugin.touhouraces + "§6可能なプラグイン一覧");
					sender.sendMessage(ChatColor.AQUA + "touhouraces/thr : バージョン説明");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr reload : リロード");
					sender.sendMessage(ChatColor.AQUA + "thr mana : 現在マナ確認");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr healmana [num] : マナをnum分回復する");
					sender.sendMessage(ChatColor.AQUA + "thr setmana [num] : マナをnumにする");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr toggleskill : 行動系のスキルの発動をトグルする");
					sender.sendMessage(ChatColor.AQUA + "thr setpoint [num] : 自分のポイント（使い方は任意）を設定する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr addpoint [num] : 自分のポイント（使い方は任意）を追加する");
					sender.sendMessage(ChatColor.AQUA + "thr setpoint [playername] [num] : playernameのポイント（使い方は任意）を設定する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr addpoint [playername] [num] : playernameのポイント（使い方は任意）を追加する");
					sender.sendMessage(ChatColor.AQUA + "thr steppoint [max] : ポイント（使い方は任意）をmaxを上限として1上昇する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr steppoint [playername] [max] : playernameのポイント（使い方は任意）をmaxを上限として1上昇する");
					sender.sendMessage(ChatColor.AQUA + "thr setrace  [内部種族名] : 自分の種族を種族名（内部名）に変更する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					sender.sendMessage(ChatColor.AQUA + "thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					sender.sendMessage(ChatColor.AQUA + "thr setrace [playername] [内部種族名] : playernameの種族を種族名（内部名）に変更する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr info : playernameの種族の情報を表示する");
					sender.sendMessage(ChatColor.AQUA + "thr evolinfo [内部種族名] : 種族の情報を表示する");
					sender.sendMessage(ChatColor.DARK_AQUA + "thr evollist : playernameの進化できる種族のリストを表示する");
					sender.sendMessage(ChatColor.AQUA + "thr evolchange [内部種族名] : 種族の進化を試みる");
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "このコマンドはプレイヤーのみ行うことが出来ます。");
			}
		}
/*トグル*/
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
						pl.sendMessage(this.plugin.touhouraces + ChatColor.DARK_AQUA + "行動スキルは再び発動します"  ); 
					}
					else
					{
						MetadataValue ignoreskill = new FixedMetadataValue(this.plugin, true) ;
						pl.setMetadata("ignoreskill", ignoreskill);
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "行動スキルを封印しました"  ); 
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
				}
			}
		}
/*マナ関連*/
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("mana")) && (args.length == 1))
		{
			if ((sender instanceof Player))
			{
				Player pl = ((Player) sender).getPlayer();
				if (pl.hasPermission("thr.mana") || pl.hasPermission("thr.user"))
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "このコマンドはプレイヤーのみ行うことが出来ます。");
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
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "このコマンドはプレイヤーのみ行うことが出来ます。");
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
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
				}
			}
			else
			{
				sender.sendMessage(this.plugin.touhouraces + ChatColor.RED + "このコマンドはプレイヤーのみ行うことが出来ます。");
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
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
					}
				}
			}
			else
			{
				Player pl = Bukkit.getPlayer(args[2]);
				if (pl != null)
				{
					this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit" , this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") + Integer.parseInt(args[1]));
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
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
						pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
					}
				}
			}
			else
			{
				Player pl = Bukkit.getPlayer(args[2]);
				if (pl != null)
				{
					this.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit" , Integer.parseInt(args[1]));
					pl.sendMessage(this.plugin.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + this.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit"));
				}
			}
		}
/*ポイント関連*/
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
				    	commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたはポイントが" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".point") + "になりました。");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "のポイントを" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "にしました。");
						pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたはポイントが" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "になりました。");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
			    	commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたはポイントが" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".point") + "になりました。");
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "のポイントを" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "にしました。");
						pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたはポイントが" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "になりました。");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたはポイントが" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".point") + "になりました。");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
							commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "のポイントを" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "にしました。");
							pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたはポイントが" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".point") + "になりました。");
						}
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
				}
			}
		}
/*種族関連*/
		else if ((commandLabel.equalsIgnoreCase("thr")) && (args[0].equalsIgnoreCase("setrace")) && (args.length == 2))
		{
			if (sender instanceof Player)
			{
				Player commander = ((Player) sender).getPlayer();
				if(commander.hasPermission("thr.setrace"))
				{
					this.plugin.getConfig().set("user." + commander.getUniqueId() + ".race" , args[1].toString());
					this.plugin.saveConfig();
			    	commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたは種族が" + this.plugin.getConfig().getString("user." + commander.getUniqueId() + ".race") + "になりました。");
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
						commander.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "の種族を" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race") + "にしました。");
						pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + "あなたは種族が" + this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race") + "になりました。");
					}
				}
				else
				{
					commander.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません！");
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
					pl.sendMessage(this.plugin.touhouraces + ChatColor.AQUA + pl.getName() + "の進化できる先リスト");
					List<String> evolraces = new ArrayList<String>();
					for (String race :this.plugin. getConfig().getConfigurationSection("race").getKeys(false))
					{
						if (this.plugin.getConfig().getString("race." + race + ".racetype.root").contains(this.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race"))) evolraces.add(race);
					}
					for (String evolrace : evolraces)
					{
						pl.sendMessage(this.plugin.getConfig().getString("race." + evolrace + ".display.real") + "：内部name＞" + evolrace);
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません。");
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
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".display.real") + "：内部name＞" + inforace + "（" + this.plugin.getConfig().getString("race." + inforace + ".display.tag") + "）の情報");
						pl.sendMessage("元種族：" + this.plugin.getConfig().getString("race." + inforace + ".racetype.root") );
						pl.sendMessage("ランク：" + this.plugin.getConfig().getString("race." + inforace + ".racetype.rank") );
						pl.sendMessage("進化に必要な進化の欠片：" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.shard") );
						pl.sendMessage("進化に必要な進化の宝石：" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.crystal") );
						pl.sendMessage("進化に必要な種族素材：" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount") + "個の" + Material.getMaterial(this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.typeid")) + "(メタ" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.meta") + "）");	
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.story") );
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.skills"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "その種族内部nameは存在しません。進化先のものを知りたければ /thr evollist を見ましょう。");
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません。");
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
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".display.real") + "：内部name＞" + inforace + "（" + this.plugin.getConfig().getString("race." + inforace + ".display.tag") + "）の情報");
						pl.sendMessage("元種族：" + this.plugin.getConfig().getString("race." + inforace + ".racetype.root") );
						pl.sendMessage("ランク：" + this.plugin.getConfig().getString("race." + inforace + ".racetype.rank") );
						pl.sendMessage("進化に必要な進化の欠片：" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.shard") );
						pl.sendMessage("進化に必要な進化の宝石：" + this.plugin.getConfig().getString("race." + inforace + ".evol.evolpoint.crystal") );
						pl.sendMessage("進化に必要な種族素材：" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.amount") + "個の" + Material.getMaterial(this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.typeid")) + "(メタ" + this.plugin.getConfig().getInt("race." + inforace + ".evol.raceitem.meta") + "）");	
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.story") );
						pl.sendMessage(this.plugin.getConfig().getString("race." + inforace + ".intro.skills"));
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "その種族内部nameは存在しません。なんででしょう？");
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません。");
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
							if (ok_shard == 2 || ok_crystal == 2 || ok_raceitem == 2) pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "その種族に進化する為のアイテムがありません！");
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
								Bukkit.broadcastMessage(this.plugin.touhouraces + ChatColor.BLUE + pl.getName() + "は" + this.plugin.getConfig().getString("race." + inforace + ".racetype.root") + "から" + this.plugin.getConfig().getString("race." + inforace + ".display.real") + "に進化した！！");
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
							pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "進化できる種族ではありません！ /thr evollist を見ましょう。");
						}
					}
					else
					{
						pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "その種族内部nameは存在しません。 /thr evollist を見ましょう。");
					}
				}
				else
				{
					pl.sendMessage(this.plugin.touhouraces + ChatColor.RED + "権限がありません。");
				}
			}
		}
		else if ((commandLabel.equalsIgnoreCase("touhouraces")) && (args.length >= 1))
		{
			sender.sendMessage(this.plugin.touhouraces + "§c thrをお使い下さい。");
		}
		return true;
	}
}