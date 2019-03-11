package com.RBTree;
/*
 * 					���������RedBlackTree��
 * 			ǰ��Ҫ�󣺶���������----------Ȼ���ٴ˻����ϣ������нڵ����ɫ����ɫ/��ɫ��
 * 			���ʣ����ֺ�ƽ��Ķ��������ϸ������ϲ���ƽ����������ں�ɫ�ڵ�����ϱ��־���ƽ�⣩
 * 				���߶ȣ�2log��n������ɫ+��ɫ��Ϊһ��ڵ���������
 * 				����Ч�ʣ���AVL���ϲ�
 * 				ά��ƽ���Ч�ʣ���AVL���ϸ�
 * 		������������5��Ҫ��
 * 	1.����һ���ڵ����ɫ�����Ǻ�ɫ�������Ǻ�ɫ��
 * 	2.���ڵ����ɫ�Ǻ�ɫ�ġ�
 * 	3.ÿһ��Ҷ�ӽڵ㣨Ҷ�ӽڵ����һ���ڵ㣺null�ڵ㣩�Ǻ�ɫ�ġ�
 * 	4.���һ���ڵ��Ǻ�ɫ�ģ���ô�������Һ��ӽڵ㶼�Ǻ�ɫ�ġ�
 * 	5.��һ�ڵ㵽Ҷ�ӽڵ㣨Ҷ�ӽڵ����һ���ڵ㣺null�ڵ㣩�������ĺ�ɫ�ڵ�ĸ�����һ���ģ�ģ��2-3���ľ���ƽ�⣩
 * 
 * 		��Դ��2-3������ʵ��2-3���ȼ�
 * 	2-3����ÿ���ڵ������2�����ӽڵ㣬������3�����ӽڵ�
 * 		���ʣ�����ƽ���ԣ�����һ���ڵ�����Һ��������ĸ߶�һ��
 * 			a 		42			b   c         	17	  33
 * 		   / \	   /  \	       /  |  \			/   |   \
 * 		  2�ڵ�	  17  50	     3�ڵ� 		6  12   18   37
 * 
 * 		��ӣ�	������ӵ�null�ڵ㣬��������Ҷ�ӽڵ��ںϳ�3�ڵ��������ʱ4�ڵ㣨��ʱ4������Ҫ�ֽ� ��
 * 			���42				42			  2�ڵ�
 * 			
 * 			���37			  37 42			  3�ڵ�
 * 
 * 			���12			12  37  42        4�ڵ�---��ʱ����Ҫ����
 * 								
 * 							    37
 * 							   /  \			  4�ڵ�Ҫ���ѣ����м�ڵ�Ϊ�µĸ��ڵ�
 * 							 12    42		  Ȼ���µĸ��ڵ�Ҫ���Ϻ����ĸ��׽ڵ��ںϣ�ֱ�������ڵ�Ϊֹ
 * 		
 * 	����� �ȼ� 2-3��
 * 			a 		42			b   c         	17	  33
 * 		   / \	   /  \	       /  |  \			/   |   \
 * 		  2�ڵ�	  17  50	     3�ڵ� 		6  12   18   37
 *       
 *       
 * 			a 		42			b������c         	17	  33
 * 		   / \	   /  \	       / \   \			/   |   \
 * 		    ��ɫ�ڵ�	  17  50	         ��ɫ�ڵ� 		6  12   18   37
 * 
 * 									 c(��ɫ)				33(��)
 * 								   /   \			  /    \
 * 						          b(��ɫ)             17(��) 37
 * 								/  \               /   \
 * 												  12   18
 * 												 /
 * 												6(��)
 * 		�ں�����У���ɫ�ڵ��ʾ����ɫ�ڵ�͸��ڵ��ǲ��еģ�ģ��2-3����3�ڵ㣨��ɫ�ڵ���һ�������ǣ�
 * 				���к�ɫ�ڵ�һ������������б�ģ�����2-3����3�ڵ�����Ϊ�����һ�ֹ���
 * 					2-3��									�����
 * 					42											42(��)
 * 				/		\									 /		\
 * 			17  33		 50								   33(��)	 50(��)
 * 		  /   |   \  	/    \							/	  \		/	\
 * 	  6  12   18  37   48 66 88					     17(��)  37(��)48(��) 88(��)
 * 													/	 \               /
 * 											       12(��) 18(��)			66(��)  
 * 												  /
 *  											 6(��)
 */
public class RBTree<K extends Comparable<K> , V> {
	//���峣��̬��ֵ����ȷ���廯��ʹ��RED/BLACK����true/false
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private class Node{
		K key;
		V value;
		Node left;
		Node right;
		boolean color;//���������ɫ��ʹ��booleanֵ����죬�ڣ�
		public Node(K key , V value) {
			this.key = key;
			this.value=value;
			left=right=null;
			color=RED;
		}
	}
	private Node root;//���ڵ�
	private int size;//RBT��Ԫ�ظ���
	
