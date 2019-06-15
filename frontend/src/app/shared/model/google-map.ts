export interface PlayingField {
    id?: number;
    apiId?:number;
    lat: number;
    lng: number;
    query?: string;
    location?: GooglePlaceLocation;
    name?: string;
    registered?: boolean
}

export interface GooglePlaceLocation {
  formattedAddress: string;
    lat: number;
    lng: number;
}
