import { IAlbums } from 'app/shared/model/albums.model';

export interface ISinger {
  id?: number;
  fname?: string;
  lname?: string;
  adress?: string;
  avoir_albums?: IAlbums[];
}

export class Singer implements ISinger {
  constructor(public id?: number, public fname?: string, public lname?: string, public adress?: string, public avoir_albums?: IAlbums[]) {}
}
