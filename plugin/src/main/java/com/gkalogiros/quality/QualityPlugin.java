package com.gkalogiros.quality;

import ca.cutterslade.gradle.analyze.AnalyzeDependenciesPlugin;
import com.github.spotbugs.snom.SpotBugsPlugin;
import groovy.transform.CompileStatic;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.quality.CheckstylePlugin;
import org.gradle.api.plugins.quality.PmdPlugin;
import org.gradle.testing.jacoco.plugins.JacocoPlugin;
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification;
import org.gradle.testing.jacoco.tasks.JacocoReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CompileStatic
public class QualityPlugin implements Plugin<Project> {

	private static final String TASK_JACOCO_REPORT = "jacocoTestReport";
	private static final String TASK_JACOCO_VERIFICATION = "jacocoTestCoverageVerification";

	private final Logger log = LoggerFactory.getLogger(QualityPlugin.class);

	@Override
	public void apply(final Project project) {
		log.info("Setting-up Quality Plugins");
		project.getPlugins().apply(JavaPlugin.class);
		project.getPlugins().apply(CheckstylePlugin.class);
		project.getPlugins().apply(JacocoPlugin.class);
		project.getTasks().named(TASK_JACOCO_REPORT, JacocoReport.class);
		project.getTasks().named("test", task -> task.finalizedBy(TASK_JACOCO_REPORT));
		project.getTasks().named(TASK_JACOCO_VERIFICATION, JacocoCoverageVerification.class);
		project.getTasks().named("check", task -> task.finalizedBy(TASK_JACOCO_VERIFICATION));
		project.getPlugins().apply(SpotBugsPlugin.class);
		project.getPlugins().apply(PmdPlugin.class);
		project.getPlugins().apply(AnalyzeDependenciesPlugin.class);
	}
}
