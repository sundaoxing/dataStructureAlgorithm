package com.binarySearch;

/*
 * ���ֲ��ң�
 * ��Ҫ��������������
 */
public class BinarySearch {
	public int binarySearch(int [] arr,int target) {
		/* [0��1��2��3��4��5��6��7��8��9������arr.length]
		 * [l,......................r,       r+1]
		 */
		int l=0;
		int r=arr.length-1;
		while(l<=r) {
			//�Ż���l+r���ܻ����int��Χ���
			//int mid=(l+r)/2;
			int mid = l+(r-l)/2;
			if(target > arr[mid]) {
				//��mid�Ұ�[mid+1..........r]
				l=mid+1;
			}
			else if(target < arr[mid]){
				//��mid���[l..........mid-1]
				r=mid-1;
			}
			else {
				//����mid���ҵ���
				return mid;
			}
		}
		return -1;//û���ҵ�target
	}
}
