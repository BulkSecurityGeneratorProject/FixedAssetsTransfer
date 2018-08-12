export interface ITesting {
    id?: number;
    test?: string;
}

export class Testing implements ITesting {
    constructor(public id?: number, public test?: string) {}
}
