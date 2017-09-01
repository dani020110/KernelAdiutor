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

package com.grarak.kerneladiutor.fragments.kernel;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.fragments.ApplyOnBootFragment;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.kernel.network.Network;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.views.recyclerview.GenericSelectView;
import com.grarak.kerneladiutor.views.recyclerview.DescriptionView;
import com.grarak.kerneladiutor.views.recyclerview.RecyclerViewItem;
import com.grarak.kerneladiutor.views.recyclerview.SeekBarView;
import com.grarak.kerneladiutor.views.recyclerview.SelectView;
import com.grarak.kerneladiutor.views.recyclerview.SwitchView;
import com.grarak.kerneladiutor.views.recyclerview.TitleView;

import java.util.List;

/*
 * Created by dani020110 on 31.08.16.
 */

public class NetworkFragment extends RecyclerViewFragment {

    @Override
    protected void init() {
        super.init();

        addViewPagerFragment(ApplyOnBootFragment.newInstance(this));
    }
    
    @Override
    protected void addItems(List<RecyclerViewItem> items) {
        networkInit(items);

        if (Network.isArpSupported()) {
            arpInit(items);
        }
    }    

    private void networkInit(List<RecyclerViewItem> items) {
        try {
            SelectView tcp = new SelectView();
            tcp.setTitle(getString(R.string.tcp));
            tcp.setSummary(getString(R.string.tcp_summary));
            tcp.setItems(Network.getTcpAvailableCongestions());
            tcp.setItem(Network.getTcpCongestion());
            tcp.setOnItemSelected(new SelectView.OnItemSelected() {
                @Override
                public void onItemSelected(SelectView selectView, int position, String item) {
                    Network.setTcpCongestion(item, getActivity());
                }
            });

            items.add(tcp);

        } catch (Exception ignored) {
        }

        GenericSelectView hostname = new GenericSelectView();
        hostname.setSummary(getString(R.string.hostname));
        hostname.setValue(Network.getHostname());
        hostname.setValueRaw(hostname.getValue());
        hostname.setOnGenericValueListener(new GenericSelectView.OnGenericValueListener() {
            @Override
            public void onGenericValueSelected(GenericSelectView genericSelectView, String value) {
                Network.setHostname(value, getActivity());
            }
        });

        items.add(hostname);
    }

    private void arpInit(List<RecyclerViewItem> items) {

        TitleView title = new TitleView();
        title.setText(getString(R.string.arp_title) + " " + Network.getArpVersion());

        SwitchView enable = new SwitchView();
        enable.setSummary(getString(R.string.arp_enable));
        enable.setChecked(Network.isArpEnabled());
        enable.addOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onChanged(SwitchView switchView, boolean isChecked) {
                Network.enableArp(isChecked, getActivity());
            }
        });

        SwitchView ignorebyrep = new SwitchView();
        ignorebyrep.setSummary(getString(R.string.arp_ignorebyrep));
        ignorebyrep.setChecked(Network.isArpIgnoreRep());
        ignorebyrep.addOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onChanged(SwitchView switchView, boolean isChecked) {
                Network.setArpIgnoreRep(isChecked, getActivity());
            }
        });

        SwitchView ignorebyreq = new SwitchView();
        ignorebyreq.setSummary(getString(R.string.arp_ignorebyreq));
        ignorebyreq.setChecked(Network.isArpIgnoreReq());
        ignorebyreq.addOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onChanged(SwitchView switchView, boolean isChecked) {
                Network.setArpIgnoreReq(isChecked, getActivity());
            }
        });

        SwitchView ignoreproxy = new SwitchView();
        ignoreproxy.setSummary(getString(R.string.arp_ignoreproxy));
        ignoreproxy.setChecked(Network.isArpIgnorePrx());
        ignoreproxy.addOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onChanged(SwitchView switchView, boolean isChecked) {
                Network.setArpIgnorePrx(isChecked, getActivity());
            }
        });

        DescriptionView attackerha = new DescriptionView();
        attackerha.setSummary(getString(R.string.arp_attacker_ha));
        attackerha.setOnItemClickListener(new RecyclerViewItem.OnItemClickListener() {
            @Override
            public void onClick(RecyclerViewItem item) {
                Utils.toast(Network.getArpAttackerHa(), getActivity());
            }
        });

        DescriptionView clattackerha = new DescriptionView();
        clattackerha.setSummary(getString(R.string.arp_clattackerha));
        clattackerha.setOnItemClickListener(new RecyclerViewItem.OnItemClickListener() {
            @Override
            public void onClick(RecyclerViewItem item) {
                Utils.toast(R.string.arp_clattackerha_toast, getActivity());
                Network.setArpClAttHa(getActivity());
            }
        });

        items.add(title);
        items.add(enable);
        items.add(ignorebyrep);
        items.add(ignorebyreq);
        items.add(ignoreproxy);
        items.add(attackerha);
        items.add(clattackerha);
    }
}