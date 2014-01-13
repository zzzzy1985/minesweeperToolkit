package com.minesweeperToolkit.videoUtil;

import com.minesweeperToolkit.bean.BoardBean;
import com.minesweeperToolkit.bean.CellBean;

/**
 * 文件解析接口
 * 
 * @author zhangYe
 * @date 2013-11-3
 */
public class BoardCommon {
	private static int closed_cells = 0;
	private static int zini = 0;
	private static int hzini = 0;
	/**
	 * 转换录像
	 * 这段代码采取的方式是先竖再横？
	 * @param rawVideoBean
	 * @param videoDisplayBean
	 */
	public static BoardBean getBoardBean(int width, int height, int mines,
			CellBean[]   board) {
		int size = width * height;
		closed_cells=width*height;
//列 比如1 6
		int openings = 0;
		int bbbv = 0;
		int num0 = 0;
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		int num4 = 0;
		int num5 = 0;
		int num6 = 0;
		int num7 = 0;
		int num8 = 0;
		int islands=0;
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
			board[i].premium = (-(board[i].number = getnumber(board, height, i)) - 2);
		}
		for (int i = 0; i < size; i++) {
			if (board[i].mine == 0) {
				switch (board[i].number) {
				case 0:
					num0++;
					break;
				case 1:
					num1++;
					break;
				case 2:
					num2++;
					break;
				case 3:
					num3++;
					break;
				case 4:
					num4++;
					break;
				case 5:
					num5++;
					break;
				case 6:
					num6++;
					break;
				case 7:
					num7++;
					break;
				case 8:
					num8++;
					break;
				}
			}
		}
		//=0*16+5
		//  现在=		5 *width 
		for (int i = 0; i < size; i++)
			if ((board[i].number == 0) && (board[i].opening == 0)) {
				processopening(board, height, ++openings, i);
			}
		
		for (int i = 0; i < size; i++){
			if(board[i].mine == 1){
				board[i].openingAr=2;
			}
			if ((board[i].opening >0) && (board[i].mine == 0) ){
				board[i].openingAr=1;
				for (int rr = board[i].rb; rr <= board[i].re; rr++)
					for (int cc = board[i].cb; cc <= board[i].ce; cc++)
						if(board[(cc * height + rr)].openingAr!=2){
						board[(cc * height + rr)].openingAr=1;
						}
			}
		}
		
		for (int i = 0; i < size; i++){
			if(board[i].openingAr ==0){
				board[i].openingAr =3;
				islands(board,height,size,i);
				islands++;
			}
		}
	  
		bbbv = openings;
		for (int i = 0; i < size; i++) {
			if ((board[i].opening == 0) && (board[i].mine == 0))
				bbbv++;
			board[i].premium += getadj3bv(board, height, i);
		}
	/*	*//** initialize cell positions *//*
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int index = c * height + r;
				System.out.print(board[index].number);
			}
			System.out.println();
		}*/
	    // 计算zini
		zinialg(false,mines,board,height,size);
		for (int i = 0; i < size; i++) {
			board[i].opened =0;
			board[i].flagged =0;
			board[i].premium = (-(board[i].number = getnumber(board, height, i)) - 2);
		}
		for (int i = 0; i < size; i++) {
			board[i].premium += getadj3bv(board, height, i);
		}
		/** initialize cell positions *//*
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int index = c * height + r;
				System.out.print(board[index].number);
			}
			System.out.println();
		}*/
		// 计算hzini
		zinialg(true,mines,board,height,size);
			//zini=0;
			// 计算hzini
			//if(hitops) hitopenings();
			//while(closed_cells>mines)
			//{
				//applyzini(board, height, size,human);
				//if(turns)
				//	pause();
				//else if(infos)
				//	printf("\n");
			//}
		/**zini = 0;
		for (int j = 0; j < size; j++) {
			if ((board[j].number == 0) && (board[j].opened == 0)) {
				click(board, height, size, j);
			}
		}
		while (closed_cells > mines) {
			applyzini(board, height, size);
		}*/
	
	
		BoardBean boardBean =new BoardBean();
		boardBean.setBbbv(bbbv);
		boardBean.setIslands(islands);
		boardBean.setNum0(num0);
		boardBean.setNum1(num1);
		boardBean.setNum2(num2);
		boardBean.setNum3(num3);
		boardBean.setNum4(num4);
		boardBean.setNum5(num5);
		boardBean.setNum6(num6);
		boardBean.setNum7(num7);
		boardBean.setNum8(num8);
		boardBean.setOpenings(openings);
		boardBean.setZini(zini);
		boardBean.setHzini(hzini);

