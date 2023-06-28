package penis.engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wird ein GameState mit dieser Annotation versehen, soll er im Default State (s. {@link Game#setDefaultGameState(GameState)}) automatisch geladen und dem Game hinzugefuegt werden
 * 
 * @author Saris, Jan-Philipp
 * @since 1.1
 * @date 01.06.2022
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplyState {

}
