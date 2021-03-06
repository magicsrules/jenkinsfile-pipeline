public class StashUnstashPlugin implements Plugin, Resettable {
    public static final String DEFAULT_STASH_NAME = 'buildArtifact'
    private static String pattern
    private static String patternFile
    private static String unstashVariableName
    private String stashedFilename

    public static withArtifact(String pattern) {
        this.pattern = pattern
        return this
    }

    public static withArtifactFrom(String patternFile) {
        this.patternFile = patternFile
        return this
    }

    public static withUnstashVariable(String unstashVariable) {
        this.unstashVariableName = unstashVariable
        return this
    }

    public String getArtifactPattern() {
        if (pattern != null) {
            return pattern
        }

        return Jenkinsfile.original.readFile(patternFile ?: '.buildArtifact')
    }

    public static void init() {
        def plugin = new StashUnstashPlugin()
        StagePlugins.add(plugin, BuildStage.class)
        StagePlugins.add(plugin, DeployStage.class)
    }

    public void apply(Stage stage) {
        if (stage instanceof BuildStage) {
            stage.decorate(stashDecoration())
        } else if (stage instanceof DeployStage) {
            stage.decorate(unstashDecoration())
        }
    }

    public Closure stashDecoration() {
        return { innerClosure ->
            innerClosure()
            def artifactPattern = getArtifactPattern()
            this.stashedFilename = sh(script: "ls ${artifactPattern}".toString(), returnStdout: true).trim()

            stash includes: artifactPattern, name: DEFAULT_STASH_NAME
        }
    }

    public String getStashedFilename() {
        return stashedFilename
    }

    public String getUnstashVariableName() {
        return unstashVariableName ?: 'BUILD_ARTIFACT'
    }

    public Closure unstashDecoration() {
        return { innerClosure ->
            unstash DEFAULT_STASH_NAME
            withEnv(["${getUnstashVariableName()}=${getStashedFilename()}".toString()], innerClosure)
        }
    }

    public static reset() {
        pattern = null
        patternFile = null
        unstashVariableName = null
    }
}
