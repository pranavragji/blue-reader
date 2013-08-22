package com.mouselee.bluereader.vo;

import java.sql.Date;

public class CatelogItem implements Comparable<CatelogItem> {

	public int id;//书签ID
	public String bookname;//书名
	public int bookid;//书ID
	public String title;//书签内容
	public int pageIndex;//页码索引
	public long date;//日期
	public int lastEnter;//
	public int percent;//百分比,全屏浏览用
	public int curLine;//当前行,全屏浏览用
	public int offset;//首行在屏幕上的偏移,全屏浏览用
	public int file; //目录索引编号
	public String font;//字体
	
	public static final String BOOK_NAME="bookname";
	public static final String ID="_id";
	public static final String BOOK_ID="bookid";
	public static final String TITLE="title";
	public static final String PAGEINDEX = "page";
	public static final String DATE="date";
	public static final String LAST_ENTER = "last_enter";
	public static final String PERCENT = "percent";
	public static final String CURLINE = "current_line";
	public static final String LINEOFFSET = "line_offset";
	public static final String FILE = "file";
	public static final String FONT = "font";
	public CatelogItem() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CatelogItem(String title, int pageIndex) {
		super();
		this.title = title;
		this.pageIndex = pageIndex;
	}


	@Override
	public int compareTo(CatelogItem another) {
		// TODO Auto-generated method stub
		return this.pageIndex-another.pageIndex;
	}

}
