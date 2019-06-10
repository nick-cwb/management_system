package com.isoft.system.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    private final static String pattern="yyyy-MM-dd HH:mm:ss";
    private static String INSERT_SET = "insertSet";
    private static String DEL_SET = "delSet";

    /**
     * 获取UUID
     * @return ID
     */
    public static String getUUID()
    {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }


    /**
     * 获取当前时间
     */
    public static Timestamp createTimestamp() throws ParseException {
        Timestamp result=null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(date);
        Date targetDate = sdf.parse(str);
        result = new Timestamp(targetDate.getTime());
        return result;
    }

    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String dateTransformToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        return dateStr;
    }


    public static List<String> transformToArray(String str){
        String[] stringArray = str.split(";");
        List<String> list = new LinkedList<String>();
        for(int i = 0; i < stringArray.length; i++){
            list.add(stringArray[i]);
        }
        return list;
    }


    /**
     * 字符串转为整型set集合
     * @param ids
     * @return
     */
    public static Set<Integer> transformStringToSet(String ids){
        String[] stringArray = ids.split(",");
        Set<Integer> set = new LinkedHashSet<Integer>();
        for (String str:stringArray) {
            set.add(Integer.valueOf(str));
        }
        return set;
    }

    /**
     * 字符串转为整型String集合
     * @param ids
     * @return
     */
    public static List<String> transformStringToList(String ids){
        String[] stringArray = ids.split(",");
        List<String> list = new LinkedList<String>();
        for (String str:stringArray) {
            list.add(str);
        }
        return list;
    }

    /**
     * 根据修改后的id和原有id，获取哪些需要新增、哪些需要删除
     * @param savedSet 已存的
     * @param updateSet 要修改的
     * @return
     */
    public static Map<String,Set<Integer>> screenIdSet(Set<Integer> savedSet,Set<Integer> updateSet){
        Set<Integer> insertSet = new HashSet<Integer>();
        Set<Integer> delSet = new HashSet<Integer>();
        
        if(updateSet!=null&&updateSet.size()>0) {
        	insertSet.addAll(updateSet);
        }
        if(savedSet!=null&&savedSet.size()>0) {
        	delSet.addAll(savedSet);
        }
        
        if(null==savedSet ||savedSet.size()==0){
            insertSet =  updateSet;
            delSet = null;
        }
        if(null==updateSet || updateSet.size()==0){
            insertSet =  null;
            delSet = savedSet;
        }
        if(updateSet != null && savedSet != null && updateSet.size()==0 && savedSet.size()==0){
            insertSet =  null;
            delSet = null;
        }
        if(null != updateSet && null != savedSet) {
        	 for(Integer i:updateSet){
                 if(savedSet.contains(i)){
                     delSet.remove(i);
                 }
             }
             for(Integer i:savedSet){
                 if(updateSet.contains(i)){
                     insertSet.remove(i);
                 }
             }
        }
        Map<String,Set<Integer>> map = new HashMap<String,Set<Integer>>();
        map.put(INSERT_SET,insertSet);
        map.put(DEL_SET,delSet);
        return map;
    }
    
    /**
     * 数组转为不重复集合
     * @param arr
     * @return
     */
	public static Set<Integer> arrayToSet(Integer[] arr){
		Set<Integer> set= new HashSet();
		if(arr.length>0) {
			for(int i = 0;i<arr.length;i++) {
				set.add(arr[i]);
			}
		}else {
			set = null;
		}
		return set;
	}

}
