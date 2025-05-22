package com.pumpfun.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PumpFunDummyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("PumpFunDummyPlugin enabled.");
        
        // Register the deploytoken command
        PluginCommand deployTokenCommand = getCommand("deploytoken");
        if (deployTokenCommand == null) {
            getLogger().severe("Command 'deploytoken' not found in plugin.yml! Check plugin.yml configuration.");
            return;
        }
        
        DeployTokenCommand executor = new DeployTokenCommand();
        deployTokenCommand.setExecutor(executor);
        getLogger().info("Command 'deploytoken' registered with executor: " + executor.getClass().getName());
        
        // Set command properties
        deployTokenCommand.setDescription("Simulate token deployment on pump.fun");
        deployTokenCommand.setUsage("/<command> <TICKER> <Name>");
        deployTokenCommand.setPermission("pumpfun.deploytoken");
        deployTokenCommand.setPermissionMessage("You do not have permission to use this command.");
    }

    @Override
    public void onDisable() {
        getLogger().info("PumpFunDummyPlugin disabled.");
    }

    private class DeployTokenCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            getLogger().info("DeployTokenCommand executed by: " + sender.getName() + " with args: " + (args.length > 0 ? String.join(" ", args) : "no args"));
            
            try {
                if (!(sender instanceof Player)) {
                    TextComponent message = new TextComponent(ChatColor.RED + "This command can only be used by players.");
                    sender.spigot().sendMessage(message);
                    return true;
                }

                Player player = (Player) sender;

                if (args.length != 2) {
                    TextComponent message = new TextComponent(ChatColor.RED + "Usage: /" + label + " <TICKER> <Name>");
                    player.spigot().sendMessage(message);
                    return true;
                }

                String ticker = args[0];
                String name = args[1];

                if (ticker.equalsIgnoreCase("test") && name.equalsIgnoreCase("test")) {
                    // Start token creation animation
                    new BukkitRunnable() {
                        int step = 0;
                        String[] spinner = {"|", "/", "-", "\\"};

                        @Override
                        public void run() {
                            TextComponent playerMessage;
                            TextComponent broadcastMessage;
                            switch (step) {
                                case 0:
                                    playerMessage = new TextComponent(ChatColor.YELLOW + "[PumpFun] Initiating token creation... " + spinner[step % 4]);
                                    broadcastMessage = new TextComponent(ChatColor.YELLOW + "[PumpFun] " + player.getName() + " is initiating token creation... " + spinner[step % 4]);
                                    player.spigot().sendMessage(playerMessage);
                                    getServer().spigot().broadcast(broadcastMessage);
                                    break;
                                case 1:
                                    playerMessage = new TextComponent(ChatColor.YELLOW + "[PumpFun] Configuring token parameters... " + spinner[step % 4]);
                                    broadcastMessage = new TextComponent(ChatColor.YELLOW + "[PumpFun] Configuring token for " + player.getName() + "... " + spinner[step % 4]);
                                    player.spigot().sendMessage(playerMessage);
                                    getServer().spigot().broadcast(broadcastMessage);
                                    break;
                                case 2:
                                    playerMessage = new TextComponent(ChatColor.GREEN + "[PumpFun] Deploying to blockchain... [##------]");
                                    broadcastMessage = new TextComponent(ChatColor.GREEN + "[PumpFun] Deploying token for " + player.getName() + "... [##------]");
                                    player.spigot().sendMessage(playerMessage);
                                    getServer().spigot().broadcast(broadcastMessage);
                                    break;
                                case 3:
                                    playerMessage = new TextComponent(ChatColor.AQUA + "[PumpFun] Finalizing deployment... [######--]");
                                    broadcastMessage = new TextComponent(ChatColor.AQUA + "[PumpFun] Finalizing token for " + player.getName() + "... [######--]");
                                    player.spigot().sendMessage(playerMessage);
                                    getServer().spigot().broadcast(broadcastMessage);
                                    break;
                                case 4:
                                    // Create clickable link
                                    TextComponent link = new TextComponent("https://pump.fun/coin/6Bp5AeAXLvPwPF63Gx8esW5TfyAAxX5kAMP7kB4CYCVX");
                                    link.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                                    link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://pump.fun/coin/6Bp5AeAXLvPwPF63Gx8esW5TfyAAxX5kAMP7kB4CYCVX"));
                                    link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                                        new ComponentBuilder("Click to visit!").color(net.md_5.bungee.api.ChatColor.GREEN).create()));

                                    TextComponent message = new TextComponent(ChatColor.GREEN + "[PumpFun] " + player.getName() + ", your token '" + ticker + "' is deployed! Check it here: ");
                                    message.addExtra(link);

                                    player.spigot().sendMessage(message);
                                    getServer().spigot().broadcast(message);
                                    getLogger().info("Successfully broadcasted token deployment message for player: " + player.getName());
                                    cancel(); // Stop the task
                                    break;
                            }
                            step++;
                        }
                    }.runTaskTimer(PumpFunDummyPlugin.this, 0L, 16L); // Run every 0.8 seconds (16 ticks)
                } else {
                    TextComponent message = new TextComponent(ChatColor.RED + "Usage: /" + label + " test test");
                    player.spigot().sendMessage(message);
                }
            } catch (Exception e) {
                getLogger().severe("Error executing deploytoken command: " + e.getMessage());
                TextComponent message = new TextComponent(ChatColor.RED + "An error occurred while executing the command. Please try again later.");
                sender.spigot().sendMessage(message);
                e.printStackTrace();
            }

            return true;
        }
    }
}