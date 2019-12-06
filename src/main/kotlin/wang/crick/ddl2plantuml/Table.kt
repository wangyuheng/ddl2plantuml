package wang.crick.ddl2plantuml

import wang.crick.ddl2plantuml.dictionary.IndexTypeEnum
import java.io.Serializable

/**
 * 表
 *
 * @author wangyuheng@outlook.com
 * @date 2019-01-20 12:54
 */
class Table(var name: String, var comment: String, var columnList: List<Column>, var indexList: List<Index>) : Serializable

/**
 * 字段
 */
data class Column(var name: String, var comment: String, var type: String, var size: Int?, var defaultValue: String?, var notNull: Boolean) : Serializable

/**
 * 索引
 */
class Index constructor(type: IndexTypeEnum, comment: String, columnList: List<Column?>)