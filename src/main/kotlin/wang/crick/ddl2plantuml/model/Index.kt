package wang.crick.ddl2plantuml.model

import wang.crick.ddl2plantuml.dictionary.IndexTypeEnum

/**
 * 索引
 *
 * @author wangyuheng
 * @date 2019-01-20 13:54
 */
class Index constructor(type: IndexTypeEnum, comment: String, columnList: List<Column?>)