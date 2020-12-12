import { Component, OnInit } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { ToastrService } from "ngx-toastr";
import { MessagingService } from "src/app/services/messaging.service";
import { UserService } from "src/app/services/user.service";

@Component({
    selector: "app-test",
    templateUrl: "./test.component.html",
    styleUrls: ["./test.component.sass"],
})
export class TestComponent implements OnInit {
    constructor(
        private msgService: MessagingService,
        private toastr: ToastrService,
        private userService: UserService,
        private cookie: CookieService,
    ) {
        this.msgService.loginMessage$.subscribe((isLoggedIn) => {
            if (isLoggedIn) {
                const user = this.userService.getUser(this.cookie.get("AUTH"));
                const msg = "User ".concat(user.username).concat(" logged in");
                this.toastr.success(msg);
            } else this.toastr.error("Error during login");
        });

        this.msgService.signupMessage$.subscribe((isSignup) => {
            if (isSignup) {
                const user = this.userService.getUser(this.cookie.get("AUTH"));
                const msg = "User ".concat(user.username).concat(" created");
                this.toastr.success(msg);
            } else this.toastr.error("Error during login");
        });
    }

    ngOnInit(): void {}
}
