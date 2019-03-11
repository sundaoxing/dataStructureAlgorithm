package com.weightgraph;
/*
 * µü´úÆ÷£¨°üº¬ÁËÏ¡ÊèÍ¼ºÍ³íÃÜÍ¼£©
 */
public interface GraphIterator<W> {
	public Edge<W> begin();
	public Edge<W> next();
	public boolean end();
}
