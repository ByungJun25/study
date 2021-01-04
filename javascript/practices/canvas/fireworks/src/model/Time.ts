export default class Time {

    static delta: number = 0; // 이전프레임과 현재 프레임의 시간차이
    static startTime: number = 0;

    static start() {
        Time.startTime = Date.now();
    }

    static update() {
        const currentTime = Date.now();
        Time.delta = (currentTime - Time.startTime) * 0.001;
        Time.startTime = currentTime;
    }
}