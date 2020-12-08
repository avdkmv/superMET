import { Component, OnInit } from "@angular/core"
import { FormControl, FormGroup } from "@angular/forms"
import { Router } from "@angular/router"
import { MessagingService } from 'src/app/services/messaging.service'

@Component({
    selector: "app-login-form",
    templateUrl: "./login-form.component.html",
    styleUrls: ["./login-form.component.sass"],
})
export class LoginFormComponent implements OnInit {
    loginForm = new FormGroup({
        usertype: new FormControl(""),
        username: new FormControl(""),
    })

    constructor(private router: Router, private msgService: MessagingService) {}

    ngOnInit(): void {
        this.loginForm.controls["usertype"].setValue("Patient")
    }

    setUserType(type: String): void {
        this.loginForm.controls["usertype"].setValue(type)
    }

    getUserType(): String {
        return this.loginForm.controls["usertype"].value
    }

    login(): void {
        console.log(this.loginForm.getRawValue())
        this.msgService.login()
        this.router.navigateByUrl("/")
    }
}
