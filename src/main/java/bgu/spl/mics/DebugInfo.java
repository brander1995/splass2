/**
 * 
 */
package bgu.spl.mics;

/**
 * @author tom
 *
 */
public class DebugInfo {
	private static final boolean VERBOSE 		= false;
	private static final boolean DEBUG_HANDLERS = true;
	private static final boolean DEBUG_TICKS	= true;
	
	
	@SuppressWarnings("unused")
	public static void PrintHandle(String strDebug)
	{
		if (VERBOSE && DEBUG_HANDLERS)
			System.out.println(strDebug);
	}
	@SuppressWarnings("unused")
	public static void PrintTick(Object strDebug)
	{
		if (VERBOSE && DEBUG_TICKS)
			System.out.println(strDebug.toString());
	}
	public static void PrintDebug(Object strDebug)
	{
		if (VERBOSE)
		{
			System.out.println(strDebug.toString());
		}
	}
	
}
