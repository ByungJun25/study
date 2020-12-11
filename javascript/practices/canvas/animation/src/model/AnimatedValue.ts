function linear(t: number) {
    return t;
}

export default class AnimatedValue {
    from: number;
    to: number;
    time: number = 0;
    elapsedTime: number = 0;
    duration: number;
    delay: number;
    easingFunction: (t: number) => number;

    constructor(from: number = 0, to: number = 1, duration: number = 1000, delay: number = 0, easingFunction: (t: number) => number = linear) {
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.delay = delay * 0.001;
        this.easingFunction = easingFunction;
    }

    get value() {
        return this.from + (this.to - this.from) * this.easingFunction.call(null, this.elapsedTime);
    }

    update(delta: number) {
        this.time += delta;

        if (this.time < this.delay) {
            return;
        }

        this.elapsedTime += delta * (1000 / this.duration);
        if (this.elapsedTime >= 1) {
            this.elapsedTime = 1;
        }
    }
}