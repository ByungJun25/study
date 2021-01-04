import Entity from "./Entity";
import EntityManager from "./EntityManager";
import Vector from "./Vector";
import Time from "./Time";

function drawEllipse(context: CanvasRenderingContext2D, x: number, y: number, w: number, h: number) {
    const kappa = 0.5522848,
        ox = (w / 2) * kappa,
        oy = (h / 2) * kappa,
        xe = x + w,
        ye = y + h,
        xm = x + w / 2,
        ym = y + h / 2;

    context.beginPath();
    context.moveTo(x, ym);
    context.bezierCurveTo(x, ym - oy, xm - ox, y, xm, y);
    context.bezierCurveTo(xm + ox, y, xe, ym - oy, xe, ym);
    context.bezierCurveTo(xe, ym + oy, xm + ox, ye, xm, ye);
    context.bezierCurveTo(xm - ox, ye, x, ym + oy, x, ym);
    context.closePath();
    context.stroke();
}

export default class Unit extends Entity {
    radius: number;
    speed: number;
    movement: boolean = false;
    targetPosition: Vector = new Vector(0, 0);
    isSelected: boolean = false;

    constructor(position: Vector) {
        super(position);

        this.radius = 15;
        this.speed = 100;
    }

    update() {
        this.collisionHandle();

        if (this.movement) {
            const angle = this.position.angleBetween(this.targetPosition);
            this.position.x += Math.cos(angle) * this.speed * Time.delta;
            this.position.y += Math.sin(angle) * this.speed * Time.delta;

            if (this.position.distance(this.targetPosition) <= 1) {
                this.movement = false;
            }
        }
    }

    collisionHandle() {
        const units = EntityManager.instance.entities.filter((entity) => entity instanceof Unit) as Unit[];

        for (let i = 0; i < units.length; i++) {
            const unit = units[i];
            if (this !== unit) {
                const distance = this.position.distance(unit.position);
                const length = this.radius + unit.radius;
                if (distance <= length) {
                    const force = length - distance;
                    const angle = this.position.angleBetween(unit.position);

                    this.position.x -= Math.cos(angle) * force;
                    this.position.y -= Math.sin(angle) * force;
                }
            }
        }
    }

    render(context: CanvasRenderingContext2D) {
        if (this.isSelected) {
            context.beginPath();
            context.lineWidth = 2;
            context.strokeStyle = "#0f0";
            drawEllipse(
                context,
                this.position.x - this.radius,
                this.position.y + this.radius * 0.75,
                this.radius * 2,
                this.radius * 0.75
            );
        }

        context.beginPath();
        context.fillStyle = "#000";
        context.arc(this.position.x, this.position.y, this.radius, 0, Math.PI * 2);
        context.fill();
    }
}