package org.thothtrust.sc.scplib.crypto;

public class ISO9797Pad {
	// GP 2.3.1 Section B.2.3
	public static int process(boolean isEncode, byte[] input, int iOff, int iLen, byte[] output, int oOff) {
		if (input != null && iLen != 0) {
			if (isEncode) {
				int outLen = iLen;
				if (output != null) {
					System.arraycopy(input, iOff, output, oOff, iLen);
					output[oOff + outLen] = (byte) 0x80; // append '80' directly to back of input
				}
				outLen++;
				while (outLen % 16 != 0) {
					if (output != null) {
						output[oOff + outLen] = (byte) 0x00;
					}
					outLen++;
				}
				return outLen; // returns final total length of input + padding bytes
			} else {
				byte b;
				for (int i = iLen - 1; i >= 0; i--) {
					b = input[iOff + i];
					if (b == (byte) 0x80) {
						if (output != null) {
							System.arraycopy(input, iOff, output, oOff, i);
						}
						return i; // returns length of unpadded data length
					} else if (b == (byte) 0x00) {
						// Do nothing and continue parsing
					} else {
						return -1;
					}
				}
			}
		}
		return 0;
	}
}