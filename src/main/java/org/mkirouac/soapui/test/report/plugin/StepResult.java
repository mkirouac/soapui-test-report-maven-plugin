package org.mkirouac.soapui.test.report.plugin;

import lombok.Data;

@Data
public class StepResult {
	
	private String fileName;
	private String testSuiteName;
	private String testCaseName;
	private String stepName;
	private boolean success;
	
}
