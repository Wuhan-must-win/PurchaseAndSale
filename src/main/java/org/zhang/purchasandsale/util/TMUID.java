package org.zhang.purchasandsale.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Random;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

/**
 * @author G.fj
 * @since 2019年12月23日 下午7:49:19
 *
 */
public class TMUID {
	private static final char[] ALAPHABET = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p',
			'o', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final int SIZE = 5;
	private static final int PID = getProcessId();

	public static String tmuid() {
		getProcessId();
		StringBuffer sb = new StringBuffer();
		String ts = Long.toString(System.currentTimeMillis(), 36);
		for (int i = 0; i < 9 - ts.length(); i++) {
			sb.append("0");
		}
		sb.append(ts);
		String spid = Long.toString(PID, 36);
		for (int i = 0; i < 4 - spid.length(); i++) {
			sb.append("0");
		}
		sb.append(spid);
		Random random = new Random();
		String rs = NanoIdUtils.randomNanoId(random, ALAPHABET, SIZE);
		sb.append(rs);
		return sb.toString();
	}

	public static final int getProcessId() {
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		return Integer.valueOf(runtimeMxBean.getName().split("@")[0]).intValue();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(tmuid());
		}

	}
}
