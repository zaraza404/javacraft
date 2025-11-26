package jogo.util;

public class Timer {
    private float wait_time;
    private float time_left;
    private boolean paused = false;
    private boolean finished = true;

    public Timer(float wait_time){
        this.wait_time = wait_time;
        this.time_left = this.wait_time;
    }

    public void setWaitTime(float wait_time) {
        this.wait_time = wait_time;
        this.time_left = this.wait_time;
    }

    public void start(){
        this.time_left = wait_time;
        this.finished = false;
        this.paused = false;
    }

    public void pause(){
        this.paused = true;
    }

    public void update(float tpf){
        if (paused || finished){
            return;
        }

        this.time_left -= tpf;
        System.out.println(this.time_left);
        if (time_left <= 0){
            this.finished = true;
        }

    }

    public boolean isFinished(){
        return finished;
    }
}
