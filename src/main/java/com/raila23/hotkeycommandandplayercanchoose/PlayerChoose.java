package com.raila23.hotkeycommandandplayercanchoose;

public class PlayerChoose {
    private HotkeyCommandAndPlayerCanChoose plugin;
    public PlayerChoose(HotkeyCommandAndPlayerCanChoose plugin){
        this.plugin = plugin;
    }

    public boolean PlayerNoNeedAll(String name) {
        return this.plugin.NoNeedAll.containsKey(name);
    }

    public boolean PlayerNoNeedShiftWithQ(String name) {
        return this.plugin.NoNeedShiftWithQ.containsKey(name);
    }

    public boolean PlayerNoNeedShiftWithHand(String name) {
        return this.plugin.NoNeedShiftWithHand.containsKey(name);
    }

    public boolean PlayerNoNeedQ(String name) {
        return this.plugin.NoNeedQ.containsKey(name);
    }

    public boolean PlayerNoNeedHand(String name) {
        return this.plugin.NoNeedHand.containsKey(name);
    }
}
