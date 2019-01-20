package wang.crick.ddl2plantuml.model

import java.io.Serializable

/**
 * 列
 *
 * @author wangyuheng
 * @date 2019-01-20 14:03
 */
class Column(var name: String, var comment: String, var type: String, var size: Int?, var defaultValue: String?, var notNull: Boolean) : Serializable
