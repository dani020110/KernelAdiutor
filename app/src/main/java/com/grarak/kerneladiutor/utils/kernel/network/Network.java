/*
 * Copyright (C) 2015-2017 Willi Ye <williye97@gmail.com>
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

package com.grarak.kerneladiutor.utils.kernel.network;

import android.content.Context;

import com.grarak.kerneladiutor.fragments.ApplyOnBootFragment;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.root.Control;
import com.grarak.kerneladiutor.utils.root.RootUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Created by dani020110 on 31.08.16.
 */

public class Network {
    private static final String TCP_AVAILABLE_CONGESTIONS = "/proc/sys/net/ipv4/tcp_available_congestion_control";
    private static final String HOSTNAME_KEY = "net.hostname";

    private static final String ARP = "/sys/arp_project";
    private static final String ARP_ENABLE = ARP + "/arp_project_enable";
    private static final String ARP_VERSION = ARP + "/arp_project_version";
    private static final String ARP_ATTACKER_HA = ARP + "/detected_attacker_ha";
    private static final String ARP_IGNORE_REP = ARP + "/ignore_gw_update_by_reply";
    private static final String ARP_IGNORE_REQ = ARP + "/ignore_gw_update_by_request";
    private static final String ARP_IGNORE_PRX = ARP + "/ignore_proxy_arp";
    private static final String ARP_CLR_HW = ARP + "/clear_attacker_ha";

    public static void setHostname(String value, Context context) {
        run(Control.setProp(HOSTNAME_KEY, value), HOSTNAME_KEY, context);
    }

    public static String getHostname() {
        return RootUtils.getProp(HOSTNAME_KEY);
    }

    public static void setTcpCongestion(String tcpCongestion, Context context) {
        run("sysctl -w net.ipv4.tcp_congestion_control=" + tcpCongestion, TCP_AVAILABLE_CONGESTIONS, context);
    }

    public static String getTcpCongestion() {
        return getTcpAvailableCongestions().get(0);
    }

    public static List<String> getTcpAvailableCongestions() {
        return new ArrayList<>(Arrays.asList(Utils.readFile(TCP_AVAILABLE_CONGESTIONS).split(" ")));
    }

    private static void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.NETWORK, id, context);
    }

    public static boolean isArpSupported() {
        return Utils.existFile(ARP);
    }

    public static void enableArp(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", ARP_ENABLE), ARP_ENABLE, context);
    }

    public static boolean isArpEnabled() {
        return Utils.readFile(ARP_ENABLE).equals("1");
    }

    public static String getArpVersion() {
        return Utils.readFile(ARP_VERSION);
    }

    public static String getArpAttackerHa() {
        return Utils.readFile(ARP_ATTACKER_HA);
    }

    public static void setArpIgnoreRep(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", ARP_IGNORE_REP), ARP_IGNORE_REP, context);
    }

    public static boolean isArpIgnoreRep() {
        return Utils.readFile(ARP_IGNORE_REP).equals("1");
    }

    public static void setArpIgnoreReq(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", ARP_IGNORE_REQ), ARP_IGNORE_REQ, context);
    }

    public static boolean isArpIgnoreReq() {
        return Utils.readFile(ARP_IGNORE_REQ).equals("1");
    }

    public static void setArpIgnorePrx(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", ARP_IGNORE_PRX), ARP_IGNORE_PRX, context);
    }

    public static boolean isArpIgnorePrx() {
        return Utils.readFile(ARP_IGNORE_PRX).equals("1");
    }

    public static void setArpClAttHa(Context context) {
        run(Control.write("1", ARP_CLR_HW), ARP_CLR_HW, context);
    }
}