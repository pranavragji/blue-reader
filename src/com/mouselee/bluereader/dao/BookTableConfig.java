/**
 * added at 下午12:03:24 2013-8-22
 */
package com.mouselee.bluereader.dao;

/**
 * @author Aaron Lee
 * @Date 下午12:03:24 2013-8-22
 */
class BookTableConfig {
	
	static final String TABLE_NAME = "book";
	static final String ID = "_id";
	
	static final String COL_BOOKNAME        = "bookname"       ;        
	static final String COL_AUTHOR          = "author"         ;          
	static final String COL_PUBLISHED       = "published"      ;       
	static final String COL_PUBLISHER       = "publisher"      ;       
	static final String COL_SELLER          = "seller"         ;          
	static final String COL_CATEGORY        = "category"       ;        
	static final String COL_BOOKTYPE        = "booktype"       ;        
	static final String COL_STARTS          = "starts"         ;          
	static final String COL_LANGUAGE        = "language"       ;        
	static final String COL_IMGPATH         = "imgpath"        ;         
	static final String COL_BOOKPATH        = "bookpath"       ;        
	static final String COL_DESCRIPTION     = "description"    ;     
	static final String COL_FILESIZE        = "filesize"       ;        
	static final String COL_LASTREADTIME    = "lastReadTime"   ;    
	static final String COL_LASTUPADATETIME = "lastUpadateTime"; 
	static final String COL_CHARSET         = "charset"        ;         
	static final String COL_FONT            = "font"           ;            
	static final String COL_FONTSIZE        = "fontSize"       ;        
	static final String COL_CURINDEX        = "curIndex"       ;
	static final String COL_SID             = "sid";
	
	static String createTableBookSQL;
	
	static String getCreateTableBookSQL () {
		if (createTableBookSQL == null) {
			createTableBookSQL = String.format(
					"CREATE TABLE TABLE_NAME (_id integer primary key AUTOINCREMENT,%s text not null,%s varchar,%s varchar,%s varchar,%s varchar,%s varchar,%s varchar,%s integer,%s varchar,%s varchar,%s varchar,%s varchar,%s integer,%s bigint,%s bigint，COL_CHARSET varchar,%s varchar,%s integer,%s integer, %s integer)",
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
