package ag6505.example.com.servicetest;

import java.nio.Buffer;

/**
 * Created by Anton on 2017-09-29.
 */

public class RunOnThread {
    private ag6505.example.com.servicetest.Buffer<Runnable> buffer = new ag6505.example.com.servicetest.Buffer<>();
    private Worker worker;

    public void start(){
        if(worker == null){
            worker = new Worker();
            worker.start();
        }
    }

    public void stop(){
        if(worker != null){
            worker.interrupt();
            worker = null;
        }
    }

    public void execute(Runnable runnable){
        buffer.put(runnable);
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            Runnable runnable;
            while(worker != null){
                try{
                    runnable = buffer.get();
                    runnable.run();
                }catch(InterruptedException e){
                    worker = null;
                }
            }
        }
    }

}
