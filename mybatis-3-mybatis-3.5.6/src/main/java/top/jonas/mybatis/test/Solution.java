package top.jonas.mybatis.test;

/**
 * @author Jonas
 * @date 2021/5/10 21:24
 */
class Solution {

    public static void main(String[] args) {
        int[] arr = {32, -40, 60, 25, -54, 59, 98, -92, -22, 85};
        System.out.println(new Solution().maxSubArray(arr));
    }

    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];

        for (int i = 0; i < nums.length; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
}