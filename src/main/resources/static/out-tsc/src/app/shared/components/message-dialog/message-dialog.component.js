import * as tslib_1 from "tslib";
import { Component, Inject, ElementRef, ViewChild } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FormControl } from "@angular/forms";
import { MessageService } from "@app/shared/service/message.service";
import { AuthService } from "@app/core/service";
import { UserService } from "@app/shared/service/user.service";
import { FriendsService } from '@app/shared/service/friends.service';
import { ToastrService } from 'ngx-toastr';
let MessageDialogComponent = class MessageDialogComponent {
    constructor(data, dialogRef, messageService, authService, userService, friendsService, toastrService) {
        this.data = data;
        this.dialogRef = dialogRef;
        this.messageService = messageService;
        this.authService = authService;
        this.userService = userService;
        this.friendsService = friendsService;
        this.toastrService = toastrService;
        this.content = new FormControl("");
        this.isActualFriend = false;
        this.editorConfig = {
            editable: true,
            spellcheck: true,
            height: "auto",
            minHeight: "90px",
            maxHeight: "150px",
            width: "auto",
            minWidth: "0",
            translate: "yes",
            enableToolbar: true,
            showToolbar: true,
            placeholder: "Enter text here...",
            defaultParagraphSeparator: "",
            defaultFontName: "",
            defaultFontSize: "",
            fonts: [
                { class: "arial", name: "Arial" },
                { class: "times-new-roman", name: "Times New Roman" },
                { class: "calibri", name: "Calibri" },
                { class: "comic-sans-ms", name: "Comic Sans MS" }
            ],
            customClasses: [
                {
                    name: "quote",
                    class: "quote"
                },
                {
                    name: "redText",
                    class: "redText"
                },
                {
                    name: "titleText",
                    class: "titleText",
                    tag: "h1"
                }
            ],
            sanitize: false,
            toolbarPosition: "top",
            toolbarHiddenButtons: [["bold", "italic"], ["fontSize"]]
        };
    }
    ngOnInit() {
        this.friendsService
            .getSendedRequests()
            .subscribe(friendsRequests => {
            const sendedFriendRequests = friendsRequests;
            if (sendedFriendRequests.indexOf(this.data.username) !== -1) {
                this.sended = true;
            }
        });
        this.friendsService.isFriend(this.data.id).subscribe(val => {
            this.isActualFriend = val;
        });
    }
    sendMessage() {
        if (this.content.value !== "") {
            this.messageService
                .sendMessage(this.data.id, this.content.value)
                .subscribe(val => {
                this.data.messages.push(val);
                this.content.setValue("");
            });
        }
    }
    addToFriends() {
        if (this.sended) {
            this.toastrService.error("Wysłałeś już zaproszenie do znajomych do tego użytkownika");
        }
        else if (this.isActualFriend) {
            this.toastrService.error("Użytkownik jest już twoim znajomym");
        }
        else {
            this.friendsService.sendRequest(this.data.id).subscribe(() => {
                this.sended = true;
                this.toastrService.success("Wysłano zaproszenie do znajomych");
            });
        }
    }
};
tslib_1.__decorate([
    ViewChild('scrollMe', { static: true }),
    tslib_1.__metadata("design:type", ElementRef)
], MessageDialogComponent.prototype, "myScrollContainer", void 0);
MessageDialogComponent = tslib_1.__decorate([
    Component({
        selector: "app-message-dialog",
        templateUrl: "./message-dialog.component.html",
        styleUrls: ["./message-dialog.component.scss"]
    }),
    tslib_1.__param(0, Inject(MAT_DIALOG_DATA)),
    tslib_1.__metadata("design:paramtypes", [Object, MatDialogRef,
        MessageService,
        AuthService,
        UserService,
        FriendsService,
        ToastrService])
], MessageDialogComponent);
export { MessageDialogComponent };
//# sourceMappingURL=message-dialog.component.js.map