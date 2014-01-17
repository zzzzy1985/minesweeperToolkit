package com.mstoolkit.videoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.mstoolkit.bean.CellBean;

/**
 * 文件解析接口
 * 
 * @author zhangYe
 * @date 2013-11-3
 */
public class VideoTest {
	public static void main(String[] args) {
		//calcBv(8, 8, 10, 100);
	}

	public static int[][] calcBv(int height, int width, int mines, int calc,JFrame frame) {
		long time = System.currentTimeMillis();
		//System.out.println(time);
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < 400; i++) {
			lst.add(0);
		}
		for (long ij = 0; ij < calc; ij++) {
			getRandombv(height, width, mines, lst);
		}
		long time2 = System.currentTimeMillis();
		double time3 = (time2 - time) / 1000d;
		int aa=0;
		for (int i = 0; i < 400; i++) {
			if (lst.get(i) > 0) {
				aa++;
				//System.out.println(i + " " + lst.get(i));
			}
			else{
			
			}
		}
		int[][] ss=new int[aa][2];
		int j=0;
		for (int i = 0; i < 400; i++) {
			if (lst.get(i) > 0) {
				ss[j][0]=i;
				ss[j][1]=lst.get(i);
				j++;
			}
			else{
			
			}
		}
		//System.out.println(time2);
		//System.out.println(time3);
		return ss;
	}

	public static void getRandombv(int height, int width, int mines,
			List<Integer> lst) {
		int openings = 0;
		int bbbv = 0;
		int islands = 0;
		int size = width * height;
		CellBean[] board = new CellBean[size];
		// 生成随机数算法
		// 种子你可以随意生成，但不能重复
		int[] seed;
		seed = new int[size - 1];
		for (int t = 1; t <= size - 1; t++) {
			seed[t - 1] = t;
		}
		int[] ranArr = new int[size - 1];
		Random ran = new Random();
		// 数量你可以自己定义。
		for (int i = 0; i < mines; i++) {
			// 得到一个位置
			int j = ran.nextInt(seed.length - i);
			// 得到那个位置的数值
			ranArr[i] = seed[j];
			// 将最后一个未用的数字放到这里
			seed[j] = seed[seed.length - 1 - i];
		}

		int[] ss = new int[mines];
		for (int i = 0; i < mines; i++) {
			ss[i] = ranArr[i];
			// System.out.print(ss[i]+" ");
		}

		for (int i = 0; i < size; i++) {
			board[i] = new CellBean();
			board[i].mine = (board[i].opened = board[i].flagged = board[i].opening = board[i].opening2 = 0);
		}
		// System.out.println(" ");
		for (int i = 0; i < mines; i++) {

			board[ss[i]].mine = 1;
		}
		/** initialize cell positions */
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int index = c * height + r;
				board[index].rb = (r != 0 ? r - 1 : r);
				board[index].re = (r == height - 1 ? r : r + 1);
				board[index].cb = (c != 0 ? c - 1 : c);
				board[index].ce = (c == width - 1 ? c : c + 1);
			}

		}
		/** initialize numbers */
		for (int i = 0; i < size; i++) {
			board[i].premium = (-(board[i].number = getnumber(board,
					height, i)) - 2);
		}
		for (int i = 0; i < size; i++)
			if ((board[i].number == 0) && (board[i].opening == 0)) {
				processopening(board, height, ++openings, i);
			}

		for (int i = 0; i < size; i++) {
			if (board[i].mine == 1) {
				board[i].openingAr = 2;
			}
			if ((board[i].opening > 0) && (board[i].mine == 0)) {
				board[i].openingAr = 1;
				for (int rr = board[i].rb; rr <= board[i].re; rr++)
					for (int cc = board[i].cb; cc <= board[i].ce; cc++)
						if (board[(cc * height + rr)].openingAr != 2) {
							board[(cc * height + rr)].openingAr = 1;
						}
			}
		}

		for (int i = 0; i < size; i++) {
			if (board[i].openingAr == 0) {
				board[i].openingAr = 3;
				islands(board, height, size, i);
				islands++;
			}
		}

		bbbv = openings;
		for (int i = 0; i < size; i++) {
			if ((board[i].opening == 0) && (board[i].mine == 0))
				bbbv++;
			board[i].premium += getadj3bv(board, height, i);
		}
		lst.set(bbbv, lst.get(bbbv) + 1);
		// lst.set(islands, lst.get(islands)+1);
	}

	private static int getadj3bv(CellBean[] board, int height, int index) {
		int res = 0;
		if (board[index].number == 0)
			return 1;
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				int i = cc * height + rr;
				res += ((board[i].mine == 0) && (board[i].opening == 0) ? 1 : 0);
			}
		if (board[index].opening != 0) {
			res++;
		}
		if (board[index].opening2 != 0) {
			res++;
		}
		return res;
	}

	private static void islands(CellBean[] board, int height, int size,
			int index) {
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				if (board[(cc * height + rr)].openingAr == 0) {
					board[(cc * height + rr)].openingAr = 3;
					islands(board, height, size, (cc * height + rr));

				}
			}

	}

	private static void setopeningborder(CellBean[] board, int op_id, int index) {
		if (board[index].opening == 0) {
			board[index].opening = op_id;
		} else if (board[index].opening != op_id) {
			board[index].opening2 = op_id;
		}
	}

	private static void processopening(CellBean[] board, int height, int op_id,
			int index) {
		board[index].opening = op_id;

		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				int i = cc * height + rr;
				if (board[i].number != 0)
					setopeningborder(board, op_id, i);
				else if (board[i].opening == 0)
					processopening(board, height, op_id, i);
			}
	}

	private static int getnumber(CellBean[] board, int height, int index) {
		int res = 0;
		for (int rr = board[index].rb; rr <= board[index].re; rr++) {
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				res += board[(cc * height + rr)].mine;
			}
		}
		return res;
	}
}
