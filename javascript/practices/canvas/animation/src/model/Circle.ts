import Shape from './Shape';
import Vector from './Vector';
import AnimatedValue from './AnimatedValue';

const PI2 = Math.PI * 2;

function createRandomColor(): string {
    const r = Math.min(255, Math.round(Math.random() * 255) + 100);
    const g = Math.round(Math.random() * 20) + 90;
    const b = Math.round(Math.random() * 20) + 90;
    return `rgb(${r}, ${g}, ${b})`;
}

export default class Circle extends Shape {
    radius: number;
    radiusAnimatedValue: AnimatedValue;
    angle: number;
    speed: number;
    color: string;

    constructor(position: Vector) {
        super(position);
        this.radius = 10 * Math.random();
        this.angle = PI2 * Math.random();
        this.speed = 100 * Math.random();
        this.color = createRandomColor();

        this.radiusAnimatedValue = new AnimatedValue(0, 1, 300, this.speed * 10);
    }

    update(delta: number) {
        const velocity = this.speed * delta;
        this.position.x += Math.cos(this.angle) * velocity;
        this.position.y += Math.sin(this.angle) * velocity;

        this.radiusAnimatedValue.update(delta);
    }

    render(context: CanvasRenderingContext2D) {
        context.beginPath();
        context.fillStyle = this.color;
        context.arc(this.position.x, this.position.y, this.radius * this.radiusAnimatedValue.value, 0, PI2);
        context.fill();
    }
}