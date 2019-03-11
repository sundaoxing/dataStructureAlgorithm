package com.RBTree;
/*
 * 					红黑树：（RedBlackTree）
 * 			前提要求：二叉搜索树----------然后再此基础上，增加列节点的颜色（红色/黑色）
 * 			性质：保持黑平衡的二叉树（严格意义上不是平衡二叉树，在黑色节点个数上保持绝对平衡）
 * 				最大高度：2log（n）（黑色+红色作为一层节点来看待）
 * 				查找效率：比AVL树较差
 * 				维持平衡的效率：比AVL树较高
 * 		必须满足以下5个要求：
 * 	1.任意一个节点的颜色或者是红色，或者是黑色。
 * 	2.根节点的颜色是黑色的。
 * 	3.每一个叶子节点（叶子节点的下一个节点：null节点）是黑色的。
 * 	4.如果一个节点是红色的，那么它的左右孩子节点都是黑色的。
 * 	5.任一节点到叶子节点（叶子节点的下一个节点：null节点）所经过的黑色节点的个数是一样的（模拟2-3树的绝对平衡）
 * 
 * 		起源：2-3树，其实与2-3树等价
 * 	2-3树：每个节点或者有2个孩子节点，或者有3个孩子节点
 * 		性质：绝对平衡性：任意一个节点的左右孩子子树的高度一样
 * 			a 		42			b   c         	17	  33
 * 		   / \	   /  \	       /  |  \			/   |   \
 * 		  2节点	  17  50	     3节点 		6  12   18   37
 * 
 * 		添加：	不是添加到null节点，而是先与叶子节点融合成3节点或者是临时4节点（临时4结束需要分解 ）
 * 			添加42				42			  2节点
 * 			
 * 			添加37			  37 42			  3节点
 * 
 * 			添加12			12  37  42        4节点---此时，需要调整
 * 								
 * 							    37
 * 							   /  \			  4节点要分裂，以中间节点为新的根节点
 * 							 12    42		  然后新的根节点要向上和它的父亲节点融合，直至到根节点为止
 * 		
 * 	红黑树 等价 2-3树
 * 			a 		42			b   c         	17	  33
 * 		   / \	   /  \	       /  |  \			/   |   \
 * 		  2节点	  17  50	     3节点 		6  12   18   37
 *       
 *       
 * 			a 		42			b———c         	17	  33
 * 		   / \	   /  \	       / \   \			/   |   \
 * 		    黑色节点	  17  50	         红色节点 		6  12   18   37
 * 
 * 									 c(黑色)				33(黑)
 * 								   /   \			  /    \
 * 						          b(红色)             17(红) 37
 * 								/  \               /   \
 * 												  12   18
 * 												 /
 * 												6(红)
 * 		在红黑树中，红色节点表示：红色节点和父节点是并列的，模拟2-3树中3节点（红色节点是一种特殊标记）
 * 				所有红色节点一定都是向左倾斜的（根据2-3树中3节点来人为定义的一种规则）
 * 					2-3树									红黑树
 * 					42											42(黑)
 * 				/		\									 /		\
 * 			17  33		 50								   33(黑)	 50(黑)
 * 		  /   |   \  	/    \							/	  \		/	\
 * 	  6  12   18  37   48 66 88					     17(红)  37(黑)48(黑) 88(黑)
 * 													/	 \               /
 * 											       12(黑) 18(黑)			66(红)  
 * 												  /
 *  											 6(红)
 */
public class RBTree<K extends Comparable<K> , V> {
	//定义常静态量值，明确语义化，使用RED/BLACK代替true/false
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private class Node{
		K key;
		V value;
		Node left;
		Node right;
		boolean color;//红黑树的颜色（使用boolean值代替红，黑）
		public Node(K key , V value) {
			this.key = key;
			this.value=value;
			left=right=null;
			color=RED;
		}
	}
	private Node root;//根节点
	private int size;//RBT中元素个数
	
