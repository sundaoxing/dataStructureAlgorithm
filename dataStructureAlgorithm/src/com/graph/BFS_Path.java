package com.graph;

import java.util.ArrayList;
import java.util.LinkedList;
		/*������ȱ���
		 * ʱ�临�Ӷȣ�
		 * 			ϡ��ͼ--�ڽӱ�O��V+E��
		 * 			����ͼ--�ڽӾ���O��V^2��
		 * Ӧ��1.��ȡһ��·�������������·��
		 */
public class BFS_Path {
	private Graph g;//��Ҫ������ͼ
	private int s;//Դ�ڵ㣬���������ڵ㿪ʼ����
	private boolean[] visited;//������飺�����ѷ��ʹ��Ľڵ�
	private int[] from;//·�����飺�������һ���ڵ㵽��ǰ�ڵ�
	private int[] ord;//�������飺����ǰ�ڵ㵽Դ�ڵ�s�ľ��루ֱ��Ϊ1������һ���ڵ���+1��

	//��ʼ��
	public BFS_Path(Graph g, int s) {
		this.g = g;
		if(s>=0 && s<g.getN()) {
			this.s = s;
		}
		visited = new boolean[g.getN()];
		from = new int[g.getN()];
		ord = new int[g.getN()];
		for (int i = 0; i < g.getN(); i++) {
			visited[i] = false;//Ĭ��ȫû�б����ʹ�
			from[i] = -1;//Ĭ��ȫΪ-1
			ord[i] = -1;//Ĭ��ȫΪ-1
		}
		bfs(s);//��Դ�ڵ㿪ʼ������ȱ���
	}

	/*������ȱ���-bfs----����������ʵ��
	 * ��Դ�ڵ㣨���Ϊ�ѷ��ʹ�������Ϊ0����ʼ�����α���������Դ�ڵ������Ľڵ㣬����У����Ϊ�ѷ��ʹ�������+1
	 * Ȼ��Ӷ��еĶ��׿�ʼ�����α�����������������Ľڵ㣬����У����Ϊ�ѷ��ʹ�������+1
	 * ֱ������Ϊ�գ���������
	 */
	private void bfs(int v) {
		LinkedList<Integer> queue= new LinkedList<>();
		queue.offer(v);
		visited[v] = true;//���Ϊ�ѷ��ʹ�
		ord[v] = 0;//��ʼԴ�ڵ����
		while (!queue.isEmpty()) {
			int temp = queue.poll();
			GraphIterator it = g.getIterator(temp);
			for (int i = it.begin(); !it.end(); i = it.next()) {
				if (!visited[i]) {
					queue.offer(i);
					visited[i] = true;
					from[i]=temp;//��¼�´�temp��i��·��
					ord[i] = ord[temp] + 1;//��¼��Դ�ڵ�s��i�ڵ�ľ���
				}
			}
		}
	}
	
	/*�ж�ĳ�ڵ����޵�src�ڵ��·��
	 * ֻ��Ҫ�鿴visited[v]�Ƿ�Ϊtrue����
	 * ���visited[v]Ϊtrue�������src�ڵ��ѷ��ʹ�v�ڵ�
	 * ��϶���·��
	 */
	public boolean hasPath(int v) {
		if(v>=0 && v<g.getN()) {
			return visited[v];
		}
		return false;
	}
	
	/*��ȡ��src�ڵ㵽v�ڵ��һ��·�������������·����
	 * ����from·�����飬�������from�����Խ���ջ��Ҳ���Է������ArrayList��
	 */
	// 0 1 2 3 4 5 6                �ڵ�
	//-1 0 0 5 5 0 0 		from·������ ;from[v]��ʾǰһ����v�����Ľڵ�
	public void path(int v,ArrayList<Integer> a) {
		a.clear();//��ֹ��ε���path������������ǰ��Ԫ�ش���
		int p=v;
		while(p!=-1) {
			a.add(p);//·���Ƿ��򱣴�ģ����ʱҪ�������
			p=from[p];//��v�ڵ��from�л�ȡ��һ����v�����Ľڵ�
		}
	}
	//�������ӡ��src�ڵ㵽v�ڵ��һ��·��
	public void showPath(int v) {
		for(int i=0;i<from.length;i++) {
			System.out.print(from[i]+" ");
		}
		System.out.println();
		for(int i=0;i<ord.length;i++) {
			System.out.print(ord[i]+" ");
		}
		ArrayList<Integer> a = new ArrayList<>();
		path(v, a);
		System.out.print(s+"->"+v+": ");
		for(int i=a.size()-1;i>=0;i--) {//�������
			if(i == 0) {
				System.out.print(a.get(i));
			}
			else {
				System.out.print(a.get(i)+"->");
			}
		}
	}
	//���ؽڵ�v��Դ�ڵ�s�ľ���
	// 0 1 2 3 4 5 6                �ڵ�
	// 0 1 1 2 2 1 1	   ord��������
	public int length(int v) {
		return ord[v];
	}
}
