package com.graph;
/*
 * ͼ�ı���
 * 1.������ȱ���
 * 2.���ͼ����ͨ����
 * ��ͨ��������ͨ��������ͨ����֮��û���κα��������������Ƕ�����һ��ͼ
 */
public class Component {
	private Graph g;//��Ҫ������ͼ
	private int ccount;//ͼ����������ͨ����
	private boolean visited[];//������ǣ��Ƿ��Ѿ��������Ľڵ�
	private int ids[];//������ǣ���Щ�ڵ��������ӵģ�ids[]��ֵһ��������������
	
	public Component(Graph g) {
		this.g=g;
		this.ccount=0;
		this.visited=new boolean [g.getN()];
		this.ids=new int [g.getN()];
		for(int i=0;i<visited.length;i++) {
			visited[i]=false;//��ʼ��Ϊfalse����ȫû�б����ʹ�
			ids[i]=-1;
		}
	}
	
	/*���ݹ飩������ȱ���
	 * ˼��:��һ���ڵ�����������������������ӵĽڵ㣬��һ�α���ÿһ�������ӵĽڵ�
	 * ����ѱ�visited���ˣ�������
	 */
	public void iteratorDFS() {
		for(int i=0;i<g.getN();i++) {
			if(!visited[i]) {
				dfs(i);
				ccount++;//ͳ��ͼ����ͨ����
			}
		}
	}

	private void dfs(int v) {
		ids[v]=ccount+1;//���v,v'���ӣ�����ids[]��ֵ����ͬ��
		visited[v]=true;//���Ϊ�ѷ���
		/*���ݴ����g�����ͣ�Spare/ϡ��ͼ,Dense/����ͼ
		 * ѡ����Ӧ�ĵ����������б���
		 */
		GraphIterator it =g.getIterator(v);
		for(int i=it.begin();!it.end();i=it.next()) {
			if(!visited[i]) {
				dfs(i);
			}
		}	
	}

	//���ظ�ͼ����ͨ����
	public int getCcount() {
		return ccount;
	}
	
	//�ж������ڵ��Ƿ�����
	public boolean isConnected(int v,int w) {
		if(v>=0 && v<g.getN()) {
			if(w>=0 && w<g.getN()) {
				return ids[v]==ids[w];
			}
		}
		return false;
	}
}
