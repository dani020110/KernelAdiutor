/*
 * Copyright (C) 2017 Willi Ye <williye97@gmail.com>
 * Copyright (C) 2017 Daniel Vasquez <danielgusvt@yahoo.com>
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.grarak.kerneladiutor.utils.kernel.cpuhotplug;

import android.content.Context;

import com.grarak.kerneladiutor.fragments.ApplyOnBootFragment;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.root.Control;

public class ClusterPlug {

    private static final String PARENT = "/sys/module/cluster_plug/parameters";
    private static final String ACTIVE = PARENT + "/active";
    private static final String LOADUP = PARENT + "/load_threshold_up";
    private static final String LOADOWN = PARENT + "/load_threshold_down";
    private static final String VOTEUP = PARENT + "/vote_threshold_up";
    private static final String VOTEDOWN = PARENT + "/vote_threshold_down";
    private static final String TIME = PARENT + "/sampling_time";
    private static final String LOWMODE = PARENT + "/low_power_mode";

    public static void setClusterPlugLoadThresholdUp(int value, Context context) {
        run(Control.write(String.valueOf(value), LOADUP), LOADUP, context);
    }

    public static int getClusterPlugLoadThresholdUp() {
        return Utils.strToInt(Utils.readFile(LOADUP));
    }

    public static boolean hasClusterPlugLoadThresholdUp() {
        return Utils.existFile(LOADUP);
    }

    public static void setClusterPlugLoadThresholdDown(int value, Context context) {
        run(Control.write(String.valueOf(value), LOADOWN), LOADOWN, context);
    }

    public static int getClusterPlugLoadThresholdDown() {
        return Utils.strToInt(Utils.readFile(LOADOWN));
    }

    public static boolean hasClusterPlugLoadThresholdDown() {
        return Utils.existFile(LOADOWN);
    }

    public static void setClusterPlugVoteThresholdUp(int value, Context context) {
        run(Control.write(String.valueOf(value), VOTEUP), VOTEUP, context);
    }

    public static int getClusterPlugVoteThresholdUp() {
        return Utils.strToInt(Utils.readFile(VOTEUP));
    }

    public static boolean hasClusterPlugVoteThresholdUp() {
        return Utils.existFile(VOTEDOWN);
    }

    public static void setClusterPlugVoteThresholdDown(int value, Context context) {
        run(Control.write(String.valueOf(value), VOTEDOWN), VOTEDOWN, context);
    }

    public static int getClusterPlugVoteThresholdDown() {
        return Utils.strToInt(Utils.readFile(VOTEDOWN));
    }

    public static boolean hasClusterPlugVoteThresholdDown() {
        return Utils.existFile(VOTEDOWN);
    }

    public static void setClusterPlugSamplingTime(int value, Context context) {
        run(Control.write(String.valueOf(value), TIME), TIME, context);
    }

    public static int getClusterPlugSamplingTime() {
        return Utils.strToInt(Utils.readFile(TIME));
    }

    public static boolean hasClusterPlugSamplingTime() {
        return Utils.existFile(TIME);
    }

    public static void enableLowPowerModeActive(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", LOWMODE),
                LOWMODE, context);
    }

    public static boolean isLowPowerModeEnabled() {
        return Utils.readFile(LOWMODE).equals("1");
    }

    public static boolean hasLowPowerMode() {
        return Utils.existFile(LOWMODE);
    }

    public static void enableClusterPlug(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", ACTIVE), ACTIVE, context);
    }

    public static boolean isClusterPlugEnabled() {
        return Utils.readFile(ACTIVE).equals("1");
    }

    public static boolean hasClusterPlugEnable() {
        return Utils.existFile(ACTIVE);
    }

    public static boolean supported() {
        return Utils.existFile(PARENT);
    }

    private static void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.CPU_HOTPLUG, id, context);
    }

}
