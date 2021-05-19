package org.mkirouac.soapui.test.report.plugin;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		//C:\telus\soapui\THXQA\thxqa-regression\results
		String rootDirectory = "C:\\telus\\soapui\\THXQA\\thxqa-regression\\results";
		ProjectResultParser projectResultsExtractor = new ProjectResultParser();
		List<ProjectResult> projectResults = projectResultsExtractor.extractTestResultsFromRootDirectory("C:\\telus\\soapui\\THXQA\\thxqa-regression\\results");
		projectResults.stream().forEach(p -> System.out.println(p.getProjectName() + " | success=" + p.isSuccess()));
		TestReportWriter writer = new TestReportWriter();
		projectResults.stream().forEach(p -> writer.writeTestReport(p, rootDirectory));
		System.out.println();
	}

	

}
