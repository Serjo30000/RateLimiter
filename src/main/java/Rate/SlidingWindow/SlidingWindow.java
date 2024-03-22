package Rate.SlidingWindow;

import Rate.RateLimiter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindow implements RateLimiter {
    Queue<Long>slidingWindow;
    int timeWindowSeconds;
    int bucketCapacity;

    public SlidingWindow(int timeWindowSeconds, int bucketCapacity){
        this.timeWindowSeconds = timeWindowSeconds;
        this.bucketCapacity = bucketCapacity;
        slidingWindow = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean grantAccess(){
        long currentTime = System.currentTimeMillis();
        checkAndUpdateQueue(currentTime);
        if(slidingWindow.size()<bucketCapacity){
            slidingWindow.offer(currentTime);
            return true;
        }
        return false;
    }

    private void checkAndUpdateQueue(long currentTime){
        if(slidingWindow.isEmpty())return;

        long calculatedTime = (currentTime - slidingWindow.peek())/1000;
        while(calculatedTime >= timeWindowSeconds){
            slidingWindow.poll();
            if(slidingWindow.isEmpty())break;
            calculatedTime = (currentTime - slidingWindow.peek())/1000;
        }
    }
}
