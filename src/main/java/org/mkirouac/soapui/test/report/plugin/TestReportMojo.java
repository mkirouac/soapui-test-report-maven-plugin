package org.mkirouac.soapui.test.report.plugin;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "report")
public class TestReportMojo extends AbstractMojo {

	@Parameter
	private String rootDirectory;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		
		
		//String rootDirectory = "C:\\telus\\soapui\\THXQA\\thxqa-regression\\results";
		
		if(rootDirectory == null) {
			throw new MojoExecutionException("rootDirectory parameter must be set");
		}
		
		System.out.println("Parsing soapui test results");
		
		ProjectResultParser projectResultsExtractor = new ProjectResultParser();
		List<ProjectResult> projectResults = projectResultsExtractor.extractTestResultsFromRootDirectory(rootDirectory);
		
		
		System.out.println("Generating junit xml test reports");
		
		TestReportWriter writer = new TestReportWriter();
		projectResults.stream().forEach(p -> writer.writeTestReport(p, rootDirectory));

		//TODO Need new goal
//		if(projectResults.stream().anyMatch(p -> !p.isSuccess())) {
//			throw new MojoFailureException("There are SOAPUI test failures");
//		}
		
		
	}

}
