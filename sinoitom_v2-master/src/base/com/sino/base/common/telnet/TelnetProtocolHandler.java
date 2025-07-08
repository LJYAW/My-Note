package com.sino.base.common.telnet;

import java.awt.*;
import java.io.IOException;

public abstract class TelnetProtocolHandler {

	public TelnetProtocolHandler() {
		tempbuf = new byte[0];
		crlf = new byte[2];
		cr = new byte[2];
		neg_state = 0;
		reset();
		crlf[0] = 13;
		crlf[1] = 10;
		cr[0] = 13;
		cr[1] = 0;
	}

	protected abstract String getTerminalType();

	protected abstract Dimension getWindowSize();

	protected abstract void setLocalEcho(boolean flag);

	protected abstract void notifyEndOfRecord();

	protected abstract void write(byte abyte0[]) throws IOException;

	private void write(byte b) throws IOException {
		one[0] = b;
		write(one);
	}

	public void reset() {
		neg_state = 0;
		receivedDX = new byte[256];
		sentDX = new byte[256];
		receivedWX = new byte[256];
		sentWX = new byte[256];
	}

	public void sendTelnetControl(byte code) throws IOException {
		byte b[] = new byte[2];
		b[0] = -1;
		b[1] = code;
		write(b);
	}

	private void handle_sb(byte type, byte sbdata[], int sbcount) throws IOException {
		switch (type) {
			default:
				break;

			case 24: // '\030'
				if (sbcount > 0 && sbdata[0] == 1) {
					write(IACSB);
					write((byte) 24);
					write((byte) 0);
					String ttype = getTerminalType();
					if (ttype == null)
						ttype = "dumb";
					write(ttype.getBytes());
					write(IACSE);
				}
				break;
		}
	}

	public void startup() throws IOException {
	}

	public void transpose(byte buf[]) throws IOException {
		int nbufptr = 0;
		byte nbuf[] = new byte[buf.length * 2];
		byte xbuf[];
		for (int i = 0; i < buf.length; i++)
			switch (buf[i]) {
				case -1:
					nbuf[nbufptr++] = -1;
					nbuf[nbufptr++] = -1;
					break;

				case 10: // '\n'
					if (receivedDX[128] != -3) {
						for (; nbuf.length - nbufptr < crlf.length; nbuf = xbuf) {
							xbuf = new byte[nbuf.length * 2];
							System.arraycopy(nbuf, 0, xbuf, 0, nbufptr);
						}

						for (int j = 0; j < crlf.length; j++)
							nbuf[nbufptr++] = crlf[j];

					} else {
						nbuf[nbufptr++] = buf[i];
					}
					break;

				case 13: // '\r'
					if (receivedDX[128] != -3) {
						for (; nbuf.length - nbufptr < cr.length; nbuf = xbuf) {
							xbuf = new byte[nbuf.length * 2];
							System.arraycopy(nbuf, 0, xbuf, 0, nbufptr);
						}

						for (int j = 0; j < cr.length; j++)
							nbuf[nbufptr++] = cr[j];

					} else {
						nbuf[nbufptr++] = buf[i];
					}
					break;

				default:
					nbuf[nbufptr++] = buf[i];
					break;
			}

		xbuf = new byte[nbufptr];
		System.arraycopy(nbuf, 0, xbuf, 0, nbufptr);
		write(xbuf);
	}

	public void setCRLF(String xcrlf) {
		crlf = xcrlf.getBytes();
	}

	public void setCR(String xcr) {
		cr = xcr.getBytes();
	}

