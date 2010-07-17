/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/lgpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.gmvc.server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.gmvc.server.util.DBUtils;

/**
 * Degisik formatlarda rapor uretimi yapar
 * </p>
 * Raporlar DERLENMEMIS halde reports dizininde yer alirlar
 * </p>
 * Rapor motoru olarak JasperReport kullanildi
 * </p>
 * Raporlar uretme araci olarak iReport kullanildi
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class ReportService extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String reportUnit = req.getParameter("reportUnit");
		String sqlHaving = req.getParameter("sqlHaving");
		String sqlQuery = req.getParameter("sqlQuery");
		String reportName = req.getParameter("reportName");

		ServletContext sc = req.getSession().getServletContext();
		String fullReportName = sc.getRealPath("reports") +"/" +reportName + ".jrxml";

		if (sqlQuery.equals("null")) sqlQuery = "";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("QUERY_STRING", sqlQuery);
		paramMap.put("HAVING_STRING", sqlHaving);

		JRExporter exporter = null;
		JasperPrint jasperPrint = null;
		
		try {
			File reportFile = new File(fullReportName);
			if (!reportFile.exists()) {
				System.err.println("File " + fullReportName + " not found.");
			} else {
				JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(reportFile.getPath());
				jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, DBUtils.getConnection());
			}
		} catch (JRException e) {
			e.printStackTrace();
		}

		if (reportUnit != null) {

			if (reportUnit.equals("Html")) {
				res.setContentType("text/html");
				res.setCharacterEncoding("UTF-8");
				exporter = new JRHtmlExporter();
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
				exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

			} else if (reportUnit.equals("PDF")) {
				res.setContentType("application/pdf");
				exporter = new JRPdfExporter();

			} else if (reportUnit.equals("Excel")) {
				res.setContentType("application/vnd.ms-excel");
				res.setHeader("Content-Disposition", "inline; filename=" + reportName + ".xls");
				exporter = new JExcelApiExporter();
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");

			} else if (reportUnit.equals("CSV")) {
				res.setContentType("application/vnd.ms-excel");
				res.setHeader("Content-Disposition", "inline; filename=" + reportName + ".csv");
				exporter = new JRCsvExporter();
			}

			OutputStream stream = null;
			try {
				stream = res.getOutputStream();
			} catch (Exception e) {
				e.printStackTrace();
			}

			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");

			try {
				exporter.exportReport();
				stream.close();
			} catch (JRException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Unknown report unit type!");
		}

	}

}
