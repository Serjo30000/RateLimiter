package Rate.SlidingWindow;


import java.util.HashMap;
import java.util.Map;

public class UserBucketCreator {
    Map<Integer, SlidingWindow> window;
    public UserBucketCreator(int id){
        window = new HashMap<>();
        window.put(id,new SlidingWindow(1,10));
    }

    void accessApplication(int id){
        if(window.get(id).grantAccess()){
            System.out.println(Thread.currentThread().getName()+ " -> able to access the application");
        }
        else{
            System.out.println(Thread.currentThread().getName()+" -> Too many request, Please try after some time");
        }
    }
}
