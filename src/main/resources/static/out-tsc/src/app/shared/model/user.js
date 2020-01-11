export class EditUserDto {
    constructor(id, age, phoneNumber, avatar, position, city, firstName, lastName, email) {
        this.id = id;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.position = position;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
export class UserCredentials {
    constructor(username, password) {
        this.username = username;
        this.password = password;
    }
}
export class RegisterUserDto {
    constructor(userCredentials, position) {
        this.userCredentials = userCredentials;
        this.position = position;
    }
}
export var UserRole;
(function (UserRole) {
    UserRole["USER"] = "ROLE_USER";
    UserRole["ADMIN"] = "ROLE_ADMIN";
    UserRole["WORKER"] = "ROLE_WORKER";
})(UserRole || (UserRole = {}));
export var Position;
(function (Position) {
    Position["Bramkarz"] = "GOALKEEPER";
    Position["Obro\u0144ca"] = "DEFENDER";
    Position["Pomocnik"] = "MIDFIELDER";
    Position["Napastnik"] = "FORWARD";
})(Position || (Position = {}));
export var NotificationType;
(function (NotificationType) {
    NotificationType[NotificationType["MESSAGE"] = 0] = "MESSAGE";
    NotificationType[NotificationType["FRIENDREQUEST"] = 1] = "FRIENDREQUEST";
})(NotificationType || (NotificationType = {}));
//# sourceMappingURL=user.js.map