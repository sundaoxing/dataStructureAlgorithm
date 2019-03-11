package com.weightgraph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 从文件中读取图的节点数和节点之间的连接关系
 */
public class ReadGraph<W> {
	public ReadGraph(Graph<W> g, String filename) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line = br.readLine();
			String[] lines = line.split(" ");
			int V = Integer.valueOf(lines[0]);//节点个数
			int E = Integer.valueOf(lines[1]);//边数
			for (int i = 0; i < E; i++) {
				line = br.readLine();
				lines = line.split(" ");
				int a = Integer.valueOf(lines[0]);
				int b = Integer.valueOf(lines[1]);
				@SuppressWarnings("unchecked")
				W weight = (W) Double.valueOf(lines[2]);
				if (a >= 0 && a < V) {
					if (b >= 0 && b < V) {
						g.addEdge(a, b,weight);//添加两个节点之间的连接
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