		return boardBean;
	}
	/**
	 * 计算zini
	 * 
	 * @param isHuman true 时计算hzini false 计算zini
	 */
	private static void zinialg(boolean isHuman,int mines,CellBean[] board, int height, int size) {
		
		
		if(isHuman) {
			hzini=0;
			closed_cells=size;
			hitopenings(board, height,size);
		}else{
			zini=0;
		}
		
		while(closed_cells>mines)
		{
			applyzini(isHuman,board, height, size);
			
		}
	}

	private static void hitopenings(CellBean[] board, int height, int size) {
		int j;
		for(j=0;j<size;++j)
			{
			if(board[j].number==0 && board[j].opened==0)
				click(board,height,size,j,true);
			}
		
	}
	private static void islands(CellBean[] board, int height, int size, int index) {
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++)
			{
				if(board[(cc * height + rr)].openingAr==0){
					board[(cc * height + rr)].openingAr=3;
					islands(board,height,size,(cc * height + rr));
			
				}
			}
				
	}
	private static void click(CellBean[] board, int height, int size, int index,boolean ishuman) {
		reveal(board, height, size, index);
		if (ishuman) {
			hzini += 1;
		} else {
			zini += 1;
		}
	}
	

	private static void flag(CellBean[] board, int height, int index,boolean isHuman) {
		if (board[index].flagged != 0) {
			return;
		}
		if(isHuman){
			hzini+=1;
		}
		else{
		zini += 1;
		}
		board[index].flagged = 1;
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++)
				board[(cc * height + rr)].premium += 1;
	}

	private static void flagaround(CellBean[] board, int height, int index,boolean isHuman) {
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				int i = cc * height + rr;
				if (board[i].mine != 0)
					flag(board, height, i,isHuman);
			}
	}
///* open all neighbours */
	private static void chord(CellBean[] board, int height, int size, int index,boolean isHuman) {
		if(isHuman){
			hzini+=1;
		}
		else{
		zini += 1;
		}
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++)
				reveal(board, height, size, cc * height + rr);
	}

	private static void applyzini(boolean human,CellBean[] board, int height, int size) {
		int maxp = -1;
		int curi = -1;
		for(int i=0;i<size;++i)
		{
			if(board[i].premium>maxp &&
				(board[i].mine==0) &&
				((board[i].opened>0)||!human))
			{
				maxp=board[i].premium;
				curi=i;
			}
		}
		if (curi != -1) {
			if (board[curi].opened == 0) {
				click(board, height, size, curi,human);
			}
			flagaround(board, height, curi,human);
			chord(board, height, size, curi,human);
		} else {
			/** left-click */
			for (int j = 0; j < size; j++) {
				if ((board[j].opened == 0) && (board[j].mine == 0)
						&& ((board[j].number == 0) || (board[j].opening == 0)))
				{
				curi = j;
				break;
				}
			}
			click(board, height, size, curi,human);
		}
	}

	private static void reveal(CellBean[] board, int height, int size, int index) {
		if (board[index].opened != 0) {
			return;
		}
		if (board[index].flagged != 0) {
			return;
		}
		if (board[index].number != 0) {
			open(board, height, index);
		} else {
			int op = board[index].opening;

			for (int i = 0; i < size; i++)
				if ((board[i].opening2 == op) || (board[i].opening == op)) {
					if (board[i].opened == 0) {
						open(board, height, i);
					}
					board[i].premium -= 1;
				}
		}
	}

	private static void open(CellBean[] board, int height, int index) {
		board[index].opened = 1;
		board[index].premium += 1;
		if (board[index].opening == 0) {
			for (int rr = board[index].rb; rr <= board[index].re; rr++) {
				for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
					board[(cc * height + rr)].premium -= 1;
				}
			}
		}
		closed_cells -= 1;
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

	private static void setopeningborder(CellBean[] board, int op_id, int index) {
		if (board[index].opening == 0)
			{board[index].opening = op_id;}
		else if (board[index].opening != op_id)
			{board[index].opening2 = op_id;}
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
}
