package com.beanmeapp.beanme.databaseclasses;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Will hold all of the run details, still need to figure that out.
 */
public class Run {

    // max number of orders per run
    public static int MAX_ORDERS_PER_RUN = 40;

    // placeholder Pair class so we can put pairs in the ArrayList
    public class Pair<K,V> {
        private K k;
        private V v;
        public Pair(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public K getK() { return k; }

        public void setK(K k) { this.k = k; }

        public V getV() { return v; }

        public void setV(V v) { this.v = v; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (k != null ? !k.equals(pair.k) : pair.k != null) return false;
            if (v != null ? !v.equals(pair.v) : pair.v != null) return false;

            return true;
        }
    }

    private String hostID;

    //Decided to use ArrayList instead of HashMap to hold runs
    private static ArrayList<Pair<String, Drink>> runs = new ArrayList<>();

    /**
     * default constructor, creates an empty list with the max capacity
     */
    public Run() {
        this.runs = new ArrayList<>(MAX_ORDERS_PER_RUN);
    }

    //TODO: add a ctor to auto-populate this run object with data maybe

    /**
     * Adds a drink to this run.
     * @param username - the username of the owner of the drink.
     * @param drink - the Drink object itself
     */
    public void putDrink(String username, Drink drink) { runs.add(new Pair<>(username, drink)); }

    /**
     * Deletes one particular drink.
     * @param username - the username of the owner of the drink.
     * @param drink - the Drink to be deleted
     */
    public void removeDrink(String username, Drink drink) { runs.remove(new Pair<>(username, drink)); }

    /**
     * Returns the size of the run.
     * @return size of run.
     */
    public int size() { return runs.size(); }

    /**
     * Generates a string readable list of all the orders.
     * Basically just calls Drink's toString().
     * @return string of all orders.
     */
    public Iterator<Pair> iterator() {
        Iterator<Pair> it = new Iterator<Pair>() {
            private int index = 0;

            @Override
            public boolean hasNext() { return index < runs.size() && runs.get(index) != null; }

            @Override
            public Pair next() {
                index++;
                return runs.get(index-1);
            }

            @Override
            public void remove() {
                //don't ever do this lol
            }

            /**
             * nextDrink() returns the userID of the guy whose drink it is and the drink itself.
             * @return uid + drink
             */
            public String nextDrink() {
                String ret = "USER " + runs.get(index).getK().toString() + " ORDERED A " + runs.get(index).getV().toString();
                index++;
                return ret;
            }
        };
        return it;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Orders: \n");
        for (int i = 0; i < runs.size(); i++) {
            builder.append(runs.get(i).getV().toString());
        }
        return builder.toString();
    }

}
