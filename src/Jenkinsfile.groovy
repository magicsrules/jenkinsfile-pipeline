public class Jenkinsfile implements Resettable {
    public static original
    private static boolean shouldInitializeDefaultPlugins = true

    public static void init(original) {
        this.original = original

        if (shouldInitializeDefaultPlugins) {
            initializeDefaultPlugins()
        }
    }

    public static void initializeDefaultPlugins() {
        ScmPlugin.init()
        DefaultEnvironmentPlugin.init()
        NodePlugin.init()
        ConfirmBeforeDeployPlugin.init()
        StageDisplayPlugin.init()
    }

    public static getInstance() {
        return original
    }

    public static skipDefaultPlugins(boolean trueOrFalse = false) {
        shouldInitializeDefaultPlugins = trueOrFalse
        return this
    }

    public static withWorkflowScript(workflowScript) {
        this.original = workflowScript
    }

    public static reset() {
        this.original = null
        this.shouldInitializeDefaultPlugins = true
    }
}
