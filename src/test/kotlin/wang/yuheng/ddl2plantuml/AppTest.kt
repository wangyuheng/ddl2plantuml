package wang.yuheng.ddl2plantuml

import org.junit.Ignore
import org.junit.Test
import wang.yuheng.Convert
import java.nio.file.Paths

/**
 * random out er plantuml
 *
 * @author wangyuheng
 * @date 2019-01-20 14:20
 */
@Ignore
class AppTest {

    @Test
    fun generate_plantuml(){
        val convert = Convert()
        convert.src = Paths.get("./ddl.sql")
        convert.call()
    }

}


