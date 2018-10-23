package com.zq.youxi.service.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PDFExportUtils {

	private static final Logger log = LoggerFactory.getLogger(PDFExportUtils.class);

	public static void createPdfDocument(String[] titles, List<?> dataList, OutputStream outputStream)
			throws FileNotFoundException {

		PageSize page_default_size = PageSize.A4;

		Integer dataLen = JSON.toJSONString(titles).length();
		Integer word_size = 7;
		if (dataLen * word_size > 2384) {
			page_default_size = PageSize.A0;
		} else if (dataLen * word_size > 1684) {
			page_default_size = PageSize.A1;
		} else if (dataLen * word_size > 1190) {
			page_default_size = PageSize.A2;
		} else if (dataLen * word_size > 842) {
			page_default_size = PageSize.A3;
		}

		PageSize pageSize = new PageSize(page_default_size);
		PdfWriter pdfWriter = new PdfWriter(outputStream);
		PdfDocument pdfDoc = new PdfDocument(pdfWriter);
		Document document = new Document(pdfDoc, pageSize);
		try {
			PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			document.setFont(font);
			Table createPDFTable = createPDFTable(titles, dataList);
			document.add(createPDFTable);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e);
		} finally {
			document.close();
			pdfDoc.close();
			try {
				pdfWriter.close();
			} catch (IOException e) {
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	private static Table createPDFTable(String[] titles, List<?> dataList) throws IOException {
		Integer column = 0;
		if (titles == null || titles.length < 1) {
			// throw
			return null;
		}
		column = titles.length;
		Table table = new Table(column);

		// 设置表格宽度百分百
		table.setWidthPercent(100); // 高度
		table.setMarginBottom(1);

		// title 样式
		PdfFont chiness_font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		try {
			chiness_font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e);
		}

		// 设置标题
		for (int i = 0, len = titles.length; i < len; i++) {
			Cell cell = new Cell();
			// 设置样式,中文
			cell.setFont(chiness_font).setBackgroundColor(Color.LIGHT_GRAY);
			cell.add(titles[i]);
			table.addHeaderCell(cell);
		}

		// 设置数据
		for (Object object : dataList) {
			List<String> rowStringList = getPdfRowStringList(object);
			for (String cellStr : rowStringList) {
				Cell cell = new Cell();
				cell.setFont(chiness_font);
				cell.add(cellStr);
				table.addCell(cell);
			}
		}
		return table;
	}

	private static List<String> getPdfRowStringList(Object object) {
		List<String> strList = new ArrayList<>();
		Class<? extends Object> clazz = object.getClass();

		Field[] declaredFields = clazz.getDeclaredFields();
		Map<String, Method> methodMapping = getMethodMapping(clazz);
		for (Field field : declaredFields) {
			String fieldStr = field.getName();
			Method method = methodMapping.get(fieldStr);
			if (method == null) {
				strList.add("");
				continue;
			}
			try {
				Object invoke = method.invoke(object);
				if (invoke == null) {
					invoke = "";
				}
				if (invoke instanceof Date) {
					// 时间转换
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					strList.add(simpleDateFormat.format(invoke));
				} else if (field.getType().isInterface()) {
					strList.add(JSON.toJSONString(invoke));
				} else {
					String result = String.valueOf(invoke);
					result = result.replaceAll("、", ",");
					result = result.replaceAll("·", ",");
					result = result.replaceAll("！", "!");
					result = result.replaceAll("，", ",");
					result = result.replaceAll("。", ".");
					result = result.replaceAll("：", ":");
					result = result.replaceAll("-", "-");
					result = result.replaceAll("——", "_");
					result = result.replaceAll("	", "");
					result = result.replaceAll("国琳 清凉绿豆糕 150g 成都特产", "国琳 清绿豆糕 150g 成都特产");
					result = result.replaceAll("\r", " ");
					result = result.replaceAll(" ", " ");
					result = result.replaceAll("\t", "");
					result = result.replaceAll("“", "\"");
					result = result.replaceAll("”", "\"");
					strList.add(result);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.error(e.getLocalizedMessage(), e);
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
				return strList;
			}
		}
		return strList;
	}

	private static Map<String, Method> getMethodMapping(Class<? extends Object> clazz) {
		Map<String, Method> resultMap = new HashMap<>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodStr = method.getName();
			String methodKey = null;
			if (methodStr.contains("get")) {
				methodKey = initialConvert(methodStr.substring(3));
				resultMap.put(methodKey, method);
			}
		}
		return resultMap;
	}

	private static String initialConvert(String str) {
		String substring = str.substring(0, 1);
		String swapCase = StringUtils.swapCase(substring);
		return swapCase + str.substring(1);
	}
}
