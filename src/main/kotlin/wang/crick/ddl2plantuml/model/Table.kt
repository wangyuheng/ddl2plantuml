package wang.crick.ddl2plantuml.model

import java.io.Serializable

/**
 * @author wangyuheng
 * @date 2019-01-20 12:54
 */
class Table(var name: String, var comment: String, var columnList: List<Column>, var indexList: List<Index>) : Serializable