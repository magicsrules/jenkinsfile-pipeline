import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class ScriptedPipelineTest {
    @Nested
    public class Constructor {
        @Test
        void deprecatedAcceptsAWorkflowScript() {
            def workflowScript = new MockWorkflowScript()
            def pipeline = new ScriptedPipeline(workflowScript)
        }
    }

    @Nested
    public class StartsWith {
        @Test
        void acceptsAStage() {
            def stage = mock(Stage.class)
            def pipeline = new ScriptedPipeline()

            pipeline.startsWith(stage)
        }

        @Test
        void returnsItself() {
            def stage = mock(Stage.class)
            def pipeline = new ScriptedPipeline()

            def result = pipeline.startsWith(stage)
            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Then {
        @Test
        void returnsItself() {
            def stage1 = mock(Stage.class)
            def stage2 = mock(Stage.class)
            def pipeline = new ScriptedPipeline()

            def result = pipeline.startsWith(stage1).then(stage2)
            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Build {
        @Test
        void runsThePipelineConfigurationOfTheStartingStage() {
            def stage = mock(Stage.class)
            def jenkinsfileDsl = { sh 'do the thing' }
            doReturn(jenkinsfileDsl).when(stage).pipelineConfiguration()

            def workflowScript = spy(new MockWorkflowScript())
            Jenkinsfile.withWorkflowScript(workflowScript)
            def pipeline = new ScriptedPipeline()

            pipeline.startsWith(stage).build()
            verify(workflowScript).sh('do the thing')
        }

        @Test
        void runsThePipelineConfigurationOfMultipleStages() {
            def stage1 = mock(Stage.class)
            doReturn { sh 'stage1 do the thing' }.when(stage1).pipelineConfiguration()

            def stage2 = mock(Stage.class)
            doReturn { sh 'stage2 do the thing' }.when(stage2).pipelineConfiguration()

            def workflowScript = spy(new MockWorkflowScript())
            Jenkinsfile.withWorkflowScript(workflowScript)
            def pipeline = new ScriptedPipeline()

            pipeline.startsWith(stage1).then(stage2).build()
            verify(workflowScript).sh('stage1 do the thing')
            verify(workflowScript).sh('stage2 do the thing')
        }
    }
}
