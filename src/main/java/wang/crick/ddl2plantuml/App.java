package wang.crick.ddl2plantuml;

import wang.crick.ddl2plantuml.convert.DdlExtractor;
import wang.crick.ddl2plantuml.convert.ErParser;
import wang.crick.ddl2plantuml.model.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DDL 转换为 ER plantUml
 *
 * @author wangyuheng
 * @date 2018/6/28 11:39
 */
public class App {


    public static void main(String[] args) throws IOException {

        List<String> sqlLineList = Files.readAllLines(Paths.get("./ddl.sql"));

        StringBuilder ddl = new StringBuilder();

        List<String> ddlList = new ArrayList<>();
        for (String sqlLine : sqlLineList) {
            ddl.append(sqlLine);
            if (sqlLine.trim().endsWith(";")) {
                ddlList.add(ddl.toString());
                ddl = new StringBuilder();
            }
        }
        List<Table> tableList = ddlList.stream().map(sql -> new DdlExtractor().extract(sql)).collect(Collectors.toList());
        String er = new ErParser().parse(tableList);
        Files.write(Paths.get("./er.puml"), er.getBytes());
    }
}
