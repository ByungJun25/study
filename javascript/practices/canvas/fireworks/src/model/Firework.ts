import Entity from './Entity'
import Vector from './Vector'
import Time from './Time'
import EntityManager from './EntityManager'

const PI2 = Math.PI * 2;

function createRandomColor(): string {
    const r = Math.round(Math.random() * 120) + 120;
    const g = Math.round(Math.random() * 120) + 120;
    const b = Math.round(Math.random() * 120) + 120;
    return `rgb(${r}, ${g}, ${b})`;
}

function sleep(ms: number) {
    return new Promise<void>(resolve => {
        setTimeout(() => {
            resolve();
        }, ms);
    })
}

export default class Firework extends Entity {
    angle: number;
    speed: number;
    radius: number;
    color: string;
    velocity: number;
    isBurst: boolean = false;

    constructor(position: Vector) {
        super(position);

        this.angle = -Math.PI * 0.5;
        this.speed = Math.random() * 15 + 15;
        this.velocity = Math.random() * 15 + 5;
        this.radius = Math.random() + 1;
        this.color = createRandomColor();
    }

    createFireworks(speed: number, size = 50) {
        const angle = PI2 / size;
        const velocity = 10;

        for (let i = 0; i < size; i++) {
            const firework = new Firework(new Vector(this.position.x, this.position.y));

            firework.angle = angle * i;
            firework.isBurst = true;
            firework.speed = speed;
            firework.velocity = velocity;
            firework.color = this.color;
            firework.radius = this.radius;

            EntityManager.addEntity(firework);
        }
    }

    async update() {
        const speedVelocity = this.speed * this.velocity * Time.delta;
        this.position.x += Math.cos(this.angle) * speedVelocity;
        this.position.y += Math.sin(this.angle) * speedVelocity;
        this.velocity *= 0.98;

        if (!this.isBurst && this.velocity <= 1) {
            this.isBurst = true;

            this.createFireworks(16, 30);
            await sleep(300);
            this.createFireworks(12, 20);
            await sleep(300);
            this.createFireworks(10, 10);
        } else if (this.isBurst) {
            this.position.y += this.speed * Time.delta + 0.98;
            this.position.y *= 1.0005;

            if (this.velocity <= 1) {
                EntityManager.removeEntity(this);
            }
        }
    }

    render(context: CanvasRenderingContext2D) {
        context.beginPath();
        context.fillStyle = this.color;
        context.arc(this.position.x, this.position.y, this.radius, 0, PI2);
        context.fill();
    }
}