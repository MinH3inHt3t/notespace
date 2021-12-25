package com.notespace.Minimalist;

public class CoffeeBuilder {
    public static void main(String[] args) {
        Coffee c1 = new CoffeeBuilder.Coffee.Builder().name("Cappu").build();
        System.out.println(c1);
    }

    static class Coffee {
        private String name;
        private boolean sugar;
        private boolean milk;

        public Coffee(Builder builder) {
            this.name = builder.name;
            this.sugar = builder.sugar;
            this.milk = builder.milk;
        }

        @Override
        public String toString() {
            return "Coffee{" +
                    "name='" + name + '\'' +
                    ", sugar=" + sugar +
                    ", milk=" + milk +
                    '}';
        }

        static class Builder {
            private String name;
            private boolean sugar;
            private boolean milk;

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder sugar(boolean sugar) {
                this.sugar = sugar;
                return this;
            }

            public Builder milk(boolean milk) {
                this.milk = milk;
                return this;
            }

            public Coffee build() {
                return new Coffee(this);
            }

        }

    }
}