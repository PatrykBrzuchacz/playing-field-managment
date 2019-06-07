export interface PlayingFieldDetails {
    id?: number;
    lat: number;
    lng: number;
    query?: string;
    location?: GooglePlaceLocation;
    name?: string;
    registered?: boolean
}

interface GooglePlaceLocation {
    address: string;
    lat: number;
    lng: number;
}