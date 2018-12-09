package io.jenkins.plugins.analysis.core.extension.warnings;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

import edu.hm.hafner.analysis.Issue;
import io.jenkins.plugins.analysis.core.filter.ExcludeModule;
import io.jenkins.plugins.analysis.core.filter.IncludeModule;
import io.jenkins.plugins.analysis.core.filter.RegexpFilter;
import io.jenkins.plugins.analysis.core.model.AnalysisResult;
import io.jenkins.plugins.analysis.core.steps.ToolConfiguration;
import io.jenkins.plugins.analysis.core.testutil.IntegrationTestWithJenkinsPerSuite;
import io.jenkins.plugins.analysis.warnings.Pmd;
import static org.assertj.core.api.Assertions.*;

import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.model.Run;

/**
 * Integration tests of the regex property filters.
 *
 * @author Manuel Hampp
 */
@SuppressWarnings("classdataabstractioncoupling")
public class FiltersDumpTest extends IntegrationTestWithJenkinsPerSuite {
    private static final String MODULE_FILTER = "module-dump-filter/";

    /**
     * Tests the module expression filter: provides a pom.xml in the workspace so that modules are correctly assigned.
     */
    @Test
    public void shouldDumpFilterPmdIssuesByModule() {
        Map<RegexpFilter, Integer[]> expectedLinesByFilter = setupModuleFilterForPmd();

        for (Entry<RegexpFilter, Integer[]> entry : expectedLinesByFilter.entrySet()) {
            FreeStyleProject project = createFreeStyleProject();
            copyDirectoryToWorkspace(project, MODULE_FILTER);
            enableWarnings(project, recorder -> {
                        recorder.setFilters(toFilter(entry));
                        recorder.setOutputs(Collections.singletonList(new Dump()));
                    },
                    new ToolConfiguration(new Pmd(), "**/pmd.xml"));

            buildAndVerifyResults(project, entry.getValue());

            Run<?, ?> run = buildWithStatus(project, Result.SUCCESS);
            assertThatLogContains(run, "Outstanding");
        }
    }

    /**
     * Provides a map, that contains the filters and the line numbers that are expected to remain after filtering.
     */
    private Map<RegexpFilter, Integer[]> setupModuleFilterForPmd() {
        /*
        CopyToClipboard.java:54         com.avaloq.adt.env.internal.ui.actions          Basic CollapsibleIfStatements   Normal  1
        ChangeSelectionAction.java:14   com.avaloq.adt.env.internal.ui.actions.change   Import Statement Rules  UnusedImports   Normal  1
         */
        Map<RegexpFilter, Integer[]> filterResultMap = new HashMap<>();
        filterResultMap.put(new ExcludeModule("m1"), new Integer[]{14});
        filterResultMap.put(new IncludeModule("m1"), new Integer[]{54});

        return filterResultMap;
    }

    /**
     * Validates the filtered issues in the projects. Asserts that only issues with the specified lines are retained.
     *
     * @param project
     *         project that contains the issues to compare
     * @param expectedLines
     *         issue line numbers that are expected
     */
    private void buildAndVerifyResults(final FreeStyleProject project, final Integer[] expectedLines) {
        AnalysisResult result = scheduleBuildAndAssertStatus(project, Result.SUCCESS);

        assertThat(getLines(result)).containsOnly(expectedLines);
    }

    private List<Integer> getLines(final AnalysisResult result) {
        return result.getIssues()
                .stream()
                .map(Issue::getLineStart)
                .collect(Collectors.toList());
    }

    private List<RegexpFilter> toFilter(final Entry<RegexpFilter, Integer[]> entry) {
        return Collections.singletonList(entry.getKey());
    }
}

