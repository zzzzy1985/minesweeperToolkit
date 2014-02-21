package com.mstoolkit;

public class Temp
{
    private static int x;
    private static int y;
    private static int left;
    private static int right;
    private static int middle;
    private static int height;
    private static int width;
    private static int openedCounter = 0;
    private static int bbbvSolvedCounter = 0;
    private static Board board = null;
    private static boolean leftPressed = false;
    private static boolean middlePressed = false;
    private static boolean rightPressed = false;
    private static int lCounter = 0;
    private static int dCounter = 0;
    private static int rCounter = 0;
    private static boolean needWaitRelaseAll = false;
    private static int lastImportantFrameIndex = -1;
    private static int flagCounter = 0;

    private static boolean check(int i, int j)
    {
        int myIndex = j * (width + 2) + i;
        int myStatus = board.getCells()[myIndex].status;
        boolean ret = (myStatus == 0 || myStatus == 2); // Can dig
        if (ret)
        {
            if (board.getCells()[myIndex].what == 0)
            {
                bbbvSolvedCounter++;
            }
            else if (board.getCells()[myIndex].what != 9)
            {
                boolean is3BV = true;
                for (int x = i - 1; x <= i + 1; x++)
                {
                    for (int y = j - 1; y <= j + 1; y++)
                    {
                        if (x >= 1 && x <= width && y >= 1 && y <= height)
                        {
                            if (board.getCells()[y * (width + 2) + x].what == 0)
                            {
                                is3BV = false;
                                break;
                            }
                        }
                    }
                }
                if (is3BV)
                {
                    bbbvSolvedCounter++;
                }
            }
        }
        return ret;
    }

    private static void dig(int i, int j)
    {
        int myIndex = j * (width + 2) + i;
        int myStatus = board.getCells()[myIndex].status;
        if (myStatus == 0 || myStatus == 2)
        {
            openedCounter++;
            board.getCells()[myIndex].status = 3;
            if (board.getCells()[myIndex].what == 0)
            {
                for (int x = i - 1; x <= i + 1; x++)
                {
                    for (int y = j - 1; y <= j + 1; y++)
                    {
                        if (x >= 1 && x <= width && y >= 1 && y <= height)
                        {
                            dig(x, y);
                        }
                    }
                }
            }
        }
    }

    private static byte[] getBoardStateAfter(Action action)
    {
        int actionX = action.y / 16 + 1;
        int actionY = action.x / 16 + 1;
        boolean changed = false;

        if (!leftPressed && !middlePressed && !rightPressed)
        {
            if (action.l == 0 && action.m == 0 && action.r == 1)
            {
                rCounter++;
                int myIndex = actionY * (width + 2) + actionX;
                boolean markFlag = false;
                // TODO boolean markFlag = info.markFlag == 1;
                if (markFlag)
                {
                    if (board.getCells()[myIndex].status == 0)
                    {
                        flagCounter++;
                        board.getCells()[myIndex].status = 1;
                    }
                    else if (board.getCells()[myIndex].status == 1)
                    {
                        flagCounter--;
                        board.getCells()[myIndex].status = 2;
                    }
                    else if (board.getCells()[myIndex].status == 2)
                    {
                        board.getCells()[myIndex].status = 0;
                    }
                }
                else
                {
                    if (board.getCells()[myIndex].status == 0)
                    {
                        flagCounter++;
                        board.getCells()[myIndex].status = 1;
                    }
                    else if (board.getCells()[myIndex].status == 1)
                    {
                        flagCounter--;
                        board.getCells()[myIndex].status = 0;
                    }
                }
                changed = true;
            }
        }
        else if (leftPressed && !middlePressed && !rightPressed)
        {
            if (action.l == 0)
            {
                if (needWaitRelaseAll)
                {
                    needWaitRelaseAll = false;
                }
                else
                {
                    lCounter++;
                    if (check(actionX, actionY))
                    {
                        dig(actionX, actionY);
                        changed = true;
                    }
                }
            }
        }
        else if (!leftPressed && !middlePressed && rightPressed)
        {
            if (action.r == 0)
            {
                if (needWaitRelaseAll)
                {
                    needWaitRelaseAll = false;
                }
            }
        }
        else if ((leftPressed && rightPressed) || middlePressed)
        {
            if (action.l == 0 || action.r == 0 || action.m == 0)
            {
                dCounter++;
                if (!leftPressed && middlePressed && !rightPressed)
                {
                    if (action.m == 0)
                    {
                        if (needWaitRelaseAll)
                        {
                            needWaitRelaseAll = false;
                            dCounter--;
                        }
                    }
                }
                if (board.getCells()[actionY * (width + 2) + actionX].status == 3)
                {
                    int flagCount = 0;
                    for (int x = actionX - 1; x <= actionX + 1; x++)
                    {
                        for (int y = actionY - 1; y <= actionY + 1; y++)
                        {
                            if (board.getCells()[y * (width + 2) + x].status == 1)
                            {
                                flagCount++;
                            }
                        }
                    }
                    if (flagCount == board.getCells()[actionY * (width + 2) + actionX].what)
                    {
                        for (int x = actionX - 1; x <= actionX + 1; x++)
                        {
                            for (int y = actionY - 1; y <= actionY + 1; y++)
                            {
                                if (check(x, y))
                                {
                                    dig(x, y);
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        leftPressed = action.l == 1;
        middlePressed = action.m == 1;
        rightPressed = action.r == 1;
        if (!needWaitRelaseAll && ((leftPressed && rightPressed) || middlePressed))
        {
            needWaitRelaseAll = true;
        }
        if (changed || lastImportantFrameIndex < 0)
        {
            return cellsToBytes(board.getCells(), 0);
        }
        else
        {
            return null;
        }
    }

    /**
     * 
     * @param cells
     * @param type
     *            0: Common; 1: ShowMines; 2:ShowFlag
     * @return
     */
    private static byte[] cellsToBytes(Cells[] cells, int type)
    {
        if (cells == null)
        {
            return null;
        }
        int len = cells.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++)
        {
            switch (cells[i].status) {
                case -1: // Wall
                    bytes[i] = (byte) (14 & 0xff);
                    break;
                case 0: // Closed
                    bytes[i] = (byte) (11 & 0xff);
                    switch (type) {
                        case 1:
                            if (cells[i].what == 9)
                            {
                                bytes[i] = (byte) (10 & 0xff);
                            }
                            break;
                        case 2:
                            if (cells[i].what == 9)
                            {
                                bytes[i] = (byte) (12 & 0xff);
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 1: // Flagged
                    bytes[i] = (byte) (12 & 0xff);
                    break;
                case 2: // Marked
                    bytes[i] = (byte) (13 & 0xff);
                    break;
                case 3: // Opened
                    bytes[i] = (byte) (cells[i].what & 0xff);
                    break;
                default:
                    bytes[i] = (byte) (11 & 0xff);
                    break;
            }
        }
        return bytes;
    }
}
