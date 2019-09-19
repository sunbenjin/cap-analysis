package com.capinfo.utils;

public class SysConstants {
   public static final String ROLE_WTF = "057251d2a5d6492799741d064033759a";
   public static final String ROLE_YJTD="058047768d3340a3aed1ae5ee5d751c7";
   public static final String ROLE_ZXTD="b96e0268888c4bb18ee00030a9fb8346";
   public static String createCheckTableName(String prefix){
      long suffix = System.currentTimeMillis();
      return prefix+"_"+suffix;
   }

   public static void main(String[] args) {
     String table = createCheckTableName("cap_check");
      System.out.println(table);
   }
}
