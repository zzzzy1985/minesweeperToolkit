package com.minesweeperToolkit;
import java.io.File;

public class Const
{
  public static boolean useChinese = false;
  public static File lang = new File("cn");
  public static String TITLE;
  public static String VERSION;
  public static String WELCOME;
  public static String AUTHOR;
  public static String DIATITLE;
  public static String NOTADIRECTORY;
  public static String CANNOTACCESS;
  public static String EMPTYFOLDER;
  public static String FOUNDMVF;
  public static String BASIC;
  public static String EXTRA;
  public static String ABOUT;
  public static String OPEN;
  public static String EXPORT;
  public static String QUIT;
  public static String ABOUTSOFT;
  public static String ABOUTAUTHOR;
  public static String GOTOSAOLEINET;
  public static String GOTOAUTHORS;
  public static String CALCULATING;
  public static String INVALID;
  public static String FILENOFOUND;
  public static String NOTHINGEXPORTED;
  public static String EXPORTFAILED;
  public static String INFORMATION;
  public static String WARNING;
  public static String ERROR;
  public static String DONOTINTERRUPT;
  public static String EXPORTTO;
  public static String SUCCESS;
  public static String LOADHISTORY;
  public static String FIXHISTORY;
  public static String SWITCHLANGUAGE;
  public static String DEVELOPING;
  public static String SORRY;
  public static String FORBIDDEN;
  public static String HOWTOUSE;
  public static String CONTACTME;
  public static String OPENBROWSERFAILED;
  public static String SWITCHLANGUAGESUCCESS;
  public static String SWITCHLANGUAGEFAILED;
  public static String RESWITCHLANGUAGE;
  public static String LEVEL_BEG;
  public static String LEVEL_INT;
  public static String LEVEL_EXP;
  public static String LEVEL_CUS;
  public static String MODE_CLS;
  public static String MODE_DEN;
  public static String MODE_UPK;
  public static String MODE_CHT;
  public static String STYLE_FL;
  public static String STYLE_NF;
  public static String[] name;
  public static int[] power2;
  public static String QUESTION;
  public static String CHOOSEHISTORYFILE;
  public static String FINDHISTORYFILE;
  public static String LOADHISTORYFAILED;
  public static String DOYOUWANNAFIX;
  public static String[] historyName;
  public static final String SAOLEINETURL = "http://www.saolei.net";
  public static final String ZHOUKESPLACE = "http://www.saolei.net/Player/Index.asp?Id=6284";
  public static String EXPORTTABLE;
  public static String WANNAOVERWRITE;
  public static String CHOOSEXLSFILE;
  public static File f;

