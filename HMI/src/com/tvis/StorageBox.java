package com.tvis;

public class StorageBox {
    private int x;
    private int y;
    private String id;
    private int status;

    public StorageBox(String id, int x, int y, int status) {
        setId(id);
        setX(x);
        setY(y);
        setStatus(status);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
