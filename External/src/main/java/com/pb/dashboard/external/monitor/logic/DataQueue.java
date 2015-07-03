package com.pb.dashboard.external.monitor.logic;

public class DataQueue {
    private int wid = 2;
    private int lastInQueue;
    private int[][] dataQueue;
    private int buffered;

    public void setBuffered(int buffered) {
        this.buffered = buffered;
    }

    public DataQueue(int lastInQueue) {
        this.lastInQueue=lastInQueue;
        fillQueue();
    }

    private void fillQueue() {
        dataQueue = new int[lastInQueue][wid];
        fillDataQueue();
    }

    private void fillDataQueue() {
        for (int i = 0; i < lastInQueue; i++) {
            for (int j = 0; j < wid; j++) {
                dataQueue[i][j]=-1;
            }
        }
    }

    public void addNewToHourlyQueue() {
        int valueToAdd =  buffered;
        for (int i = 0; i < lastInQueue; i++) {
            if (dataQueue[i][1]!=-1){
                dataQueue[i][1]++;
            }
        }
        int i = 0;
        while (dataQueue[i][0] != -1) {
            i++;
        }
        dataQueue[i][1] = 1;
        dataQueue[i][0] = valueToAdd;
    }

    public Integer getToRemove() {
        int i=0;
        while (dataQueue[i][1] != lastInQueue){
            i++;
        }
        return dataQueue[i][0];
    }

    public void updateAfterRemove() {
        int i=0;
        while (dataQueue[i][1] != lastInQueue){
            i++;
        }
        dataQueue[i][0] = -1;
        dataQueue[i][1] = -1;
    }

    public boolean isFilled() {
        boolean returnedValue = false;
        for (int i = 0; i < lastInQueue; i++) {
            if (dataQueue[i][1]==15){
                returnedValue=true;
            }
        }
        return returnedValue;
    }
}