  static
  {
    useChinese = !lang.exists();

    TITLE = useChinese ? "扫雷工具箱" : 
      "Minesweeper Tool Kit";
    VERSION = "V0.72 Alpha";
    WELCOME = useChinese ? "欢迎使用扫雷工具箱" : 
      "Welcome to use Minesweeper Tool Kit";
    AUTHOR = useChinese ? "作者：crazyks，感谢超级丹、石教授、击晕、技术帝以及扫雷网（Saolei.net）的全体成员！" : 
      "Made by crazyks! Great thanks to Dan Zhou, Weiguo Shi,  Jiyun Wang and all member of Saolei.net.";
    DIATITLE = useChinese ? "请选择一个有MVF文件的文件夹" : 
      "Please choose a folder with MVF Files";
    NOTADIRECTORY = useChinese ? "不是一个文件夹" : 
      "is not a directory!";
    CANNOTACCESS = useChinese ? "不可访问" : "can not access!";
    EMPTYFOLDER = useChinese ? "没有找到任何MVF文件，文件夹：" : 
      "There is no any MVF file in";
    FOUNDMVF = useChinese ? "个MVF文件被检测到" : 
      "MVF File(s) found in";
    BASIC = useChinese ? "基础工具箱" : "Basic Kit";
    EXTRA = useChinese ? "扩展工具箱" : "Extra Kit";
    ABOUT = useChinese ? "关于" : "About";
    OPEN = useChinese ? "打开MVF文件夹" : "Open MVF Floder";
    EXPORT = useChinese ? "导出MVF信息" : "Export MVF Info";
    QUIT = useChinese ? "退出" : "Quit";
    ABOUTSOFT = useChinese ? "软件的使用说明" : "Software";
    ABOUTAUTHOR = useChinese ? "作者的联系方式" : "Author";
    GOTOSAOLEINET = useChinese ? "去扫雷网看看" : 
      "Go to Saolei.net";
    GOTOAUTHORS = useChinese ? "去瞅瞅作者" : 
      "Go to Author's Page";
    CALCULATING = useChinese ? "统计中..." : "Calculating...";
   // INVALID = useChinese ? "毛大叔V5" : "Invalid!";
    INVALID = useChinese ? "ZZV5" : "Invalid!";
    FILENOFOUND = useChinese ? "文件丢失" : "No File!";
    NOTHINGEXPORTED = useChinese ? "导不出任何东东！" : 
      "Nothing can be exported!";
    EXPORTFAILED = useChinese ? "导出失败" : "Export failed!";
    INFORMATION = useChinese ? "信息" : "Information";
    WARNING = useChinese ? "警告" : "Warning";
    ERROR = useChinese ? "错误" : "Error";
    DONOTINTERRUPT = useChinese ? "请不要终止统计！" : 
      "CALCULATION NOT FINISHED! PlEASE DO NOT INTERRUPT!";
    EXPORTTO = useChinese ? "MVF信息导出至：" : "Export to";
    SUCCESS = useChinese ? "成功" : "Success!";
    LOADHISTORY = useChinese ? "加载history.inf文件" : 
      "Load History File";
    FIXHISTORY = useChinese ? "修复history.inf文件" : 
      "Fix History File";
    SWITCHLANGUAGE = useChinese ? "to English" : "使用中文";
    DEVELOPING = useChinese ? "开发中..." : "Developing...";
    SORRY = useChinese ? "抱歉" : "Sorry";
    FORBIDDEN = useChinese ? "此项功能禁止使用！" : 
      "This function is forbidden to use!";
    HOWTOUSE = useChinese ? "0. 本软件只适用于扫雷克隆版0.97！\n1. 在“基本工具箱”中，你可以打开一个有MVF文件的文件夹，软件会统计出该文件夹下每一个MVF文件中包含的信息。\n    统计完成后，你可以将表格导出为XLS格式的文件。\n2. 在“扩展工具箱”中，你可以加载history.inf文件。\n    如果你的history.inf文件被毛大叔光环侵蚀，本软件可以将其修复，不过此项功能正在开发中。\n3. 在“扩展工具箱”中，你可以在英文和中文之间切换\n4. 感谢石教授、超级丹、击晕以及扫雷网（Saolei.net）的全体成员。" : 
      "0. This software if only for Minesweeper Clone 0.97\n1. In \"Basic Kit\", you can [open] a folder with MVF files, then this software will list the information of each MVF file.\n    After calculation completed, you can [export] the table into an XLS file.\n2. In \"Extra Kit\", you can [Load] your history.inf.\n    If it got broken, this software will [fix] it for you,however, this function is in developing.\n3. In \"Extra Kit\", you can switch the language between English and Chinese.\n4. Great thanks to Weiguo Shi, Ye Zhang, Dan zhou, Jiyun Wang and all member of Saolei.net.";

    CONTACTME = useChinese ? "欢迎任何意见和建议！\n联系我：crazyks@yeah.net" : 
      "Welcome any comment or suggestion!\nContacts me: crazyks@yeah.net";
    OPENBROWSERFAILED = useChinese ? "打开网页失败" : 
      "Open Browser Failed!";
    SWITCHLANGUAGESUCCESS = useChinese ? "切换语言成功，重启软件后生效~" : 
      "Switch Language Success, It will be available after restart!";
    SWITCHLANGUAGEFAILED = useChinese ? "切换语言失败！" : 
      "Switch Language Failed!";
    RESWITCHLANGUAGE = useChinese ? "重复操作，切换语言重启后生效！" : 
      "Reswitch Language! It will be available after restart!";

    LEVEL_BEG = useChinese ? "初级" : "Beginner";
    LEVEL_INT = useChinese ? "中级" : "Intermediate";
    LEVEL_EXP = useChinese ? "高级" : "Expert";
    LEVEL_CUS = useChinese ? "自定义" : "Custom";

    MODE_CLS = useChinese ? "经典" : "Classic";
    MODE_DEN = useChinese ? "密集" : "Density";
    MODE_UPK = useChinese ? "UPK" : "UPK";
    MODE_CHT = useChinese ? "作弊" : "Cheat";

    STYLE_FL = useChinese ? "飞标" : "Flagging";
    STYLE_NF = useChinese ? "盲扫" : "Non-Flagging";

    name = new String[] { useChinese ? "编号" : "No.", 
      useChinese ? "文件名" : "File Name", "文件类型","版本",useChinese ? "标识" : "ID", 
      useChinese ? "日期" : "Date", useChinese ? "级别" : "Level", 
      useChinese ? "风格" : "Style", useChinese ? "模式" : "Mode", 
      useChinese ? "时间" : "Time", "3BV", "3BV/s", 
      useChinese ? "移动距离" : "Distance", "Click/s", "ZiNi", "H-ZiNi", "RQP", 
      "IOE", useChinese ? "完成度" : "Completion" ,"num0","num1","num2","num3",
    		  "num4","num5","num6","num7","num8",
    		  "numAll","disSpeed","openings","allClicks","disBv",
    		  "disNum","hzoe","zoe","numSpeed","hzinis","zinis","occam",
    		  "lclicks","dclicks","rclicks","qg","flags","markFlag","islands"};

    power2 = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 
      2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 
      1048576, 2097152, 4194304 };

    QUESTION = useChinese ? "请问" : "Question";
    CHOOSEHISTORYFILE = useChinese ? "选择一个history.inf文件" : 
      "Please choose a history.inf file";
    FINDHISTORYFILE = useChinese ? "开始解析History文件" : 
      "Start Parsing histroy.inf";
    LOADHISTORYFAILED = useChinese ? "加载history.inf文件失败！" : 
      "Load History File Failed!";
    DOYOUWANNAFIX = useChinese ? "文件貌似损坏，您打算修复此history.inf文件吗？" : 
      "This file maybe broken. Do you wanna Fix this histroy.inf?";
    historyName = new String[] { useChinese ? "编号" : "No.", 
      useChinese ? "级别" : "Level", useChinese ? "风格" : "Style", 
      useChinese ? "时间" : "Time", useChinese ? "日期" : "Date", "3BV", 
      "3BV/s", "Click/s", "RQP", "IOE","L","D","R","QG" };

    EXPORTTABLE = useChinese ? "导出表格" : 
      "Export table to file";
    WANNAOVERWRITE = useChinese ? "文件已存在，您打算覆盖？" : 
      "File already exited! OVER WRITE IT?";
    CHOOSEXLSFILE = useChinese ? "请输入您要保存的文件名" : 
      "Choose an xls file to save the table";

    f = new File("debug.txt");
  }
}