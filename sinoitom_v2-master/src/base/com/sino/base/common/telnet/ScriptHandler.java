package com.sino.base.common.telnet;


public class ScriptHandler {

	public ScriptHandler() {
		done = true;
	}

	public void setup(String match) {
		if (match == null) {
			return;
		} else {
			match = match.trim();
			this.match = match.getBytes();
			matchPos = 0;
			done = false;
			return;
		}
	}

	public boolean match(byte s[], int length) {
		if (done)
			return true;
		for (int i = 0; !done && i < length; i++)
			if (s[i] == match[matchPos]) {
				if (++matchPos >= match.length) {
					done = true;
					return true;
				}
			} else {
				matchPos = 0;
			}

		return false;
	}

	private static final int debug = 0;
	private int matchPos;
	private byte match[];
	private boolean done;
}