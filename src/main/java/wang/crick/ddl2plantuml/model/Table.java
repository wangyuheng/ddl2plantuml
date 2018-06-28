package wang.crick.ddl2plantuml.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表
 *
 * @author wangyuheng
 * @date 2018/6/28 14:04
 */
@Data
public class Table implements Serializable {

    private String name;
    private String comment;

    private List<Column> columnList;
    private List<Index> indexList;
}
