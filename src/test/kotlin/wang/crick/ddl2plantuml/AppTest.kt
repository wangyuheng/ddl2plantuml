package wang.crick.ddl2plantuml

import org.junit.jupiter.api.Test
import kotlin.random.Random

/**
 * random out er plantuml
 *
 * @author wangyuheng
 * @date 2019-01-20 14:20
 */
class AppTest {

    @Test
    fun generate_plantuml(){
        val args = arrayOf("./ddl.sql", String.format("./%d.puml", Random.nextInt()))
        main(args)
    }

}

