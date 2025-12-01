package jogo.util;

public class Timer {
    private float waitTime;
    private float timeLeft;
    private boolean paused = false;
    private boolean finished = true;

    public Timer(float waitTime){
        this.waitTime = waitTime;
        this.timeLeft = this.waitTime;
    }

    public void setWaitTime(float waitTime) {
        this.waitTime = waitTime;
        this.timeLeft = this.waitTime;
    }

    public void start(){
        this.timeLeft = waitTime;
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

        this.timeLeft -= tpf;
        System.out.println(this.timeLeft);
        if (timeLeft <= 0){
            this.finished = true;
        }

    }

    public boolean isFinished(){
        return finished;
    }
}
