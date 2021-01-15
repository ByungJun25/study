package com.bj25.study.java.exceptions.custom;

public class CustomResources implements AutoCloseable {

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub

    }

    public void example() {
        try (CustomResources crs = new CustomResources()) {

        } catch (Exception ex) {
      
        }
    }
}
