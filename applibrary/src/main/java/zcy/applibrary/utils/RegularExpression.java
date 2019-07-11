package zcy.applibrary.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 正则表达式
 * Created by zcy on 2017/9/15.
 */
public class RegularExpression {
    /**
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 
     * 验证手机格式 
       */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][356789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 判断是否是邀请码的正则表达式
     * @param inviteCode 内容
     * @return 结果
     */
    public static boolean isInviteCode(String inviteCode){
        String s = "[0-9A-Za-z]{8}";
        return (!TextUtils.isEmpty(inviteCode)) && inviteCode.matches(s);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        boolean ret = false;
        try {
            String regex = "^-?\\d+$";//"\\-?[1-9]\\d+";
//           String regex = "^\\d*[1-9]\\d*$"; //验证正整数
            ret = Pattern.matches(regex, digit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
