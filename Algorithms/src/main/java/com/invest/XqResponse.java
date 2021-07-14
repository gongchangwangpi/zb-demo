package com.invest;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.db.jdbc.JdbcUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author bo6.zhang
 * @date 2021/2/23
 */
@Data
public class XqResponse {

    private static final String[] COLUMNS = {"timestamp","volume","open","high","low","close","chg","percent","turnoverrate","amount","volume_post","amount_post","pe","pb","ps","pcf","market_capital","balance","hold_volume_cn","hold_ratio_cn","net_volume_cn","hold_volume_hk","hold_ratio_hk","net_volume_hk"};

    private int error_code;
    private String error_description;

    private Item data;

    public static void main(String[] args) throws Exception {

        String file = "D:\\investment\\shuanghui.txt";
        String s = FileUtil.readString(file, Charset.defaultCharset());

        XqResponse xqResponse = JSON.parseObject(s, XqResponse.class);

//        System.out.println(xueQiuData.getData().getSymbol());
//        System.out.println(xueQiuData.getData().getColumn().length);
//        System.out.println(xueQiuData.getData().getItem().length);
//        System.out.println(Arrays.toString(xueQiuData.getData().getItem()[0]));


        StringBuilder sql = new StringBuilder("insert into t_xueqiu (`symbol`, `date`, `volume`,`open`,`high`,`low`,`close`,`chg`,`percent`,`turnoverrate`,`amount`,`volume_post`,`amount_post`,`pe`,`pb`,`ps`,`pcf`,`market_capital`,`balance`,`hold_volume_cn`,`hold_ratio_cn`,`net_volume_cn`,`hold_volume_hk`,`hold_ratio_hk`,`net_volume_hk`) values ");

        String symbol = xqResponse.getData().getSymbol();
        symbol = "'" + symbol + "'";

        // 1614009600000
        // 913219200000
        FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd");

        String[][] items = xqResponse.getData().getItem();
        for (String[] item : items) {
            sql.append("(").append(symbol).append(",'").append(dateFormat.format(Long.parseLong(item[0]))).append("'");
            for (int j = 0; j < item.length; j++) {
                if (j == 0) continue;;
                String i = item[j];
                if (StringUtils.isEmpty(i) || "null".equals(i)) {
                    sql.append(",null");
                } else {
                    sql.append(",'").append(i).append("'");
                }
            }
            sql.append("),");
        }
        sql.deleteCharAt(sql.length() - 1);

        System.out.println(sql);

        Connection connection = JdbcUtil.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql.toString());

    }

    @lombok.Data
    public static class Item {
        // 代码
        private String symbol;
        private String[] column;
        private String[][] item;
    }

}
