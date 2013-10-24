package com.mc97toolkit;

public class Board {
	private Cells[] cells = null;

	public Board(MVFInfo mvfInfo) {
		super();
		cells = mvfInfo.cells;
		for (int i = 0; i < mvfInfo.height + 2; i++) {
			cells[i * (mvfInfo.width + 2) + 0].status = -1;
			cells[i * (mvfInfo.width + 2) + mvfInfo.width + 1].status = -1;
		}
		for (int i = 0; i < mvfInfo.width + 2; i++) {
			cells[i].status = -1;
			cells[(mvfInfo.height + 1) * (mvfInfo.width + 2) + i].status = -1;
		}
//		for (int y = 1; y <= mvfInfo.height; y++) {
//			for (int x = 1; x <= mvfInfo.width; x++) {
//				int myIndex = y * (mvfInfo.width + 2) + x;
//				System.out.print(String.format("%d ", cells[myIndex].what));
//			}
//			System.out.println();
//		}
	}
	
	public Cells[] getCells() {
		return this.cells;
	}
}
