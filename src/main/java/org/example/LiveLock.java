package org.example;

public class LiveLock {

    public static void main(String[] args) {

        Human husband = new Human(true);
        Human wife = new Human(true);

        Spoon spoon = new Spoon(husband);

        new Thread(() -> {
            husband.eat(wife, spoon);
        }).start();

        new Thread(() -> {
            wife.eat(husband, spoon);
        }).start();

    }

    static class Human {

        boolean isHungry;

        public Human(boolean isHungry) {
            this.isHungry = isHungry;
        }

        public boolean isHungry() {
            return isHungry;
        }

        public void eat(Human spouse, Spoon spoon) {
            while (isHungry){
                if (spoon.getOwner() != this){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (spouse.isHungry){
                    System.out.println(Thread.currentThread().getName() + " give the spoon.");
                    spoon.setOwner(spouse);
                }
            }
        }
    }

    static class Spoon{

        Human Owner;

        public Spoon(Human owner) {
            Owner = owner;
        }

        public Human getOwner() {
            return Owner;
        }

        public synchronized void setOwner(Human owner) {
            Owner = owner;
        }

        public synchronized void eat(){
            System.out.println(Thread.currentThread().getName() + " eat.");
        }
    }

}
