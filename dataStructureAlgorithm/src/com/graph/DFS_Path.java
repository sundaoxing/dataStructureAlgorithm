package com.graph;

import java.util.ArrayList;
import java.util.Stack;

/*������ȱ���
 * ʱ�临�Ӷȣ�
 * 			ϡ��ͼ--�ڽӱ�O��V+E��
 * 			����ͼ--�ڽӾ���O��V^2��
 * Ӧ��1.��ȡһ��·��������һ�������·��
 */
public class DFS_Path {
	private Graph g;// ��Ҫ������ͼ
	@SuppressWarnings("unused")
	private int src;// Դ�ڵ㣬�����һ���ڵ㿪ʼ����
	private boolean[] visited;// ������飬�����ѱ����ʵĽڵ�
	private int[] from;// ·�����飬�������һ���ڵ���ʵ���ǰ�ڵ��

	// ��ʼ��
	public DFS_Path(Graph g, int src) {
		this.g = g;
		if (src >= 0 && src < g.getN()) {
			this.src = src;
		}
		visited = new boolean[g.getN()];
		from = new int[g.getN()];
		for (int i = 0; i < g.getN(); i++) {
			visited[i] = false;//Ĭ��ȫû������
			from[i] = -1;//Ĭ��Ϊ-1����������ѭ����ֹ����Χ[0-g.getN())
		}
		dfs(src);//������ȱ�������Դ�ڵ㿪ʼ
	}
	
	/*������ȱ���dfs------�����ݹ�ʵ��
	 * ��Դ�ڵ㿪ʼ�����α�����Դ�ڵ������Ľڵ�
	 * Ȼ���ڴӸýڵ㿪ʼ�����α�����ýڵ������Ľڵ㣨�ݹ飩
	 * ��һ�η��ʽڵ㣬���Ϊvisited[true]
	 * �������ڱ����ýڵ�
	 */
	private void dfs(int v) {
		visited[v]=true;
		GraphIterator it = g.getIterator(v);
		for(int i=it.begin();!it.end();i=it.next()) {
			if(!visited[i]) {
				from[i]=v;//��ʾ��v��i�ڵ��·��
				dfs(i);
			}
		}
	}

	/*�ж�ĳ�ڵ����޵�src�ڵ��·��
	 * ֻ��Ҫ�鿴visited[v]�Ƿ�Ϊtrue����
	 * ���visited[v]Ϊtrue�������src�ڵ��ѷ��ʹ�v�ڵ�
	 * ��϶���·��
	 */
	public boolean hasPath(int v) {
		if (v >= 0 && v < g.getN()) {
			return visited[v];
		}
		return false;
	}
	
	/*��ȡ��src�ڵ㵽v�ڵ��һ��·��
	 * ����from·�����飬�������from�����Խ���ջ��Ҳ���Է������ArrayList��
	 */
	// 0 1 2 3 4 5 6                �ڵ�
	//-1 0 0 5 3 0 4      from���飺from[v]��ʾǰһ����v�����Ľڵ�
	public void path(int v ,ArrayList<Integer>a) {
		Stack<Integer> s = new Stack<>();
		int p=v;
		while(p != -1) {//from[src]=-1��û�и��Ĺ���˵���Ѿ�����src�ڵ�
			s.push(p);//��ջ
			p=from[p];//��v�ڵ��from�л�ȡ��һ����v�����Ľڵ�
		}
		a.clear();//��ֹ��ε���path������������ǰ��Ԫ�ش���
		while(!s.isEmpty()) {
			a.add(s.peek());//��ջ�������뼯��
			s.pop();//ɾ��ջ��Ԫ��
		}
	}

	//�������ӡ��src�ڵ㵽v�ڵ��һ��·��
	public void showPath(int v) {
		for(int i=0;i<from.length;i++) {
			System.out.print(from[i]+" ");
		}
		System.out.println("-------------");
		ArrayList<Integer> a = new ArrayList<>();
		path(v, a);
		System.out.print(src+"->"+v+": ");
		for(int i=0;i<a.size();i++) {
			if(i == a.size()-1) {
				System.out.print(a.get(i));
			}
			else {
				System.out.print(a.get(i)+"->");
			}
		}
	}
	
}
