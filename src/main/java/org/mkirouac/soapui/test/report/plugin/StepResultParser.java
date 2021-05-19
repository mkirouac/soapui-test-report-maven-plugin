package org.mkirouac.soapui.test.report.plugin;

import lombok.NonNull;

public class StepResultParser {

	private static final int EXPECTED_FILE_NAME_PARTS = 5;
	private static final int TEST_SUITE_PART_INDEX = 0;
	private static final int TEST_CASE_PART_INDEX = 1;
	private static final int STEP_NAME_PART_INDEX = 2;
	private static final int STEP_NAME_NUMBER_PART_INDEX = 3;
	private static final int STATUS_PART_INDEX = 4;

	public StepResult parseFromFileName(@NonNull String fileName) {
		String[] splitted = fileName.replace(".txt", "").split("-");
		if (splitted.length != EXPECTED_FILE_NAME_PARTS) {
			throw new IllegalArgumentException("The filename represented by '" + fileName + "' is not a valid test case file");
		}
		
		StepResult result = new StepResult();
		
		result.setFileName(fileName);
		result.setTestSuiteName(splitted[TEST_SUITE_PART_INDEX]);
		result.setTestCaseName(splitted[TEST_CASE_PART_INDEX]);
		result.setStepName(splitted[STEP_NAME_PART_INDEX] + "-" + splitted[STEP_NAME_NUMBER_PART_INDEX]);
		result.setSuccess("OK".equals(splitted[STATUS_PART_INDEX]));
		
		return result;
		
	}

}
