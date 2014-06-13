/**
 * added at 下午12:03:24 2013-8-22
 */
package com.mouselee.bluereader.dao;

/**
 * @author Aaron Lee
 * @Date 下午12:03:24 2013-8-22
 */
public class BookTableConfig {
	
	public static final String TABLE_NAME = "book";
	public static final String ID = "_id";
	public static final String COL_BOOKNAME        = "bookname"       ;        
	public static final String COL_AUTHOR          = "author"         ;          
	public static final String COL_PUBLISHED       = "published"      ;       
	public static final String COL_PUBLISHER       = "publisher"      ;       
	public static final String COL_SELLER          = "seller"         ;          
	public static final String COL_CATEGORY        = "category"       ;        
	public static final String COL_BOOKTYPE        = "booktype"       ;        
	public static final String COL_STARTS          = "starts"         ;          
	public static final String COL_LANGUAGE        = "language"       ;        
	public static final String COL_IMGPATH         = "imgpath"        ;         
	public static final String COL_BOOKPATH        = "bookpath"       ;        
	public static final String COL_DESCRIPTION     = "description"    ;     
	public static final String COL_FILESIZE        = "filesize"       ;        
	public static final String COL_LASTREADTIME    = "lastReadTime"   ;    
	public static final String COL_LASTUPADATETIME = "lastUpadateTime"; 
	public static final String COL_CHARSET         = "charset"        ;         
	public static final String COL_FONT            = "font"           ;            
	public static final String COL_FONTSIZE        = "fontSize"       ;        
	public static final String COL_CURINDEX        = "curIndex"       ;
	public static final String COL_SID             = "sid";
	
	static String createTableBookSQL;
	
	static String getCreateTableBookSQL () {
		if (createTableBookSQL == null) {
			createTableBookSQL = String.format(
					"CREATE TABLE %s (_id integer primary key AUTOINCREMENT,%s text not null,%s varchar,%s varchar,%s varchar,%s varchar,%s varchar,%s varchar,%s integer,%s varchar,%s varchar,%s varchar,%s varchar,%s bigint,%s bigint,%s bigint，COL_CHARSET varchar,%s varchar,%s integer,%s integer, %s integer)",
					TABLE_NAME,
					COL_BOOKNAME       ,
					COL_AUTHOR         ,
					COL_PUBLISHED      ,
					COL_PUBLISHER      ,
					COL_SELLER         ,
					COL_CATEGORY       ,
					COL_BOOKTYPE       ,
					COL_STARTS         ,
					COL_LANGUAGE       ,
					COL_IMGPATH        ,
					COL_BOOKPATH       ,
					COL_DESCRIPTION    ,
					COL_FILESIZE       ,
					COL_LASTREADTIME   ,
					COL_LASTUPADATETIME,
					COL_CHARSET        ,
					COL_FONT           ,
					COL_FONTSIZE       ,
					COL_CURINDEX,
					COL_SID     );
		}
		return createTableBookSQL;
	}
}
