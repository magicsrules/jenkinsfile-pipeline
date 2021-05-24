public class ConfirmBeforeDeployPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new ConfirmBeforeDeployPlugin(), BuildStage.class)
    }

    public static autoDeploy(String environmentName) {
        return this
    }

    public void apply(Stage stage) {
        stage.decorate(confirmClosure())
    }

    public Closure confirmClosure() {
        return { innerClosure ->
            timeout(time: 15, unit: 'MINUTES') {
                println "prompt for input"
            }

            innerClosure()
        }
    }
}
