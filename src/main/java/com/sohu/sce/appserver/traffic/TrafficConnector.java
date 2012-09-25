package com.sohu.sce.appserver.traffic;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jetty.io.nio.SelectChannelEndPoint;
import org.eclipse.jetty.io.nio.SelectorManager.SelectSet;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

/**
 * sohu net traffic selected channel connector
 * 
 * @author alexzhang (zhangyuxin85@gmail.com)
 * 
 */
public class TrafficConnector extends
		SelectChannelConnector { 
	
	private List<TrafficListener> listeners = new CopyOnWriteArrayList<TrafficListener>();

	/**
	 * add a traffic listener on this connector
	 * @param listener
	 */
	public void addListener(TrafficListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * set the traffic listeners
	 * @param listeners
	 */
	public void setListeners(List<TrafficListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	protected SelectChannelEndPoint newEndPoint(SocketChannel channel,
			SelectSet selectSet, SelectionKey key) throws IOException {
		TrafficEndPoint endPoint = new TrafficEndPoint(
				channel, selectSet, key, getMaxIdleTime(), listeners);
		endPoint.setConnection(selectSet.getManager().newConnection(channel,
				endPoint, key.attachment()));
		return endPoint;
	}

}
