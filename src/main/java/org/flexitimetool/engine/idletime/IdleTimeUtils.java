package org.flexitimetool.engine.idletime;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

public class IdleTimeUtils {
	
	private IdleTimeCalculator idleCalculator;

	public IdleTimeUtils() {
		if (Platform.isWindows()) {
			idleCalculator = new W32IdleTimeCalculator();
		} else {
			throw new RuntimeException("Unsupported platform " + Platform.getOSType());
		}
	}
	
	public long getIdleTimeMillis() {
		return idleCalculator.getIdleTimeMillis();
	}
	
	private interface IdleTimeCalculator {
		public long getIdleTimeMillis();
	}
	
	
	public interface Kernel32 extends StdCallLibrary {
		Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32",
				Kernel32.class);

		public int GetTickCount();
	};

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		public static class LASTINPUTINFO extends Structure {
			public int cbSize = 8;
			public int dwTime;
		}

		public boolean GetLastInputInfo(LASTINPUTINFO result);
	};

	public class W32IdleTimeCalculator implements IdleTimeCalculator {
		public long getIdleTimeMillis() {
			User32.LASTINPUTINFO lastInput = new User32.LASTINPUTINFO();
			User32.INSTANCE.GetLastInputInfo(lastInput);
			return Kernel32.INSTANCE.GetTickCount() - lastInput.dwTime;
		}
	}
}
