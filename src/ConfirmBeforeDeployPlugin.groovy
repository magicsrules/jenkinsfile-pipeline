public class ConfirmBeforeDeployPlugin implements Plugin, Resettable {
    private static autoDeployEnvironments = []
    private static timeoutTime
    private static timeoutUnit

    public static init() {
        StagePlugins.add(new ConfirmBeforeDeployPlugin(), DeployStage.class)
    }

    public static autoDeploy(String environment) {
        autoDeployEnvironments << environment
        return this
    }

    public static withTimeout(int time, String unit) {
        timeoutTime = time
        timeoutUnit = unit
        return this
    }

    public void apply(Stage stage) {
        String environment = stage.getEnvironment()
        stage.decorate(confirmClosure(environment))
    }

    public Closure confirmClosure(String environment) {
        return { innerClosure ->
            if (!shouldAutoDeploy(environment)) {
                timeout(time: timeoutTime ?: 15, unit: timeoutUnit ?: 'MINUTES') {
                    def results = input([
                        message: "Do you really want to deploy ${environment}?".toString(),
                        ok: "Deploy ${environment}".toString(),
                        submitterParameter: 'approver'
                    ])
                }
            }

            innerClosure()
        }
    }

    public boolean shouldAutoDeploy(String environment) {
        return autoDeployEnvironments.contains(environment)
    }

    public static reset() {
        autoDeployEnvironments = []
    }
}
