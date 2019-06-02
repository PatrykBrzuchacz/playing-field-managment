export interface GooglePlaceMap {
    id?: number;
    lat?: number;
    lng?: number;
    query: string;
    location?: GooglePlaceLocation;
    name?: string;
    isRegistered?: boolean
}

interface GooglePlaceLocation {
    address: string;
    lat: number;
    lng: number;
}