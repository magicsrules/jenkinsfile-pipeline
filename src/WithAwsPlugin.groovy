class WithAwsPlugin implements Plugin {
    public static init() {
        StagePlugins.add(new WithAwsPlugin(), DeployStage)
    }

    public void apply(Stage stage) {
        def environment = stage.getEnvironment()
        stage.decorate(withAwsClosure(environment))
    }

    public Map getOptions(String environment, EnvironmentUtil util) {
        def results = [:]
        def role = null

        role = util.getEnvironmentVariable("${environment.toUpperCase()}_AWS_ROLE_ARN".toString())
        if (role != null) {
            results['role'] = role
        } else {
            role = util.getEnvironmentVariable('AWS_ROLE_ARN')
            if (role != null) {
                results['role'] = role
            }
        }

        return results
    }

    public Closure withAwsClosure(String environment) {
        return { innerClosure ->
            def envUtil = new EnvironmentUtil(delegate)
            def options = getOptions(environment, envUtil)
            sh "echo \"WithAwsPlugin.withAWS(${options}) for ${environment}\""
            withAWS(options, innerClosure)
        }
    }
}
