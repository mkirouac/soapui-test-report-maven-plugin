package org.mkirouac.soapui.test.report.plugin;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class StepResultParserTest {

	private StepResultParser parser = new StepResultParser();
	
	@Test
	public void itShouldParseSuccessTestStepResult() {
		String stepResultFileName = "MySoapUiTestSuite-MySoapUiTestCase-MyTestStep-0-OK.txt";
		StepResult stepResult = parser.parseFromFileName(stepResultFileName);
		assertThat(stepResult.getTestSuiteName()).isEqualTo("MySoapUiTestSuite");
		assertThat(stepResult.getTestCaseName()).isEqualTo("MySoapUiTestCase");
		assertThat(stepResult.getStepName()).isEqualTo("MyTestStep-0");
		assertThat(stepResult.isSuccess()).isTrue();
	}
	
	@Test
	public void itShouldParseFailedTestStepResult() {
		String stepResultFileName = "MySoapUiTestSuite-MySoapUiTestCase-MyTestStep-0-FAIL.txt";
		StepResult stepResult = parser.parseFromFileName(stepResultFileName);
		assertThat(stepResult.getTestSuiteName()).isEqualTo("MySoapUiTestSuite");
		assertThat(stepResult.getTestCaseName()).isEqualTo("MySoapUiTestCase");
		assertThat(stepResult.getStepName()).isEqualTo("MyTestStep-0");
		assertThat(stepResult.isSuccess()).isFalse();
	}
	
}
