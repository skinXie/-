package common.tool;

import java.util.List;

public class Tool {
    public static String ListToString(List<?> list) {
        String res = "";
        for (Object var : list
        ) {
            res += var.toString() + ",";
        }
        return res;
    }
}
