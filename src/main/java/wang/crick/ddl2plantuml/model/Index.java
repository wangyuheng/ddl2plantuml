package wang.crick.ddl2plantuml.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import wang.crick.ddl2plantuml.dictionary.IndexTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * 索引
 *
 * @author wangyuheng
 * @date 2018/6/28 13:27
 */
@Data
@AllArgsConstructor
public class Index implements Serializable {
    private IndexTypeEnum type;
    private String comment;
    private List<Column> columnList;
}