	public RBTree() {
		root=null;
		size=0;
	}
	//返回//RBT中元素个数
	public int size() {
		return size;
	}
	//判断RBT是否为空
	public boolean isEmpty() {
		return size==0;
	}
	//判断node节点是否为红色节点（前面定义：RED==true，null节点为黑节点）
	public boolean isRed(Node node) {
		return node == null ?BLACK : node.color;
	}
	//向RBT中添加键为key，值为value的节点-------递归实现：时间复杂度：O（log（n））
	public void add(K key ,V value) {
		root=add(root,key,value);
		root.color=BLACK;//保证红黑树的根节点一定是黑色的
	}
	/*
	 * 思想:依然延续了二叉搜索树的添加逻辑，只是在添加后要保持红黑树的平衡
	 * 递归方法：Node add(Node node, K key, V value)
	 * 递归终止条件：
	 * 		一直到null节点：node==null
	 * 递归公式：
	 * 		node.right=add(node.right,key,value)//到右子树中添加
	 * 		node.left=add(node.left,key,value)  //到左子树中添加
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
		//在归的过程中，处理每一个节点，维护红黑树的性质
		return keepBalanced(node);
	}
	/*
	 * 保持平衡（黑平衡）：
	 * 什么时候：当添加一个节点后，树可能会失衡
	 * 为什么：保持树的高度不要太大，左右子树的节点数相当，不会让树过于倾斜向左/右
	 * 怎么判断失衡：通过当前节点的左右孩子节点的颜色
	 * 怎么做：通过旋转和变色，维护树的平衡
	 *	 1.向2节点中添加新的节点：
	 *    1）添加37节点							对应2-3树中：	
	 *		42（黑）									37  42（3节点）
	 *	   /						 
	 *	 37（红）				---这种：不做处理          	  
	 *	
	 *	  2）添加42节点	  对应2-3树中：				   左旋：
	 *		37（黑）		  37  42（3节点）         			42（黑） 	
	 *		 \             ----需要左旋转---》      		/	
	 *		 42(红)             					   37（红）							
	 *		需要处理的原因：我们设计红黑树时，便规定42（黑）这种形式代表2-3树中3节点，所以需要旋转
	 *	   								  /						 
	 *									 37（红）
	 *	 2.向3节点中添加新的节点：
	 *	  1）添加66节点：	 对应2-3树中：						 翻转：				
	 *		42（黑）		 37  42  66（4节点）	----filpColor--》42（红）
	 *	  /    \	 ---》4节点的中间节点需要向上找父节点融合		/    \
	 *	37(红)	66（红）	---》而在红黑树中，融合==颜色的翻转     	  37(黑)   66(黑)
	 *
	 *	  2）添加12节点					右旋：			翻转：	
	 *		42（黑）		对应2-3树中：	     37（黑）	 --filpColor->37(红)
	 *	   /    		 12  37  42	     /   \				 /	 \
	 *    37(红)	 	 ---》右旋		  12（红）42（红）		  12（黑）    42（黑）
	 *    /			 ---》颜色翻转
	 *   12（红）
	 *    3）添加12节点					左旋：		  右旋：
	 *		42（黑）		对应2-3树中：	     42（黑）		   40（黑）	 --filpColor->40(红)
	 *	   /    		 37 40  42	     /			   /   \				 /	 \
	 *    37(红)	 	 ---》左旋		 	40(红)		 37（红）42（红）		  37（黑）    42（黑）
	 *     \		 ---》右旋			/
	 *     40（红）	 ---》颜色翻转		   37（红）
	 *     
	 *     统一逻辑：
	 *              1    左旋转：      2     右旋转：       3            颜色反转：     
	 *     黑	-》 	黑     	 -》         黑         -》         黑              -》 	 红            
	 *    /		   /		  /			/    \		   /   \
	 *   红  	               红       		  红                      红             红       	     黑           黑
	 *             \        / 
	 *           	红              红
	 *           
	 *   只要红黑树的节点满足上面1，2，3种任一种情况，都进行相应的操作，即可保持红黑树的平衡性
	 *   对2，3节点都适用
	 */
	private Node keepBalanced(Node node) {
		//第1种情况
		if(isRed(node.right) && ! isRed(node.left)) {
			node=leftRotate(node);
		}
		//第2种情况
		if(isRed(node.left) && isRed(node.left.left)) {
			node=rightRotate(node);
		}
		//第3中情况
		if(isRed(node.left) && isRed(node.right)) {
			flipColor(node);
		}
		return node;
	}
	/*
	 * 左旋转：旋转过程，只是让node和x形成3节点，并不保持红黑树的性质		注意：
	 * 	   node		  	思想：左旋						  x			node和x节点
	 * 	  /    \		思路：						/   \		重新形成一个
	 * 	 T1     x			1.node.right=x.left  node(红) T3		3节点
	 * 		   / \			2.x.left=node		  /  \  
	 *        T2  T3		3.x.color=node.color T1    T2
	 *        				4.node.color=RED
	 */
	private Node leftRotate(Node node) {
		Node rightNode = node.right;
		Node rightNodeLeft =rightNode.left;
		//左旋
		rightNode.left=node;
		node.right=rightNodeLeft;
		//维护颜色
		rightNode.color=node.color;
		node.color=RED;
		
		return rightNode;
	}
	/*
	 * 右旋转：旋转过程，只是让node和x形成3节点，并不保持红黑树的性质		注意：
	 * 	   node		  	思想：右旋						  x			node和x节点
	 * 	  /    \		思路：						/   \		重新形成一个
	 * 	 x		T1			1.node.left=x.right  node(红) T3		3节点
	 * 	/ \					2.x.right=node		  /  \  
	 * T2  T3				3.x.color=node.color T1    T2
	 *        				4.node.color=RED
	 */
	private Node rightRotate(Node node) {
		Node leftNode = node.left;
		Node leftNodeRight = leftNode.right;
		//右旋
		leftNode.right=node;
		node.left=leftNodeRight;
		//维护颜色
		leftNode.color=node.color;
		node.color=RED;
		
		return leftNode;
	}
	/*
	 * 颜色翻转：              			对应2-3树中：		   翻转：
	 * 		    42（黑）     		 32  42  66				42（红）
	 * 		   /    \			4节点的中间节点		   	  /		 \
	 * 		32（红）   66（红）		 需要向上找父节点融合		32(黑)	 66（黑）
	 */
	private void flipColor(Node node) {
		node.color=RED;
		node.left.color=node.right.color=BLACK;
	}
	//判断RBT中是否包含键为key的节点-------递归实现：时间复杂度：O（log（n））
	public boolean contains(K key) {
		Node node = getNode(root, key);
		return node==null?false:true;
	}
	//返回键为key的节点的value值（如果不存在键key，则返回null）--递归实现：时间复杂度：O（log（n））
	public V get(K key) {
		Node node = getNode(root, key);
		return node==null?null:node.value;
	}
	//更新键为key的节点的value值-------递归实现：时间复杂度：O（log（n））
	public void set(K key ,V newValue) {
		Node node = getNode(root, key);
		if(node ==null) {
			throw new RuntimeException("key doesn't exist");
		}
		node.value=newValue;
	}
	//返回以node为根节点的RBT中键为key的节点-------递归实现：时间复杂度：O（log（n））
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
