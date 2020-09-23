package ThreadPool;

public class Main {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(3);

        Runnable task1 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " A " + i);
            }
            System.out.println("------------------------A-------------------------");
        };

        Runnable task2 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " B " + i);
            }
            System.out.println("------------------------B-------------------------");
        };

        Runnable task3 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " C " + i);
            }
            System.out.println("------------------------C-------------------------");
        };

        Runnable task4 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " D " + i);
            }
            System.out.println("------------------------D-------------------------");
        };

        Runnable task5 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " E " + i);
            }
            System.out.println("------------------------E-------------------------");
        };

        Runnable task6 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " F " + i);
            }
            System.out.println("------------------------F-------------------------");
        };

        pool.submit(task1);
        pool.submit(task2);
        pool.submit(task3);
        pool.submit(task4);
        pool.submit(task5);
        pool.submit(task6);
    }
}