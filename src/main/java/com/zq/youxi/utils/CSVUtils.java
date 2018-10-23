package com.zq.youxi.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * CSV操作(导出和导入)
 *
 * @author GraceZhang
 * @version 1.0 Mar 07, 2017
 */
public class CSVUtils {

	/**
	 * 导出
	 * 
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CSVUtils.class);

	public static boolean exportCsv(File file, List<String> dataList) {
		boolean isSucess = false;

		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\r");
				}
			}
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
			LOGGER.info("exportCsv Failed!");
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					LOGGER.info("close BufferedWriter Failed!" + e);
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					LOGGER.info("close OutputStreamWriter Failed!" + e);
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					LOGGER.info("close FileOutputStream Failed!" + e);
				}
			}
		}
		return isSucess;
	}

	/**
	 * 导入
	 * 
	 * @param file
	 *            csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
			LOGGER.info("importCsv Failed!" + e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.info("close BufferedReader Failed!" + e);
				}
			}
		}
		return dataList;
	}
}
