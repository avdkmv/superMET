import { Component, OnInit } from "@angular/core"

@Component({
    selector: "app-facility",
    templateUrl: "./facility.component.html",
    styleUrls: ["./facility.component.sass"],
})
export class FacilityComponent implements OnInit {
    testdata = [
        {
            id: 1,
            name: "Doctor 1",
            description: "Random descr 1",
        },
        {
            id: 2,
            name: "Doctor 2",
            description: "Random descr 2",
        },
        {
            id: 3,
            name: "Doctor 3",
            description: "Random descr 3",
        },
    ]

    constructor() {}

    ngOnInit(): void {}
}
