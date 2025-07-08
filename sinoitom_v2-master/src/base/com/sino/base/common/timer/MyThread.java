package com.sino.base.common.timer;

import com.sino.base.common.TelnetPool;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.TelnetUtil;

public class MyThread extends Thread {

	public void run() {
		while (true) {
			try {
				if (!StringUtil.isNullString(SaveConfigTask.getIp1())) {
					if (!TelnetUtil.isConnect(SaveConfigTask.getIp1())) {
						TelnetPool.getTelnetMap().clear();
						TelnetPool.getTelnetMap2().clear();
					}
				} else if (!StringUtil.isNullString(SaveConfigTask.getIp2())) {
					if (!TelnetUtil.isConnect(SaveConfigTask.getIp2())) {
						TelnetPool.getTelnetMap().clear();
						TelnetPool.getTelnetMap2().clear();
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
