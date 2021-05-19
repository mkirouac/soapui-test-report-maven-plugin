package org.mkirouac.soapui.test.report.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectResultParser {

	public List<ProjectResult> extractTestResultsFromRootDirectory(String rootDirectory) {

		//File resultsDirectory = new File("C:\\telus\\soapui\\THXQA\\thxqa-regression\\results");
		File resultsDirectory = new File(rootDirectory);
		List<File> projectResultDirectories = new ArrayList<File>();
		for(File childFile : resultsDirectory.listFiles()) {
			if(childFile.isDirectory()) {
				projectResultDirectories.add(childFile);
			}
		}
		
		List<ProjectResult> projectResults = new ArrayList<ProjectResult>();
		
		for(File projectResultDirectory : projectResultDirectories) {
			
			List<StepResult> projectTestResults = extractTestResultsFromDirectory(projectResultDirectory);
			projectResults.add(new ProjectResult(projectResultDirectory.getAbsolutePath(), projectResultDirectory.getName(), projectTestResults));
			System.out.println("Parsed " + projectTestResults.size() + " test step results for project " + projectResultDirectory.getName());
		}
		
		return projectResults;
	}
	
	private List<StepResult> extractTestResultsFromDirectory(File projectResultDirectory) {

		StepResultParser parser = new StepResultParser();

		List<StepResult> stepResults = new ArrayList<StepResult>();
		for (String potentialTestResultFile : projectResultDirectory.list()) {
			if(!potentialTestResultFile.endsWith(".txt")) {
				continue;
			}
			try {
				StepResult stepResult = parser.parseFromFileName(potentialTestResultFile);
				System.out.println("Parsed test step result: " + stepResult.toString());
				stepResults.add(stepResult);
			} catch (IllegalArgumentException e) {
				System.err.println("Failed to parse test step result from file: " + potentialTestResultFile);
				e.printStackTrace(System.err);
			}
		}
		
		return stepResults;
	}
	
}
