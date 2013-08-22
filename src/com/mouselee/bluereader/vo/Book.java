package com.mouselee.bluereader.vo;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type is the bean of Book in local. It contains basic messages.
 * 
 * @author Aaron Lee
 * @Date 上午10:34:33 2013-8-22
 */
@SuppressLint("ParcelCreator")
public class Book implements Parcelable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int mData;
	
	/** The book's ID ,it saved in databases */
	private int id;
	/** The book's name in showing , maybe not same as its filename */
	private String bookname;
	/** The book's author */
	private String author;
	/** The book's published data,it confire by publisher */
	private String published;
	/** The book's publisher */
	private String publisher;
	/** The book's seller */
	private String seller;
	/** The book's category */
	private String category;
	/** The book's type,which is syncopated by user */
	private String booktype;
	/** The book's starts,which is rated by user */
	private int starts;
	/** The book's language */
	private String language;
	/** The book's cover image saved path */
	private String imgpath;
	/** The book's file saved path */
	private String bookpath;
	/** The book's file description */
	private String description;
	/** The book's charectors sizes */
	private int filesize;
	/** The time of this book last read by user */
	private long lastReadTime;
	/** The book's last update millions */
	private long lastUpadateTime;
	/** The book's charset */
	private String charset;
	/** The book's font-type */
	private String font;
	/** The book's font-size */
	private int fontSize;
	/** The index of this book read by user last time */
	private int curIndex;
	
	public static final Parcelable.Creator<Book> CREATOR
	    = new Parcelable.Creator<Book>() {
	public Book createFromParcel(Parcel in) {
	    return new Book(in);
	}
	
	public Book[] newArray(int size) {
	    return new Book[size];
	}
	};
	
	private Book(Parcel in) {
        mData = in.readInt();
    }
	
	/**
	 * @Author Aaron Lee at 上午11:56:09 2013-8-22
	 */
	public Book() {
		super();
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the bookname
	 */
	public String getBookname() {
		return bookname;
	}

	/**
	 * @param bookname
	 *            the bookname to set
	 */
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the published
	 */
	public String getPublished() {
		return published;
	}

	/**
	 * @param published
	 *            the published to set
	 */
	public void setPublished(String published) {
		this.published = published;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the seller
	 */
	public String getSeller() {
		return seller;
	}

	/**
	 * @param seller
	 *            the seller to set
	 */
	public void setSeller(String seller) {
		this.seller = seller;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the booktype
	 */
	public String getBooktype() {
		return booktype;
	}

	/**
	 * @param booktype
	 *            the booktype to set
	 */
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	/**
	 * @return the starts
	 */
	public int getStarts() {
		return starts;
	}

	/**
	 * @param starts
	 *            the starts to set
	 */
	public void setStarts(int starts) {
		this.starts = starts;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the imgpath
	 */
	public String getImgpath() {
		return imgpath;
	}

	/**
	 * @param imgpath
	 *            the imgpath to set
	 */
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	/**
	 * @return the bookpath
	 */
	public String getBookpath() {
		return bookpath;
	}

	/**
	 * @param bookpath
	 *            the bookpath to set
	 */
	public void setBookpath(String bookpath) {
		this.bookpath = bookpath;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the filesize
	 */
	public int getFilesize() {
		return filesize;
	}

	/**
	 * @param filesize
	 *            the filesize to set
	 */
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	/**
	 * @return the lastReadTime
	 */
	public long getLastReadTime() {
		return lastReadTime;
	}

	/**
	 * @param lastReadTime
	 *            the lastReadTime to set
	 */
	public void setLastReadTime(long lastReadTime) {
		this.lastReadTime = lastReadTime;
	}

	/**
	 * @return the lastUpadateTime
	 */
	public long getLastUpadateTime() {
		return lastUpadateTime;
	}

	/**
	 * @param lastUpadateTime
	 *            the lastUpadateTime to set
	 */
	public void setLastUpadateTime(long lastUpadateTime) {
		this.lastUpadateTime = lastUpadateTime;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the curIndex
	 */
	public int getCurIndex() {
		return curIndex;
	}

	/**
	 * @param curIndex
	 *            the curIndex to set
	 */
	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}

	@Override
	public String toString() {
		/*
		 * return "bookid:" + id + ",bookname:" + bookname + ",downloadPath:" +
		 * downloadPath;
		 */
		return String.format("bookid: %d, bookname: %s, path %s", id, bookname,
				bookpath);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bookpath == null) ? 0 : bookpath.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookpath == null) {
			if (other.bookpath != null)
				return false;
		} else if (!bookpath.equals(other.bookpath))
			return false;
		return true;
	}

	@Override
    public int describeContents() {
        return 0;
    }
	
	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

}
