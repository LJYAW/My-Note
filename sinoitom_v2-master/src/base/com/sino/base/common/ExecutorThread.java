package com.sino.base.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public  class ExecutorThread {
 
	private static ExecutorService executorService = Executors.newSingleThreadExecutor();

	synchronized public static void execute(Runnable command) {
        executorService.execute(command);
    }


/*    private class MyRunable implements Runnable {

        private Runnable command;

        public MyRunable(Runnable command){

            this.command = command;

        }

        public void run() {

            try{

                command.run();

            } finally {


            }

        }

    }*/
}
