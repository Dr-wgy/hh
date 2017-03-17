package com.makenv.model.mc.server.message.util;

/**
 * Created by alei on 2017/3/8.
 */
public class McUtil {
  /**
   * @param ppn   每个节点的计算资源
   * @param cores 执行模式需要的计算资源
   * @return 节点数量，每个节点的核数
   */
  public static int[] buildComputeResource(int ppn, int cores) {
    int needNodes = (int) Math.ceil(cores / ppn);
    return new int[]{needNodes, ppn};
  }

  public static String buildMultiplier(int num) {
    int best = -1;
    int x1 = 0, x2 = 0;
    for (int i = 1; i <= num; i++) {
      if (num % i == 0) {
        int multiplier = num / i;
        int poor = Math.abs(i - multiplier);
        if (best < 0 || best > poor) {
          best = poor;
          x1 = i;
          x2 = multiplier;
        }
      }
    }
    return String.format("%s %s", x1, x2);
  }

//  public static void main(String[] args) {
//    System.out.println(Arrays.toString(buildMultiplier(48)));
//  }
}
