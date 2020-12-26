export interface IAlbums {
  id?: number;
  title?: string;
  type?: string;
  singerrFname?: string;
  singerrId?: number;
}

export class Albums implements IAlbums {
  constructor(public id?: number, public title?: string, public type?: string, public singerrFname?: string, public singerrId?: number) {}
}
