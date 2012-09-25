package com.sohu.sce.appserver.traffic;

/**
 * traffic listener, implements this listener, register into TrafficConnector
 * in\out will be called when traffic in\out
 * @author alex
 *
 */
public interface TrafficListener {

	/**
	 * traffic in
	 * @param in
	 */
	void in(long in);
	
	/**
	 * traffic out
	 * @param out
	 */
	void out(long out);
}
