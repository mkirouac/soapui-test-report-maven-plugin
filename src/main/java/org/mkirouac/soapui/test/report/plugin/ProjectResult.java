package org.mkirouac.soapui.test.report.plugin;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResult {

	private final String projectName;
	private final List<StepResult> stepResults;
	
	public boolean isSuccess() {
		return stepResults.stream().noneMatch(n -> !n.isSuccess());
	}
}
