package wang.crick.ddl2plantuml.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 列
 *
 * @author wangyuheng
 * @date 2018/6/28 11:39
 */
@Data
@AllArgsConstructor
public class Column implements Serializable {

    private String name;
    private String comment;
    private String type;
    private Integer size;
    private String defaultValue;
    private boolean notNull;
}
