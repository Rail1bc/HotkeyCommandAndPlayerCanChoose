package com.raila23.hotkeycommandandplayercanchoose;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class HotkeyCommandAndPlayerCanChoose extends JavaPlugin implements CommandExecutor{

    public static Plugin plugin;
    public List<String> ShiftHand = this.getConfig().getStringList("Shift+Hand_command");
    public List<String> ShiftQ = this.getConfig().getStringList("ShiftQ_command");
    public List<String> F = this.getConfig().getStringList("Hand_command");
    public List<String> Q = this.getConfig().getStringList("Q_command");

    public boolean Shift_Hand = this.getConfig().getBoolean("Switch.Shift_Hand");
    public boolean Hand = this.getConfig().getBoolean("Switch.Hand");
    public boolean Switch_ShiftQ = this.getConfig().getBoolean("Switch.ShiftQ");
    public boolean Switch_Q = this.getConfig().getBoolean("Switch.Q");
    public HashMap<String, Boolean> NoNeedAll;
    public HashMap<String, Boolean> NoNeedShiftWithHand;
    public HashMap<String, Boolean> NoNeedQ;
    public HashMap<String, Boolean> NoNeedHand;
    public HashMap<String, Boolean> NoNeedShiftWithQ;

    public File fileConfig;
    public File filePlayerData;

    public YamlConfiguration ymlConfig;
    public YamlConfiguration ymlPlayerData;



    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();

        getCommand("hc").setExecutor(this);

        fileConfig = new File(this.getDataFolder(), "config.yml");
        if (!fileConfig.exists()) {
            this.saveDefaultConfig();
        }

        filePlayerData = new File(this.getDataFolder(), "playerWhoNoNeedHotkeyCommand.yml");
        if (!filePlayerData.exists()) {
            try (InputStream inputStream = getResource("playerWhoNoNeedHotkeyCommand.yml")) {
                if (inputStream != null) {
                    Files.copy(inputStream, filePlayerData.toPath());
                } else {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ymlPlayerData = YamlConfiguration.loadConfiguration(filePlayerData);
        if (ymlPlayerData == null) {
        } else {
            init();
        }

        this.getServer().getPluginManager().registerEvents(new PlayerInputListener(this), this);
    }

    private void init(){
        this.NoNeedAll = new HashMap<>();
        List<String> noNeedAllList = ymlPlayerData.getStringList("NoNeedAll");
        if (noNeedAllList != null) {
            for (String s : noNeedAllList) {
                this.NoNeedAll.put(s, true);
            }
        }
        this.NoNeedShiftWithHand = new HashMap<>();
        List<String> noNeedShiftWithHandList = ymlPlayerData.getStringList("NoNeedShiftWithHand");
        if (noNeedShiftWithHandList != null) {
            for (String s : noNeedShiftWithHandList) {
                this.NoNeedShiftWithHand.put(s, true);
            }
        }
        this.NoNeedShiftWithQ = new HashMap<>();
        List<String> noNeedShiftWithQList = ymlPlayerData.getStringList("NoNeedShiftWithQ");
        if (noNeedShiftWithQList != null) {
            for (String s : noNeedShiftWithQList) {
                this.NoNeedShiftWithQ.put(s, true);
            }
        }
        this.NoNeedQ = new HashMap<>();
        List<String> noNeedQList = ymlPlayerData.getStringList("NoNeedQ");
        if (noNeedQList!= null) {
            for (String s : noNeedQList) {
                this.NoNeedQ.put(s, true);
            }
        }
        this.NoNeedHand = new HashMap<>();
        List<String> noNeedHandList = ymlPlayerData.getStringList("NoNeedHand");
        if (noNeedHandList!= null) {
            for (String s : noNeedHandList) {
                this.NoNeedHand.put(s, true);
            }
        }
    }

    public void onDisable() {
        updateConfigList("NoNeedAll", NoNeedAll);
        updateConfigList("NoNeedShiftWithHand", NoNeedShiftWithHand);
        updateConfigList("NoNeedShiftWithQ", NoNeedShiftWithQ);
        updateConfigList("NoNeedQ", NoNeedQ);
        updateConfigList("NoNeedHand", NoNeedHand);
        try {
            ymlPlayerData.save(filePlayerData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateConfigList(String key, HashMap<String, Boolean> map) {
        List<String> list = new ArrayList<>(map.keySet());
        ymlPlayerData.set(key, list);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hc")) {
            switch (args[0]) {
                case "reload" -> {
                    this.reloadConfig();
                    this.ShiftHand = this.getConfig().getStringList("Shift+Hand_command");
                    this.F = this.getConfig().getStringList("Hand_command");
                    this.ShiftQ = this.getConfig().getStringList("ShiftQ_command");
                    this.Q = this.getConfig().getStringList("Q_command");
                    sender.sendMessage("has overloaded");
                    return true;
                }
                case "need" -> {
                    switch (args[1]) {
                        case "all" -> {
                            if (this.NoNeedAll.containsKey(sender.getName())) {
                                NoNeedAll.remove(sender.getName());
                            }
                            if (this.NoNeedShiftWithHand.containsKey(sender.getName())) {
                                NoNeedShiftWithHand.remove(sender.getName());
                            }
                            if(this.NoNeedShiftWithQ.containsKey(sender.getName())){
                                NoNeedShiftWithQ.remove(sender.getName());
                            }
                            if (this.NoNeedQ.containsKey(sender.getName())) {
                                NoNeedQ.remove(sender.getName());
                            }
                            if (this.NoNeedHand.containsKey(sender.getName())) {
                                NoNeedHand.remove(sender.getName());
                            }
                            sender.sendMessage("able to use all hotkeys");
                            return true;
                        }
                        case "shiftQ" -> {
                            if (this.NoNeedShiftWithQ.containsKey(sender.getName())) {
                                NoNeedShiftWithQ.remove(sender.getName());
                                sender.sendMessage("able to use shift with q hotkeys");
                                return true;
                            }
                        }
                        case "shiftHand" -> {
                            if (this.NoNeedShiftWithHand.containsKey(sender.getName())) {
                                NoNeedShiftWithHand.remove(sender.getName());
                                sender.sendMessage("able to use shift with hand hotkeys");
                                return true;
                            }
                        }
                        case "q" -> {
                            if (this.NoNeedQ.containsKey(sender.getName())) {
                                NoNeedQ.remove(sender.getName());
                                sender.sendMessage("able to use abandon hotkeys");
                                return true;
                            }
                        }
                        case "hand" -> {
                            if (this.NoNeedHand.containsKey(sender.getName())) {
                                NoNeedHand.remove(sender.getName());
                                sender.sendMessage("able to use swap hand hotkeys");
                                return true;
                            }
                        }
                    }
                }
                case "noneed" -> {
                    switch (args[1]) {
                        case "all" -> {
                            NoNeedAll.put(sender.getName(), true);
                            sender.sendMessage("disable to use all hotkeys");
                            return true;
                        }
                        case "shiftQ" -> {
                            NoNeedShiftWithQ.put(sender.getName(), true);
                            sender.sendMessage("disable to use shift with q hotkeys");
                            return true;
                        }
                        case "shiftHand" -> {
                            NoNeedShiftWithHand.put(sender.getName(), true);
                            sender.sendMessage("disable to use shift with hand hotkeys");
                            return true;
                        }
                        case "q" -> {
                          NoNeedQ.put(sender.getName(), true);
                          sender.sendMessage("disable to use abandon hotkeys");
                          return true;
                        }
                        case "hand" -> {
                            NoNeedHand.put(sender.getName(), true);
                            sender.sendMessage("disable to use swap hand hotkeys");
                            return true;
                        }
                    }
                }
                case "show" -> {
                    if(NoNeedAll.containsKey(sender.getName()))sender.sendMessage("disable to use all hotkeys");
                    if(NoNeedShiftWithQ.containsKey(sender.getName()))sender.sendMessage("disable to use shift with q hotkeys");
                    if(NoNeedShiftWithHand.containsKey(sender.getName()))sender.sendMessage("disable to use shift with hand hotkeys");
                    if(NoNeedQ.containsKey(sender.getName()))sender.sendMessage("disable to use abandon hotkeys");
                    if(NoNeedHand.containsKey(sender.getName()))sender.sendMessage("disable to use swap hand hotkeys");
                    return true;
                }
            }
        }
        else {
            return false;
        }
        return false;
    }

}