	public RBTree() {
		root=null;
		size=0;
	}
	//����//RBT��Ԫ�ظ���
	public int size() {
		return size;
	}
	//�ж�RBT�Ƿ�Ϊ��
	public boolean isEmpty() {
		return size==0;
	}
	//�ж�node�ڵ��Ƿ�Ϊ��ɫ�ڵ㣨ǰ�涨�壺RED==true��null�ڵ�Ϊ�ڽڵ㣩
	public boolean isRed(Node node) {
		return node == null ?BLACK : node.color;
	}
	//��RBT����Ӽ�Ϊkey��ֵΪvalue�Ľڵ�-------�ݹ�ʵ�֣�ʱ�临�Ӷȣ�O��log��n����
	public void add(K key ,V value) {
		root=add(root,key,value);
		root.color=BLACK;//��֤������ĸ��ڵ�һ���Ǻ�ɫ��
	}
	/*
	 * ˼��:��Ȼ�����˶���������������߼���ֻ������Ӻ�Ҫ���ֺ������ƽ��
	 * �ݹ鷽����Node add(Node node, K key, V value)
	 * �ݹ���ֹ������
	 * 		һֱ��null�ڵ㣺node==null
	 * �ݹ鹫ʽ��
	 * 		node.right=add(node.right,key,value)//�������������
	 * 		node.left=add(node.left,key,value)  //�������������
	 */
	private Node add(Node node, K key, V value) {
		if(node ==null) {
			size++;
			return new Node(key, value);
		}
		if(key.compareTo(node.key) >0) {
			node.right=add(node.right,key,value);
		}
		else if(key.compareTo(node.key) <0) {
			node.left=add(node.left,key,value);
		}else {
			node.value=value;
		}
		//�ڹ�Ĺ����У�����ÿһ���ڵ㣬ά�������������
		return keepBalanced(node);
	}
	/*
	 * ����ƽ�⣨��ƽ�⣩��
	 * ʲôʱ�򣺵����һ���ڵ�������ܻ�ʧ��
	 * Ϊʲô���������ĸ߶Ȳ�Ҫ̫�����������Ľڵ����൱����������������б����/��
	 * ��ô�ж�ʧ�⣺ͨ����ǰ�ڵ�����Һ��ӽڵ����ɫ
	 * ��ô����ͨ����ת�ͱ�ɫ��ά������ƽ��
	 *	 1.��2�ڵ�������µĽڵ㣺
	 *    1�����37�ڵ�							��Ӧ2-3���У�	
	 *		42���ڣ�									37  42��3�ڵ㣩
	 *	   /						 
	 *	 37���죩				---���֣���������          	  
	 *	
	 *	  2�����42�ڵ�	  ��Ӧ2-3���У�				   ������
	 *		37���ڣ�		  37  42��3�ڵ㣩         			42���ڣ� 	
	 *		 \             ----��Ҫ����ת---��      		/	
	 *		 42(��)             					   37���죩							
	 *		��Ҫ�����ԭ��������ƺ����ʱ����涨42���ڣ�������ʽ����2-3����3�ڵ㣬������Ҫ��ת
	 *	   								  /						 
	 *									 37���죩
	 *	 2.��3�ڵ�������µĽڵ㣺
	 *	  1�����66�ڵ㣺	 ��Ӧ2-3���У�						 ��ת��				
	 *		42���ڣ�		 37  42  66��4�ڵ㣩	----filpColor--��42���죩
	 *	  /    \	 ---��4�ڵ���м�ڵ���Ҫ�����Ҹ��ڵ��ں�		/    \
	 *	37(��)	66���죩	---�����ں�����У��ں�==��ɫ�ķ�ת     	  37(��)   66(��)
	 *
	 *	  2�����12�ڵ�					������			��ת��	
	 *		42���ڣ�		��Ӧ2-3���У�	     37���ڣ�	 --filpColor->37(��)
	 *	   /    		 12  37  42	     /   \				 /	 \
	 *    37(��)	 	 ---������		  12���죩42���죩		  12���ڣ�    42���ڣ�
	 *    /			 ---����ɫ��ת
	 *   12���죩
	 *    3�����12�ڵ�					������		  ������
	 *		42���ڣ�		��Ӧ2-3���У�	     42���ڣ�		   40���ڣ�	 --filpColor->40(��)
	 *	   /    		 37 40  42	     /			   /   \				 /	 \
	 *    37(��)	 	 ---������		 	40(��)		 37���죩42���죩		  37���ڣ�    42���ڣ�
	 *     \		 ---������			/
	 *     40���죩	 ---����ɫ��ת		   37���죩
	 *     
	 *     ͳһ�߼���
	 *              1    ����ת��      2     ����ת��       3            ��ɫ��ת��     
	 *     ��	-�� 	��     	 -��         ��         -��         ��              -�� 	 ��            
	 *    /		   /		  /			/    \		   /   \
	 *   ��  	               ��       		  ��                      ��             ��       	     ��           ��
	 *             \        / 
	 *           	��              ��
	 *           
	 *   ֻҪ������Ľڵ���������1��2��3����һ���������������Ӧ�Ĳ��������ɱ��ֺ������ƽ����
	 *   ��2��3�ڵ㶼����
	 */
	private Node keepBalanced(Node node) {
		//��1�����
		if(isRed(node.right) && ! isRed(node.left)) {
			node=leftRotate(node);
		}
		//��2�����
		if(isRed(node.left) && isRed(node.left.left)) {
			node=rightRotate(node);
		}
		//��3�����
		if(isRed(node.left) && isRed(node.right)) {
			flipColor(node);
		}
		return node;
	}
	/*
	 * ����ת����ת���̣�ֻ����node��x�γ�3�ڵ㣬�������ֺ����������		ע�⣺
	 * 	   node		  	˼�룺����						  x			node��x�ڵ�
	 * 	  /    \		˼·��						/   \		�����γ�һ��
	 * 	 T1     x			1.node.right=x.left  node(��) T3		3�ڵ�
	 * 		   / \			2.x.left=node		  /  \  
	 *        T2  T3		3.x.color=node.color T1    T2
	 *        				4.node.color=RED
	 */
	private Node leftRotate(Node node) {
		Node rightNode = node.right;
		Node rightNodeLeft =rightNode.left;
		//����
		rightNode.left=node;
		node.right=rightNodeLeft;
		//ά����ɫ
		rightNode.color=node.color;
		node.color=RED;
		
		return rightNode;
	}
	/*
	 * ����ת����ת���̣�ֻ����node��x�γ�3�ڵ㣬�������ֺ����������		ע�⣺
	 * 	   node		  	˼�룺����						  x			node��x�ڵ�
	 * 	  /    \		˼·��						/   \		�����γ�һ��
	 * 	 x		T1			1.node.left=x.right  node(��) T3		3�ڵ�
	 * 	/ \					2.x.right=node		  /  \  
	 * T2  T3				3.x.color=node.color T1    T2
	 *        				4.node.color=RED
	 */
	private Node rightRotate(Node node) {
		Node leftNode = node.left;
		Node leftNodeRight = leftNode.right;
		//����
		leftNode.right=node;
		node.left=leftNodeRight;
		//ά����ɫ
		leftNode.color=node.color;
		node.color=RED;
		
		return leftNode;
	}
	/*
	 * ��ɫ��ת��              			��Ӧ2-3���У�		   ��ת��
	 * 		    42���ڣ�     		 32  42  66				42���죩
	 * 		   /    \			4�ڵ���м�ڵ�		   	  /		 \
	 * 		32���죩   66���죩		 ��Ҫ�����Ҹ��ڵ��ں�		32(��)	 66���ڣ�
	 */
	private void flipColor(Node node) {
		node.color=RED;
		node.left.color=node.right.color=BLACK;
	}
	//�ж�RBT���Ƿ������Ϊkey�Ľڵ�-------�ݹ�ʵ�֣�ʱ�临�Ӷȣ�O��log��n����
	public boolean contains(K key) {
		Node node = getNode(root, key);
		return node==null?false:true;
	}
	//���ؼ�Ϊkey�Ľڵ��valueֵ����������ڼ�key���򷵻�null��--�ݹ�ʵ�֣�ʱ�临�Ӷȣ�O��log��n����
	public V get(K key) {
		Node node = getNode(root, key);
		return node==null?null:node.value;
	}
	//���¼�Ϊkey�Ľڵ��valueֵ-------�ݹ�ʵ�֣�ʱ�临�Ӷȣ�O��log��n����
	public void set(K key ,V newValue) {
		Node node = getNode(root, key);
		if(node ==null) {
			throw new RuntimeException("key doesn't exist");
		}
		node.value=newValue;
	}
	//������nodeΪ���ڵ��RBT�м�Ϊkey�Ľڵ�-------�ݹ�ʵ�֣�ʱ�临�Ӷȣ�O��log��n����
	private Node getNode(Node node,K key) {
		if(node ==null) {
			return null;
		}
		if(key.compareTo(node.key) ==0) {
			return node;
		}
		else if(key.compareTo(node.key) >0) {
			return getNode(node.right, key);
		}
		else {
			return getNode(node.left, key);
		}
	}
}
