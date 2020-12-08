import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { Router } from "@angular/router";
import { MessagingService } from "src/app/services/messaging.service";
import { UserService } from "src/app/services/user.service";

@Component({
    selector: "app-login-form",
    templateUrl: "./login-form.component.html",
    styleUrls: ["./login-form.component.sass"],
})
export class LoginFormComponent implements OnInit {
    loginForm = new FormGroup({
        usertype: new FormControl(""),
        username: new FormControl(""),
    });

    isLogin = true;

    constructor(private router: Router, private msgService: MessagingService, private userService: UserService) {}

    ngOnInit(): void {
        this.loginForm.controls["usertype"].setValue("Patient");
    }

    setUserType(type: string): void {
        this.loginForm.controls["usertype"].setValue(type);
    }

    getUserType(): string {
        return this.loginForm.controls["usertype"].value;
    }

    signup(): void {
        const userCreated = this.userService.createUser(this.loginForm.value.username, this.loginForm.value.usertype);

        if (userCreated) {
            this.msgService.signup();
        } else {
            this.msgService.login();
        }
        this.router.navigateByUrl("/");
    }

    login(event): void {
        event.preventDefault();
        this.isLogin = !this.isLogin;
    }
}
