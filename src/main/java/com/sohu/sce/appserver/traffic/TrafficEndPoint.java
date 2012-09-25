package com.sohu.sce.appserver.traffic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.nio.SelectChannelEndPoint;
import org.eclipse.jetty.io.nio.SelectorManager;

/**
 * 
 * @author alexzhang(zhangyuxin85@gmail.com)
 * 
 */
public class TrafficEndPoint extends SelectChannelEndPoint {

	private static Logger log = Logger.getLogger(TrafficEndPoint.class.getName());

	private List<TrafficListener> listeners;

	public TrafficEndPoint(SocketChannel channel,
			SelectorManager.SelectSet selectSet, SelectionKey key,
			int maxIdleTime, List<TrafficListener> listeners)
			throws IOException {

		super(channel, selectSet, key, maxIdleTime);
		this.listeners = listeners;
	}

	@Override
	public int fill(Buffer buffer) throws IOException {
		int read = super.fill(buffer);
		in(read);
		return read;
	}

	@Override
	public int flush(Buffer buffer) throws IOException {
		int written = super.flush(buffer);
		out(written);
		return written;
	}

	@Override
	protected int gatheringFlush(Buffer header, ByteBuffer bbuf0,
			Buffer buffer, ByteBuffer bbuf1) throws IOException {
		int written = super.gatheringFlush(header, bbuf0, buffer, bbuf1);
		out(written);
		return written;
	}

	protected void in(long size) {
		if (size > 0) {
			for (TrafficListener listener : listeners) {
				try {
					listener.in(size);
				} catch (Exception ex) {
					log.log(Level.SEVERE, ex.getMessage(), ex);
				}
			}
		}
	}

	protected void out(long size) {
		if (size > 0) {
			for (TrafficListener listener : listeners) {
				try {
					listener.out(size);
				} catch (Exception ex) {
					log.log(Level.SEVERE, ex.getMessage(), ex);
				}
			}
		}
	}

	
}
