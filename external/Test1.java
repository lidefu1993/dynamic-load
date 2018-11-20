
import com.ldf.dl.common.IExecuteService;

/**
 * Created by ldf on 2018/11/19.
 */
public class Test1 implements IExecuteService {

    @Override
    public void noResultMethod(String msg) {
        System.out.println("测试：noResultMethod:" + msg);
    }

    @Override
    public Object withResultMethod(int a, int b) {
        System.out.println( "测试：withResultMethod (a+b):" + (a + b));
        return a + b;
    }

}
