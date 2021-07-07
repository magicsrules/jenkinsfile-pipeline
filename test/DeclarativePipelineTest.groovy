import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class DeclarativePipelineTest {
    @Nested
    public class Constructor {
        @Test
        void acceptsAWorkflowScript() {
            def workflowScript = new MockWorkflowScript()
            def pipeline = new DeclarativePipeline(workflowScript)
        }
    }

    @Nested
    public class WithNodeLabel {
        @Test
        void isFluent() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            def result = pipeline.withNodeLabel('foo')

            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class StartsWith {
        @Test
        void isFluent() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            def result = pipeline.startsWith(mock(Stage))

            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Then {
        @Test
        void isFluent() {
            def pipeline = new DeclarativePipeline(new MockWorkflowScript())

            def result = pipeline.then(mock(Stage))

            assertThat(result, equalTo(pipeline))
        }
    }

    @Nested
    public class Build {
        @Test
        void buildsThePipelineUsingTheAppropriateDeclarativeTemplate() {
            def wasCalled = false
            def pipelineTemplate = { stages, label -> wasCalled = true }
            def pipeline = spy(new DeclarativePipeline(new MockWorkflowScript()))
            doReturn(pipelineTemplate).when(pipeline).getPipelineTemplate(any(List))

            pipeline.build()

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void correctlyOrdersTheStages() {
            def expectedStages = [mock(Stage), mock(Stage), mock(Stage)]
            def actualStages = null
            def pipelineTemplate = { stages, label -> actualStages = stages }
            def pipeline = spy(new DeclarativePipeline(new MockWorkflowScript()))
            doReturn(pipelineTemplate).when(pipeline).getPipelineTemplate(any(List))

            pipeline.startsWith(expectedStages[0])
                    .then(expectedStages[1])
                    .then(expectedStages[2])
                    .build()

            assertThat(actualStages, equalTo(expectedStages))
        }
    }

    @Nested
    public class GetPipelineTemplate {
        @Test
        void returnsEmptyDeclarativeTemplateWhenZeroStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline0Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def result = pipeline.getPipelineTemplate([])

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForOneStageWhenOneStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline1Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def result = pipeline.getPipelineTemplate([mock(Stage)])

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForTwoStagesWhenTwoStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline2Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def stages = (1..2).collect { mock(Stage) }
            def result = pipeline.getPipelineTemplate(stages)

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForThreeStagesWhenThreeStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline3Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def stages = (1..3).collect { mock(Stage) }
            def result = pipeline.getPipelineTemplate(stages)

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForFourStagesWhenFourStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline4Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def stages = (1..4).collect { mock(Stage) }
            def result = pipeline.getPipelineTemplate(stages)

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForFiveStagesWhenFiveStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline5Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def stages = (1..5).collect { mock(Stage) }
            def result = pipeline.getPipelineTemplate(stages)

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForSixStagesWhenSixStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline6Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def stages = (1..6).collect { mock(Stage) }
            def result = pipeline.getPipelineTemplate(stages)

            assertThat(result, equalTo(expectedTemplate))
        }

        @Test
        void returnsDeclarativeTemplateForSevenStagesWhenSevenStagesAdded() {
            def expectedTemplate = { }
            def workflowScript = new PipelineTemplateMockWorkflowScript()
            workflowScript.Pipeline7Stage = expectedTemplate
            def pipeline = new DeclarativePipeline(workflowScript)

            def stages = (1..7).collect { mock(Stage) }
            def result = pipeline.getPipelineTemplate(stages)

            assertThat(result, equalTo(expectedTemplate))
        }
    }

    public class PipelineTemplateMockWorkflowScript extends MockWorkflowScript {
        public Pipeline0Stage
        public Pipeline1Stage
        public Pipeline2Stage
        public Pipeline3Stage
        public Pipeline4Stage
        public Pipeline5Stage
        public Pipeline6Stage
        public Pipeline7Stage
    }
}
