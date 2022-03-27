package com.misa.deadlock;

public class MoreOnDeadlocks {
    public static void main(String[] args) {
        final PolitePerson john = new PolitePerson("John");
        final PolitePerson cena = new PolitePerson("Cena");

        new Thread(new Runnable() {
            @Override
            public void run() {
                john.sayHello(cena);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                cena.sayHello(john);
            }
        }).start();
    }

    static class PolitePerson {
        private final String name;

        public PolitePerson(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHello(PolitePerson person) {
            System.out.format("%s: %s" + " has said hello to me!%n",
                    this.name, person.getName());
            person.sayHelloBack(this);
        }

        public synchronized void sayHelloBack(PolitePerson person) {
            System.out.format("%s: %s" + " has said hello back to me!%n",
                    this.name, person.getName());
        }
    }
}
