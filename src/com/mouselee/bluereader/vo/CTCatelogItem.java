package com.mouselee.bluereader.vo;

import java.sql.Date;

public class CTCatelogItem implements Comparable<CTCatelogItem> {

	public int id;//书签ID
	public String bookname;//书名
	public int bookid;//书ID
	public String title;//书签内容

	public long date;//日期
	
	public int file; //目录索引编号
	
	public static final String BOOK_NAME="bookname";
	public static final String ID="_id";
	public static final String BOOK_ID="bookid";
	public static final String TITLE="title";

	public static final String DATE="date";
	
	public static final String FILE = "file";
	public CTCatelogItem() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CTCatelogItem(String title, int file) {
		super();
		this.title = title;
		this.file = file;
	}


	@Override
	public int compareTo(CTCatelogItem another) {
		// TODO Auto-generated method stub
		return this.file-another.file;
	}

}
