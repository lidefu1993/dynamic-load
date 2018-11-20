
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.util.*;

/**
 * Created by ldf on 2018/11/19.
 */
@Getter
public class Test3 {

    public static void m1(){
        System.out.println("Test3 m1");
    }

    private static void m2(Map map){
        System.out.println("Test3 m2" + JSONObject.toJSONString(map));
    }

}
