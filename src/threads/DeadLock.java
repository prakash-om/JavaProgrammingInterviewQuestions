package threads;

public class DeadLock {

	String s1 = new String();
    String s2 = new String();

    class T1 implements Runnable{
        @Override
        public void run() {

            synchronized (s1) {

                try {
                    System.out.println("Thread "+Thread.currentThread().getName()+"Inside s1 ");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (s2) {

                    System.out.println("Thread "+Thread.currentThread().getName()+"Inside s2 ");
                }
            }

        }
    }

    class T2 implements Runnable {
        @Override
        public void run() {

            synchronized (s2) {

                try {
                    System.out.println("Thread "+Thread.currentThread().getName()+"Inside s2 ");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (s1) {

                    System.out.println("Thread "+Thread.currentThread().getName()+"Inside s1 ");
                }
            }

        }
    }

    public void runPg() {
        Thread t1 = new Thread(new  T1());
        Thread t2 = new Thread(new T2());
        t1.start();
        t2.start();
    }
    public static void main(String[] args) {

        DeadLock dl = new DeadLock();
        dl.runPg();
    }
}