	public int negotiate(byte nbuf[]) throws IOException {
		byte sbbuf[] = new byte[tempbuf.length];
		int count = tempbuf.length;
		byte buf[] = tempbuf;
		byte sendbuf[] = new byte[3];
		int sbcount = 0;
		int boffset = 0;
		int noffset = 0;
		boolean dobreak = false;
		if (count == 0)
			return -1;
		while (!dobreak && boffset < count && noffset < nbuf.length) {
			byte b = buf[boffset++];
			if (b >= 128)
				b -= 256;
			switch (neg_state) {
				case 0: // '\0'
				{
					if (b == -1) {
						neg_state = 1;
						dobreak = true;
					} else {
						nbuf[noffset++] = b;
					}
					break;
				}

				case 1: // '\001'
				{
					switch (b) {
						case -1:
							neg_state = 0;
							nbuf[noffset++] = -1;
							break;

						case -5:
							neg_state = 3;
							break;

						case -4:
							neg_state = 5;
							break;

						case -2:
							neg_state = 6;
							break;

						case -3:
							neg_state = 4;
							break;

						case -17:
							notifyEndOfRecord();
							dobreak = true;
							neg_state = 0;
							break;

						case -6:
							neg_state = 2;
							sbcount = 0;
							break;

						case -16:
						case -15:
						case -14:
						case -13:
						case -12:
						case -11:
						case -10:
						case -9:
						case -8:
						case -7:
						default:
							neg_state = 0;
							break;
					}
					break;
				}

				case 3: // '\003'
				{
					byte reply;
					switch (b) {
						case 1: // '\001'
							reply = -3;
							setLocalEcho(false);
							break;

						case 3: // '\003'
							reply = -3;
							break;

						case 25: // '\031'
							reply = -3;
							break;

						case 0: // '\0'
							reply = -3;
							break;

						default:
							reply = -2;
							break;
					}
					if (reply != sentDX[b + 128] || -5 != receivedWX[b + 128]) {
						sendbuf[0] = -1;
						sendbuf[1] = reply;
						sendbuf[2] = b;
						write(sendbuf);
						sentDX[b + 128] = reply;
						receivedWX[b + 128] = -5;
					}
					neg_state = 0;
					break;
				}

				case 5: // '\005'
				{
					byte reply;
					switch (b) {
						case 1: // '\001'
							setLocalEcho(true);
							reply = -2;
							break;

						case 3: // '\003'
							reply = -2;
							break;

						case 25: // '\031'
							reply = -2;
							break;

						case 0: // '\0'
							reply = -2;
							break;

						default:
							reply = -2;
							break;
					}
					if (reply != sentDX[b + 128] || -4 != receivedWX[b + 128]) {
						sendbuf[0] = -1;
						sendbuf[1] = reply;
						sendbuf[2] = b;
						write(sendbuf);
						sentDX[b + 128] = reply;
						receivedWX[b + 128] = -5;
					}
					neg_state = 0;
					break;
				}

				case 4: // '\004'
				{
					byte reply;
					switch (b) {
						case 1: // '\001'
							reply = -5;
							setLocalEcho(true);
							break;

						case 3: // '\003'
							reply = -5;
							break;

						case 24: // '\030'
							reply = -5;
							break;

						case 0: // '\0'
							reply = -5;
							break;

						case 31: // '\037'
							Dimension size = getWindowSize();
							receivedDX[b] = -3;
							if (size == null) {
								write((byte) -1);
								write((byte) -4);
								write((byte) 31);
								reply = -4;
								sentWX[b] = -4;
							} else {
								reply = -5;
								sentWX[b] = -5;
								sendbuf[0] = -1;
								sendbuf[1] = -5;
								sendbuf[2] = 31;
								write(sendbuf);
								write((byte) -1);
								write((byte) -6);
								write((byte) 31);
								write((byte) (size.width >> 8));
								write((byte) (size.width & 0xff));
								write((byte) (size.height >> 8));
								write((byte) (size.height & 0xff));
								write((byte) -1);
								write((byte) -16);
							}
							break;

						default:
							reply = -4;
							break;
					}
					if (reply != sentWX[128 + b] || -3 != receivedDX[128 + b]) {
						sendbuf[0] = -1;
						sendbuf[1] = reply;
						sendbuf[2] = b;
						write(sendbuf);
						sentWX[b + 128] = reply;
						receivedDX[b + 128] = -3;
					}
					neg_state = 0;
					break;
				}

				case 6: // '\006'
				{
					byte reply;
					switch (b) {
						case 1: // '\001'
							reply = -4;
							setLocalEcho(false);
							break;

						case 3: // '\003'
							reply = -4;
							break;

						case 31: // '\037'
							reply = -4;
							break;

						case 0: // '\0'
							reply = -4;
							break;

						default:
							reply = -4;
							break;
					}
					if (reply != sentWX[b + 128] || -2 != receivedDX[b + 128]) {
						write((byte) -1);
						write(reply);
						write(b);
						sentWX[b + 128] = reply;
						receivedDX[b + 128] = -2;
					}
					neg_state = 0;
					break;
				}

				case 7: // '\007'
				{
					if (b == -1) {
						sbcount = 0;
						current_sb = b;
						neg_state = 8;
					} else {
						System.err.println("(bad) " + b + " ");
						neg_state = 0;
					}
					break;
				}

				case 2: // '\002'
				{
					switch (b) {
						case -1:
							neg_state = 7;
							break;

						default:
							current_sb = b;
							sbcount = 0;
							neg_state = 8;
							break;
					}
					break;
				}

				case 8: // '\b'
				{
					switch (b) {
						case -1:
							neg_state = 9;
							break;

						default:
							sbbuf[sbcount++] = b;
							break;
					}
					break;
				}

				case 9: // '\t'
				{
					switch (b) {
						case -1:
							neg_state = 8;
							sbbuf[sbcount++] = -1;
							break;

						case -16:
							handle_sb(current_sb, sbbuf, sbcount);
							current_sb = 0;
							neg_state = 0;
							break;

						case -6:
							handle_sb(current_sb, sbbuf, sbcount);
							neg_state = 2;
							break;

						default:
							neg_state = 0;
							break;
					}
					break;
				}

				default: {
					neg_state = 0;
					break;
				}
			}
		}
		byte xb[] = new byte[count - boffset];
		System.arraycopy(tempbuf, boffset, xb, 0, count - boffset);
		tempbuf = xb;
		return noffset;
	}

