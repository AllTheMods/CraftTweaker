package crafttweaker.sandbox

import org.codehaus.groovy.ast.expr.Expression

/**
 * Created by jonas on 05.06.2017.
 */
class ExpressionWrapper {
    Expression wrapped

    ExpressionWrapper(Expression expression) { this.wrapped = expression }

    static String getPropertiesString(def a) { a.properties.toString() }
}