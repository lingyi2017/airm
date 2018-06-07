package com.infosoul.mserver.common.utils;

public class DaemonThread extends Thread {
	public DaemonThread() {
		super();
		this.setDaemon(true);
	}
}
