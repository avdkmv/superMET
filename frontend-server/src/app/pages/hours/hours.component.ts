import { Component, OnInit } from "@angular/core"

@Component({
    selector: "app-hours",
    templateUrl: "./hours.component.html",
    styleUrls: ["./hours.component.sass"],
})
export class HoursComponent implements OnInit {

    hours = [
        {
            id: 1,
            time: "13:00",
        },
        {
            id: 2,
            time: "14:00",
        },
        {
            id: 3,
            time: "15:00",
        }
    ]

    constructor() {}

    ngOnInit(): void {}
}
