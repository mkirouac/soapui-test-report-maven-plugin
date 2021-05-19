package org.mkirouac.soapui.test.report.plugin;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class TestReportWriter {

	public void writeTestReport(ProjectResult projectResult, String rootDirectory) {

		String fileName = "TEST-" + projectResult.getProjectName() + ".xml";
		int testCount = projectResult.getStepResults().size();
		long failureCount = projectResult.getStepResults().stream().filter(n -> !n.isSuccess()).count();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(
				"<testsuite xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"https://maven.apache.org/surefire/maven-surefire-plugin/xsd/surefire-test-report.xsd\" name=\"%s\" time=\"0.001\" tests=\"%s\" errors=\"0\" skipped=\"0\" failures=\"%s\">",
				fileName, testCount, failureCount));
		for (StepResult stepResult : projectResult.getStepResults()) {
			String testCaseName = stepResult.getStepName();
			String className = stepResult.getTestSuiteName() + "-" + stepResult.getTestCaseName();
			boolean success = stepResult.isSuccess();
			sb.append(String.format("<testcase name=\"%s\" classname=\"%s\" time=\"0\">", testCaseName, className));
			if (!success) {
				sb.append("<failure type=\"SoapUIStepFailure\">The SoapUI step has failed</failure>");
			}
			sb.append("</testcase>");
		}
		sb.append("</testsuite>");

		try (Writer fileWriter = new FileWriter(rootDirectory + "/" + fileName, false)) {
			fileWriter.write(sb.toString());
		} catch (IOException e) {
			throw new RuntimeException("Failed to write test report", e);
		}

	}

}