	public void inputfeed(byte b[], int len) {
		byte xb[] = new byte[tempbuf.length + len];
		System.arraycopy(tempbuf, 0, xb, 0, tempbuf.length);
		System.arraycopy(b, 0, xb, tempbuf.length, len);
		tempbuf = xb;
	}

	public static final String ID = "$Id: TelnetProtocolHandler.java,v 1.1 2014/07/17 12:44:24 frank Exp $";
	private static final int debug = 0;
	private byte tempbuf[];
	private byte crlf[];
	private byte cr[];
	private static byte one[] = new byte[1];
	private byte neg_state;
	private static final byte STATE_DATA = 0;
	private static final byte STATE_IAC = 1;
	private static final byte STATE_IACSB = 2;
	private static final byte STATE_IACWILL = 3;
	private static final byte STATE_IACDO = 4;
	private static final byte STATE_IACWONT = 5;
	private static final byte STATE_IACDONT = 6;
	private static final byte STATE_IACSBIAC = 7;
	private static final byte STATE_IACSBDATA = 8;
	private static final byte STATE_IACSBDATAIAC = 9;
	private byte current_sb;
	private static final byte IAC = -1;
	private static final byte EOR = -17;
	private static final byte WILL = -5;
	private static final byte WONT = -4;
	private static final byte DO = -3;
	private static final byte DONT = -2;
	private static final byte SB = -6;
	private static final byte SE = -16;
	private static final byte TELOPT_BINARY = 0;
	private static final byte TELOPT_ECHO = 1;
	private static final byte TELOPT_SGA = 3;
	private static final byte TELOPT_EOR = 25;
	private static final byte TELOPT_NAWS = 31;
	private static final byte TELOPT_TTYPE = 24;
	private static final byte IACWILL[] = {-1, -5};
	private static final byte IACWONT[] = {-1, -4};
	private static final byte IACDO[] = {-1, -3};
	private static final byte IACDONT[] = {-1, -2};
	private static final byte IACSB[] = {-1, -6};
	private static final byte IACSE[] = {-1, -16};
	private static final byte TELQUAL_IS = 0;
	private static final byte TELQUAL_SEND = 1;
	private byte receivedDX[];
	private byte receivedWX[];
	private byte sentDX[];
	private byte sentWX[];

}