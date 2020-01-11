export class PlayingFieldSetupDto {
    constructor(description, teamSize, pfPhoto, name) {
        this.description = description;
        this.teamSize = teamSize;
        this.pfPhoto = pfPhoto;
        this.name = name;
    }
}
export class SearchDto {
    constructor(fromDate, toDate, fromTime, toTime, showPrivate, showFull, reserved) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.showPrivate = showPrivate;
        this.showFull = showFull;
        this.reserved = reserved;
    }
}
export class SearchParams {
    constructor(lat, lng, rangeInKm, searchDto) {
        this.lat = lat;
        this.lng = lng;
        this.rangeInKm = rangeInKm;
        this.searchDto = searchDto;
    }
}
export class ReservationDto {
    constructor(phoneNumber, email, lastName, isPrivate) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastName = lastName;
        this.isPrivate = isPrivate;
    }
}
//# sourceMappingURL=playing-field.js.map