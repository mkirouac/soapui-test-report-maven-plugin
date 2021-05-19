package org.mkirouac.soapui.test.report.plugin;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
				String fileContent = null;
				try {
					fileContent = new String(Files.readAllBytes(Paths.get(projectResult.getFolder() + "/" + stepResult.getFileName())), StandardCharsets.UTF_8);
				} catch (IOException e) {
					throw new RuntimeException("Failed to read file " + stepResult.getFileName(), e);
				}
				sb.append("<failure type=\"SoapUIStepFailure\">")
				.append("<![CDATA[").append(fileContent).append("]]>")
				.append("</failure>");
			}
			sb.append("</testcase>");
		}
		sb.append("</testsuite>");

		try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(rootDirectory + "/" + fileName, false), StandardCharsets.UTF_8)) {//new FileWriter(rootDirectory + "/" + fileName, false)) {
			fileWriter.write(sb.toString());
		} catch (IOException e) {
			throw new RuntimeException("Failed to write test report", e);
		}

	}

}
