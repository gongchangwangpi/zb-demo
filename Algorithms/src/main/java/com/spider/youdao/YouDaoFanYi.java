package com.spider.youdao;

import com.util.SimpleHttpClient;
import com.util.file.ImageToPdfUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/**
 * @author bo6.zhang
 * @date 2021/4/27
 */
public class YouDaoFanYi {

  public static void main(String[] args) throws Exception {

    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36 Edg/89.0.774.77";
    String cookie = "OUTFOX_SEARCH_USER_ID_NCOO=713137776.3803809; OUTFOX_SEARCH_USER_ID=\"-1191259700@10.108.160.18\"; _ga=GA1.2.762844942.1597033869; NTES_SESS=EA8xgBHV3sQs11M4CwgzdyxLVUpl2bqHtz8K1rw6X13PXu9MX1ahwn9TNNpXy7CqB2dvTbUHImgCVTMWt2RsFszu_fEAD3BmjKS_5F2aC.yX0OUpqV7euMWlkoLU9Ve5Eh.wrGyPF1kjiGhWwdPUTj9.2QEByq30gbzQIugriNBQYGuKwE6XWeI4B3dmQY3XpwsCFqwGVtWtmZbqbDIkj2dZBdiK4Jsa5; S_INFO=1619317726|0|3&80##|cookie_zhangbo; P_INFO=cookie_zhangbo@163.com|1619317726|0|youdao_fanyi|00&99|sic&1617868395&note_client#sic&510100#10#0#0|187073&0|youdaodict_client|cookie_zhangbo@163.com; DICT_SESS=v2|n3SZgizJhBkfOMqK64qF0zAOLpLnHTu0TK6LkAhfeB0wuPMpFk4TFRUWnL6K0fPu0eShLeFnHOMROfh4YGk4lE0ll64gLPLey0; DICT_PERS=v2|urscookie||DICT||web||604800000||1619317729143||117.173.154.137||cookie_zhangbo@163.com||gBhHkl6LqS0gK6LqyP4eL0gFhfO5hMJ40p4nMzfPLPB0YAh4lA0LP4RPynHl50fJL0pFP4kmh4QyR6BRMYYOfgLR; DICT_LOGIN=3||1619317729148";

    String imageFolder = "D:\\images\\paxos";
    String imageFilePrefix = "paxos";
    String pdfFileName = "D:\\images\\paxos\\paxos-lamport.pdf";

//    Map<String, String> resp = httpClient.getResponseHeader("https://pdf.youdao.com/", header);
//    System.out.println(resp);

    String imgUrl = "https://doctrans-service.youdao.com/trandoc/doc/getFullOriginModeImg?docKey=0AFACB66A7A84D6AA118F70B887382F1&pageName=tran-%d.jpeg&src=fanyiweb&isCheckTermUpdate=false&isUseTerm=true";

    int page = 33;

    Map<String, String> header = new HashMap<>();
    header.put("User-Agent", userAgent);
    header.put("Cookie", cookie);
    // 将远程的有道翻译文档的图片下载到本地
    transferRemoteImageToLocal(imageFolder, imageFilePrefix, imgUrl, page, header);
    // 将图片合成为PDF
    ImageToPdfUtil.toPdf(imageFolder, pdfFileName);
  }

  private static void transferRemoteImageToLocal(String imageFolder, String imageFilePrefix,
      String imgUrl, int page, Map<String, String> header) throws IOException {
    SimpleHttpClient httpClient = new SimpleHttpClient();
    httpClient.init();

    for (int i = 0; i < page; i++) {
      String path = String.format(imgUrl, i);
      InputStream inputStream = httpClient.getImage(path, header);

      String fileNo = "";
      if (i < 10) {
        fileNo = "0" + i;
      } else {
        fileNo = String.valueOf(i);
      }

      FileOutputStream outputStream = new FileOutputStream(
          imageFolder + imageFilePrefix + fileNo + ".png");

      IOUtils.copy(inputStream, outputStream);

      IOUtils.closeQuietly(outputStream);
      IOUtils.closeQuietly(inputStream);
    }
  }

}
