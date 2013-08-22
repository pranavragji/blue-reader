/**
 * added at 下午2:32:11 2013-8-22
 */
package com.mouselee.bluereader.dao;

import java.util.List;

import com.mouselee.bluereader.R.string;
import com.mouselee.bluereader.vo.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.mouselee.bluereader.dao.BookTableConfig.*;

/**
 * The only way to access databases of book.
 * @author Aaron Lee
 * @Date 下午2:32:11 2013-8-22
 */
public class BooksDao {
	
	private static BooksDao instance;
	
	private DBHelper helper;
	private SQLiteDatabase db;
	
	private BooksDao(Context context) {
		helper = DBHelper.getHelper(context);
		db = helper.getWritableDatabase();
	}
	
	/**
	 * Add all booklist into database
	 *
	 * @Author Aaron Lee at 下午3:33:42 2013-8-22
	 * @param list
	 */
	public void addBookList(List<Book> list) {
		if (list != null) {
			for (Book book : list) {
				addSingleBook(book);
			}
		}
	}
	
	/**
	 * Add only book into databases.
	 *
	 * @Author Aaron Lee at 下午3:34:26 2013-8-22
	 * @param book
	 */
	public void addSingleBook(Book book) {
		ContentValues values = new ContentValues();
		values.put(COL_BOOKNAME, book.getBookname());
		values.put(COL_AUTHOR, book.getAuthor());
		values.put(COL_PUBLISHED, book.getPublished());
		values.put(COL_PUBLISHER, book.getPublisher());
		values.put(COL_SELLER, book.getSeller());
		values.put(COL_CATEGORY, book.getCategory());
		values.put(COL_BOOKTYPE, book.getBooktype());
		values.put(COL_STARTS, book.getStarts());
		values.put(COL_LANGUAGE, book.getLanguage());
		values.put(COL_IMGPATH, book.getImgpath());
		values.put(COL_BOOKPATH, book.getBookpath());
		values.put(COL_DESCRIPTION, book.getDescription());
		values.put(COL_FILESIZE, book.getFilesize());
		values.put(COL_LASTREADTIME, book.getLastReadTime());
		values.put(COL_LASTUPADATETIME, book.getLastUpadateTime());
		values.put(COL_CHARSET, book.getCharset());
		values.put(COL_FONT, book.getFont());
		values.put(COL_FONTSIZE, book.getFontSize());
		values.put(COL_CURINDEX, book.getCurIndex());
		db.insertOrThrow(TABLE_NAME, null, values);
	}
	
	/**
	 * 
	 *
	 * @Author Aaron Lee at 下午4:20:54 2013-8-22
	 * @param id
	 * @param book The data need to insert into. If null, it should not changed the previous.
	 */
	public void updateBook(int id, Book book) {
		ContentValues values = new ContentValues();
		if (book.getBookname() != null) values.put(COL_BOOKNAME, book.getBookname());
		if (book.getAuthor() != null) values.put(COL_AUTHOR, book.getAuthor());
		if (book.getPublished() != null) values.put(COL_PUBLISHED, book.getPublished());
		if (book.getPublisher() != null) values.put(COL_PUBLISHER, book.getPublisher());
		if (book.getSeller() != null) values.put(COL_SELLER, book.getSeller());
		if (book.getSeller() != null) values.put(COL_CATEGORY, book.getCategory());
		if (book.getBooktype() != null) values.put(COL_BOOKTYPE, book.getBooktype());
		if (book.getStarts() > -1) values.put(COL_STARTS, book.getStarts());
		if (book.getLanguage() != null) values.put(COL_LANGUAGE, book.getLanguage());
		if (book.getImgpath() != null) values.put(COL_IMGPATH, book.getImgpath());
		if (book.getBookpath() != null) values.put(COL_BOOKPATH, book.getBookpath());
		if (book.getDescription() != null) values.put(COL_DESCRIPTION, book.getDescription());
		if (book.getFilesize() > 0) values.put(COL_FILESIZE, book.getFilesize());
		if (book.getLastReadTime() > 0) values.put(COL_LASTREADTIME, book.getLastReadTime());
		if (book.getLastUpadateTime() > 0) values.put(COL_LASTUPADATETIME, book.getLastUpadateTime());
		if (book.getCharset() != null) values.put(COL_CHARSET, book.getCharset());
		if (book.getFont() != null) values.put(COL_FONT, book.getFont());
		if (book.getFontSize() > 0) values.put(COL_FONTSIZE, book.getFontSize());
		if (book.getCurIndex() > 0) values.put(COL_CURINDEX, book.getCurIndex());
		db.update(TABLE_NAME, values, ID + " = ? ", new String[]{String.valueOf(id)});
	}
	
	/**
	 * Check whether is in databases,if true, update the data.if not ,insert one.
	 *
	 * @Author Aaron Lee at 下午4:19:51 2013-8-22
	 * @param book
	 */
	public void addOrUpdateSingleBook (Book book) {
		int id = getBookId(book);
		if (id >= 0) {
			// id > 0, the book item has been added in databases.
			updateBook(id, book);
		} else {
			// others, the book item has not been added,it need to insert;
			addSingleBook(book);
		}
	}
	
	/**
	 * Get all the data
	 *
	 * @Author Aaron Lee at 下午4:27:44 2013-8-22
	 * @return
	 */
	public Cursor searchBooks() {
		return db.query(TABLE_NAME, null, null, null, null, null,
				COL_LASTREADTIME + " desc ");
	}

	public int getBookId(Book book) {
		Cursor c = db.query(true, TABLE_NAME, new String[] { ID }, COL_BOOKPATH
				+ " = ? ", new String[] { book.getBookpath() }, null, null,
				null, " 0, 1");
		c.moveToFirst();
		if (!c.isAfterLast()) {
			return c.getInt(0);
		}
		return -1;
	}
	
	/**
	 * Get {@link BooksDao} object in single mode.
	 *
	 * @Author Aaron Lee at 下午2:42:51 2013-8-22
	 * @param context
	 * @return
	 */
	public static synchronized BooksDao getInstance(Context context) {
		if (instance == null) {
			instance = new BooksDao(context);
		}
		return instance;
	}
}
