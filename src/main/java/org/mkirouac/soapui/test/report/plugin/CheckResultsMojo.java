package org.mkirouac.soapui.test.report.plugin;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "checkresults")
public class CheckResultsMojo extends AbstractMojo {


	@Parameter
	private String rootDirectory;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		//TODO Pass information from "report" goal?
		
		if(rootDirectory == null) {
			throw new MojoExecutionException("rootDirectory parameter must be set");
		}
		
		//String rootDirectory = "C:\\telus\\soapui\\THXQA\\thxqa-regression\\results";
		
		System.out.println("Parsing soapui test results");
		
		ProjectResultParser projectResultsExtractor = new ProjectResultParser();
		List<ProjectResult> projectResults = projectResultsExtractor.extractTestResultsFromRootDirectory(rootDirectory);
		
		//TODO Need new goal
		if(projectResults.stream().anyMatch(p -> !p.isSuccess())) {
			throw new MojoFailureException("There are SOAPUI test failures");
		}
		
		
	}

}
