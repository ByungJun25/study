import Entity from './Entity';

export default class EntityManager {
    static instance: EntityManager;

    static addEntity(entity: Entity) {
        EntityManager.instance.addEntity(entity);
    }

    static removeEntity(entity: Entity) {
        EntityManager.instance.removeEntity(entity);
    }

    constructor() {
        EntityManager.instance = this;
    }

    entities: Entity[] = [];

    update() {
        for (let i = 0; i < this.entities.length; i++) {
            this.entities[i].update();
        }
    }

    render(context: CanvasRenderingContext2D) {
        context.clearRect(0, 0, context.canvas.width, context.canvas.height);

        for (let i = 0; i < this.entities.length; i++) {
            this.entities[i].render(context);
        }
    }

    addEntity(entity: Entity) {
        this.entities.push(entity);
    }

    removeEntity(entity: Entity) {
        const entityIndex = this.entities.indexOf(entity);
        if (entityIndex > -1) {
            this.entities.splice(entityIndex, 1);
        }
    }
}