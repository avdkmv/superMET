import { Component, OnInit } from "@angular/core"
import { ToastrService } from "ngx-toastr"
import { MessagingService } from "src/app/services/messaging.service"

@Component({
    selector: "app-test",
    templateUrl: "./test.component.html",
    styleUrls: ["./test.component.sass"],
})
export class TestComponent implements OnInit {
    constructor(private msgService: MessagingService, private toastr: ToastrService) {
        this.msgService.loginMessage$.subscribe((isLoggedIn) => {
            if (isLoggedIn) this.toastr.success("Logged in successfuly")
            else this.toastr.error("Error during login")
        })
    }

    ngOnInit(): void {}
}
