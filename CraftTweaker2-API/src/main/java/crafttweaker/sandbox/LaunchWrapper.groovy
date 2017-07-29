package crafttweaker.sandbox

/**
 * Created by jonas on 05.06.2017.
 */
class LaunchWrapper {

    static def init() {
        // String.metaClass.mixin(StringMixin) TODO: Alternative
    }

    static def run(Script script) {
        script.run()
    }

    static def invokeMethod(Script script, String name, Object... args) {
        script.invokeMethod(name, args)
    }
}