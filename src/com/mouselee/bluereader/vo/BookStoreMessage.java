/**
 * added at 上午10:52:55 2013-8-22
 */
package com.mouselee.bluereader.vo;

/**
 *  The type is the bean of Book in server. It contains online messages.
 * @author Aaron Lee
 * @Date 上午10:52:55 2013-8-22
 */
public class BookStoreMessage {
	
	private int id;
	/**The book's id in server */
	private int sid;
	/**The book's current price in store */
	private double price;
	/**The book's fixed price which always printed in book's back cover */
	private double mprice;
	/**The book's downloadPath in the server */
	private String downloadPath;
	/**The book's percent of downloaded .if successful complement ,it is 1.0f */
	private float downloadPrecent;

}
