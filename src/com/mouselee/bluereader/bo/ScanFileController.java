/**
 * added at 下午4:44:45 2013-8-22
 */
package com.mouselee.bluereader.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.mouselee.bluereader.dao.BooksDao;
import com.mouselee.bluereader.util.BookConfig;
import com.mouselee.bluereader.util.Tools;
import com.mouselee.bluereader.vo.Book;

/**
 * Control scanning of file in disk.
 * 
 * @author Aaron Lee
 * @Date 下午4:44:45 2013-8-22
 */
public class ScanFileController {
	
	private static ScanFileController instance;
	
	private Context context;
	
	
	public static ScanFileController getInstance(Context context) {
		if(instance == null) {
			instance = new ScanFileController(context);
		} 
		return instance;
	}

	/**
	 * @Author Aaron Lee at 下午5:46:03 2013-8-23
	 * @param context
	 */
	private ScanFileController(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 
	 *
	 * @Author Aaron Lee at 下午7:44:24 2013-8-23
	 * @param scanRoot
	 * @return
	 */
	public List<Book> scanDirector(String scanRoot) {
		List<Book> list = new ArrayList<Book>();
		traverseDir(new File(BookConfig.BASIC_DIRECTOR), list);
		BooksDao.getInstance(context).addBookList(list);
		return list;
	}

	private void traverseDir(File dir, List<Book> list) {

		if (!dir.isDirectory() || dir.isHidden()) {
			return;
		}
		File[] children = dir.listFiles();
		if (children == null) {
			return;
		}

		for (File f : children) {
			if (f.isDirectory()) {
				// if it is directory ,call call the method itself and scan the
				// children files.
				traverseDir(dir, list);
			} else {
				// if it is file scan it and put into list.
				String path = f.getPath();
				String format = BookConfig.judgeFileType(path);
				if (format != null) {
					// First scanning only add the message provider from filr
					Book book = new Book();
					book.setBookname(Tools.getFileNameWithoutExten(path));
					book.setBookpath(path);
					book.setBooktype(format);
					//book.setFilesize(f.length());
					list.add(book);
				}
			}
			
		}

	}

}
