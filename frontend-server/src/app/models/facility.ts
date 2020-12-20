export class Facility {
    private _id: number
    private _name: string
    private _description: string

    public constructor(id: number, name: string, description: string) {
        this._id = id
        this._name = name
        this._description = description
    }

    get id(): number {
        return this._id
    }

    set id(id: number) {
        this._id = id
    }

    get name(): number {
        return this.name
    }

    set name(name: number) {
        this.name = name
    }

    get description(): string {
        return this._description
    }

    set description(description: string) {
        this._description = description
    }
}
