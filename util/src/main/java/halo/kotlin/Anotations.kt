/**
 * Created by Lucio on 17/12/7.
 */
package halo.kotlin


/**
 * 最优使用建议
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.TYPEALIAS)
@MustBeDocumented
annotation class BestPerformance(
        val message: String,
        val level: DeprecationLevel = DeprecationLevel.WARNING
)

/**
 * 警告
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.TYPEALIAS)
@MustBeDocumented
annotation class Caveat(val message: String, val level: DeprecationLevel = DeprecationLevel.WARNING)


/**
 * api提供日期
 */
@Retention(AnnotationRetention.BINARY)
annotation class ApiVersion(val message: String = "")