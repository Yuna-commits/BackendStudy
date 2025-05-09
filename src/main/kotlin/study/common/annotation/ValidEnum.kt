//annotation: 사용자 생성 어노테이션
package study.common.annotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

//@Target: annotation 이 적용될 위치 선택
//@Retention: 어노테이션을 컴파일된 클래스 파일에 저장할 것인지(SOURCE) 런타임에 반영할 것인지(RUNTIME) 정의
//@MustBeDocumented: API 의 일부분으로 문서화하기 위해 사용
//@Constraint
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidEnumValidator::class])
//annotation: 주석처럼 코드에 달아 클래스에 특별한 의미 부여, 기능 주입 ex)@Override
annotation class ValidEnum (
    val message: String = "Invalid enum value",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val enumClass: KClass<out Enum<*>>
)

//유효성 검사
class ValidEnumValidator : ConstraintValidator<ValidEnum, Any> {
    private lateinit var enumValues: Array<out Enum<*>>

    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants
    }

    //value: 사용자로부터 받은 값
    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }
        //any: 조건을 만족하는 원소가 1개 이상 존재하면 true
        return enumValues.any {it.name == value.toString()}
    }
}