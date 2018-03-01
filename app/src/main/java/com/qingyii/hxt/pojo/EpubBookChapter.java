package com.qingyii.hxt.pojo;

import java.io.Serializable;

/**
 * epub电子书章节信息实体类
 * 
 * @author shelia
 * 
 */
public class EpubBookChapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4646683753493999199L;
	/**
	 * 章节html文件路径
	 */
	private String componentId;
	/**
	 * 章节标题
	 */
	private String title;
	/**
	 * 解压存放根目录
	 */
	private String fileDir;
	/**
	 * epub电子书解压html文件存放目录
	 */
	private String epubDir;
	/**
	 * 章节大小
	 */
	private long chapterSize;
	/**
	 * 章节的起始大小
	 */
	private long startSizeInBook;

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getEpubDir() {
		return epubDir;
	}

	public void setEpubDir(String epubDir) {
		this.epubDir = epubDir;
	}

	public long getChapterSize() {
		return chapterSize;
	}

	public void setChapterSize(long chapterSize) {
		this.chapterSize = chapterSize;
	}

	public long getStartSizeInBook() {
		return startSizeInBook;
	}

	public void setStartSizeInBook(long startSizeInBook) {
		this.startSizeInBook = startSizeInBook;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
